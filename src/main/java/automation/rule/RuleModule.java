package automation.rule;

import automation.webdriver.AutomationModule;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.inject.Named;

@Configuration
@Import({AutomationModule.class, AutomationRules.class})
public class RuleModule
{
    @Bean
    public ScreenShotRule provideScreenShotRule(WebDriver driver)
    {
        return new ScreenShotRule(driver);
    }

    @Bean
    public QuitWebDriverRule provideQuitWebDriverRule(WebDriver driver)
    {
        return new QuitWebDriverRule(driver);
    }

    @Bean
    public RetryTestRule provideRetryRule(@Named("noRetry") int count)
    {
        return new RetryTestRule(count);
    }

}
