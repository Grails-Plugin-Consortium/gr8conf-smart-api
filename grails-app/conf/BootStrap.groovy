import grails.converters.JSON
import org.grails.demo.session.SessionMode
import org.grails.demo.session.SessionModeStatus

class BootStrap {

    def customerBootStrapDataService

    def init = { servletContext ->
        SessionMode.findOrSaveWhere(
                sessionModeStatus: SessionModeStatus.RECORD,
                sessionId: '96d1a465-8538-4de1-9470-92088953194c')

        JSON.registerObjectMarshaller(SessionMode) {
            [
                    id               : it?.id,
                    sessionId        : it?.sessionId,
                    sessionModeStatus: it?.sessionModeStatus,
                    cachingEnabled   : it?.cachingEnabled
            ]
        }

        JSON.registerObjectMarshaller(SessionModeStatus) {
            it.toString()
        }


        customerBootStrapDataService.loadAllData()
    }
    def destroy = {
    }
}
