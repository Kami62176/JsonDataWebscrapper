/**
 * @author jperezbenitez
 * @createdOn 10/30/2023 at 4:46 PM
 * @projectName WebScrapingWithProfComire
 * @packageName csc180.perezbenitez.jose;
 */
package trw.jroom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLParser {
    private static final RegexValidation rv = new RegexValidation();

    public static String extractingObjectWithDataFromHtmlFile(String filePath, String nameSearch) throws IOException {
        return extractJsonStringOutOfGraphWebsite(FileManager.readFromTxtFile(filePath), nameSearch);
    }

    public static String extractJsonStringOutOfGraphWebsite(String htmlContent, String graphName) {
        Document doc = Jsoup.parse(htmlContent);
        Elements scriptTags = doc.select("script");

        String desiredScriptContent = null;
        for (Element scriptTag : scriptTags) {
            String scriptContent = scriptTag.html();
            if (scriptContent.contains(graphName)) {
                desiredScriptContent = scriptContent;
                break;
            }
        }
        int endBracketIndex = -1;
        int startBracketIndex = -1;
        while (desiredScriptContent != null) {
            int nameIndex = desiredScriptContent.indexOf(graphName);
            int openBracketCount = 0;
            int closeBracketCount = 0;

            for (int index = nameIndex; index <= desiredScriptContent.length(); ) {
                char currentLetter = desiredScriptContent.charAt(index);
                if (endBracketIndex == -1) {
                    if (currentLetter == '}') {
                        if (openBracketCount == 0) {
                            endBracketIndex = index;
                            index--;
                            continue;
                        } else {
                            openBracketCount--;
                        }
                    } else if (currentLetter == '{') {
                        openBracketCount++;
                    }
                    index++;
                } else {
                    if (currentLetter == '{') {
                        if (closeBracketCount == 0) {
                            startBracketIndex = index;
                            break;
                        } else {
                            closeBracketCount--;
                        }
                    } else if (currentLetter == '}') {
                        closeBracketCount++;
                    }
                    index--;
                }
            }
            String extractedObject = desiredScriptContent.substring(startBracketIndex, endBracketIndex + 1);
            if (rv.matchesRegex(extractedObject, "[\\\"']?y[\\\"']?:\\s?\\[") &&
                    rv.matchesRegex(extractedObject, "[\\\"']?x[\\\"']?:\\s?\\[")) {
                System.out.println(extractedObject);
                return extractedObject;
            } else {
                // Reset indices and continue searching the remaining script content
                desiredScriptContent = desiredScriptContent.substring(endBracketIndex + 1);
                endBracketIndex = -1;
                startBracketIndex = -1;
            }
        }
        return null;

    }


}
