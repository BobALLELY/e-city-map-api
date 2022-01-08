package com.abstudio.crimecity.api.service.metric;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class RestMetricService {

    /**
     * Increment status request without endpoint
     *
     * @param status http status
     */
    public void increaseStatusRequest(int status) {
        // log metric
        RestMetricService.log.info(this.formatLog(Integer.toString(status), null));
    }

    /**
     * Increment status request
     *
     * @param status   http status
     * @param endpoint rest endpoint
     */
    public void increaseStatusRequest(int status, String endpoint) {
        // log metric
        RestMetricService.log.info(this.formatLog(Integer.toString(status), endpoint));
    }

    /**
     * Increment status request
     *
     * @param status   http status
     * @param endpoint rest endpoint
     */
    public void increaseStatusRequest(int status, String endpoint, Map<String, String> parameters) {
        // log metric
        RestMetricService.log.info(this.formatLog(Integer.toString(status), endpoint));
    }

    /**
     * Increment incoming request
     *
     * @param endpoint rest endpoint
     */
    public void increaseIncomingRequest(String endpoint) {
        RestMetricService.log.info(this.formatLog("IN", endpoint));
    }

    /**
     * Log formater
     *
     * @param status http status
     * @param s
     * @return
     */
    private String formatLog(String status, String s) {
        if (null == s) {
            return new StringBuilder()
                    .append("[Metric][HTTP:").append(status).append("]")
                    .toString();
        }

        return new StringBuilder()
                .append("[Metric][HTTP:").append(status).append("][")
                .append(s)
                .append("]")
                .toString();
    }
}
