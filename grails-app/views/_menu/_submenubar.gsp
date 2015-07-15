<!-- 
This menu is used to show function that can be triggered on the content (an object or list of objects).
-->

<%-- Only show the "Pills" navigation menu if a controller exists (but not for home) --%>
<g:if test="${	params.controller != null &&	params.controller != '' &&	params.controller != 'home'}">
	%{--<ul id="Menu" class="nav nav-pills margin-top-small btn-group">--}%

    <div class="btn-group nav nav-pills margin-top-small " id="Main">
		<g:set var="entityName" value="${message(code: params.controller+'.label', default: params.controller.substring(0,1).toUpperCase() + params.controller.substring(1).toLowerCase())}" />
		
		%{--<li class="${ params.action == "list" ? 'active' : '' }">--}%
			<g:link action="list" class="btn ${ params.action == "list" ? 'btn-primary' : '' }"><i class="fa fa-list"></i> <g:message code="default.list.label" args="[entityName]"/></g:link>
		%{--</li>--}%
		%{--<li class="${ params.action == "create" ? 'active' : '' }">--}%
			<g:link action="create" class="btn ${ params.action == "create" ? 'btn-primary' : '' }"><i class="fa fa-plus-square"></i> <g:message code="default.new.label"  args="[entityName]"/></g:link>
		%{--</li>--}%
		
		<g:if test="${ params.action == 'show' || params.action == 'edit' }">
			<!-- the item is an object (not a list) -->
			%{--<li class="${ params.action == "edit" ? 'active' : '' }">--}%
				<g:link action="edit" id="${params.id}" class="btn ${ params.action == "edit" ? 'btn-primary' : '' }"><i class="fa fa-pencil"></i> <g:message code="default.edit.label"  args="[entityName]"/></g:link>
			%{--</li>--}%
			%{--<li class="">--}%
				<g:render template="/_common/modals/deleteTextLink"/>
			%{--</li>--}%
		</g:if>
		
	%{--</ul>--}%
    </div>
</g:if>
