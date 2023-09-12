import java.io.File;
import java.io.IOException;

public class GitTest2 {
    public static void main(String[] args) {
        String repositoryPath = "https://github.com/darshana0406/CCT-Bots-Automation.git"; // Replace with the local path to your cloned repository
        String commentFilePath = "C:/Users/gg/Downloads/botexport_shell.txt"; 

        try {
            // Modify the comment file in your cloned repository (use your preferred method)
            // For example, you can read the file, modify the comment, and then write it back.

            // Stage the changes
            ProcessBuilder stageProcess = new ProcessBuilder("git", "add", commentFilePath);
            stageProcess.directory(new File(repositoryPath));
            Process stage = stageProcess.start();
            int stageExitCode = stage.waitFor();

            if (stageExitCode != 0) {
                System.err.println("Failed to stage the changes.");
                return;
            }

            // Commit the changes
            ProcessBuilder commitProcess = new ProcessBuilder("git", "commit", "-m", "Updated comment");
            commitProcess.directory(new File(repositoryPath));
            Process commit = commitProcess.start();
            int commitExitCode = commit.waitFor();

            if (commitExitCode == 0) {
                System.out.println("Comment updated and committed successfully.");
            } else {
                System.err.println("Failed to commit the changes.");
            }

            // Push the changes to the remote repository
            ProcessBuilder pushProcess = new ProcessBuilder("git", "push");
            pushProcess.directory(new File(repositoryPath));
            Process push = pushProcess.start();
            int pushExitCode = push.waitFor();

            if (pushExitCode == 0) {
                System.out.println("Changes pushed to the remote repository.");
            } else {
                System.err.println("Failed to push the changes.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

    

