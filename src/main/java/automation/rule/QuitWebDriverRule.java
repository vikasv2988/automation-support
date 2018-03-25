package automation.rule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class QuitWebDriverRule extends ExternalResource
{
    private static final Logger LOGGER = LogManager.getLogger(QuitWebDriverRule.class);
    private final WebDriver driver;
    private final Thread shutdownHook;

    @Inject
    public QuitWebDriverRule(WebDriver webDriver)
    {
        this.driver = webDriver;

        /* Add a shutdown hook in case of early termination of the JVM. */
        this.shutdownHook = new Thread(()-> shutdownWebDriver(driver));
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    private void shutdownWebDriver(WebDriver webDriver)
    {
        try
        {
            webDriver.quit();
        }
        catch(UnreachableBrowserException e)
        {
            LOGGER.warn(e.getMessage());
        }
    }

    @Override
    protected void after()
    {
        LOGGER.info("Closing driver");
        shutdownWebDriver(driver);
        Runtime.getRuntime().removeShutdownHook(shutdownHook);
    }
}
