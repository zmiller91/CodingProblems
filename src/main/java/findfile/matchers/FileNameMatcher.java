package findfile.matchers;

import java.io.File;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

public class FileNameMatcher implements Matcher {

    /**
     * Supported evaluators.
     */
    public enum NameOperator {

        EQUALS( (x, y) -> x.equals(y)),
        CONTAINS( (x, y) -> x.contains(y)),
        REGEX( (x, y) -> Pattern.matches(y, x));

        private final BiFunction<String, String, Boolean> operation;
        NameOperator(BiFunction<String, String, Boolean> operation) {
            this.operation = operation;
        }

        boolean evaluate(String x, String y) {
            return operation.apply(x, y);
        }

    }

    private final String evaluationString;
    private final NameOperator operator;

    /**
     * Public construction of a FileNameMatcher. This matcher evaluates a File's name against a provided operator.
     *
     * Note: For REGEX evaluation the `evaluationString` should be the regex. For CONTAINS evaluation the
     * `evaluationString` should be the substring.
     *
     * @param evaluationString used to evaluate the file's name
     * @param operator used to compare the file's name to the evaluationString
     */
    public FileNameMatcher(String evaluationString, NameOperator operator) {
        this.evaluationString = checkNotNull(evaluationString);
        this.operator = checkNotNull(operator);
    }

    /**
     * Evaluates a file's name against the provided `evaluationString` using the provided `operator`.
     *
     * @param file to evaluate
     * @return true if the operator evaluates to true, otherwise false
     */
    @Override
    public boolean matches(File file) {
        checkNotNull(file);
        return this.operator.evaluate(file.getName(), this.evaluationString);
    }
}
