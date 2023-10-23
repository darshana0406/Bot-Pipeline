import java.util.ArrayList;
import java.util.List;

public class BotConstants {

    public static final String BOTID = "botId";
	public static final String EMPTY_STRING = "";	
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String APPLICATION_JSON = "application/json";
	public static final String MULTI_FORM_DATA = "multipart/form-data; boundary= ";
	public static final String EXP_STATUS_URL = "exportStatusUrl";
	public static final String AUTH = "auth";
	public static final String ZIPFILE_PATH = "ZipFilePath";
	public static final String DEST_DIR = "DestDir";
	public static final String FULL_EXP_FILE = "fullexport.zip";
	public static final String TMP_PATH = "/TMP";
	public static final String DOWNLOAD_URL = "downloadURL";
	public static final String TARGET_REPO_URL = "targetRepoUrl";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String WS_LOCATION = "wsLocation";
	public static final String TS_FORMAT = "yyyyMMddHHmmss";
	public static final String FWSLASH = "/";
	public static final String HYPHEN = "-";
	public static final String UNDER_SCORE = "_";
	public static final String ORIGIN = "origin";
	public static final String MAIN = "main";
	public static final String GIT_EXN = ".git";
	public static final String BOT_DEFINITION = "botDefinition";
	public static final String FILE_ID = "fileId";
	public static final String WORKSPACE = "workspace";
	public static final String TOKEN = "Token";
	public static final String EXPORT = "/export";
	public static final String EXPORTSTATUS = "/export/status";
	public static final String IMPORT = "import";
	public static final String BOTDEF_ID = "botDefinition";
	public static final String CONF_INFO_ID = "configInfoId";
    public static final String BOTCONFIG = "BotConfig.yaml";
    public static final String YAML_EXN = ".yaml";
    public static final String NEWBOT = "NewBot";
    public static final String CONFIG = "config";
    
    public static final List <String> fileNames = new ArrayList<String>() {{ 
											add ("botDefinition.json");
											add("config.json");
											add("icon.png");
											add("bot_functions.js");
											}};
    

	
	//Bot names
	public static final String CCT_IVR_BILLING = "cct_ivr_billing";
	public static final String CONV_BILLING_MGR = "TestBot 1";
	public static final String ENV_DEV = "dev_nce";
	public static final String ENV_QA = "qa_nce";
	public static final String ENV_PROD = "prod_nce";

	
	public static final String EXPORTBOT = "ExportBot";
	public static final String EXPORT_JWT = "exportJwt";
	public static final String EXPORT_URL = "exportUrl";
	public static final String EXPORT_BODY = "Export_Body";
	
	public static final String UPLOAD_URL = "uploadUrl";
	public static final String UPLOAD_JWT = "uploadJwt";
	public static final String UPLOAD_FILE = "UploadFileName";
	public static final String UPLOAD_FILE_CONTEXT = "Upload_FileContext";
	public static final String UPLOAD_BOUNDARY = "------------------------abcdef1234567890";
	public static final String UPLOAD_FILE_EXTN = "json";
	public static final String BULK_IMPORT = "bulkImport";

   	
	//Export Types
	public static final String EXP_BOT_TASKS = "Export_BotTasks";
	public static final String EXP_NLP = "Export_NLPData";
	public static final String EXP_WHT_SETTINGS = "Export_WithoutSettings";
	public static final String EXP_ALL = "Export_All";
	
	public static final String EXP_NLP_REQ_BODY = "{\"exportType\": \"published\",\"exportOptions\": {\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"allTasks\": true,\"IncludeDependentTasks\": true}";
	//public static final String EXP_BOTTSKS_REQ_BODY = "{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": false,\"IncludeDependentTasks\": true}";
    public static final String EXP_BOTTSKS_REQ_BODY = "{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"IncludeDependentTasks\": true}";
	//public static final String EXP_WHTSTG_REQ_BODY = "{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": true,\"IncludeDependentTasks\": true}";
    public static final String EXP_WHTSTG_REQ_BODY =  "{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"IncludeDependentTasks\": true}";
	public static final String EXP_ALL_REQ_BODY = "{\"exportType\": \"published\",\"exportOptions\": {\"settings\": [\"botSettings\",\"botVariables\",\"ivrSettings\"],\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"IncludeDependentTasks\": true}";
    
