package automation.supportAndConfig;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

import automation.rule.AutomationRules;

import javax.inject.Inject;
import javax.inject.Named;

import automation.rule.RuleModule;
import automation.support.AppEnvironment;
import automation.support.UrlComponentsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RuleModule.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SupportTest
{
    @Rule
    @Inject
    public AutomationRules rules;

    @Inject
    WebDriver driver;

    @Inject @Named("unified")
    UrlComponentsBuilder builder;

    @Inject @Named("dev")
    UrlComponentsBuilder defaultBuilder;

    @Inject
    AppEnvironment appEnvironment;

    @Test
    public void shouldGetChromeWebDriver_FromPropertiesFile()
    {
        assertThat(driver.toString(), containsString("chrome"));
        driver.get("http://www.google.com");
    }

    @Test
    public void shouldGetEnvironment_FromPropertiesFile()
    {
        String appEnvironmentName = appEnvironment.getName();
        assertThat(appEnvironmentName, is(equalToIgnoringCase("cd03")));
    }

    @Test
    public void shouldGetUrlFromUrlComponentBuilder()
    {
        String path = builder.buildUrl().pathSegment("home").toUriString();
        assertThat(path, is(equalToIgnoringCase("http://cd03.dev.experticity.com/home")));
    }

    @Test
    public void shouldGetUrlFromApplicationsProperties()
    {
        String path = defaultBuilder.buildUrl().pathSegment("home").toUriString();
        System.out.println("path: " + path);
    }

    @Test
    @Ignore("Test Only for debugging")
    public void shouldTakeScreenshotAtFailure()
    {
        driver.get("http://www.google.com");
        driver.findElement(By.cssSelector(".unknown")).click();
    }

}
