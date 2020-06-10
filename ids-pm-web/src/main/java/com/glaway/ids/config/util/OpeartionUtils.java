package com.glaway.ids.config.util;


import javax.servlet.http.HttpServletRequest;


/**
 * Collection of general utility methods that do not fit in one of the more specific classes.
 *
 * @author Arjan Tijms
 * @author Bauke Scholtz
 */
public final class OpeartionUtils {

    
    /**
     * URL-encode the given string using UTF-8.
     * 
     *            The string to be URL-encoded using UTF-8.
     * @return The given string, URL-encoded using UTF-8, or <code>null</code> if <code>null</code>
     *         was given.
     * @throws UnsupportedOperationException
     *             When UTF-8 is not supported.
     * @since 1.4
     */
    public static Object getOperationCodes(HttpServletRequest request) {
        Object operationCodes = request.getAttribute("operationCodes");
        return operationCodes;
    }

}