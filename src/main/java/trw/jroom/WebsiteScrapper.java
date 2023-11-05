/**
 * @author jperezbenitez
 * @createdOn 10/30/2023 at 4:42 PM
 * @projectName WebScrapingWithProfComire
 * @packageName csc180.perezbenitez.jose;
 */
package trw.jroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebsiteScrapper {

    public static void saveWebsiteToFile(String url, String filepath) {
        try {
            String htmlContent = scrapeWebsite(url);
            FileManager.writeToFile(filepath, htmlContent, false);
        } catch (Exception e) {
            System.out.println("An error occurred." + e.getMessage());
        }
    }

    public static String scrapeWebsite(String website) throws IOException {
        URL realURL = new URL(website);
        HttpURLConnection conn = (HttpURLConnection) realURL.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine = "";

        StringBuilder sb = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        conn.disconnect();
        br.close();
        return sb.toString();
    }
}
