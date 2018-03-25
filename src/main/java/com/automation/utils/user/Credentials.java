package com.automation.utils.user;

import javax.validation.constraints.NotNull;

public class Credentials
{
    @NotNull
    private final String username;

    @NotNull
    private final String password;

    public Credentials(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credentials that = (Credentials) o;
        return password.equals(that.password) && username.equals(that.username);
    }

    @Override
    public int hashCode()
    {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "Credentials{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
    }
}

