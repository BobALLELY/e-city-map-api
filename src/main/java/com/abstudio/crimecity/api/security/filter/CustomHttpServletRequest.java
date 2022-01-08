package com.abstudio.crimecity.api.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpServletRequest "custom" in order to be able to add specific HTTP Headers
 *
 * @author ULAV4A gdelauna
 */
public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    // Header Map
    private Map<String, String> headerMap = null;

    /**
     * Constructor
     *
     * @param request HttpServletRequest
     */
    public CustomHttpServletRequest(HttpServletRequest request) {
        super(request);
        headerMap = new HashMap<>();
    }

    /**
     * Adding a particular Header
     *
     * @param name  Name of the Header to add
     * @param value Value of the new Header
     */
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        // If the Header sought is present in the Map, we return its value from the Map
        return headerMap.containsKey(name) ? headerMap.get(name) : super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // Retrieving the list of Headers
        List<String> names = Collections.list(super.getHeaderNames());

        // Adding Headers from the Map
        headerMap.keySet().stream().forEach(names::add);

        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        // Retrieving the values of the sought Header
        List<String> values = Collections.list(super.getHeaders(name));

        // If the Header sought is present in the Map ...
        if (headerMap.containsKey(name)) {
            // ... So, Adding its value to the list
            values.add(headerMap.get(name));
        }

        return Collections.enumeration(values);
    }
}
