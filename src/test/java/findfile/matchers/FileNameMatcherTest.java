package findfile.matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static findfile.matchers.FileNameMatcher.NameOperator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileNameMatcherTest {

    @Test
    void test_constructor_NullOperator_ThrowsException() {
        assertThrows(NullPointerException.class, () -> new FileNameMatcher("name", null));
    }

    @Test
    void test_constructor_NullName_ThrowsException() {
        assertThrows(NullPointerException.class, () -> new FileNameMatcher(null, NameOperator.EQUALS));
    }

    @Test
    void test_matches_NullInput_ThrowsException() {
        FileNameMatcher fileNameMatcher = new FileNameMatcher("name", NameOperator.CONTAINS);
        assertThrows(NullPointerException.class, () -> fileNameMatcher.matches(null));
    }

    private static Stream<Arguments> providesMatchesInput() {
        return Stream.of(
                Arguments.of("name", "not name", NameOperator.EQUALS, false,"EQUALS returns false not equal"),
                Arguments.of("name", "name", NameOperator.EQUALS, true, "EQUALS returns true when equal"),

                Arguments.of("string_name", "not substring", NameOperator.CONTAINS, false, "CONTAINS returns false when not substring"),
                Arguments.of("string_name", "string_name", NameOperator.CONTAINS, true, "CONTAINS returns true when equals"),
                Arguments.of("string_name", "string", NameOperator.CONTAINS, true, "CONTAINS returns true when substring"),

                Arguments.of("STRING", "^[a-z]+$", NameOperator.REGEX, false, "REGEX returns false when no match"),
                Arguments.of("string", "^[a-z]+$", NameOperator.REGEX, true, "REGEX returns true when match")
        );
    }

    @ParameterizedTest
    @MethodSource("providesMatchesInput")
    void test_matches_ShouldCorrectlyMatch(
            String fileName,
            String evaluationString,
            NameOperator operator,
            boolean result,
            String description) {

        FileNameMatcher fileNameMatcher = new FileNameMatcher(evaluationString, operator);
        File file = mock(File.class);
        when(file.getName()).thenReturn(fileName);
        assertEquals(result, fileNameMatcher.matches(file), "Failed Test: " + description);
    }
}
