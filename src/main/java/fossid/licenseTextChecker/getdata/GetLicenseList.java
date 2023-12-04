package fossid.licenseTextChecker.getdata;

import fossid.licenseTextChecker.values.LicenseValues;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static fossid.licenseTextChecker.values.AllValues.allValues;

public class GetLicenseList {
    private final Logger logger = LogManager.getLogger(GetLicenseList.class);

    public void getData() {
        getAllLicense();
    }

    private void getAllLicense() {
        JSONObject dataObject = new JSONObject();
        dataObject.put("username", allValues.loginValues.getUsername());
        dataObject.put("key", allValues.loginValues.getApikey());

        JSONObject rootObject = new JSONObject();
        rootObject.put("group", "licenses");
        rootObject.put("action", "list_licenses");
        rootObject.put("data", dataObject);

        BufferedReader br = null;
        HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(entity);

            HttpResponse httpClientResponse = httpClient.execute(httpPost);

            if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
                throw new Exception("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
            }

            br = new BufferedReader(new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
            String result = br.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject resultJson = (JSONObject) jsonParser.parse(result);

            logger.debug("resultJson: " + resultJson);

            JSONObject resultJsonData = (JSONObject) resultJson.get("data");

            resultJsonData.values().forEach(values -> {
                JSONObject value = (JSONObject) values;

                String name = (String) value.get("name");
                String identifier = (String) value.get("identifier");

                LicenseValues licenseValues = new LicenseValues(name, identifier);

                allValues.licenseListValues.setAllLicenseList(licenseValues);

                getLicenseInfo(licenseValues);

                if (licenseValues.getText().isEmpty()) {
                    allValues.licenseListValues.setNoTextLicenseList(licenseValues);
                    logger.info(licenseValues.getLicenseIdentifier());
                } else {
                    allValues.licenseListValues.setTextLicenseList(licenseValues);
                }
            });

        } catch (Exception e) {
            logger.error("Exception Message", e);
            System.exit(1);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                logger.error("Exception Message", e);
            }
        }
    }

    private void getLicenseInfo(LicenseValues licenseValues) {
        JSONObject dataObject = new JSONObject();
        dataObject.put("username", allValues.loginValues.getUsername());
        dataObject.put("key", allValues.loginValues.getApikey());
        dataObject.put("license_identifier", licenseValues.getLicenseIdentifier());

        JSONObject rootObject = new JSONObject();
        rootObject.put("group", "licenses");
        rootObject.put("action", "get_information");
        rootObject.put("data", dataObject);

        BufferedReader br = null;
        HttpPost httpPost = new HttpPost(allValues.loginValues.getServerApiUri());
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(entity);

            HttpResponse httpClientResponse = httpClient.execute(httpPost);

            if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
                throw new Exception("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
            }

            br = new BufferedReader(new InputStreamReader(httpClientResponse.getEntity().getContent(), StandardCharsets.UTF_8));
            String result = br.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject resultJson = (JSONObject) jsonParser.parse(result);

            logger.debug("resultJson: " + resultJson);

            JSONObject resultJsonData = (JSONObject) resultJson.get("data");

            String licenseText = resultJsonData.get("text")
                    != null
                    ? (String) resultJsonData.get("text") : "";

            licenseValues.setText(licenseText);
        } catch (Exception e) {
            logger.error("Exception Message", e);
            System.exit(1);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                logger.error("Exception Message", e);
            }
        }
    }
}
