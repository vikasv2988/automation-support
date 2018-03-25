package automation.support;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppEnvironment
{
    private final String name;

    public AppEnvironment(String name)
    {
        this.name = checkNotNull(name, "Unable to get environment from application properties");
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppEnvironment that = (AppEnvironment) o;
        return name.equals(that.name);
    }

    @Override
    public String toString()
    {
        return "AppEnvironment{" +
                "name='" + name + '\'' +
                '}';
    }
}
