package com.securingweb.vpn.config.micrometer;


import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * the metric name is "http.server.requests"
 * the tag name is "method", "uri" and so on, which is defined in
 * @class org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags#method(javax.servlet.http.HttpServletRequest)
 * and etc
 */
public class DefaultWebMvcTagsProvider implements WebMvcTagsProvider {

    @Override
    public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 Throwable exception) {
        return Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, response), WebMvcTags.exception(exception),
                WebMvcTags.status(response), WebMvcTags.outcome(response));
    }

    @Override
    public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
        return Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, null));
    }
}
