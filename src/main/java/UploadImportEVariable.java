import java.util.Arrays;

public class UploadImportEVariable {


    public static void setEnvironmentVariables() {
        
        //Upload Properties

        System.setProperty("Upload_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw");
        System.setProperty("Upload_URL", "https://bots.kore.ai/api/public/uploadfile");
        System.setProperty("Upload_FileContext", "bulkImport");
        System.setProperty("Upload_FileExtension", "json");
        System.setProperty("Upload_boundary", "------------------------abcdef1234567890");
        System.setProperty("Upload_FileName",   ".ExportBot/botDefinition.json" );
        System.setProperty("Upload_FileName",   "./ExportBot/config.json" );
      

        // String[] values = {"./ExportBot/botDefinition.json", "./ExportBot/config.json"};
        // String combinedValues = String.join(";", values); 
        // System.setProperty("myProperty", combinedValues);
                    
        String retrievedProperty = System.getProperty("Upload_FileName");
        if (retrievedProperty != null) {
            String[] fileNames = retrievedProperty.split(",");
            Arrays.stream(fileNames).forEach(System.out::println);
    } else {
        System.out.println("Upload_FileName property is not set.");
    }

        //Import properties

        System.setProperty("Import_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw");
        System.setProperty("Import_URL", "https://bots.kore.ai/api/public/bot/st-c99808ed-b936-5b7d-a49f-a0fad24a1a00/import");
         String configInfoId = "";
         String botDefinitionId = "";
        System.setProperty("Import_Body", "{\n" + "    \"botDefinition\": \"" + botDefinitionId + "\",\n"
                    + "    \"configInfo\": \"" + configInfoId + "\",\n" + "    \"importOptions\": {\n"
                    + "        \"nlpData\": [\n" + "            \"training_data\",\n"
                    + "            \"bot_synonyms\",\n" + "            \"nlpSettings\",\n"
                    + "            \"defaultDialog\",\n" + "            \"standardResponses\",\n"
                    + "            \"utterances\",\n" + "            \"patterns\"\n" + "        ],\n"
                    + "        \"settings\": [\n" + "            \"botSettings\",\n" + "            \"ivrSettings\",\n"
                    + "            \"botVariables\"\n" + "        ],\n" + "        \"tasks\": [\n"
                    + "            \"botTask\",\n" + "            \"knowledgeGraph\",\n" + "            \"smallTalk\"\n"
                    + "        ],\n" + "        \"options\": {\n" + "            \"utterances\": {\n"
                    + "                \"append\": true,\n" + "                \"replace\": true\n" + "            }\n"
                    + "        },\n" + "        \"botComponents\": [\n" + "            \"linkedBots\",\n"
                    + "            \"smallTalk\"\n" + "        ],\n" + "        \"customDashboard\": true\n" + "    }\n"
                    + "}");
        System.setProperty("Import_FileExtension", "json");
        System.setProperty("Import_boundary", "------------------------abcdef1234567890");
            
    }


}