package com.automation.rule;

import com.automation.supportAndConfig.TestAppLogin;
import com.automation.webdriver.AutomationModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AutomationModule.class)
public class RuleTestModule
{
    @Bean
    public TestAppLogin provideTestAppLogin()
    {
        return new TestAppLogin("automator", "test1");
    }

    @Bean
    public LoginRule provideLoginRule(TestAppLogin login)
    {
        return new LoginRule(login);
    }

}
