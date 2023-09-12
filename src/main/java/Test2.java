import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class Test2 {
    public static void main(String[] args) throws Exception {
        // // Replace these with your repository URL and credentials
        // String remoteRepositoryURL = "https://github.com/darshana0406/CCT-Bots-Automation.git";
        // String username = "darshana0406";
        // String password = "Darshana@0406";

        // File localRepoDir = new File("C:\\Users\\gg\\Documents\\Darshana-infy\\Bot-Pipeline");
        // String filePath = "C:\\Users\\gg\\Documents\\Darshana-infy\\Bot-Pipeline\\fullexport.zip"; // Path to the file you want to push

        // try (Repository repository = Git.open(localRepoDir).getRepository()) {
        //     Git git = new Git(repository);

        //     // Stage the file
        //     git.add().addFilepattern(filePath).call();

        //     // Commit the change
        //     git.commit().setMessage("Added file: " + filePath).call();

        //     // Push the change to the remote repository
        //     PushCommand pushCommand = git.push();
        //     pushCommand.setRemote("origin");
        //     pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
        //   //  pushCommand.setRefSpecs("refs/heads/master:refs/heads/master"); // Adjust the refspec as needed
        //   //  pushCommand.setForce(true); // Use force if necessary
        //     pushCommand.call();

        //     System.out.println("File pushed to remote repository successfully.");
        // }



        String localRepoPath = "C:/Users/gg/Documents/Darshana-infy/CCT-Bots-Automation";
        String remoteRepoURL = "https://github.com/darshana0406/CCT-Bots-Automation.git";
        String filePath = "C:/Users/gg/Downloads/botexport_shell.txt";

        try {
            // Step 1: Stage the file
            ProcessBuilder stageBuilder = new ProcessBuilder("git", "add", filePath);
            stageBuilder.directory(new File(localRepoPath));
            Process stageProcess = stageBuilder.start();
            stageProcess.waitFor();

            // Step 2: Commit the change
            ProcessBuilder commitBuilder = new ProcessBuilder("git", "commit", "-m", "Added file: " + filePath);
            commitBuilder.directory(new File(localRepoPath));
            Process commitProcess = commitBuilder.start();
            commitProcess.waitFor();

            // Step 3: Push the change
            ProcessBuilder pushBuilder = new ProcessBuilder("git", "push", remoteRepoURL);
            pushBuilder.directory(new File(localRepoPath));
            Process pushProcess = pushBuilder.start();
            pushProcess.waitFor();

            // Check the exit code of the push process
            int pushExitCode = pushProcess.exitValue();
            if (pushExitCode == 0) {
                System.out.println("File pushed to remote repository successfully.");
            } else {
                System.err.println("Error: Push operation failed with exit code " + pushExitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }





    }
}

    

