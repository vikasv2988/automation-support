package automation.supportAndConfig;

import automation.login.Login;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class TestAppLogin implements Login
{
    private String user;
    private String passwd;

    @Inject
    public TestAppLogin(String user, String passwd)
    {
        this.user = user;
        this.passwd = passwd;
    }

    @Override
    public void login()
    {
        Assert.assertTrue(user.equalsIgnoreCase("automator"));
    }
}
