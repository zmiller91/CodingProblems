package findfile.matchers;

import java.io.File;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class OrMatcher implements Matcher {

    private final List<Matcher> matchers;

    public OrMatcher(List<Matcher> matchers) {
        this.matchers = checkNotNull(matchers);
    }

    @Override
    public boolean matches(File file) {
        checkNotNull(file);
        return this.matchers.stream().anyMatch(m -> m.matches(file));
    }
}
