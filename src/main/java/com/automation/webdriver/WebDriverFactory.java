package com.automation.webdriver;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Map;

public class WebDriverFactory
{
    private static final Logger LOGGER = LogManager.getLogger(WebDriverFactory.class);
    private static final Map<String, Browser> BROWSERS_MAP = ImmutableMap.<String, Browser>builder()
            .put("chrome", Browser.CHROME)
            .put("headless", Browser.CHROME_HEADLESS)
            .put("firefox", Browser.FIREFOX)
            .build();

    private  final Capabilities capabilities;
    private  final Browser browser;

    public WebDriverFactory(Capabilities capabilities)
    {
        checkNotNull(capabilities);
        this.capabilities = capabilities;

        String browserName = capabilities.getBrowserName().toLowerCase();
        checkArgument(BROWSERS_MAP.containsKey(browserName), "Unknown browser name");
        this.browser = BROWSERS_MAP.get(browserName);
    }

    public WebDriver createWebDriver()throws IOException
    {
        return this.browser.createDriver();
    }

}

