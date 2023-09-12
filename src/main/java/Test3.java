import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test3 {
    public static void main(String[] args) throws Exception {
       
          // Replace these with your repository URL and local path
        String remoteRepositoryURL = "https://github.com/darshana0406/CCT-Bots-Automation.git";
        String username = "darshana0406";
        String password = "Darshana@0406";
        String localPath = "C:/Users/gg/Downloads/Testing"; // Change this to your desired local path
        String filePath = "botexport_shell.txt"; // Name of the file you want to add

        try {
            // Clone the repository
            CloneCommand cloneCommand = Git.cloneRepository()
                    .setURI(remoteRepositoryURL)
                    .setDirectory(new File(localPath));

            Git git = cloneCommand.call();

            // Create and add the file to the repository
            File fileToAdd = new File(localPath, filePath);
            try (FileWriter writer = new FileWriter(fileToAdd)) {
                writer.write("This is the content of the file.");
            }

            // Add the file to the Git index
            git.add()
                .addFilepattern(filePath)
                .call();

            // Commit the changes
            RevCommit commit = git.commit()
                .setMessage("Added a new file: " + filePath)
                .call();

            // Push the changes to the remote repository
            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
            git.push()
                .setCredentialsProvider(credentialsProvider)
                .call();

            System.out.println("File added and pushed to the repository.");
        } catch (TransportException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

    

