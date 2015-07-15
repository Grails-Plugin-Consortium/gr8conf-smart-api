<!-- Templates -->

<script id="tmpl-session-list" type="text/html">
<!-- ko template: {name: 'tmpl-session', foreach: $data} --><!-- /ko -->
</script>

<script id="tmpl-kvp" type="text/html">
    <div class="row">
        <div class="col-xs-10 strong" data-bind="text: $data.key"></div>
        <div class="col-xs-2" data-bind="text: $data.value"></div>
    </div>
</script>

<script id="tmpl-session" type="text/html">
<div class="well session" data-bind="visible: $data">
    <!-- ko if: $data && $data.session -->
    <div class="row padding-sm">
        <div class="col-xs-12 col-sm-6">
            <div class="row">
                <div class="col-xs-3 heavy">ID:</div>

                <div class="col-xs-9" data-bind="text: $data.session.id"></div>
            </div>

            <div class="row">
                <div class="col-xs-3 heavy">Session ID:</div>

                <div class="col-xs-9" data-bind="text: $data.session.sessionId"></div>
            </div>

            <div class="row">
                <div class="col-xs-3 heavy">Mode:</div>

                <div class="col-xs-9" data-bind="text: $data.session.sessionModeStatus"></div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-6">
            <div class="heavy">Keys</div>
            <!-- ko template: {name: 'tmpl-kvp', foreach: keys} --><!-- /ko -->
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">
            <button class="btn btn-danger" data-bind='click: function(){$root.stopRecording($data.session.id)}, visible: $data.session.sessionModeStatus == "RECORD"'><i
                    class="fa fa-stop"></i> Stop Recording</button>
            <button class="btn btn-default" data-bind='click: function(){$root.startRecording($data.session.id)}, visible: $data.session.sessionModeStatus == "NONE"'><i
                    class="fa fa-circle"></i> Start Recording</button>
            <button class="btn btn-success" data-bind='click: function(){$root.stopReplaying($data.session.id)}, visible: $data.session.sessionModeStatus == "REPLAY"'><i
                    class="fa fa-stop"></i> Stop Replaying</button>
            <button class="btn btn-default" data-bind='click: function(){$root.startReplaying($data.session.id)}, visible: $data.session.sessionModeStatus == "NONE"'><i
                    class="fa fa-circle"></i> Start Replaying</button>
            <button class="btn btn-info" data-bind='click: function(){$root.stopMocking($data.session.id)}, visible: $data.session.sessionModeStatus == "MOCK"'><i
                    class="fa fa-stop"></i> Stop Mock</button>
            <button class="btn btn-default" data-bind='click: function(){$root.startMocking($data.session.id)}, visible: $data.session.sessionModeStatus == "NONE"'><i
                    class="fa fa-circle"></i> Start Mock</button>
            <button class="btn btn-contrast" data-bind='click: function(){$root.stopCaching($data.session.id)}, visible: $data.session.cachingEnabled'><i
                    class="fa fa-stop"></i> Stop Caching</button>
            <button class="btn btn-default" data-bind='click: function(){$root.startCaching($data.session.id)}, visible: !$data.session.cachingEnabled'><i
                    class="fa fa-database"></i> Start Caching</button>
            <button class="btn btn-success" data-bind='click: function(){$root.resetReplay($data.session.id)}'><i
                    class="fa fa-recycle"></i> Reset Replay Counter</button>
            <button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" data-bind='click: function(){$root.deleteSessionId($data.session.id)}'><i
                    class="fa fa-trash-o"></i> Delete</button>

        </div>

    </div>
    <!-- /ko -->
</div>
</script>
<script id="tmpl-session-confirm-delete" type="text/html">
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModal"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Confirm Delete</h4>
            </div>

            <div class="modal-body">
                Are you sure you want to delete this recording? This action CAN NOT be undone!
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" data-bind="click: confirmDeleteSession" data-dismiss="modal"
                        class="btn btn-danger">Confirm Delete
                </button>
            </div>
        </div>
    </div>
</div>
</script>
<script id="tmpl-session-create" type="text/html">
<div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="deleteModal"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="createlLabel">Create Session</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <input type="text" data-bind="value: newSessionId" class="form-control"
                           style="width: 300px;"
                           placeholder="Session ID">
                </div>
                <button class="btn" data-bind="click: newRandom"><i class="fa fa-random"></i> New Random</button>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" data-bind="click: createNew" data-dismiss="modal"
                        class="btn btn-success">Create
                </button>
            </div>
        </div>
    </div>
</div>
</script>

<script id="tmpl-message-global" type="text/html">
<!-- ko if: show -->
<div class="message-global-container">
    <div class="message-global">
        <!-- ko template: {name: 'tmpl-message'} --><!-- /ko -->
    </div>
</div>
<!-- /ko -->
</script>

<script id="tmpl-message" type="text/html">
<!-- ko if: show -->
<div class="user-feedback">
    <div class="alert" data-bind="css: type().cssClass">
        <span class="icon-font icon alert-informational-icon" data-bind="html: type().icon"></span>

        <h3 class="alert-title message-title" data-bind="text: title"></h3>

        <div class="message-details">
            <!-- ko foreach: messages -->
            <p class="message-detail" data-bind="text: $data"></p>
            <!-- /ko -->
        </div>

        <div class="message-dismiss">
            <a href="#" data-bind="click: dismiss">Dismiss</a>
        </div>
    </div>
</div>
<!-- /ko -->
</script>

<script id="tmpl-message-btn" type="text/html">
<!-- ko if: show -->
<button class="btn user-feedback" data-bind="css: type().cssClass">
    <span class="fa fa-info" data-bind="html: type().icon"></span>
    <span data-bind="text: title"></span>
    <span class="message-details">
        <!-- ko foreach: messages -->
        <span class="message-detail" data-bind="text: $data"></span>
        <!-- /ko -->
    </span>
    <span class="message-dismiss">
        <a href="#" data-bind="click: dismiss">Dismiss</a>
    </span>
</button>
<!-- /ko -->
</script>
