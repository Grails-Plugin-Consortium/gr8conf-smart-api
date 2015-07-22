package org.grails.demo

//import org.springframework.security.core.Authentication
//import org.springframework.security.core.context.SecurityContext
//import org.springframework.security.core.context.SecurityContextHolder

class LoginController {

//    def activeDirectoryService
//
//    def auth() {
//        render view: '/login/auth'
//    }
//
//    def index() {
//        render view: '/login/auth'
//    }
//
//    def logout() {
//        request.getSession(false)?.setAttribute('user', null)
//        render view: '/login/auth'
//    }
//
//    def login() {
//        String username = params?.username
//        String password = params?.password
//
////        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password)
//
//        Authentication authentication = null
//        String authMessage = ''
//        // Authenticate the user
//        try {
//            authentication = activeDirectoryService.authenticate(username, password, "OCP Site Manager")
//        } catch (Exception e) {
//            authMessage = e?.message
//        }
//        if (authentication?.isAuthenticated()) {
//            SecurityContext securityContext = SecurityContextHolder.context
//            securityContext.setAuthentication(authentication)
//            request.getSession(true).setAttribute('user', username)
//            // Create a new session and add the security context.
//            request?.getSession(true)?.setAttribute("SPRING_SECURITY_CONTEXT", securityContext)
//            redirect controller: 'home'
//        } else {
//            render view: '/login/auth', model: [authMessage: authMessage]
//        }
//
//
//    }
}
