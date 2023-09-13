public class UIENV extends UI {

    public static String botDefinition ;
    public static String configId;
    
        
    public static void setEnvironmentVariables(String env, String importType) {

      // condition for import NLP

      if(importType.equals("ImportNLP")){
         System.setProperty("Import_Body",  "\"importOptions\": {\n"
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
            + "        \"customDashboard\": true,\n"
            + "        \"allTasks\": true,\n"
            + "        \"IncludeDependentTasks\": true\n"
            + "    }\n"
            + "}");

         
     }

     //condition for import only Task and Sub Task
     else if(importType.equals("ImportBotTasks")){

      System.setProperty("Import_Body",  "\"importOptions\": {\n"
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
            + "}");
         
     }

     //condition for import without Setting
     else if(importType.equals("ImportWithOutSettings")){

      System.setProperty("Import_Body",  "\"importOptions\": {\n"
            + "        \"tasks\": [\n"
            + "            \"botTask\",\n"
            + "            \"knowledgeGraph\"\n"
            + "        ],\n"
            + "        \"nlpData\": [\n"
            + "            \"training_data\",\n"
            + "            \"bot_synonyms\",\n"
            + "            \"nlpSettings\",\n"
            + "            \"defaultDialog\",\n"
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
            + "        \"customDashboard\": true\n"
            + "    }\n"
            + "}");

         
     }

     //condition for import Full Bot
     else{
      System.setProperty("Import_Body",  "    \"importOptions\": {\n"
            + "        \"tasks\": [\n"
            + "            \"botTask\",\n"
            + "            \"knowledgeGraph\"\n"
            + "        ],\n"
            + "        \"nlpData\": [\n"
            + "            \"training_data\",\n"
            + "            \"bot_synonyms\",\n"
            + "            \"nlpSettings\",\n"
            + "            \"defaultDialog\",\n"
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
            + "        ],\n"
            + "        \"customDashboard\": true\n"
            + "    }\n"
            + "}");
         
     }
     // for prod use workspace : VB and bot : demobot1
     if(env.equals("prod")){
     
  }
  // for qa use workspace : VB and bot : demobot2
  else if(env.equals("qa")){
     
      
  }
  // for dev use diffrent account workspace : DB and bot : Exportbot
  else {
     
        System.setProperty("Upload_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw");
        System.setProperty("Upload_URL", "https://bots.kore.ai/api/public/uploadfile");
        System.setProperty("Upload_FileContext", "bulkImport");
        System.setProperty("Upload_FileExtension", "json");
        System.setProperty("Upload_boundary", "------------------------abcdef1234567890");
        String[] values = {"./ExportBot/botDefinition.json", "./ExportBot/config.json"};
        String combinedValues = String.join(",", values);
        System.setProperty("Upload_FileName", combinedValues);
    

         
        
        System.setProperty("Import_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw");
        System.setProperty("Import_URL", "https://bots.kore.ai/api/public/bot/st-c99808ed-b936-5b7d-a49f-a0fad24a1a00/import");
          botDefinitionId = botDefinition; 
          configInfoId = configId;

        //   UploadImport up = new UploadImport();
        //   String botDeninitionId = up.botDefinitionId;
        //  String configInfoId = up.configInfoId;
        //  System.out.println("config"+configInfoId);
         
            //    String botDefinitionId = "4f8342142f30e00e762afd0"; 
            //    String configInfoId = "64f8342142f30e00e762afd9";

         
        System.setProperty("Test","{\n" + " \"botDefinition\": \"" + botDefinitionId + "\",\n"
        + "\"configInfo\": \"" + configInfoId + "\",\n" +System.getProperty("Import_Body"));
        System.setProperty("Import_FileExtension", "json");
        System.setProperty("Import_boundary", "------------------------abcdef1234567890");
        
        System.out.println("botDefinition-->"+botDefinitionId);

        // String s = UploadImportEVariable.getCon();
        // System.out.println(s+".....");
    }
     
  }
      
        //Upload Properties
      
    
    
    
     public static void  getBotdefinitionId(String bot ){
        System.out.println("BotDefinition-->"+bot);
        
        botDefinition = bot;
        botDefinitionId = bot;
     }

     public static void getConfigId(String con){
        System.out.println("Config -->"+con);
        configId = con;
     }

     public static String getBot(String bot){
        return bot;
     }

    
    // protected static String botDefinitionId  = UploadImport.botDefinitionId;
    // public static String configInfoId = UploadImport.configInfoId;

     public void setBotDefinition(String bot){
        this.botDefinition = bot;
     }
     public String getBot1(){
        return botDefinition;
     }

     public void setConfig(String bot){
        this.configId = bot;
     }
     public static String getCon(){
        return configId;
     }


}