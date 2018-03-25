package com.automation.configuration;

public class TestRunnerConfig
{
    private String browser;
    private String environment;
    private String applicationPath;

    public TestRunnerConfig(){}

    public TestRunnerConfig(Builder builder)
    {
        this.browser = builder.browser;
        this.environment = builder.environment;
        this.applicationPath = builder.applicationPath;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getEnvironment()
    {
        return environment;
    }

    public void setEnvironment(String environment)
    {
        this.environment = environment;
    }

    public String getApplicationPath()
    {
        return applicationPath;
    }

    public void setApplicationPath(String applicationPath)
    {
        this.applicationPath = applicationPath;
    }

    @Override
    public String toString()
    {
        return "TestRunnerConfig{" + "browser='" + browser + '\'' + ", environment='" + environment + '\'' + ", applicationPath='" + applicationPath + '\'' + '}';
    }

    public static final class Builder
    {
        private String browser;
        private String environment;
        private String applicationPath;

        public Builder()
        {
        }

        public static Builder newBuilder()
        {
            return new Builder();
        }

        public Builder withBrowser(String browser)
        {
            this.browser = browser;
            return this;
        }

        public Builder withEnvironment(String environment)
        {
            this.environment = environment;
            return this;
        }

        public Builder withApplicationPath(String path)
        {
            this.applicationPath = path;
            return this;
        }

        public TestRunnerConfig build()
        {
            return new TestRunnerConfig(this);
        }
    }

}
