package findfile.matchers;

import java.io.File;
import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkNotNull;

public class FileSizeMatcher implements Matcher {

    /**
     * Supported evaluators.
     */
    public enum SizeOperator {
        LT( (x, y) -> x < y),
        LTEQ( (x, y) -> x <= y ),
        EQ ( (x, y) -> x.equals(y)),
        GTEQ( (x, y) -> x >= y ),
        GT( (x, y) -> x > y);

        private final BiFunction<Long, Long, Boolean> operation;
        SizeOperator(BiFunction<Long, Long, Boolean> operation) {
            this.operation = operation;
        }

        boolean evaluate(long x, long y) {
            return operation.apply(x, y);
        }
    }

    private final long size;
    private final SizeOperator operator;

    /**
     * Public construction of a FileSizeMatcher. This matcher evaluates a File's size against a provided operator.
     *
     * @param size to compare against
     * @param operator used to compare the file's size to the provided `size`
     */
    public FileSizeMatcher(long size, SizeOperator operator) {
        this.size = size;
        this.operator = checkNotNull(operator);
    }

    /**
     * Evaluates a file's size against the provided `size` using the provided `operator`.
     *
     * @param file to evaluate
     * @return true if the operator evaluates to true, otherwise false
     */
    @Override
    public boolean matches(File file) {
        checkNotNull(file);
        return this.operator.evaluate(file.length(), size);
    }

}
