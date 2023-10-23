import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

public class ImportBot {

	public static String botDefinitionId;
	public static String configInfoId;
	
	static String importType = "ImportNLP";
	static String env = BotConstants.ENV_DEV;
	static String exportType = BotConstants.EXP_ALL;
	// static String newBot = BotConstants.NEWBOT;
	static String newBot = "abcd";
	

	public static void main(String[] args) throws Exception {
		String tagName = "Demobot1-dev_nce-export-20231020163158";
		String srcBotName = "";
		String targetBotName = "";
		if (args.length > 0) {
			importType = args[0];
			env = args[1];
			tagName = args[2];
			targetBotName = args[3];
			newBot =args[4];

			System.out.println("Chosen Value: " + importType);
		} else {
			System.out.println("No chosen value provided.");
		}
		String[] values = tagName.split(BotConstants.HYPHEN);
		srcBotName = values[0];
		exportType = values[2];
		System.out.println("Source BotName" + srcBotName);

		//String[] envArray = env.split(BotConstants.UNDER_SCORE);
		//String region = envArray[1];

		//System.out.println("Region" + region);
		
		//Yaml Coniguration
		String yamlConfig = BotConstants.BOTCONFIG;
		InputStream inputStream = new FileInputStream(new File("C:/Users/gg/Documents/Darshana-infy/Bot-Pipeline/src/main/config/"+env+"/"+yamlConfig));
		Yaml yaml = new Yaml();
		Map<String, Object> configMap = (Map<String, Object>) yaml.load(inputStream);
		Map<String, Object> botConfigMap;
		//Map<String, Object> botConfigRegMap = (Map<String, Object>) configMap.get(region);
		// Map<String, Object> botConfigMap = (Map<String, Object>) botConfigRegMap.get(region);
		//System.out.println("Yaml:::" +configMap);	
		//	System.out.println("BotConfigRegMap-" + botConfigRegMap);
		System.out.println ("newBot " + newBot);
		//Retrieving 
		if(newBot !="" && newBot!= null && newBot.length()>1) {
			botConfigMap = (Map<String, Object>)configMap.get(BotConstants.NEWBOT);
		}
		else {
			botConfigMap = (Map<String, Object>) configMap.get(targetBotName.replaceAll("\\s",""));
			
		}

		//Map<String, Object> botConfigMap = (Map<String, Object>) configMap.get(targetBotName.replaceAll("\\s",""));

		
		System.out.println("botConfigMap:::" +botConfigMap);
		String importPath = (String)botConfigMap.get(BotConstants.IMPORT_DIR);
		FileUtils.deleteDirectory(new File(importPath));

		String username = (String)botConfigMap.get(BotConstants.USERNAME);
		String password = (String)botConfigMap.get(BotConstants.PASSWORD);
		// Clone the Git repository
		CloneCommand cloneCommand = Git.cloneRepository();
		cloneCommand.setURI((String)botConfigMap.get(BotConstants.TARGET_REPO_URL));
		cloneCommand.setDirectory(new File(importPath));
		cloneCommand.setBranch(tagName);
		cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
		cloneCommand.call();

		// Checkout the Git tag
		Git git = Git.open(new File(importPath));
		git.checkout().setName(tagName).call();
		
		uploadAPICall(botConfigMap, srcBotName, newBot);
		System.out.println("Target botName" + targetBotName);
	}
	
