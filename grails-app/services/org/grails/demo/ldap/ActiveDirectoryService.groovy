package org.grails.demo.ldap

import grails.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

import javax.naming.AuthenticationException

@Transactional
public class ActiveDirectoryService {

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * @see AuthenticationManager#authenticate(org.springframework.security.core.Authentication) exceptions thrown
     * @param username
     * @param password
     */
    Authentication authenticate(String username, String password, String authGroup) throws RuntimeException, AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))
    }
}