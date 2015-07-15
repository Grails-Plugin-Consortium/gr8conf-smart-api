package api.v1.session

import grails.converters.JSON
import grails.rest.RestfulController
import org.grails.demo.session.SessionMode
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.NO_CONTENT

//todo: use base methods where applicable and add transactional support
class SessionModeController extends RestfulController<SessionMode> {

    static allowedMethods = [index: "GET", show: "GET", reset: "GET", save: "POST", update: "PUT", patch: "PATCH", delete: "DELETE"]

    def sessionModeService

    static responseFormats = ['json']

    SessionModeController() {
        super(SessionMode, false)
    }

    def manage() {

    }

    def index() {
        def object = SessionMode.list(params)

        renderResponse(object)
    }

    def create() {
        save()
    }

    def update() {
        if (handleReadOnly()) {
            return
        }

        def instance
        if (params?.id?.isNumber()) {
            instance = SessionMode.get(params.id)
        } else {
            instance = SessionMode.findBySessionId(params.id)
        }

        if (instance == null) {
            notFound()
            return
        }

        instance.sessionModeStatus = request.JSON?.sessionModeStatus ?: instance.sessionModeStatus
        instance.cachingEnabled = request.JSON?.cachingEnabled != null ? request.JSON?.cachingEnabled : instance.cachingEnabled

        if (instance.hasErrors()) {
            response.status = HttpStatus.BAD_REQUEST.value()
            renderResponse(instance)
            return
        }

        instance.save flush: true

        renderResponse(instance)
    }

    def save() {
        if (handleReadOnly()) {
            return
        }
        def instance = SessionMode.newInstance()
        bindData instance, getObjectToBind()

        instance.validate()
        if (instance.hasErrors()) {
            response.status = HttpStatus.BAD_REQUEST.value()
            if (params?.callback) {
                render "${params.callback}(${(instance.errors as JSON)})"
            } else {
                render instance.errors as JSON
            }
            return
        }

        instance.save flush: true

        renderResponse(instance)
    }

    def show() {
        def object
        if (params?.id?.isNumber()) {
            object = SessionMode.get(params.id)
        } else {
            object = SessionMode.findBySessionId(params.id)
        }
        renderResponse(object)
    }

    def reset() {
        def object
        if (params?.id?.isNumber()) {
            object = SessionMode.get(params.id)
        } else {
            object = SessionMode.findBySessionId(params.id)
        }
        if (object?.sessionId) {
            sessionModeService.resetSequenceNumbers(object.sessionId)
        }
        renderResponse(object)
    }

    def delete() {
        def object
        if (params?.id?.isNumber()) {
            object = SessionMode.get(params.id)
        } else {
            object = SessionMode.findBySessionId(params.id)
        }
        object.delete(flush: true)
        render status: NO_CONTENT
    }

    public void renderResponse(object) {
        def keys = []
        def data = []
        if (object instanceof SessionMode) {
            keys = sessionModeService.getSequenceNumbers(object.sessionId)
            data = [session: object, keys: keys]
        } else if (object instanceof List) {
            object.each {
                keys = sessionModeService.getSequenceNumbers(it.sessionId)
                data << [session: it, keys: keys]
            }
        }

        if (params?.callback) {
            render "${params.callback}(${(data as JSON)})"
        } else {
            render data as JSON
        }
    }
}