	//Import Types
	public static final String IMP_BOT_TASKS = "ImportBotTasks";
	public static final String IMP_NLP = "ImportNLP";
	public static final String IMP_WHT_SETTINGS = "ImportWithOutSettings";
	public static final String IMP_ALL = "ImportAll";
	
	public static final String IMP_FILE_EXN = "Import_FileExtension";
	public static final String IMP_BOUNDARY = "Import_boundary";
	public static final String BOUND_VAL = "------------------------abcdef1234567890";
	public static final String JSON = "json";
	public static final String IMPORT_JWT = "importJwt";
	public static final String IMPORT_URL = "importUrl";
	public static final String IMPORT_DIR = "targetImpDir";
	
	public static final String CONFIG_FILE = "BotConfig.properties";
	
	public static final String IMP_NLP_REQ_BODY = "\"importOptions\": {\n"
    //public static final String ImportNLP = "\"importOptions\": {\n"
            + "        \"nlpData\": [\n"
            + "            \"training_data\",\n"
            + "            \"bot_synonyms\",\n"
            + "            \"nlpSettings\",\n"
            + "            \"defaultDialog\",\n"
            + "            \"patterns\",\n"    
            + "            \"standardResponses\",\n"
            + "            \"utterances\"\n"
            + "        ],\n"
            + "        \"options\": {\n"
            + "            \"utterances\": {\n"
            + "                \"append\": true,\n"
            + "                \"replace\": true\n"
            + "            }\n"
            + "        },\n"
            + "        \"allTasks\": true,\n"
            + "        \"IncludeDependentTasks\": true\n"
            + "    }\n"
            + "}";
	
	
	public static final String IMP_BOTTSK_REQ_BODY = "\"importOptions\": {\n"
            + "        \"tasks\": [\n"
            + "            \"botTask\",\n"
            + "            \"knowledgeGraph\",\n"
            + "            \"smallTalk\"\n"
            + "        ],\n"
            + "        \"subTasks\": {\n"
            + "            \"alerts\": [],\n"
            + "            \"actions\": [],\n"
            + "            \"dialogs\": []\n"
            + "        },\n"
            + "        \"allTasks\": true,\n"
            + "        \"IncludeDependentTasks\": true\n"
            + "    }\n"
            + "}";
	
	public static final String IMP_BOTWHTSTG_REQ_BODY = "\"importOptions\": {\n"
            + "        \"tasks\": [\n"
            + "            \"botTask\",\n"
            + "            \"knowledgeGraph\"\n"
            + "        ],\n"
            + "        \"nlpData\": [\n"
            + "            \"training_data\",\n"
            + "            \"bot_synonyms\",\n"
            + "            \"nlpSettings\",\n"
            + "            \"defaultDialog\",\n"
            + "            \"patterns\",\n"
            + "            \"standardResponses\",\n"
            + "            \"utterances\"\n"
            + "        ],\n"
            + "        \"options\": {\n"
            + "            \"utterances\": {\n"
            + "                \"append\": true,\n"
            + "                \"replace\": true\n"
            + "            }\n"
            + "        },\n"
            + "        \"botComponents\": [\n"
            + "            \"linkedBots\",\n"
            + "            \"smallTalk\"\n"
            + "        ],\n"
            + "    }\n"
            + "}";
	
	public static final String IMP_ALL_REQ_BODY = "\"importOptions\": {\n"
            + "        \"tasks\": [\n"
            + "            \"botTask\",\n"
            + "            \"knowledgeGraph\"\n"
            + "        ],\n"
            + "        \"nlpData\": [\n"
            + "            \"training_data\",\n"
            + "            \"bot_synonyms\",\n"
            + "            \"nlpSettings\",\n"
            + "            \"defaultDialog\",\n"
            + "            \"patterns\",\n"
            + "            \"standardResponses\",\n"
            + "            \"utterances\"\n"            
            + "        ],\n"
            + "        \"settings\": [\n"
            + "            \"botSettings\",\n"
            + "            \"ivrSettings\",\n"
            + "            \"botVariables\",\n"
            + "            \"ivrSettings\"\n"
            + "        ],\n"
            + "        \"options\": {\n"
            + "            \"utterances\": {\n"
            + "                \"append\": true,\n"
            + "                \"replace\": true\n"
            + "            }\n"
            + "        },\n"
            + "        \"botComponents\": [\n"
            + "            \"linkedBots\",\n"
            + "            \"smallTalk\"\n"
            + "        ]\n"
            + "    }\n"
            + "}" ;
}
