import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.w3c.dom.events.UIEvent;

public class UI {
    
   public static String botDefinitionId ;
   public static String configInfoId ;
            
            
    
    public static void main(String[] args) {

        String importType = args[0];
        String env = args[1];
        
        if (args.length > 0 ) {
                System.out.println("Chosen Value: " + importType);              
            } else {
                System.out.println("No chosen value provided.");
            }
        // Call the method to set environment variables
        UIENV.setEnvironmentVariables(env, importType);

        // UIENV.setEnvironmentVariables();
             String botDefinitionId = "";
             String configInfoId = "";


        try {
            
            String uploadApiUrl = System.getProperty("Upload_URL");
            String authToken = System.getProperty("Upload_JWT");
        
 
            String[] fileNames = System.getProperty("Upload_FileName").split(",");
            String fileContext = System.getProperty("Upload_FileContext");
            String fileExtension = System.getProperty("Upload_FileExtension");
            String boundary = System.getProperty("Upload_boundary");

            UI upload = new UI();
            upload.botDefinitionId = "";        
            upload.configInfoId = "";
            try {

                for (String filePath : fileNames) {
                    String postData = "--" + boundary + "\r\n"
                            + "Content-Disposition: form-data; name=\"fileContext\"\r\n\r\n" + fileContext + "\r\n"
                            + "--" + boundary + "\r\n"
                            + "Content-Disposition: form-data; name=\"fileExtension\"\r\n\r\n" + fileExtension + "\r\n"
                            + "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"file\"; filename=\""
                            + Paths.get(filePath).getFileName() + "\"\r\n"
                            + "Content-Type: application/octet-stream\r\n\r\n";

                    String endBoundary = "\r\n--" + boundary + "--\r\n";

                    byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

                    URL url = new URL(uploadApiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("auth", authToken);
                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                    connection.setDoOutput(true);
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes());
                    outputStream.write(fileBytes);
                    outputStream.write(endBoundary.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    int responseCode = connection.getResponseCode();
                    System.out.println("Upload Success : : " + responseCode);

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                
                    // Print the response from the server
                    System.out.println("Response from Server:" + response.toString());
                    JSONObject jsonObj = new JSONObject(response.toString());
                    if (filePath.contains("botDefinition")) {
                        botDefinitionId = jsonObj.getString("fileId");
                        System.out.println("botdefinitionId: " + botDefinitionId);
                    } else {
                        configInfoId = jsonObj.getString("fileId");
                    }

                }
                 
                System.out.println("botdefinitionId: " + botDefinitionId);
                System.out.println("configId: " + configInfoId);
                

                UIENV upload1 = new UIENV();
                upload1.getBotdefinitionId(botDefinitionId);
                upload1.getConfigId(configInfoId);
                upload1.getBot(botDefinitionId);
                upload1.setBotDefinition(botDefinitionId);
                upload1.setConfig(configInfoId);
            }

            catch (Exception e) {
                e.printStackTrace();
            }

            // Call Import API
       

             String importBody = "{\n" + " \"botDefinition\": \"" + botDefinitionId + "\",\n"
                    + "\"configInfo\": \"" + configInfoId + "\",\n" +System.getProperty("Import_Body");
            // Create the HttpURLConnection
            String importToken = System.getProperty("Import_JWT");
            URL impUrl = new URL(System.getProperty("Import_URL"));
            HttpURLConnection impConnection = (HttpURLConnection) impUrl.openConnection();
            impConnection.setRequestMethod("POST");
            impConnection.setRequestProperty("auth", importToken);
            impConnection.setRequestProperty("content-type", "application/json");
            impConnection.setDoOutput(true);

            // Write the payload to the request body
            try (OutputStream outputStream = impConnection.getOutputStream()) {
                outputStream.write(importBody.getBytes());
            }

            // Read the response
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(impConnection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader1.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader1.close();
            // Print the response
            System.out.println("Import Success : : " + impConnection.getResponseCode());
            System.out.println("Response Body: " + responseBuilder.toString());
            
            // Close the connection
            impConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    
    
}