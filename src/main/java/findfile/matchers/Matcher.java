package findfile.matchers;

import java.io.File;

public interface Matcher {
    boolean matches(File file);
}
