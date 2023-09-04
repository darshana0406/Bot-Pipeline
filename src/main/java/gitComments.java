import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class gitComments {
    public static void main(String[] args) {
        // Set the path to your Git repository
        String repositoryPath = "git@github.com:darshana0406/CCT-Bots-Automation.git";

        // Set the commit message
        String commitMessage = "Your commit message goes here";

        try {
            // Create a ProcessBuilder to run Git commands
            ProcessBuilder processBuilder = new ProcessBuilder("git", "commit", "-m", commitMessage);
            processBuilder.directory(new java.io.File(repositoryPath));

            // Start the Git commit process
            Process process = processBuilder.start();

            // Read and display the process output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Commit successful.");
            } else {
                System.err.println("Commit failed with exit code " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

    


    

