function Page() {

    // assign context.
    var page = this, sessionClient = new SessionClient(), sessionMessage = new MessageModel();

    function render() {
        var ViewModel = function () {

            var REPLAY = 'REPLAY';
            var RECORD = 'RECORD';
            var NONE = 'NONE';
            var MOCK = 'MOCK';

            var self = this;
            self.message = sessionMessage;
            self.sessionId = ko.observable("");
            self.dataFound = ko.observable(false);
            self.listFound = ko.observable(false);
            self.session = ko.observable(null);
            self.sessionList = ko.observableArray([]);
            self.newSessionId = ko.observable(null);
            self.deleteSessionId = ko.observable(null);
            self.isRecording = ko.computed(function () {
                return self.session() && self.session().sessionModeStatus === RECORD;
            });
            self.isReplaying = ko.computed(function () {
                return self.session() && self.session().sessionModeStatus === REPLAY;
            });

            self.clearNew = function () {
                self.newSessionId('');
            };

            self.confirmDeleteSession = function () {
                try {
                    sessionClient.deleteSession(self.deleteSessionId()).success(self.deleteSuccess).fail(self.error);
                } catch (e) {
                    self.error(e);
                }
            };

            self.newRandom = function () {
                self.newSessionId('xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                    var r = Math.random() * 16 | 0, v = c == 'x' ? r : r & 0x3 | 0x8;
                    return v.toString(16);
                }));
            };

            self.createNew = function () {
                try {
                    sessionClient.createSession(self.newSessionId()).done(self.createSuccess).fail(self.error);
                } catch (e) {
                    self.error(e);
                }
            };

            self.list = function () {
                try {
                    sessionClient.list().done(self.bindList).fail(self.error);
                } catch (e) {
                    self.error(e);
                }
            };

            self.search = function () {
                try {
                    sessionClient.search(self.sessionId()).done(self.bindData).fail(self.error);
                } catch (e) {
                    self.error(e);
                }
            };

            self.stopCaching = function (sessionId) {
                sessionClient.update(sessionId, {'cachingEnabled': false}).done(self.bindData).fail(self.error);
            };
            self.startCaching = function (sessionId) {
                sessionClient.update(sessionId, {'cachingEnabled': true}).done(self.bindData).fail(self.error);
            };
            self.stopMocking = function (sessionId) {
                setMode(sessionId, NONE);
            };
            self.startMocking = function (sessionId) {
                setMode(sessionId, MOCK);
            };
            self.stopRecording = function (sessionId) {
                setMode(sessionId, NONE);
            };
            self.startRecording = function (sessionId) {
                setMode(sessionId, RECORD);
            };
            self.stopReplaying = function (sessionId) {
                setMode(sessionId, NONE);
            };
            self.startReplaying = function (sessionId) {
                setMode(sessionId, REPLAY);
            };
            self.resetReplay = function (sessionId) {
                sessionClient.resetReplayCounter(sessionId).done(self.bindData).fail(self.error);
            };


            function setMode(sessionId, mode) {
                try {
                    sessionClient.update(sessionId, {'sessionModeStatus': mode}).done(self.bindData).fail(self.error);
                } catch (e) {
                    self.error(e);
                }
            }

            self.createSuccess = function (data) {
                console.log(data);
                sessionMessage.success('Created', 'Successfully created "' + self.sessionId() + '"');
                self.bindData(data);
                self.dataFound(true);
                self.sessionId(self.newSessionId());
            };

            self.deleteSuccess = function () {
                sessionMessage.success('Deleted', 'Successfully deleted "' + self.deleteSessionId() + '"');
                self.session("");
                self.deleteSessionId("");
                self.dataFound(false);
                self.list();
            };

            self.bindList = function (data) {
                resetSessions();
                if (!data || data.length === 0) {
                    sessionMessage.info('No sessions found', 'No sessions found.');
                } else {
                    try {
                        self.sessionList(data);
                        self.listFound(true);
                        sessionMessage.dismiss();
                    } catch (e) {
                        self.dataFound(false);
                        sessionMessage.error('Error', 'Could not parse json ' + e);
                    }
                }
            };

            function resetSessions() {
                self.dataFound(false);
                self.listFound(false);
                self.sessionList([]);
                self.session("");
            }

            self.bindData = function (data) {
                resetSessions();
                if (!data || data === '') {
                    sessionMessage.info('Not Found', 'No session found matching "' + self.sessionId() + '"');
                } else {
                    try {
                        self.session(data);
                        self.dataFound(true);
                        sessionMessage.dismiss();
                    } catch (e) {
                        self.dataFound(false);
                        sessionMessage.error('Error', 'Could not parse json ' + e);
                    }
                }
            };

            self.error = function (data) {
                self.dataFound(false);
                var message = data && data.statusText ? data.statusText : (data ? data : 'Unknown Error');
                sessionMessage.error('Error', message);
            };

            self.print = function () {
                window.print();
            };

            return self;
        };
        ko.applyBindings(new ViewModel(), $('#sessionContainer')[0]);
    }

    // API
    page.load = function () {
        render();
    };
}

