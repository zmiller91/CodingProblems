package findfile.matchers;

import java.io.File;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AndMatcher implements Matcher {

    private final List<Matcher> matchers;
    public AndMatcher(List<Matcher> matchers) {
        this.matchers = checkNotNull(matchers);
    }

    @Override
    public boolean matches(File file) {
        checkNotNull(file);
        return this.matchers.stream().allMatch(m -> m.matches(checkNotNull(file)));
    }
}
