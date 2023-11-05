package trw.jroom;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String urlTrueMarketMean = "https://checkonchain.com/btconchain/pricing/cointime_mvrv_aviv_1/cointime_mvrv_aviv_1_light.html";
        String urlSMVRV = "https://chainexposed.com/XthMVRVShortTermHolderAddress.html";
        String relativeUnrealizedProfitURL = "https://chainexposed.com/RelativeUnrealizedProfit.html";
        String soprURL = "https://chainexposed.com/SOPR.html";
        String nuplURL = "https://chainexposed.com/NUPL.html";
        String filename = "Relative_Unrealized_Profit";

        saveGraphDataToJsonByName(relativeUnrealizedProfitURL,"Data\\" + filename, "RUP");

    }

    /***
     * Scrapes websites with graphs that contain the data of their graph within their JavaScripts and saved into a json object
     * @param url the url of the chart you are trying to scrape.
     * @param filename name for the file that will be created. DON'T ADD .json or .txt.
     * @param graphName the name of the graph as it is displayed in the html content.
     */
    public static void saveGraphDataToJsonByName(String url, String filename, String graphName) {
        try {
            String htmlContent = WebsiteScrapper.scrapeWebsite(url);
            String extractedData = HTMLParser.extractJsonStringOutOfGraphWebsite(htmlContent, graphName);
            if (extractedData == null) {
                System.out.println("""
                        No data extracted from the page, Here are the steps to take:
                        1) make sure URL is correct and that it has .html at the end
                        2) validate the graphName. This will require you to look inside the html and find the name used in their script.\s
                        For example Relative Unrealized Profit chart on "chain exposed" uses 'RUP' as their graph name.""");
                return;
            }
            FileManager.writeToFile(filename + ".json", extractedData, false);
        } catch (IOException e) {
            System.out.println("Failed to extract data and write to file.");
        }
    }


}