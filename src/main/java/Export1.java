import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONObject;

import java.nio.file.Path;


public class Export1 {

    public static void main(String[] args) {
        HttpURLConnection exportStatusConnection = null;
        try {
            String export = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ";
            String exportStatusAuth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ";
            String exportUrl = "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export";
            String exportBody = "{\"exportType\": \"published\",\"exportOptions\": {\"settings\": [\"botSettings\",\"botVariables\",\"ivrSettings\"],\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": true,\"IncludeDependentTasks\": true}";

            // Export API Call
            URL exportUrlObj = new URL(exportUrl);
            HttpURLConnection exportConnection = (HttpURLConnection) exportUrlObj.openConnection();
            exportConnection.setRequestMethod("POST");
            exportConnection.setRequestProperty("auth", export);
            exportConnection.setRequestProperty("Content-Type", "application/json");
            exportConnection.setDoOutput(true);

            OutputStream exportOutputStream = exportConnection.getOutputStream();
            exportOutputStream.write(exportBody.getBytes());
            exportOutputStream.flush();
            exportOutputStream.close();
            System.out.println("Export  API Response Code :: " + exportConnection.getResponseCode());

            Thread.sleep(1500);

            // Export Status API call to get the download URL
            StringBuilder expStatusResp = new StringBuilder();
            String exportStatusUrl = "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export/status";
            exportStatusConnection = (HttpURLConnection) new URL(exportStatusUrl).openConnection();
            exportStatusConnection.setRequestMethod("GET");
            exportStatusConnection.setRequestProperty("auth", exportStatusAuth);
            System.out.println("Export Status API Response Code :: " + exportStatusConnection.getResponseCode());

            if (exportStatusConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(exportStatusConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    expStatusResp.append(line);
                }
                System.out.println("Export API Status Response :: " + expStatusResp);
                // JSONObject jsonObject = new JSONObject(expStatusResp.toString());
                // String downloadUrl = jsonObject.get("downloadURL").toString();
                // System.out.println("downloadUrl:: " + downloadUrl);

                // Download the file using URL
                URL downloadUrlObj = new URL("https://github.com/darshana0406/CCT-Bots-Automation.git");
                downloadFile(downloadUrlObj, "fullexport.zip");

                // String downloadUrlObj2 = "C:/Users/gg/Downloads/Bot2Test";
                // downloadFile2(downloadUrlObj2,"fullexport.zip");
                
                // String gitRepoUrl = "https://github.com/darshana0406/CCT-Bots-Automation.git";
                // String fileName = "fullexport.zip";
                // URL downloadUrlObj2 = new URL(gitRepoUrl);
                // downloadFile2(downloadUrlObj2, fileName);
                // String zipFilePath1 = "fullexport.zip";
                // String destDir1 = "ExportBot";
                // unzip(zipFilePath1, destDir1);

                System.out.println("File Downloaded in current working directory");
                Thread.sleep(1500);

                // Unzip the files
                String zipFilePath = "fullexport.zip";
                String destDir = "Dev_NCE/ExportBot";
                unzip(zipFilePath, destDir);
                System.out.println("Files unzipped to " + destDir);

            } else {
                InputStream exportStatusInputStream = exportStatusConnection.getErrorStream();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exportStatusConnection.disconnect();
        }

    }

//   public static void downloadFile2(URL url, String fileName) throws Exception {
//     try {
//       //  URL u = new URL(url); // Create a URL object from the Git repository URL
//         try (InputStream in = url.openStream()) {
//             // If the file needs to be copied to a specific path, create a custom path and provide it
//             Path path = Paths.get("ExportBot");
//             System.out.println("path ---->"+path);
//             Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
//             System.out.println("Working");
//         }
//     } catch (MalformedURLException e) {
//         // Handle invalid URL exception
//         e.printStackTrace();
//     }
// }
  
 
    

    public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            // If the file needs to be copied to specific path, create custom path and
            // provide
            Path path = Paths.get("https://github.com/darshana0406/CCT-Bots-Automation/tree/javamain/CCT_Billing/Dev_NCE");
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
    }


    private static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists())
            dir.mkdirs();
        FileInputStream fis;
        // buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                // create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                // close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            // close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // try {
        //     // Set user email
        //     ProcessBuilder setEmail = new ProcessBuilder("git", "config", "--global", "user.email", "darshana@gmail.com");
        //     Process emailProcess = setEmail.start();
        //     int emailExitCode = emailProcess.waitFor();
        //     if (emailExitCode == 0) {
        //         System.out.println("User email set successfully.");
        //     } else {
        //         System.out.println("Error setting user email.");
        //     }

        //     // Set user name
        //     ProcessBuilder setName = new ProcessBuilder("git", "config", "--global", "user.name", "darshana");
        //     Process nameProcess = setName.start();
        //     int nameExitCode = nameProcess.waitFor();
        //     if (nameExitCode == 0) {
        //         System.out.println("User name set successfully.");
        //     } else {
        //         System.out.println("Error setting user name.");
        //     }
        // } catch (IOException | InterruptedException e) {
        //     e.printStackTrace();
        // }
    
    }
}
