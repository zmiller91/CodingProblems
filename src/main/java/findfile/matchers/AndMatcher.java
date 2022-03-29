package findfile.matchers;

import java.io.File;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matcher that evaluates other matchers, returning true if and only if all matchers return true.
 */
public class AndMatcher implements Matcher {

    private final List<Matcher> matchers;

    /**
     * Public constructor of the AndMatcher.
     *
     * @param matchers list of matchers used to evaluate an `and` statement
     */
    public AndMatcher(List<Matcher> matchers) {
        this.matchers = checkNotNull(matchers);
    }

    /**
     * Evaluates each matcher against a file and returns true if and only if all matchers return true. If an empty
     * list was provided to the constructor, then this method will return true.
     *
     * @param file to evaluate
     * @return true if all matchers are true, otherwise false
     */
    @Override
    public boolean matches(File file) {
        checkNotNull(file);
        return this.matchers.stream().allMatch(m -> m.matches(file));
    }
}
