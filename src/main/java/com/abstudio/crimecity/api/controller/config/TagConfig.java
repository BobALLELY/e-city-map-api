package com.abstudio.crimecity.api.controller.config;

import com.abstudio.crimecity.api.exception.NotFoundException;
import com.abstudio.crimecity.api.exception.TechnicalException;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order
@Configuration
public class TagConfig {
    @Bean
    public WebMvcTagsContributor webMvcTagsContributor() {
        return new WebMvcTagsContributor() {
            @Override
            public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
                Tags tags = Tags.empty();
                if (request != null &&
                        request.getQueryString() != null &&
                        exception != null
                ) {
                    if (exception instanceof NotFoundException) {
                        tags = tags.and(Tag.of("404.queryString", request.getQueryString()));
                    } else if (exception instanceof TechnicalException) {
                        tags = tags.and(Tag.of("500.queryString", request.getQueryString()));
                    }
                }
                return tags;
            }

            @Override
            public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
                return Tags.empty();
            }
        };
    }
}
