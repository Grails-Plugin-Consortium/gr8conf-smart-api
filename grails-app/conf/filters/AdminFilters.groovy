package filters

class AdminFilters {

    def filters = {
        authentication(controller: '*', action: "*") {
            before = {
                //turn off authentication
               session.user = "GR8"

                if (controllerName != 'home' && controllerName != 'login' && !session.user && !request.getRequestURI().contains('assets')) {
                    log.debug "session.userId not found"
                    if (request.xhr) { // If request is AJAX, send 401 (Unauthorized) Error
                        response.sendError(response.SC_UNAUTHORIZED)
                    } else {
                        flash.message = "You have been logged out."
                        redirect(controller: "login", action: 'auth')
                    }
                    return false
                }
            }
        }
    }
}