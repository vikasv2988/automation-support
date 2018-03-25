package com.automation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.inject.Named;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesModule
{
    @Value("${browser}")
    private String browser;

    @Value("${environment}")
    private String environment;

    @Value("${applicationPath}")
    private String applicationPath;

    @Value("${noRetry}")
    private int noRetry;

    @Bean
    public TestRunnerConfig provideTestRunnerConfig()
    {
        return  new TestRunnerConfig.Builder()
                .withBrowser(browser)
                .withEnvironment(environment)
                .withApplicationPath(applicationPath)
                .build();
    }

    @Bean @Named("noRetry")
    public int provideNoRetry()
    {
        return noRetry;
    }
}
