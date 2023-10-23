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
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

public class ExportBot {
	
	static String exportType = BotConstants.EXP_NLP;
	static String env = BotConstants.ENV_DEV;
		
	public static void main(String[] args) throws IOException, GitAPIException {
		String botName = "Demobot1";
		//Get argument values from Jenkins
		if (args.length > 0) {
			botName = args[0];
			exportType = args[1];
			env = args[2];
		} else {
			System.out.println("No Arguments received from Jenkins");
		}
		System.out.println("ExportType: " + exportType +"\n" +"Environment: " + env+"\n"+"Bot Name: " + botName);

		//String[] envArray = env.split(BotConstants.UNDER_SCORE);
		
		//String region = envArray[1];

		//Yaml Coniguration
		String yamlConfig = BotConstants.BOTCONFIG;

		InputStream inputStream = new FileInputStream(new File("C:/Users/gg/Documents/Darshana-infy/Bot-Pipeline/src/main/config/"+env+"/"+yamlConfig));
		Yaml yaml = new Yaml();
		Map<String, Object> configMap = (Map<String, Object>) yaml.load(inputStream);
		//Retrieving the bot specific config values
		//Map<String, Object> botConfigRegMap = (Map<String, Object>) configMap.get(region);
		//Retrieving the bot specific config values
		Map<String, Object> botConfigMap = (Map<String, Object>) configMap.get(botName.replaceAll("\\s",""));

		System.out.println("Yaml:::" +configMap);	
		System.out.println("botConfigMap:::" +botConfigMap);
		
		System.out.println("**************************** Starting Export Bot Process ****************************");
		
		try {
		// 	//Export Bot API call to export the bot from source account
		// 	exportBotAPICall(botConfigMap);
			
		// 	//Export Status API call to check the export status
		// 	String downloadUrl = callExportStatusAPI(botConfigMap);
		// 	System.out.println("downloadUrl: " + downloadUrl);

		// 	//Download the source files using downloadUrl
		// 	URL downloadUrlObj = new URL(downloadUrl);
		// 	downloadFile(downloadUrlObj, BotConstants.FULL_EXP_FILE);
		// 	System.out.println("File Downloaded in current working directory");
		// 	Thread.sleep(1500);

		// 	// Unzip the files to local workspace
		// 	String zipFile = BotConstants.FULL_EXP_FILE;
		// 	String destDir = BotConstants.EXPORTBOT;
		// 	unzip(zipFile, destDir);
		// 	System.out.println("Files unzipped to " + destDir);
			
		// 	//Commit files to target repository
		// 	commitToTargetRepo(botConfigMap,botName,env,exportType);

		// } catch (Exception e) {
		// 	e.printStackTrace();
		// }
		System.out.println("******** Starting EXP_ALL **********");

                  //Export Bot API call to export the bot from source account
                  exportBotAPICall(botConfigMap, BotConstants.EXP_ALL);
                  //Export Status API call to check the export status
                  String expAllDwnUrl = callExportStatusAPI(botConfigMap);
                  System.out.println("expAllDwnUrl:: " + expAllDwnUrl);
                  
                  //Download the source files using downloadUrl
                  URL downloadAllUrlObj = new URL(expAllDwnUrl);
                  downloadFile(downloadAllUrlObj, BotConstants.FULL_EXP_FILE);
                  System.out.println("FullExp File Downloaded in current working directory");
                  Thread.sleep(1500);

                  // Unzip the files to local workspace
                  String zipFile = env + "/" + BotConstants.EXP_ALL + "/" + BotConstants.EXPORTBOT + "/" + BotConstants.FULL_EXP_FILE;
                  String fullExpdestDir = env + "/" + BotConstants.EXP_ALL + "/" + BotConstants.EXPORTBOT ;
                  unzip(zipFile, fullExpdestDir);
                  System.out.println("Files unzipped to " + fullExpdestDir);
                  System.out.println("******** EXP_ALL Completed **********");
                  
                  System.out.println("******** Starting EXP_NLP **********");

                  //Export Bot API call to export the bot from source account
                 exportBotAPICall(botConfigMap,BotConstants.EXP_NLP);
                  //Export Status API call to check the export status
                  String expNlpDwnUrl = callExportStatusAPI(botConfigMap);
                  System.out.println("expNlpDwnUrl:: " + expNlpDwnUrl);

                  //Download the source files using downloadUrl
                  URL downloadUrlNlp = new URL(expNlpDwnUrl);
                  downloadFile(downloadUrlNlp, BotConstants.FULL_EXP_FILE);
                  System.out.println("NLP File Downloaded in current working directory");
                  Thread.sleep(1500);

                  // Unzip the files to local workspace
                  //String zipFile = BotConstants.FULL_EXP_FILE;
                  String nlpdestDir = env + "/" + BotConstants.EXP_NLP + "/" + BotConstants.EXPORTBOT;
                  unzip(zipFile, nlpdestDir);
                  System.out.println("Files unzipped to " + nlpdestDir);
                  
                  System.out.println("******** EXP_NLP Completed **********");

                  System.out.println("******** Starting EXP_BOT_TASKS **********");
                  //Export Bot API call to export the bot from source account
            exportBotAPICall(botConfigMap,BotConstants.EXP_BOT_TASKS);
                  //Export Status API call to check the export status
                  String botTaskdownloadUrl = callExportStatusAPI(botConfigMap);
                  System.out.println("downloadUrl: " + botTaskdownloadUrl);
                  
                  //Download the source files using downloadUrl
                  URL botTskdownloadUrlObj = new URL(botTaskdownloadUrl);
                  downloadFile(botTskdownloadUrlObj, BotConstants.FULL_EXP_FILE);
                  System.out.println("File Downloaded in current working directory");
                  Thread.sleep(1500);

                  // Unzip the files to local workspace
                  //String zipFile = BotConstants.FULL_EXP_FILE;
                  String expBotdestDir = env + "/" + BotConstants.EXP_BOT_TASKS + "/" + BotConstants.EXPORTBOT;
                  unzip(zipFile, expBotdestDir);
                  System.out.println("Files unzipped to " + expBotdestDir);
                  System.out.println("******** EXP_BOT_TASKS Completed **********");

                  System.out.println("******** Starting EXP_WHT_SETTINGS **********");
                  //Export Bot API call to export the bot from source account
            exportBotAPICall(botConfigMap,BotConstants.EXP_WHT_SETTINGS);
                  
                  
                  //Export Status API call to check the export status
                  String whtStgdownloadUrl = callExportStatusAPI(botConfigMap);
                  System.out.println("downloadUrl: " + whtStgdownloadUrl);

                  //Download the source files using downloadUrl
                  URL whtStgdownloadObj= new URL(whtStgdownloadUrl);
                  downloadFile(whtStgdownloadObj, BotConstants.FULL_EXP_FILE);
                  System.out.println("File Downloaded in current working directory");
                  Thread.sleep(1500);

                  // Unzip the files to local workspace
                  //String zipFile = BotConstants.FULL_EXP_FILE;
                  String whtStgdestDir = env + "/" + BotConstants.EXP_WHT_SETTINGS + "/" +BotConstants.EXPORTBOT;
                  unzip(zipFile, whtStgdestDir);
                  System.out.println("Files unzipped to " + whtStgdestDir);
                  System.out.println("******** Starting EXP_WHT_SETTINGS **********");
                  
                  //Commit files to target repository
            commitToTargetRepo(botConfigMap,botName,env,exportType);


		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("**************************** Export Bot Process Completed **********************");

	}
	
    public static void exportBotAPICall(Map<String, Object> botConfigMap, String exportType) throws Exception {
		// Access the environment variable
		String exportJwt = (String)botConfigMap.get(BotConstants.EXPORT_JWT);
		String exportBody = BotConstants.EMPTY_STRING;
		
		try {
			//Populate export Body based on the export type passed
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
			//String botId = (String)botConfigMap.get(botName.replaceAll("\\s",""));
			//String botId = (String)botConfigMap.get(botName);
			String botId = (String)botConfigMap.get(BotConstants.BOTID);
			String exportUrl = (String)botConfigMap.get(BotConstants.EXPORT_URL) + botId + BotConstants.EXPORT;
			
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
	
	public static String callExportStatusAPI(Map<String, Object> botConfigMap) throws Exception {
		
		// Export Status API call to get the download URL
		String downloadUrl = BotConstants.EMPTY_STRING;
		HttpURLConnection exportStatusConnection = null;
		StringBuilder expStatusResp = new StringBuilder();
		
		try {
			//String botId = (String)botConfigMap.get(botName.replaceAll("\\s",""));
			//String botId = (String)botConfigMap.get(botName);
			String botId = (String)botConfigMap.get(BotConstants.BOTID);
			String exportStatusAuth = (String)botConfigMap.get(BotConstants.EXPORT_JWT);
			String exportStatusUrl = (String)botConfigMap.get(BotConstants.EXP_STATUS_URL)+ botId + BotConstants.EXPORTSTATUS;
			
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
            ProcessBuilder setEmail = new ProcessBuilder("git", "config", "--global", "user.email", "darshanav@gmail.com");
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
     
        String gitAdd = "git add .";
        String gitCommit = "git commit -m 'Updated'";
       

        // Execute Git commands
        try {
            executeCommand(gitRepoPath, gitAdd);
            System.out.println("Executing: " + gitCommit);
            executeCommand(gitRepoPath, gitCommit);
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
    
	public static void commitToTargetRepo(Map<String, Object> botConfigMap, String botName,String env,String exportType) throws Exception {

		try {
			String repoUrl = (String)botConfigMap.get(BotConstants.TARGET_REPO_URL);
			String username = (String)botConfigMap.get(BotConstants.USERNAME);
			String password = (String)botConfigMap.get(BotConstants.PASSWORD);
			SimpleDateFormat dateFormat = new SimpleDateFormat(BotConstants.TS_FORMAT);
			String workspace = (String)botConfigMap.get(BotConstants.WS_LOCATION);
			String TIMESTAMPS = dateFormat.format(new Date());
			// String filePath = botName + "/" + env + "/" + exportType + "/ExportBot";
			String filePath = botName + "/" + env;

			FileUtils.deleteDirectory(new File(workspace + BotConstants.TMP_PATH));
			FileUtils.forceMkdir(new File(workspace + BotConstants.TMP_PATH));
			Git git = Git.cloneRepository().setURI(repoUrl).setDirectory(new File(workspace + BotConstants.TMP_PATH))
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
			// Delete all folders from target repo except .git older
			File[] files = new File(workspace + BotConstants.TMP_PATH).listFiles();

			if (files != null) {
				for (File file : files) {
					if (file.isDirectory() && !file.getName().equals(BotConstants.GIT_EXN)) {
						try {
							FileUtils.deleteDirectory(file);
							System.out.println(" Deleted file:: " + file.getName());
						} catch (IOException io) {
							io.printStackTrace();
						}
					}
				}
								
			}
			//to add only updated folders in the tag
			git.add().addFilepattern(".").setUpdate(true).call();

			// FileUtils.copyDirectory(new File(workspace + "/ExportBot"), new File(
			// 		workspace + BotConstants.TMP_PATH + "/" + botName + "/" + env + "/" + exportType + "/ExportBot"));

			// FileUtils.copyFile(new File(workspace + "/fullexport.zip"), new File(
			// 		workspace + BotConstants.TMP_PATH + "/" + botName + "/" + env + "/" + exportType + "/fullexport.zip"));

			FileUtils.copyDirectory(new File(workspace + "/ExportBot"), new File(
					workspace + BotConstants.TMP_PATH + "/" + botName + "/" + env));

			FileUtils.copyFile(new File(workspace + "/fullexport.zip"), new File(
					workspace + BotConstants.TMP_PATH + "/" + botName + "/" + env ));

			git.add().addFilepattern(".").call();

			git.commit().setMessage("pushing bot configs").call();
			System.out.println("Files are committed to target repo.");
			String gitTag = botName.replaceAll("\\s","") + "-" + env + "-" + exportType + "-" + TIMESTAMPS;
			//String gitTag = botName + "-" + env + "-" + exportType + "-" + TIMESTAMPS;
			git.tag().setName(gitTag).setMessage("tag " + gitTag).call();
			git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
					.setRemote(BotConstants.ORIGIN).setRefSpecs(new RefSpec(gitTag)).call();
			System.out.println("GIT Tag is created: " + gitTag);

			git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
					.setRemote(BotConstants.ORIGIN).setRefSpecs(new RefSpec(BotConstants.MAIN)).call();
			System.out.println("Files are pushed to main branch of target repo.");
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
}
