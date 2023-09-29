import java.util.HashMap;
import java.util.Map;

public class ExportEVariable {

    private static final Map<String, String> botDtlMap = new HashMap<>();
    static {
    	botDtlMap.put("cct_ivr_billing-devBotId", "st-e5669197-991d-5971-9417-a422368a0805");
    	botDtlMap.put("cct_ivr_billing-qaBotId", "st-c72008d0-ce30-571b-ba13-1078b89dbef4");
    	botDtlMap.put("cct_ivr_billing-prodBotId", "st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132");
    	botDtlMap.put("cct_ivr_billing-devJwtToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUwZDA1N2YwLWY2MDUtNTljYS05OGFjLWI5MWJhMGQ3MmVlMiJ9.xmrKV3z7a9yoDlG7geOlCbaxgzWzVvvXM40LhdJya1A");
    	botDtlMap.put("cct_ivr_billing-qaJwtToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWJmNWFkNGUyLTE4MjgtNTc2MS04YmY2LTQyNjg2OGI0NWUyYiJ9.tl5Cy379FH0Tws4Mu2f9uDSnKoh0JIaxFgD5XjgQD3k");
    	botDtlMap.put("cct_ivr_billing-prodJwtToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ");
    }
    public static void setEnvironmentVariables(String botName, String env, String exportType ) { 
        String botIdKey = botName + "-" + env + "BotId";
        String botId = "";
        if (botDtlMap.containsKey(botIdKey)) {
            botId = botDtlMap.get(botIdKey);
        }
        String JwtToken ="";
        String JwtKey = botName+"-"+env+"JwtToken";
        if (botDtlMap.containsKey(JwtKey)) {
            JwtToken = botDtlMap.get(JwtKey);
        }
       System.out.print(JwtToken);
       System.out.print(botId);
        //condition for Export only NLPData
        if(exportType.equals("ExportNLP")){
            System.setProperty("Export_Body","{\"exportType\": \"published\",\"exportOptions\": {\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"allTasks\": true,\"IncludeDependentTasks\": true}");
            System.setProperty("ZipFile_Path", "fullexport.zip");
            System.setProperty("Dest_Dir", "ExportNLP");
        }

        //condition for Export only Task and Sub Task
        else if(exportType.equals("ExportBotTasks")){
             System.setProperty("Export_Body","{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": false,\"IncludeDependentTasks\": true}");
             System.setProperty("ZipFile_Path", "fullexport.zip");
             System.setProperty("Dest_Dir", "ExportBot");
        }

        //condition for Export without Setting
        else if(exportType.equals("ExportWithOutSettings")){
            System.setProperty("Export_Body","{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": true,\"IncludeDependentTasks\": true}");
             System.setProperty("ZipFile_Path", "fullexport.zip");
            System.setProperty("Dest_Dir", "ExportBot");
        }

        //condition for Export Full Bot
        else{
            System.setProperty("Export_Body","{\"exportType\": \"published\",\"exportOptions\": {\"settings\": [\"botSettings\",\"botVariables\",\"ivrSettings\"],\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"IncludeDependentTasks\": true}");
             System.setProperty("ZipFile_Path", "fullexport.zip");
            System.setProperty("Dest_Dir", "ExportBot");
            
        }

        // for prod use workspace : VB and bot : demobot1
        if(env.equals("prod")){
            System.setProperty("Export_JWT", JwtToken);
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/" +botId +"/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/" +botId +"/export/status");
        
        }
        // for qa use workspace : VB and bot : demobot2
        else if(env.equals("qa")){
            System.setProperty("Export_JWT",JwtToken);
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/" +botId +"/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/" +botId +"/export/status");
            
        }
        // for dev use diffrent account workspace : DB and bot : Exportbot
        else {
            System.setProperty("Export_JWT", JwtToken);
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/" +botId +"/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/" +botId +"/export/status");
          
        }
            
        
        
         
        
        
    }
}



