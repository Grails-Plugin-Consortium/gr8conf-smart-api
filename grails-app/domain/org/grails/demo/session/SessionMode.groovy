package org.grails.demo.session

class SessionMode {

    String sessionId = UUID.randomUUID().toString()
    SessionModeStatus sessionModeStatus = SessionModeStatus.NONE
    boolean cachingEnabled = false

    static mapping = {
        version true
    }

    static constraints = {
        sessionId(unique: true)
        cachingEnabled nullable: false
    }
}
