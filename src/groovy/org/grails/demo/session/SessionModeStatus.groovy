package org.grails.demo.session

enum SessionModeStatus {
    NONE("NONE"),
    RECORD("RECORD"),
    REPLAY("REPLAY"),
    MOCK("MOCK")

    String sessionModeStatus

    SessionModeStatus(String sessionModeStatus) {
        this.sessionModeStatus = sessionModeStatus
    }
}
