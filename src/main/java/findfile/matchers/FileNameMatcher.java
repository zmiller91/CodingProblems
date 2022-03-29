package findfile.matchers;

import java.io.File;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

public class FileNameMatcher implements Matcher {


    public enum NameOperator {

        EQUALS( (x, y) -> x.equals(y)),
        CONTAINS( (x, y) -> y.contains(x)),
        REGEX( (x, y) -> Pattern.matches(y, x));

        private BiFunction<String, String, Boolean> operation;
        private NameOperator(BiFunction<String, String, Boolean> operation) {
            this.operation = operation;
        }

        boolean evaluate(String x, String y) {
            return operation.apply(x, y);
        }

    }

    private final String evaluationString;
    private final NameOperator operator;

    public FileNameMatcher(String name, NameOperator operator) {
        this.evaluationString = checkNotNull(name);
        this.operator = checkNotNull(operator);
    }

    @Override
    public boolean matches(File file) {
        checkNotNull(file);
        return this.operator.evaluate(file.getName(), this.evaluationString);
    }
}
