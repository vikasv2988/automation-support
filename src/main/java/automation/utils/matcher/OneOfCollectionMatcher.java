package automation.utils.matcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

public class OneOfCollectionMatcher <T> extends TypeSafeMatcher<T>
{
    Collection<T> valuesToCheck;

    public OneOfCollectionMatcher(Collection<T> valuesToCheck)
    {
        this.valuesToCheck = valuesToCheck;
    }

    @Override
    protected boolean matchesSafely(T item)
    {
        for(T valueToCheck : valuesToCheck)
        {
            if(valueToCheck == item || valueToCheck.equals(item))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description)
    {
        description.appendText("one of " + this.valuesToCheck);
    }

    @Factory
    public static <T> OneOfCollectionMatcher<T> oneOf(Collection<T> valuesToCheck)
    {
        return new OneOfCollectionMatcher<>(valuesToCheck);
    }

    @Factory
    public static <String> OneOfCollectionMatcher<String> oneOfIgnoringCase(Collection<String> valuesToCheck)
    {
        return new OneOfCollectionMatcher<String>(valuesToCheck)
        {

            @Override
            protected boolean matchesSafely(final String item)
            {
                for(String valueToCheck : valuesToCheck)
                {
                    if(valueToCheck == item || valueToCheck.equals(item)
                            || valueToCheck.toString().toLowerCase().equals(item.toString().toLowerCase()))
                    {
                        return true;
                    }
                }
                return false;
            }
        };
    }

}