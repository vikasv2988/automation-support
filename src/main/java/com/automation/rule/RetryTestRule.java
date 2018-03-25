package com.automation.rule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryTestRule implements TestRule
{
    private static final Logger LOGGER = LogManager.getLogger(RetryTestRule.class);
    private int retryCount;

    public RetryTestRule(int retryCount)
    {
        this.retryCount = retryCount;
    }

    @Override
    public Statement apply(Statement base, Description description)
    {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description)
    {
        if(retryCount <= 1 )
        {
            return base;
        }
        return new Statement() {
            @Override
            public void evaluate() throws Throwable
            {
                Throwable caughtThrowable = null;
                for (int i = 0; i < retryCount; i++)
                {
                    try {
                        base.evaluate();
                        LOGGER.debug(description.getDisplayName() + ": run " + (i+1) + " passed");
                        return;
                    } catch (Throwable t)
                    {
                        caughtThrowable = t;
                        LOGGER.warn(description.getDisplayName() + ": run " + (i+1) + " of " + retryCount + " failed... " + t.getMessage(),t);
                    }
                }
                LOGGER.error(description.getDisplayName() + ": giving up after " + retryCount + " failures");
                throw caughtThrowable;
            }
        };
    }
}

