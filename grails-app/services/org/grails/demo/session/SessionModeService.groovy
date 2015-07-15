package org.grails.demo.session

import grails.plugin.redis.RedisService
import grails.transaction.Transactional
import redis.clients.jedis.Jedis

@Transactional
class SessionModeService {

    RedisService redisService

    void recordResponse(String cacheKey, String response) {
        redisService.rpush cacheKey, response
    }

    String replayResponse(String cacheKey) {
        Integer index = getOrSetSequenceNumber(cacheKey)
        incrementSequenceNumber(cacheKey)
        redisService.lindex(cacheKey, index)
    }

    void incrementSequenceNumber(String cacheKey) {
        def key = "$cacheKey:sequence"
        String sequence = redisService.get(key)
        if (sequence) {
            redisService.set(key, "${Integer.parseInt(sequence) + 1}".toString())
        }
    }

    Integer getOrSetSequenceNumber(String cacheKey) {
        def key = "$cacheKey:sequence"
        String sequence = redisService.get(key)
        if (!sequence) {
            redisService.set(key, "0")
            sequence = "0"
        }
        Integer.parseInt(sequence)
    }

    void resetSequenceNumbers(String sessionId) {
        if (sessionId.length() > 10) {
            redisService.keys("$sessionId*sequence").each { key ->
                redisService.set(key, "0")
            }
        }
    }

    List getSequenceNumbers(String sessionId) {
        List keys = []
        if (sessionId.length() > 10) {
            redisService.withRedis { Jedis jedis ->
                jedis.keys("$sessionId*sequence").each { key ->
                    keys << [key: key, value: jedis.get(key)]
                }
            }
        }
        keys
    }
}
