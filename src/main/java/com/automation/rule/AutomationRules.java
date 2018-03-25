package com.automation.rule;

import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class AutomationRules implements TestRule
{
    private final RuleChain chain;

    @Inject
    public AutomationRules(ScreenShotRule screenShotRule, RetryTestRule retryTestRule, QuitWebDriverRule quitWebDriverRule)
    {
        RuleChain ruleChain = RuleChain.outerRule(quitWebDriverRule);
        ruleChain = ruleChain
                .around(retryTestRule)
                .around(screenShotRule);

        this.chain = ruleChain;
    }

    @Override
    public Statement apply(Statement base, Description description)
    {
        return chain.apply(base, description);
    }
}