	public static void uploadAPICall(Map<String, Object> botConfigMap, String botName, String newBot) throws Exception {

		String uploadApiUrl = (String)botConfigMap.get(BotConstants.UPLOAD_URL);
		String authToken = (String)botConfigMap.get(BotConstants.UPLOAD_JWT);

		String fileContext = BotConstants.BULK_IMPORT;
		String fileExtension = BotConstants.UPLOAD_FILE_EXTN;
		String boundary = BotConstants.UPLOAD_BOUNDARY;
		String workspaceDir = (String)botConfigMap.get(BotConstants.IMPORT_DIR);
		String icon = BotConstants.EMPTY_STRING;
		try {
			if("Demobot1".equals (botName)) {
				botName = BotConstants.CONV_BILLING_MGR;
			}

			switch (importType) {
				case (BotConstants.IMP_NLP):
					exportType = BotConstants.EXP_NLP;
					break;
				case (BotConstants.IMP_BOT_TASKS):
					exportType = BotConstants.EXP_BOT_TASKS;
					break;
				case (BotConstants.IMP_WHT_SETTINGS):
					exportType = BotConstants.EXP_WHT_SETTINGS;
					break;
				default:
					exportType = BotConstants.EXP_ALL;
				}	

			String[] fileNames = { workspaceDir +  "/" + botName + "/" + env + "/" + "export/" + exportType + "/ExportBot/botDefinition.json",
					workspaceDir + "/" + botName + "/" + env + "/" + "export/" + exportType + "/ExportBot/config.json",
					workspaceDir + "/" + botName + "/" + env + "/" + "export/" + exportType + "/ExportBot/icon.png" };
				
			
			for (String filePath : fileNames) {
				String postData = "--" + boundary + "\r\n"
						+ "Content-Disposition: form-data; name=\"fileContext\"\r\n\r\n" + fileContext + "\r\n" + "--"
						+ boundary + "\r\n" + "Content-Disposition: form-data; name=\"fileExtension\"\r\n\r\n"
						+ fileExtension + "\r\n" + "--" + boundary + "\r\n"
						+ "Content-Disposition: form-data; name=\"file\"; filename=\""
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
				if (filePath.contains(BotConstants.BOT_DEFINITION)) {
					botDefinitionId = jsonObj.getString(BotConstants.FILE_ID);
					System.out.println("botdefinitionId: " + botDefinitionId);
				}	
				else if (filePath.contains(BotConstants.CONFIG)) {
						configInfoId = jsonObj.getString(BotConstants.FILE_ID);
				}else {						
						icon = jsonObj.getString(BotConstants.FILE_ID);
					}
			}
			System.out.println("botdefinitionId: " + botDefinitionId);
			System.out.println("configId: " + configInfoId);
			System.out.println("Icon: " + icon);

			if(newBot !="" && newBot!= null && newBot.length()>1) {
				importNewBotAPICall(botConfigMap, botDefinitionId, configInfoId, icon, newBot);
			} else {
				importExistingBotAPICall(botConfigMap, botDefinitionId, configInfoId);
			}

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	 
	 public static void importExistingBotAPICall(Map<String, Object> botConfigMap,String botDefinitionId,String configInfoId) throws Exception {
			// Access the environment variable
			String importJwt = (String)botConfigMap.get(BotConstants.IMPORT_JWT);
			String importBody = BotConstants.EMPTY_STRING;
			
			try {
				//Populate import Body based on the import type passed
				switch (importType) {
				case (BotConstants.IMP_NLP):
					importBody = BotConstants.IMP_NLP_REQ_BODY;
					break;
				case (BotConstants.IMP_BOT_TASKS):
					importBody =  BotConstants.IMP_BOTTSK_REQ_BODY;
					break;
				case (BotConstants.IMP_WHT_SETTINGS):
					importBody =  BotConstants.IMP_BOTWHTSTG_REQ_BODY;
					break;
				default:
					importBody = BotConstants.IMP_ALL_REQ_BODY;
				}
				
	             String finalImportBody = "{\n" + " \"botDefinition\": \"" + botDefinitionId + "\",\n"
	                     + "\"configInfo\": \"" + configInfoId + "\",\n" +importBody;
				System.out.println(finalImportBody);
				// Import API Call
				String botId = (String)botConfigMap.get(BotConstants.BOTID);
				//String botId = (String)botConfigMap.get(targetBotName.replaceAll("\\s",""));
				String importUrl = (String)botConfigMap.get(BotConstants.IMPORT_URL) + botId + "/" + BotConstants.IMPORT;
				
				System.out.println("botId:: " + botId);
				System.out.println("importUrl:: " + importUrl);
				
				URL importUrlObj = new URL(importUrl);
				HttpURLConnection importConnection = (HttpURLConnection) importUrlObj.openConnection();
				
				importConnection.setRequestMethod("POST");
                importConnection.setRequestProperty("auth", importJwt);
                importConnection.setRequestProperty("content-type", "application/json");
				
				importConnection.setDoOutput(true);

				OutputStream impOutStream = importConnection.getOutputStream();

				impOutStream.write(finalImportBody.getBytes());
				impOutStream.flush();
				impOutStream.close();
				System.out.println("Import  API Response Code :: " + importConnection.getResponseCode());
				Thread.sleep(1000);
			}catch(Exception e) {
				e.getMessage();
				e.printStackTrace();
			}
	    }

		public static void importNewBotAPICall(Map<String, Object> botConfigMap,String botDefinitionId,String configInfoId,String icon, String newBot) throws Exception {
			// Access the environment variable
			String importJwt = (String)botConfigMap.get(BotConstants.IMPORT_JWT);
									
			try {
				
	             String finalImportBody = "{\n" + " \"botDefinition\": \"" + botDefinitionId + "\",\n"
	                     + "\"configInfo\": \"" + configInfoId + "\",\n"
	                     + " \"icon\": \"" + icon+ "\",\n"
	                     + " \"name\": \"" + newBot+ "\"\n}";
				// Export API Call
				//String botId = (String)configMap.get(targetBotName);
				//String botId = (String)botConfigMap.get(BotConstants.BOTID);
				String importUrl = (String)botConfigMap.get(BotConstants.IMPORT_URL) + BotConstants.IMPORT;
				
				//System.out.println("botId:: " + botId);
				System.out.println("importUrl:: " + importUrl);
				System.out.println("finalImportBody: " + finalImportBody);
				
				URL importUrlObj = new URL(importUrl);
				HttpURLConnection importConnection = (HttpURLConnection) importUrlObj.openConnection();
				importConnection.setRequestMethod(BotConstants.METHOD_POST);
				importConnection.setRequestProperty(BotConstants.AUTH, importJwt);
				importConnection.setRequestProperty(BotConstants.CONTENT_TYPE, BotConstants.APPLICATION_JSON);
				importConnection.setDoOutput(true);

				OutputStream impOutStream = importConnection.getOutputStream();

				impOutStream.write(finalImportBody.getBytes());
				impOutStream.flush();
				impOutStream.close();
				System.out.println("Import  API Response Code :: " + importConnection.getResponseCode());
				Thread.sleep(1000);
			}catch(Exception e) {
				e.getMessage();
				e.printStackTrace();
			}
	    }
}

