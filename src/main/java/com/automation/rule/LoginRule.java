package com.automation.rule;

import com.automation.login.Login;

import org.junit.rules.ExternalResource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class LoginRule extends ExternalResource
{
    private final Login login;

    @Inject
    public LoginRule(Login login)
    {
        this.login = login;
    }

    @Override
    public void before()
    {
        login.login();
    }
}