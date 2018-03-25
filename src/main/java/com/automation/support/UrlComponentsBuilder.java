package com.automation.support;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * A {@link UriComponentsBuilder} which is specifically an implementation using composition for flexible execution.
 * The environment and root domain must be provided separately.
 */
public class UrlComponentsBuilder
{
    private final String rootDomain;
    private final String environmentPrefix;
    private final boolean secure;

    /**
     * Through the constructor we can save and manipulate the environments, app domain and if we need secure scheme.
     * @param environment the testing environment for the app to test.
     * @param rootDomain app domain, experticity, admin, marketer, etc.
     * @param secure if schema needs to be http or https
     */
    public UrlComponentsBuilder(AppEnvironment environment, String rootDomain, boolean secure)
    {
        this.environmentPrefix = environment.getName();
        this.rootDomain = rootDomain;
        this.secure = secure;
    }

    private String getHostUrl()
    {
        return environmentPrefix + "." + rootDomain + ".com";
    }

    private String getScheme(boolean secure)
    {
        return "http" + (secure ? "s" : "");
    }

    /**
     * After setting the host, the UrlComponentsBuilder is ready to implement any other methods.
     * @return a new UrlComponentsBuilder that can use all the Spring UriComponentsBuilder methods for url manipulation
     */
    public UriComponentsBuilder buildUrl()
    {
        return UriComponentsBuilder.newInstance().scheme(getScheme(secure)).host(getHostUrl());
    }
}

