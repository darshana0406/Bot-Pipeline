 import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test5 {
    public static void main(String[] args) {
        // Replace with your GitHub personal access tokens
        String sourcePat = "SHA256:bWvp7yTPArRSHiJ3vuE90btXv2YmkHq6qWJ4OFkMvO0"; // PAT for the source repository
        String targetPat = "SHA256:bWvp7yTPArRSHiJ3vuE90btXv2YmkHq6qWJ4OFkMvO0"; // PAT for the target repository
        
        // Replace with your GitHub repository details
        String sourceOwner = "darshana0406";
        String sourceRepoName = "Bot-Pipeline";
        String sourceFilePath = "fullexport.zip";
        
        String targetOwner = "darshana0406";
        String targetRepoName = "CCT-Bots-Automation";
        String targetFilePath = "https://github.com/darshana0406/CCT-Bots-Automation/blob/javamain/target-file.txt";
        
        try {
            // Step 1: Fetch the content of the file from the source repository
            URL sourceFileUrl = new URL("https://github.com" + sourceOwner + "/" + sourceRepoName + "/main/" + sourceFilePath);
            String fileContent;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(sourceFileUrl.openStream()))) {
                StringBuilder contentBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line);
                    contentBuilder.append("\n");
                }
                fileContent = contentBuilder.toString();
            }
            
            // Step 2: Create or update the file in the target repository using GitHub REST API
            URL targetFileUrl = new URL("https://github.com" + targetOwner + "/" + targetRepoName + "/contents/" + targetFilePath);
            HttpURLConnection connection = (HttpURLConnection) targetFileUrl.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "token " + targetPat);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Create a JSON payload to update the file content
            String jsonPayload = "{"
                    + "\"message\": \"Updated file from source repository\","
                    + "\"content\": \"" + encodeBase64(fileContent) + "\","
                    + "\"sha\": \"\"" // Leave empty for creating a new file
                    + "}";
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("File transferred successfully.");
            } else {
                System.err.println("Failed to transfer file. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to encode content in Base64
    private static String encodeBase64(String content) {
        return java.util.Base64.getEncoder().encodeToString(content.getBytes());
    }
}

    

