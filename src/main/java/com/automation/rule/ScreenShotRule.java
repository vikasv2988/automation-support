package com.automation.rule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

@Component
public class ScreenShotRule implements TestRule
{
    private static final Logger LOGGER = LogManager.getLogger(ScreenShotRule.class);
    private final WebDriver driver;

    @Inject
    public ScreenShotRule(WebDriver webDriver)
    {
        this.driver = webDriver;
    }

    @Override
    public Statement apply(final Statement base, final Description description)
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                try
                {
                    base.evaluate();
                }
                catch(Throwable t)
                {
                    captureScreenshot(description.getClassName() + "_" + description.getMethodName());
                    throw t;
                }
            }

            public void captureScreenshot(String fileName)
            {
                try
                {
                    String directory = "target/failsafe-reports/";
                    new File(directory).mkdirs();
                    FileOutputStream out = new FileOutputStream(directory + fileName + ".png");
                    LOGGER.info("Screenshot taken in " + directory + " directory");
                    out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                    out.close();
                }
                catch(Exception e)
                {
                }
            }
        };
    }
}
