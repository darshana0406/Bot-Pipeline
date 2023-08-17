public class ExportEVariable {
    public static void setEnvironmentVariables(String env, String exportType) {
        
        
        System.setProperty("Export_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ");
        // System.setProperty("ExportStatus_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ");
        System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export");
        if(exportType=="Fullexport"){
        System.setProperty("Export_Body", "{\"exportType\": \"published\",\"exportOptions\": {\"settings\": [\"botSettings\",\"botVariables\",\"ivrSettings\"],\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": true,\"IncludeDependentTasks\": true}");
        System.out.println("Importing FullExport");
        }
        else if(exportType=="ExportWithoutSetting"){
        System.setProperty("Export_Body", "{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": true,\"IncludeDependentTasks\": true}");
        System.out.println("Importing WithOutSetting");
        }
        else if(exportType=="ExportTask"){
        System.setProperty("Export_Body", "{\"exportType\": \"published\",\"exportOptions\": {\"nlpData\": [\"bot_synonyms\",\"defaultDialog\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": true,\"IncludeDependentTasks\": true}");
        System.out.println("Importing ExportTask");
        }
        else{
        System.out.println("Unknown exportType: " + exportType);
        }
        System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export/status");
        System.setProperty("ZipFile_Path", "fullexport.zip");
        System.setProperty("Dest_Dir", "ExportBot");
        
        
    }
}
