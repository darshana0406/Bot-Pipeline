// package cicd;
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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONObject;

public class ExportBot {
	
	static String exportType = BotConstants.EXP_ALL;
	static String env = BotConstants.ENV_DEV;;
	static String botName = BotConstants.CCT_IVR_BILLING;
	
	public static void main(String[] args) throws IOException, GitAPIException {

		//Get argument values from Jenkins
		if (args.length > 0) {
			botName = args[0];
			exportType = args[1];
			env = args[2];
		} else {
			System.out.println("No Arguments received from Jenkins");
		}
		System.out.println("ExportType: " + exportType +"\n" +"Environment: " + env+"\n"+"Bot Name: " + botName);

		//Load property files based on the env selected
		Properties prop = new Properties();
		InputStream inputStream = new FileInputStream("C:\\Users\\gg\\Documents\\Darshana-infy\\Bot-Pipeline\\src\\main\\config\\"+env+"\\BotConfig.properties");
		prop.load(inputStream);
		
		System.out.println("**************************** Starting Export Bot Process ****************************");
		
		try {
			//Export Bot API call to export the bot from source account
			exportBotAPICall(prop);
			
			//Export Status API call to check the export status
			String downloadUrl = callExportStatusAPI(prop);
			System.out.println("downloadUrl: " + downloadUrl);

			//Download the source files using downloadUrl
			URL downloadUrlObj = new URL(downloadUrl);
			downloadFile(downloadUrlObj, BotConstants.FULL_EXP_FILE);
			System.out.println("File Downloaded in current working directory");
			Thread.sleep(1500);

			// Unzip the files to local workspace
			String zipFile = BotConstants.FULL_EXP_FILE;
			String destDir = BotConstants.EXPORTBOT;
			unzip(zipFile, destDir);
			System.out.println("Files unzipped to " + destDir);
			
			//Commit files to target repository
			commitToTargetRepo(prop,botName,env,exportType);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("**************************** Export Bot Process Completed **********************");

	}
	
    public static void exportBotAPICall(Properties prop) throws Exception {
		// Access the environment variable
		String exportJwt = prop.getProperty(BotConstants.EXPORT_JWT);
		String exportBody = BotConstants.EMPTY_STRING;
		
		try {
			//Populate export Bosy based on the export type passed
			switch (exportType) {
			case (BotConstants.EXP_NLP):
				exportBody = BotConstants.EXP_NLP_REQ_BODY;
				break;
			case (BotConstants.EXP_BOT_TASKS):
				exportBody =  BotConstants.EXP_BOTTSKS_REQ_BODY;
				break;
			case (BotConstants.EXP_WHT_SETTINGS):
				exportBody =  BotConstants.EXP_WHTSTG_REQ_BODY;
				break;
			default:
				exportBody = BotConstants.EXP_ALL_REQ_BODY;
			}
			// Export API Call
			String botId = prop.getProperty(botName);
			String exportUrl = prop.getProperty(BotConstants.EXPORT_URL) + botId + BotConstants.EXPORT;
			
			System.out.println("botId: " + botId);
			System.out.println("exportUrl: " + exportUrl);
			
			URL exportUrlObj = new URL(exportUrl);
			HttpURLConnection exportConnection = (HttpURLConnection) exportUrlObj.openConnection();
			exportConnection.setRequestMethod(BotConstants.METHOD_POST);
			exportConnection.setRequestProperty(BotConstants.AUTH, exportJwt);
			exportConnection.setRequestProperty(BotConstants.CONTENT_TYPE, BotConstants.APPLICATION_JSON);
			exportConnection.setDoOutput(true);

			OutputStream exportOutputStream = exportConnection.getOutputStream();

			exportOutputStream.write(exportBody.getBytes());
			exportOutputStream.flush();
			exportOutputStream.close();
			System.out.println("Export  API Response Code :: " + exportConnection.getResponseCode());
			Thread.sleep(1000);
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
    }
	
	public static String callExportStatusAPI(Properties prop) throws Exception {
		
		// Export Status API call to get the download URL
		String downloadUrl = BotConstants.EMPTY_STRING;
		HttpURLConnection exportStatusConnection = null;
		StringBuilder expStatusResp = new StringBuilder();
		
		try {
			String botId = prop.getProperty(botName);
			String exportStatusAuth = prop.getProperty(BotConstants.EXPORT_JWT);
			String exportStatusUrl = prop.getProperty(BotConstants.EXP_STATUS_URL)+ botId + BotConstants.EXPORTSTATUS;
			
			System.out.println("botId: " + botId);
			System.out.println("exportStatusAuth: " + exportStatusAuth);
			System.out.println("exportStatusUrl: " + exportStatusUrl);
			
			exportStatusConnection = (HttpURLConnection) new URL(exportStatusUrl).openConnection();
			exportStatusConnection.setRequestMethod(BotConstants.METHOD_GET);
			exportStatusConnection.setRequestProperty(BotConstants.AUTH, exportStatusAuth);
			System.out.println("Export Status API Response Code :: " + exportStatusConnection.getResponseCode());

			if (exportStatusConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = new BufferedInputStream(exportStatusConnection.getInputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));

				String line;
				while ((line = reader.readLine()) != null) {
					expStatusResp.append(line);
				}
				System.out.println("Export API Status Response :: " + expStatusResp);
				JSONObject jsonObject = new JSONObject(expStatusResp.toString());
				downloadUrl = jsonObject.get(BotConstants.DOWNLOAD_URL).toString();
			} else {
				InputStream exportStatusInputStream = exportStatusConnection.getErrorStream();
				System.out.println(exportStatusInputStream);
			}
		}catch(Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
		return downloadUrl;
	}
	
	
    public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
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
       try {
            // Set user email
            ProcessBuilder setEmail = new ProcessBuilder("git", "config", "--global", "user.email", "db@gmail.com");
            Process emailProcess = setEmail.start();
            int emailExitCode = emailProcess.waitFor();
            if (emailExitCode == 0) {
                System.out.println("User email set successfully.");
            } else {
                System.out.println("Error setting user email.");
            }

            // Set user name
            ProcessBuilder setName = new ProcessBuilder("git", "config", "--global", "user.name", "Darshana V");
            Process nameProcess = setName.start();
            int nameExitCode = nameProcess.waitFor();
            if (nameExitCode == 0) {
                System.out.println("User name set successfully.");
            } else {
                System.out.println("Error setting user name.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        String gitRepoPath = "."; // Replace this with the actual path 

         // Git commands        
        String gitAdd = "git add .";
        String gitCommit = "git commit -m 'Updated'";

        // Execute Git commands
        try {
            // executeCommand(gitclone, gitRepoPath);
            // executeCommand(gitRepoPath, gitpull);
            executeCommand(gitRepoPath, gitAdd);
            System.out.println("Executing: " + gitCommit);
            executeCommand(gitRepoPath, gitCommit);
            // System.out.println("Executing: " + gitPush);
            // executeCommand(gitRepoPath, gitPush);
            System.out.println("Changes added, committed and push successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Failed to add, commit, and push changes." + e.getMessage());
        }

    }
    private static void executeCommand(String workingDir, String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new java.io.File(workingDir));
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Failed to execute command: " + command); 
        }
    } 
    
	public static void commitToTargetRepo(Properties prop, String botName,String env,String exportType) throws Exception {

		// String repoUrl =
		// "https://darshana0406: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@github.com/darshana0406/CCT-Bots-Automation.git";
		// String username = "darshana0406";
		// String password =
		// "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";
		// String workspace = "C:\\Users\\gg\\Documents\\Darshana-infy\\Bot-Pipeline";

		try {
			String repoUrl = prop.getProperty(BotConstants.TARGET_REPO_URL);
			String username = prop.getProperty(BotConstants.USERNAME);
			String password = prop.getProperty(BotConstants.PASSWORD);
			SimpleDateFormat dateFormat = new SimpleDateFormat(BotConstants.TS_FORMAT);
			String workspace = prop.getProperty(BotConstants.WS_LOCATION);
			String gitTag = botName;
			String TIMESTAMPS = dateFormat.format(new Date());
			// String filePath = botName + "/" + env + "/" + exportType + "/ExportBot";
			System.out.println(workspace + BotConstants.TMP_PATH);
			FileUtils.deleteDirectory(new File(workspace + BotConstants.TMP_PATH));
			FileUtils.forceMkdir(new File(workspace + BotConstants.TMP_PATH));
			Git git = Git.cloneRepository().setURI(repoUrl).setDirectory(new File(workspace + BotConstants.TMP_PATH))
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
			// Delete all folders from target repo except .git older
			File[] files = new File(workspace + BotConstants.TMP_PATH).listFiles();
			String filePath = workspace + "/repo";
			// String tempPath = workspace + BotConstants.TMP_PATH;

			if (files != null) {
				for (File file : files) {
					if (file.isDirectory() && !file.getName().equals(BotConstants.GIT_EXN)) {
						try {							
							FileUtils.copyDirectory(file, new File(filePath));	

							FileUtils.deleteDirectory(file);
							System.out.println(" Deleted file:: " + file.getName());
							
						} catch (IOException io) {
							io.printStackTrace();
						}
					}
				}
				git.add().addFilepattern(".").setUpdate(true).call();
			}
			FileUtils.copyDirectory(new File(workspace + "/ExportBot"), new File(
					workspace + BotConstants.TMP_PATH + "/" + botName + "/" + env + "/" + exportType + "/ExportBot"));

			FileUtils.copyFile(new File(workspace + "/fullexport.zip"), new File(
					workspace + BotConstants.TMP_PATH + "/" + botName + "/" + env + "/" + exportType + "/fullexport.zip"));

			git.add().addFilepattern(".").call();
		

			git.commit().setMessage("pushing bot configs").call();
			System.out.println("Files are committed to target repo." + botName + "/" + env + "/" + exportType);

			gitTag = botName + "-" + env + "-" + exportType + "-" + TIMESTAMPS;
			git.tag().setName(gitTag).setMessage("tag " + gitTag).call();
			
			git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
					.setRemote(BotConstants.ORIGIN).setRefSpecs(new RefSpec(gitTag)).setForce(true).call();
			System.out.println("GIT Tag is created: " + gitTag);

			FileUtils.copyDirectory(new File(workspace + "/repo/" + env ), new File(
					workspace + "/" + BotConstants.TMP_PATH + "/" + botName + "/" + env ));

			// FileUtils.copyDirectory(new File(workspace + "/ExportBot"), new File(
			// 		workspace + BotConstants.TMP_PATH + "/" + botName + "/" + env + "/" + exportType + "/ExportBot"));

			// FileUtils.copyFile(new File(workspace + "/fullexport.zip"), new File(
			// 		workspace + BotConstants.TMP_PATH + "/" + botName + "/" + env + "/" + exportType + "/fullexport.zip"));

			git.add().addFilepattern(".").setUpdate(false).call();
			
			git.commit().setMessage("pushing all files into repo").call();
			System.out.println("Files are committed to main repo.");

			git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
			.setRemote(BotConstants.ORIGIN).setRefSpecs(new RefSpec(BotConstants.MAIN)).call();
			System.out.println("Files are pushed to main branch of target repo.");
			
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
}
