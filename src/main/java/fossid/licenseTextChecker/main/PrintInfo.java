package fossid.licenseTextChecker.main;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static fossid.licenseTextChecker.values.AllValues.allValues;

public class PrintInfo {
    private static final Logger logger = LogManager.getLogger(PrintInfo.class);
    public static void startFOSSID() {
        logger.info("Start License Text Checker");
        logger.info("");
        logger.info(" ******     ******     ******        ******");
        logger.info("*      *   *           *     *      *      ");
        logger.info("*      *   *           *      *    *       ");
        logger.info("*      *   *           *     *    *        ");
        logger.info("*      *    ******     ******     *        ");
        logger.info("*      *          *    *     *    *        ");
        logger.info("*      *          *    *      *    *       ");
        logger.info("*      *          *    *     *      *      ");
        logger.info(" ******     ******     ******        ******");
        logger.info("");
    }

    public static void endFOSSID() {
        logger.info("--[Finish License Text Check]--");
        logger.info("Check License Count : " + allValues.licenseListValues.getAllLicenseList().size());
        logger.info("License with text Count : " + allValues.licenseListValues.getTextLicenseList().size());
        logger.info("License without text Count : " + allValues.licenseListValues.getNoTextLicenseList().size());
    }

    public static void printInfo() {
        int apiKeyLength = allValues.loginValues.getApikey().length();
        StringBuilder apiKey = new StringBuilder();

        for (int i = 0; i < apiKeyLength; i++) {
            apiKey.append("*");
        }

        logger.info("");
        logger.info("Server URL: " + allValues.loginValues.getServerApiUri());
        logger.info("UserName: " + allValues.loginValues.getUsername());
        logger.info("ApiKey: " + apiKey);
        logger.info("");
    }
}
