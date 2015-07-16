package org.grails.demo

import com.google.gson.Gson
import grails.plugin.jesque.JesqueService
import grails.plugin.redis.RedisService
import grails.util.Holders
import org.codehaus.groovy.grails.web.util.WebUtils
import org.grails.demo.marshaller.ResponseMarshallerService
import org.grails.demo.session.SessionMode
import org.grails.demo.session.SessionModeService
import org.grails.demo.session.SessionModeStatus
import org.grails.demo.soap.customer.CustomerService

import javax.xml.bind.JAXBElement

abstract class BaseCacheService {
    JesqueService jesqueService
    RedisService redisService
    SessionModeService sessionModeService
    ResponseMarshallerService responseMarshallerService

    protected static final Gson gson = new Gson()

    protected static String getCacheKey(String prefix, Object customerId = null, List<String> params = []) {
        "$prefix:${customerId?.toString()}${params?.findAll{ it } ? ':' + params.findAll{ it }.join(':') : ''}".toString()
    }

    protected void clearCachedObject(String cacheKey) {
        redisService.deleteKeysWithPattern(cacheKey)
    }

    protected void cacheObjectInBackground(String cacheKey, Object object, Integer expireInSeconds, Boolean spawnThread = true) {
        if (spawnThread) {
            Thread.start(cacheKey) {
                redisService.setex cacheKey.toString(), expireInSeconds, gson.toJson(object)
            }
        } else {
            redisService.setex cacheKey.toString(), expireInSeconds, gson.toJson(object)
        }
    }

    def getDomainClass(domainClass, Map params) {
        def response = null

        if (params) {
            response = domainClass.findWhere(params)
        }

        response = response ?: domainClass.findByIsDefault(true)
        response = response ?: domainClass.first()
        response ?: domainClass.newInstance()
    }

    protected static boolean isRecordEnabled() {
        return SessionMode.findBySessionId(sessionId)?.sessionModeStatus == SessionModeStatus.RECORD
    }

    protected static boolean isReplayEnabled() {
        return SessionMode.findBySessionId(sessionId)?.sessionModeStatus == SessionModeStatus.REPLAY
    }

    protected static boolean isCachingEnabled() {
        return SessionMode.findBySessionId(sessionId)?.cachingEnabled
    }

    protected static boolean isMockEnabled() {
        return SessionMode.findBySessionId(sessionId)?.sessionModeStatus == SessionModeStatus.MOCK
    }

    protected <T> T getResponse(T response, JAXBElement jaxbElement) {
        if (recordEnabled && !replayEnabled) {
            sessionModeService.recordResponse("$sessionId:${response?.class?.simpleName}", responseMarshallerService.marshal(response, jaxbElement))
        }

        response
    }

    protected <T> T replayResponse(T type) {
        String cacheKey = type?.simpleName
        def response = type?.newInstance()
        if (cacheKey && response) {
            String object = sessionModeService.replayResponse("$sessionId:$cacheKey")
            if (object) {
                response = responseMarshallerService.unmarshal(object, type)
            }
        }
        (T) response
    }

    protected static String getSessionId() {
        WebUtils.retrieveGrailsWebRequest().request?.getHeader('sessionId')
    }

    /*
    This is done because the cxf client plugin and cxf are fighting for some reason when these are defined
    in the global scope for autowiring.
     */

    protected static CustomerService getCustomerServiceClient() {
        Holders?.grailsApplication?.mainContext?.getBean('customerServiceClient')
    }
}
