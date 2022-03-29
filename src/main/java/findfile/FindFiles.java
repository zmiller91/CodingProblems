package findfile;

import findfile.matchers.Matcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FindFiles {

    /**
     * Recurses through a directory structure to find all files that match the provided `matcher.`
     *
     * @param file root of a directory to search
     * @param matcher evaluation criteria used to find files
     * @return all files that match the `matcher`
     */
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
