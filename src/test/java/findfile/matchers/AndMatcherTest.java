package findfile.matchers;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AndMatcherTest {

    @Test
    void test_constructor_NullInput_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> new AndMatcher(null));
    }

    @Test
    void test_matches_NullInput_ShouldThrowException() {
        AndMatcher andMatcher = new AndMatcher(Collections.emptyList());
        assertThrows(NullPointerException.class, () -> andMatcher.matches(null));
    }

    @Test
    void test_matches_EmptyList_ShouldReturnTrue() {
        AndMatcher andMatcher = new AndMatcher(Collections.emptyList());
        assertTrue(andMatcher.matches(mock(File.class)));
    }

    @Test
    void test_matches_AllTrue_ShouldReturnTrue() {

        Matcher trueMatcher1 = mock(Matcher.class);
        Matcher trueMatcher2 = mock(Matcher.class);

        when(trueMatcher1.matches(any())).thenReturn(true);
        when(trueMatcher2.matches(any())).thenReturn(true);

        AndMatcher andMatcher = new AndMatcher(Arrays.asList(trueMatcher1, trueMatcher2));
        assertTrue(andMatcher.matches(mock(File.class)));
    }

    @Test
    void test_matches_SomeTrue_ShouldReturnFalse() {

        Matcher trueMatcher1 = mock(Matcher.class);
        Matcher falseMatcher1 = mock(Matcher.class);

        when(trueMatcher1.matches(any())).thenReturn(true);
        when(falseMatcher1.matches(any())).thenReturn(false);

        AndMatcher andMatcher = new AndMatcher(Arrays.asList(trueMatcher1, falseMatcher1));
        assertFalse(andMatcher.matches(mock(File.class)));
    }

    @Test
    void test_matches_NoneTrue_ShouldReturnFalse() {

        Matcher falseMatcher1 = mock(Matcher.class);
        Matcher falseMatcher2 = mock(Matcher.class);

        when(falseMatcher1.matches(any())).thenReturn(false);
        when(falseMatcher2.matches(any())).thenReturn(false);

        AndMatcher andMatcher = new AndMatcher(Arrays.asList(falseMatcher1, falseMatcher2));
        assertFalse(andMatcher.matches(mock(File.class)));
    }
}
