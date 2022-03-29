package findfile;

import findfile.matchers.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindFilesTest {

    private final File l3File = file();
    private final File l3Directory = directory();

    private final File l2File = file();
    private final File l2Directory = directory();

    private final File l1File = file();
    private final File root = directory();

    @BeforeEach
    void setup() {
        when(l3Directory.listFiles()).thenReturn(new File[]{l3File});
        when(l2Directory.listFiles()).thenReturn(new File[]{l2File, l3Directory});
        when(root.listFiles()).thenReturn(new File[]{l1File, l2Directory});
    }

    @Test
    void test_find_NullFile_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> FindFiles.find(null, mock(Matcher.class)));
    }

    @Test
    void test_find_NullMatcher_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> FindFiles.find(mock(File.class), null));
    }

    @Test
    void test_find_AllFilesMatch_ShouldReturnAllFiles() {

        Matcher trueMatcher = mock(Matcher.class);
        when(trueMatcher.matches(any())).thenReturn(true);

        List<File> found = FindFiles.find(this.root, trueMatcher);
        assertEquals(Arrays.asList(l1File, l2File, l3File), found);
    }

    @Test
    void test_find_SomeFilesMatch_ShouldReturnSomeFiles() {

        Matcher someMatcher = mock(Matcher.class);
        when(someMatcher.matches(any())).thenAnswer(answer -> answer.getArguments()[0].equals(l3File));

        List<File> found = FindFiles.find(this.root, someMatcher);
        assertEquals(Collections.singletonList(l3File), found);
    }

    @Test
    void test_find_NoneFilesMatch_ShouldReturnNoFiles() {

        Matcher falseMatcher = mock(Matcher.class);
        when(falseMatcher.matches(any())).thenReturn(false);

        List<File> found = FindFiles.find(this.root, falseMatcher);
        assertEquals(Collections.emptyList(), found);
    }

    private File file() {
        File file = mock(File.class);
        when(file.isDirectory()).thenReturn(false);
        return file;
    }

    private File directory() {
        File directory = mock(File.class);
        when(directory.isDirectory()).thenReturn(true);
        return directory;
    }

}