function SessionClient() {
    // Assign context.
    var client = this;
    client.host = 'http://localhost:8282/grails-smart-api';

    /**
     * Get a group list.
     */
    client.list = function () {
        return $.ajax({
            url: client.host + '/api/sessions',
            contentType: "application/json",
            method: 'GET',
            dataType: 'json',
            timeout: 15000
        });
    };

    /**
     * Get a group list.
     */
    client.search = function (sessionId) {
        if (!sessionId) {
            throw 'Session ID is required.';
        }
        return $.ajax({
            url: client.host + '/api/sessions/' + sessionId,
            contentType: "application/json",
            dataType: 'json',
            timeout: 15000
        });
    };

    /**
     * Remove categories from a group list.
     *
     * @param sessionId - session id.
     */
    client.deleteSession = function (sessionId) {
        if (!sessionId) {
            throw 'Session ID is required.';
        }
        return $.ajax({
            url: client.host + '/api/sessions/' + sessionId,
            type: 'DELETE',
            traditional: true
        })
    };

    client.update = function (sessionId, data) {
        if (!sessionId) {
            throw 'Session ID is required.';
        }
        return $.ajax({
            url: client.host + '/api/sessions/' + sessionId,
            data: JSON.stringify(data),
            type: "PUT",
            contentType: "application/json",
            dataType: 'json'
        });
    };

    client.resetReplayCounter = function (sessionId) {
        if (!sessionId) {
            throw 'Session ID is required.';
        }
        return $.ajax({
            url: client.host + '/api/sessions/' + sessionId + '/reset',
            type: "GET",
            contentType: "application/json",
            dataType: 'json'
        });
    };

    client.createSession = function (sessionId) {
        if (!sessionId) {
            throw 'Session ID is required.';
        }
        return $.ajax({
            url: client.host + '/api/sessions',
            data: {'sessionId': sessionId},
            contentType: "application/json",
            type: 'POST',
            dataType: 'json'
        });
    }
}

function MessageModel() {
    var message = this;

    var type = {
        success: {
            icon: '&#xe005;',
            cssClass: 'confirmational'
        },
        error: {
            icon: '&#xe004;',
            cssClass: 'error'
        },
        info: {
            icon: '&#xe007;',
            cssClass: 'informational'
        }
    };

    function setState(title, newMessages, type) {
        message.title(title || '');
        messages.removeAll();
        if (newMessages && isArray(newMessages)) {
            for (var i = 0, len = newMessages.length; i < len; i++) {
                messages.push(newMessages[i]);
            }
        } else if (newMessages) {
            messages.push(newMessages);
        }
        message.type(type);
    }

    function isArray(object) {
        return object.constructor === Array;
    }

    message.title = ko.observable('');

    var messages = message.messages = ko.observableArray();

    message.type = ko.observable(type.success);

    message.show = ko.computed(function () {
        return message.title().length > 0 || messages().length > 0;
    });

    /**
     * Returns a callback that will show a REST error when called.
     *
     * Example usage: $.ajax({...}).error(restError('Something bad happened!'));
     *
     * @param title The error title.
     */
    message.restError = function (title) {
        return function (jqXHR) {
            message.showRestError(title, jqXHR);
        };
    };

    /**
     * Display REST endpoint error messages. Expects common JSON response body for rest error. If unable to parse response body, defaults to show
     * HTTP status code and text.
     */
    message.showRestError = function (title, jqXHR) {
        try {
            message.error(title, JSON.parse(jqXHR.responseText).messages);
        } catch (e) {
            var statusCode = jqXHR.statusCode();
            message.error(title, [statusCode.status + ' ' + statusCode.statusText]);
        }

        return message;
    };

    message.getRestError = function (title, jqXHR) {
        var messageString = [];
        message.showRestError(title, jqXHR);
        _.each(message.messages(), function (o) {
            messageString.push(ko.isObservable(o) ? o() : o);
        });

        return messageString.join();
    };

    /**
     * Displays an error.
     *
     * @param title The error title.
     * @param errorMessages The error messages to display.
     */
    message.error = function (title, errorMessages) {
        setState(title, errorMessages, type.error);
    };

    /**
     * Display a success message.
     *
     * @param title The success message title.
     * @param successMessages Optional. The success messages to display.
     */
    message.success = function (title, successMessages) {
        setState(title, successMessages, type.success);
    };

    /**
     * Display a success message.
     *
     * @param title The success message title.
     * @param infoMessages Optional. The success messages to display.
     */
    message.info = function (title, infoMessages) {
        setState(title, infoMessages, type.info);
    };

    /**
     * Dismiss this message.
     */
    message.dismiss = function () {
        message.title('');
        messages.removeAll();
    };
}

ko.virtualElements.allowedBindings.log = true;
ko.bindingHandlers.log = {
    init: function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        var value = ko.utils.unwrapObservable(valueAccessor());
        console.log(value);
    }
};
