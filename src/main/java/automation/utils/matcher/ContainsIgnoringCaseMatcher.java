package automation.utils.matcher;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.List;

public class ContainsIgnoringCaseMatcher extends TypeSafeMatcher<String>
{
    private List<String> segmentsToMatch;

    public ContainsIgnoringCaseMatcher(String subString, String... additional)
    {
        Preconditions.checkArgument(subString != null && !subString.isEmpty());
        segmentsToMatch = Lists.newArrayList(subString);

        if(additional != null)
        {
            segmentsToMatch.addAll(Arrays.asList(additional));
        }
    }

    @Override
    protected boolean matchesSafely(String fullText)
    {
        return segmentsToMatch.stream()
                .allMatch(v -> StringUtils.containsIgnoreCase(fullText, v));

    }

    @Override
    public void describeTo(Description description)
    {
        description.appendText(String.format("containing each %s", segmentsToMatch));
    }

    @Factory
    public static Matcher<String> containsIgnoringCase(String subString, String... additionalStrings)
    {
        return new ContainsIgnoringCaseMatcher(subString, additionalStrings);
    }

    @Factory
    public static Matcher<String> containsIgnoringCase(List<String> subStrings)
    {
        String first = subStrings.get(0);

        if(subStrings.size() > 1)
        {
            List<String> subList = subStrings.subList(1, subStrings.size());
            String additional[] = subList.toArray(new String[subList.size() - 1]);

            return new ContainsIgnoringCaseMatcher(first, additional);
        }
        else
        {
            return new ContainsIgnoringCaseMatcher(first);
        }
    }
}

