package findfile;

import findfile.matchers.Matcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FindFiles {

    public static List<File> find(File file, Matcher matcher) {

        checkNotNull(matcher);
        checkNotNull(file);

        List<File> matches = new ArrayList<>();
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                matches.addAll(find(f, matcher));
            }
        } else if (matcher.matches(file))  {
            matches.add(file);
        }

        return matches;
    }
}
