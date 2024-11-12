package com.redhat.servlets;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.eclipse.microprofile.config.ConfigProvider;

public class GenericServlet extends HttpServlet
  {
    private static final long serialVersionUID = 1L;

    /**
     * Lookup config values from application.properties.
     * 
     * @param key String key value
     * @return resolved value or empty String
     */
    protected static String getConfig(String key)
      {
        String value = "";

        try
          {
            if((key != null) && (key.trim().length() > 0))
              value = ConfigProvider.getConfig().getValue(key, String.class);
          }
        catch(NoSuchElementException ex)
          {
          }

        return(value);
      }
    
    /**
     * Get remote client IP address.  This will also check for forwarded IP headers if set.
     * 
     * @param request HttpServletRequest object
     * @return The resolved IP String.
     */
    protected static String getClientIpAddr(HttpServletRequest request)
      {
        Enumeration<String> headerNames = request.getHeaderNames();
        String headerName = null;
        String ip = null;
        boolean forwarded = false;
        
        while(headerNames.hasMoreElements())
          {
            headerName = headerNames.nextElement();
            if("X-Forwarded-For".equals(headerName) || "Proxy-Client-IP".equals(headerName) || "WL-Proxy-Client-IP".equals(headerName) || 
               "HTTP_CLIENT_IP".equals(headerName) || "HTTP_X_FORWARDED_FOR".equals(headerName))
              {
                ip = request.getHeader(headerName);
                if(ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip))
                  {
                    forwarded = true;
                    break;
                  }
              }
          }
        
        if(!forwarded)
          ip = request.getRemoteAddr();

        return(ip);
      }
  }
