package main.com.adobe.aem.support.linkrewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class LinkRewriterTest {

    @Test
    public void testRegexMatches_1() {
        Pattern pattern = Pattern.compile("/content/wknd/language-masters/en/(.+)\\.html/content/dam/(.+)");
        Matcher matcher = pattern.matcher("/content/wknd/language-masters/en/magazine.html/content/dam/test.pdf");

        assertTrue(matcher.matches());
        
    }

    @Test
    public void testRegexGroups() {
        Pattern pattern = Pattern.compile("\\/content\\/wknd\\/language-masters\\/en\\/(.+)\\.html\\/content\\/dam\\/(.+)");
        Matcher matcher = pattern.matcher("/content/wknd/language-masters/en/magazine.html/content/dam/test.pdf");

        while(matcher.find()) {
            assertEquals("magazine", matcher.group(1));
            assertEquals("test.pdf", matcher.group(2));
        }

    }

    @Test
    public void testRegexNotMatches() {
        Pattern pattern = Pattern.compile("/content/wknd/language-masters/en/(.+)\\.html/content/dam/(.+)");
        Matcher matcher = pattern.matcher("/content/wknd/language-masters/e/magazine.html/content/dam/test.pdf");

        assertTrue(!matcher.matches());
    }
}
