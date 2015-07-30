<%@ taglib prefix="admin" uri="/WEB-INF/ocp-admin.tld" %>
<!DOCTYPE html>
<head>

    <meta name='layout' content='main'/>
    <title>System Info</title>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/knockout/3.3.0/knockout-min.js"></script>
    <script language="JavaScript">
        angular.module('smartApi', []).controller('SessionModeCtrl', ['$scope', function($scope){
             //todo: write in angular instead
        }]);
    </script>
    <style>
    .session {
        margin-top: 20px;
    }
    </style>
</head>
<body>
<div id="sessionContainer" class="pipeline-container" ng-app="smartApi">
    <span ng-controller="SessionModeCtrl">
    <form class="form-inline" role="form">
        <div class="form-group">
            <input type="text" data-bind="value: sessionId" class="form-control" id="sessionId" style="width: 300px;"
                   placeholder="Session ID">
        </div>
        <button type="button" class="btn btn-primary" data-bind="click: search"><i class="fa fa-search"></i> Search</button>
        <button type="button" class="btn btn-success" data-toggle="modal" data-bind="click: clearNew" data-target="#createModal"><i class="fa fa-plus-square"></i> Create</button>
        <button type="button" class="btn btn-info" data-bind="click: list"><i class="fa fa-list"></i> List All</button>
        %{--<button class="btn btn-info" style="display: none" data-bind="visible: $data.session"><i class="fa fa-thumb-tack"></i> Set As My Session</button>--}%
        <!-- ko template: {name: 'tmpl-message-btn', data: message} --><!-- /ko -->
    </form>
    <!-- ko template: {name: 'tmpl-session', data: session} --><!-- /ko -->
    <!-- ko template: {name: 'tmpl-session-list', data: sessionList} --><!-- /ko -->
    <!-- ko template: {name: 'tmpl-session-confirm-delete'} --><!-- /ko -->
    <!-- ko template: {name: 'tmpl-session-create'} --><!-- /ko -->
    </span>
</div>


<g:render template="/templates/recording" />
<asset:javascript src="recording/page" />
<script language="JavaScript">
    $(function(){
        var page = new Page();
        page.load();
    })
</script>


</body>
</html>
