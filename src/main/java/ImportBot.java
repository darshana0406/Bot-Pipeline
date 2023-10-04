// package cicd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONObject;

public class ImportBot {

	public static String botDefinitionId;
	public static String configInfoId;
	static String importType = "ImportBotTasks";
	static String env = BotConstants.ENV_DEV;;
	static String botName = BotConstants.CCT_IVR_BILLING;
	static String exportType = BotConstants.EXP_BOT_TASKS;

	public static void main(String[] args) throws Exception {
		String tagName = "cct_ivr_billing-dev-ExportBotTasks-20231004151111";

		String[] values = tagName.split(BotConstants.HYPHEN);
		String botName = values[0];
		String exportType = values[2];

		if (args.length > 0) {
			importType = args[0];
			env = args[1];
			System.out.println("Chosen Value: " + importType);
		} else {
			System.out.println("No chosen value provided.");
		}
		// Load property files based on the env selected
		Properties prop = new Properties();
		// InputStream inputStream = new FileInputStream(BotConstants.CONFIG_PATH + env +BotConstants.FWSLASH + "BotConstants.CONFIG_FILE");
		InputStream inputStream = new FileInputStream("C:\\Users\\gg\\Documents\\Darshana-infy\\Bot-Pipeline\\src\\main\\config\\" +env+ "\\BotConfig.properties");
		prop.load(inputStream);
		
		FileUtils.deleteDirectory(new File(BotConstants.IMPORT_DIR));

		String username = prop.getProperty(BotConstants.USERNAME);
		String password = prop.getProperty(BotConstants.PASSWORD);
		// Clone the Git repository
		CloneCommand cloneCommand = Git.cloneRepository();
		cloneCommand.setURI(prop.getProperty(BotConstants.TARGET_REPO_URL));
		cloneCommand.setDirectory(new File(BotConstants.IMPORT_DIR));
		cloneCommand.setBranch(tagName);
		cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
		cloneCommand.call();

		// Checkout the Git tag
		Git git = Git.open(new File(BotConstants.IMPORT_DIR));
		git.checkout().setName(tagName).call();
		
		uploadAPICall(prop);


	}
	
	public static void uploadAPICall(Properties prop) throws Exception {

		String uploadApiUrl = prop.getProperty(BotConstants.UPLOAD_URL);
		String authToken = prop.getProperty(BotConstants.UPLOAD_JWT);

		String fileContext = prop.getProperty(BotConstants.UPLOAD_FILE_CONTEXT);
		String fileExtension = prop.getProperty(BotConstants.UPLOAD_FILE_EXTN);
		String boundary = prop.getProperty(BotConstants.UPLOAD_BOUNDARY);
		String workspaceDir = prop.getProperty(BotConstants.IMPORT_DIR);
		try {

			String[] fileNames = { workspaceDir +  "/" + botName + "/" + env + "/" + exportType + "/ExportBot/botDefinition.json",
					workspaceDir + botName + "/" + env + "/" + exportType + "/ExportBot/config.json" };

			
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
				// connection.setRequestMethod(BotConstants.METHOD_POST);
				// connection.setRequestProperty(BotConstants.AUTH, authToken);
				// connection.setRequestProperty(BotConstants.CONTENT_TYPE, BotConstants.MULTI_FORM_DATA + boundary);
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
				} else {
					configInfoId = jsonObj.getString(BotConstants.FILE_ID);
				}
			}
			System.out.println("botdefinitionId: " + botDefinitionId);
			System.out.println("configId: " + configInfoId);

			importBotAPICall(prop,botDefinitionId,configInfoId);

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	 
	 public static void importBotAPICall(Properties prop,String botDefinitionId,String configInfoId) throws Exception {
			// Access the environment variable
			String importJwt = prop.getProperty(BotConstants.IMPORT_JWT);
			String importBody = BotConstants.EMPTY_STRING;
			
			try {
				
				System.out.println("importType: " + importType);
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
				// Export API Call
				System.out.println(finalImportBody);
				String botId = prop.getProperty(botName);
				
				String importUrl = prop.getProperty(BotConstants.IMPORT_URL) + "st-c99808ed-b936-5b7d-a49f-a0fad24a1a00" + BotConstants.IMPORT;
				
				System.out.println("botId:: " + botId);
				System.out.println("importUrl:: " + importUrl);
				
				URL importUrlObj = new URL(importUrl);
				HttpURLConnection importConnection = (HttpURLConnection) importUrlObj.openConnection();
				// importConnection.setRequestMethod(BotConstants.METHOD_POST);
				// importConnection.setRequestProperty(BotConstants.AUTH, importJwt);
				// importConnection.setRequestProperty(BotConstants.CONTENT_TYPE, BotConstants.APPLICATION_JSON);
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
}
