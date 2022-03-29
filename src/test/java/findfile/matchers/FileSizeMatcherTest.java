package findfile.matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static findfile.matchers.FileSizeMatcher.SizeOperator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileSizeMatcherTest {

    @Test
    void test_constructor_NullOperator_ThrowsException() {
        assertThrows(NullPointerException.class, () -> new FileSizeMatcher(100, null));
    }

    @Test
    void test_matches_NullInput_ThrowsException() {
        FileSizeMatcher fileSizeMatcher = new FileSizeMatcher(100, SizeOperator.GT);
        assertThrows(NullPointerException.class, () -> fileSizeMatcher.matches(null));
    }

    private static Stream<Arguments> providesMatchesInput() {
        return Stream.of(
                Arguments.of(100, 1000, SizeOperator.EQ, false,"EQ returns false not EQ"),
                Arguments.of(100, 100, SizeOperator.EQ, true, "EQ returns true when EQ"),

                Arguments.of(100, 10, SizeOperator.LT, false, "LT returns false when GT"),
                Arguments.of(100, 100, SizeOperator.LT, false, "LT returns false when EQ"),
                Arguments.of(10, 100, SizeOperator.LT, true, "LT returns true when LT"),

                Arguments.of(100, 10, SizeOperator.LTEQ, false, "LTEQ returns false when GT"),
                Arguments.of(10, 100, SizeOperator.LTEQ, true, "LTEQ returns true when LT"),
                Arguments.of(100, 100, SizeOperator.LTEQ, true, "LTEQ returns true when EQ"),

                Arguments.of(10, 100, SizeOperator.GT, false, "GT returns false when LT"),
                Arguments.of(100, 100, SizeOperator.GT, false, "GT returns false when EQ"),
                Arguments.of(100, 10, SizeOperator.GT, true, "GT returns true when GT"),

                Arguments.of(10, 100, SizeOperator.GTEQ, false, "GTEQ returns false when LT"),
                Arguments.of(100, 100, SizeOperator.GTEQ, true, "GTEQ returns true when GT"),
                Arguments.of(100, 10, SizeOperator.GTEQ, true, "GTEQ returns true when EQ")
        );
    }

    @ParameterizedTest
    @MethodSource("providesMatchesInput")
    void test_matches_ShouldCorrectlyMatch(
            long fileSize,
            long evaluationSize,
            SizeOperator operator,
            boolean result,
            String description) {

        FileSizeMatcher fileSizeMatcher = new FileSizeMatcher(evaluationSize, operator);
        File file = mock(File.class);
        when(file.length()).thenReturn(fileSize);
        assertEquals(result, fileSizeMatcher.matches(file), "Failed Test: " + description);
    }
}
