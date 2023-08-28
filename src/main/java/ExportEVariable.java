public class ExportEVariable {
    public static void setEnvironmentVariables(String env, String exportType) {    
       
        //condition for Export only NLPData
        if(exportType.equals("ExportNLP")){
            System.setProperty("Export_Body","{\"exportType\": \"published\",\"exportOptions\": {\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"allTasks\": true,\"customDashboards\": false,\"IncludeDependentTasks\": true}");
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
            System.setProperty("Export_Body","{\"exportType\": \"published\"}");
             System.setProperty("ZipFile_Path", "fullexport.zip");
            System.setProperty("Dest_Dir", "ExportBot");
        }

        // for prod use workspace : VB and bot : demobot1
        if(env.equals("prod")){
            System.setProperty("Export_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ");
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export/status");
            // System.setProperty("Git_Repo", "https://github.com/darshana0406/BotExportFiles.git");
            // System.setProperty("git_repo_name", "darshana0406"); 
            // System.setProperty("project_name", "BotExportFiles"); 
            
        }
        // for qa use workspace : VB and bot : demobot2
        else if(env.equals("qa")){
            System.setProperty("Export_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWJmNWFkNGUyLTE4MjgtNTc2MS04YmY2LTQyNjg2OGI0NWUyYiJ9.tl5Cy379FH0Tws4Mu2f9uDSnKoh0JIaxFgD5XjgQD3k");
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/export/status");
            // System.setProperty("Git_Repo", "https://github.com/darshana0406/testrepo.git");
            // System.setProperty("git_repo_name", "darshana0406"); 
            // System.setProperty("project_name", "testrepo");
            //  System.setProperty("ZipFile_Path", "fullexport.zip");
            // System.setProperty("Dest_Dir", "testrepo/ExportBot");
            
        }
        // for dev use diffrent account workspace : DB and bot : Exportbot
        else {
            System.setProperty("Export_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUwZDA1N2YwLWY2MDUtNTljYS05OGFjLWI5MWJhMGQ3MmVlMiJ9.xmrKV3z7a9yoDlG7geOlCbaxgzWzVvvXM40LhdJya1A");
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/st-e5669197-991d-5971-9417-a422368a0805/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/st-e5669197-991d-5971-9417-a422368a0805/export/status");
            // System.setProperty("Git_Repo", "https://github.com/darshana0406/BotExportFiles.git");
            //  System.setProperty("ZipFile_Path", "fullexport.zip");
            // System.setProperty("Dest_Dir", "ExportBot");
        }
            
        
        
         
        
        
    }
}


// if [ "$env" == "dev" ]; then
//     TAG_NAME=$GitTag
//     TARGET_REPO_URL=$gitrepo1
// elif [ "$env" == "prod" ]; then
//     TAG_NAME=$GitTag
//     TARGET_REPO_URL=$gitrepo2
// elif [ "$env" == "qa" ]; then
//     echo "Invalid"
// else
//     echo "Invalid environment selected"
//     exit 1
// fi

// # Clone the target repository
// git clone "$TARGET_REPO_URL"

// REPO_NAME=$(basename "$TARGET_REPO_URL" .git)
// cd "$REPO_NAME"

// # Add a tag
// git tag "$TAG_NAME"

// # Push the tag to the same repository
// git push origin "$TAG_NAME"
