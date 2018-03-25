package com.automation.webdriver;


import com.automation.configuration.PropertiesModule;
import com.automation.configuration.TestRunnerConfig;
import com.automation.support.AppEnvironment;
import com.automation.support.UrlComponentsBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

@Configuration
@Import({PropertiesModule.class})
public class AutomationModule
{
    private static final Logger LOGGER = LogManager.getLogger(AutomationModule.class);

    @Inject
    public TestRunnerConfig testRunnerConfig;

    @Bean @Named("capabilities")
    public Capabilities getCapabilities()
    {
        String browser = testRunnerConfig.getBrowser();
        LOGGER.info("Loading browser from system property: " + browser);
        return new DesiredCapabilities(browser,"", Platform.ANY);
    }

    @Bean
    public WebDriver provideWebDriver(@Named("capabilities")Capabilities capabilities)throws IOException
    {
        WebDriverFactory factory = new WebDriverFactory(capabilities);
        return factory.createWebDriver();
    }

    @Bean
    public String provideBrowserName(@Named("capabilities")Capabilities capabilities)
    {
        return capabilities.getBrowserName();
    }

    @Bean
    public AppEnvironment provideAppEnvironment()
    {
        String environment = testRunnerConfig.getEnvironment();
        return new AppEnvironment(environment);
    }

    @Bean @Named("unified")
    public UrlComponentsBuilder provideUnifiedComponentsBuilder(AppEnvironment appEnvironment)
    {
        return new UrlComponentsBuilder(appEnvironment, "dev.experticity", false);
    }

    @Bean @Named("dev")
    public UrlComponentsBuilder provideDevComponentsBuilder(AppEnvironment appEnvironment)
    {
        return new UrlComponentsBuilder(appEnvironment, testRunnerConfig.getApplicationPath(), false);
    }

}
