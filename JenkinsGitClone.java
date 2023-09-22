import java.io.*;

public class JenkinsGitClone {
    public static void main(String[] args) {
        try {
            // Define the environment variables
            String REPO_URL = "https://darshana0406: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@github.com/darshana0406/CCT-Bots-Automation.git";
            String WORKSPACE = "$WORKSPACE_TMP"; // Replace with the actual workspace path
            String GIT_TAG = "1.76767"; // Replace with your desired tag name
            
            // Get the current timestamp
            String TIMESTAMPS = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

            // Execute shell commands
            ProcessBuilder builder = new ProcessBuilder(
                "sh", "-c",
                "rm -rf " + WORKSPACE + "/TMP && " +
                "mkdir -p " + WORKSPACE + "/TMP && " +
                "git clone " + REPO_URL + " " + WORKSPACE + "/TMP/ && " +
                "cp -r " + WORKSPACE + "/ExportBot " + WORKSPACE + "/TMP/conv_billing_mgr/dev_nce/Export_All/ && " +
                "cd " + WORKSPACE + "/TMP/conv_billing_mgr/dev_nce/Export_All/ && " +
                "git config credential.helper store && " +
                "git add . && " +
                "git commit -m \"pushing bot configs\" && " +
                "git push origin main && " +
                "git tag -a " + GIT_TAG + "-" + TIMESTAMPS + " -m \"tag " + GIT_TAG + "-" + TIMESTAMPS + "\" && " +
                "git push origin " + GIT_TAG + "-" + TIMESTAMPS + " && " +
                "rm -rf " + WORKSPACE + "/TMP"
            );
            builder.directory(new File(WORKSPACE));
            
            // Redirect output and error streams
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectError(ProcessBuilder.Redirect.INHERIT);
            
            // Start the process
            Process process = builder.start();
            
            // Wait for the process to complete
            int exitCode = process.waitFor();
            
            // Check the exit code
            if (exitCode == 0) {
                System.out.println("Git operations completed successfully.");
            } else {
                System.err.println("Git operations failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
