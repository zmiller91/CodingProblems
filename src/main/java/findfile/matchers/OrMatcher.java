package findfile.matchers;

import java.io.File;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matcher that evaluates other matchers, returning true if at least one matcher returns true.
 */
public class OrMatcher implements Matcher {

    private final List<Matcher> matchers;

    /**
     * Public constructor of the OrMatcher.
     *
     * @param matchers list of matchers used to evaluate an `or` statement
     */
    public OrMatcher(List<Matcher> matchers) {
        this.matchers = checkNotNull(matchers);
    }

    /**
     * Evaluates each matcher against a file and returns true if at least one matcher returns true. If an empty
     * list was provided to the constructor, then this method will return false.
     *
     * @param file to evaluate
     * @return true if at least one mather returns true, otherwise false
     */
    @Override
    public boolean matches(File file) {
        checkNotNull(file);
        return this.matchers.stream().anyMatch(m -> m.matches(file));
    }
}
