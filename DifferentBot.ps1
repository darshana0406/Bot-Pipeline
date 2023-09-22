# Export Full bot and rename zip file 
$export_header = @{
    'auth' = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ'
    'content-type' = 'application/json'
}
$export_body = '{
    "exportType": "published",
    "exportOptions": {
        "settings": [
            "botSettings",
            "botVariables",
            "ivrSettings"
        ],
        "tasks": [
            "botTask",
            "knowledgeGraph",
            "smallTalk"
        ],
        "nlpData": [
            "training_data",
            "bot_synonyms",
            "defaultDialog",
            "nlpSettings",
            "utterances",
            "patterns",
            "standardResponses"
        ]
    },
    "subTasks": {
        "alerts": [],
        "actions": [],
        "dialogs": []
    },
    
    "allTasks": true, 
    "customDashboards": true,
    "IncludeDependentTasks": true
}'

git pull

$tmp=Invoke-restMethod -Uri "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export" -Method Post -Headers $export_header -Body $export_body 
Start-Sleep -Seconds 1.5
$export_res = Invoke-restMethod -Uri "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export/status" -Method Get -Headers $export_header 

Invoke-WebRequest $export_res.downloadURL -OutFile fullexport.zip 
Start-Sleep -Seconds 1.5

git config --global user.email "darshanavadalia04@gmail.com"
git config --global user.name "darshana0406"
Expand-Archive -Path fullexport.zip -DestinationPath ./ExportBot  -Force

git add .
git commit -m "Bot Files Update"

git push origin main

# Set variables
$REPO_URL = "https://$test@github.com/darshana0406/CCT-Bots-Automation.git"
$TIMESTAMPS = (Get-Date -Format "yyyyMMddHHmmss")

# Remove and recreate the workspace directory
Remove-Item -Recurse -Force $env:WORKSPACE_TMP
New-Item -ItemType Directory -Path $env:WORKSPACE_TMP

# Clone the Git repository
git clone $REPO_URL $env:WORKSPACE_TMP

# Copy files
Copy-Item -Recurse -Force $env:WORKSPACE/ExportBot $env:WORKSPACE_TMP/CCT_Billing/Dev_NCE/ExportAll 

# Change directory
Set-Location -Path $env:WORKSPACE_TMP/CCT_Billing/Dev_NCE/ExportAll

# Configure Git credentials helper
git config credential.helper store 

# Add, commit, and push changes
git add .
git commit -m "pushing bot configs"
git push origin main

# Create and push a Git tag
$GitTag = "v1.99"  # Replace with your desired tag name
git tag -a "$GitTag-$TIMESTAMPS" -m "tag $GitTag-$TIMESTAMPS"
git push origin "$GitTag-$TIMESTAMPS"



# cd ExportBot
# $upload_res_bot= curl.exe --silent -X POST https://bots.kore.ai/api/public/uploadfile -H 'auth: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw'   -H 'content-type: multipart/form-data' -F file=@botDefinition.json -F fileContext=bulkImport -F fileExtension=json  
# $bot_definitionid= $upload_res_bot | ConvertFrom-Json 

# $upload_res_config= curl.exe --silent -X POST https://bots.kore.ai/api/public/uploadfile -H 'auth: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw'   -H 'content-type: multipart/form-data' -F file=@config.json -F fileContext=bulkImport -F fileExtension=json  
# $bot_configid= $upload_res_config | ConvertFrom-Json 

# $bot_definitionid.fileID
# $bot_configid.fileID


# $import_header = @{
#     'auth' = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw'
#     'content-type' = 'application/json'
# }

# $import_body = '{
#     "botDefinition" : "'+$bot_definitionid.fileID+'",
#     "configInfo" : "'+$bot_configid.fileID+'",
#     "importOptions": {
# 	"nlpData": [
# 		"training_data",
# 		"bot_synonyms",
# 		"nlpSettings",
# 		"defaultDialog",
# 		"standardResponses",
#         "utterances",
#         "patterns"
# 	],
# 	"settings": [
# 		"botSettings",
# 		"ivrSettings",
# 		"botVariables"
# 	],
#       "tasks": [
#             "botTask",
#             "knowledgeGraph",
#             "smallTalk"
#         ],
# 	"options": {
# 		"utterances": {
# 		"append": true,
# 		"replace": true
# 		}
# 	},
# 	"botComponents": [
# 		"linkedBots",
# 		"smallTalk"
# 	],
# 	"customDashboard": true
# } 

# }'

# Start-Sleep -Seconds 1.5
# $import_res=Invoke-RestMethod -Uri "https://bots.kore.ai/api/public/bot/st-c99808ed-b936-5b7d-a49f-a0fad24a1a00/import" -Method Post -Body $import_body -Headers $import_header
# Start-Sleep -Seconds 1.5
# $import_res._id

# Start-Sleep -Seconds 5


# $status_url = "https://bots.kore.ai/api/public/bot/import/status/"+$import_res._id
# $status=Invoke-RestMethod -Uri $status_url -Headers $import_header

# $status.status




