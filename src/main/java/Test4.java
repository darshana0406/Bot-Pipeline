import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.*;
import java.net.URL;
import java.nio.file.*;

public class Test4 {
    public static void main(String[] args) {
        // Replace with your GitHub repository details and personal access token (PAT)
        String sourceRepoURL = "https://github.com/darshana0406/Bot-Pipeline";
        String targetRepoURL = "https://github.com/darshana0406/CCT-Bots-Automation";
        String sourceFileURL = "https://github.com/darshana0406/Bot-Pipeline/blob/javamain/fullexport.zip";
        String targetFileName = "https://github.com/darshana0406/CCT-Bots-Automation/blob/javamain/target-file.txt";
        String pat = "SHA256:bWvp7yTPArRSHiJ3vuE90btXv2YmkHq6qWJ4OFkMvO0"; // Your personal access token

        try {
            // Download the file from the source GitHub URL
            downloadFile(new URL(sourceFileURL), targetFileName);

            // Initialize JGit repositories
            // Git sourceGit = Git.cloneRepository()
            //         .setURI(sourceRepoURL)
            //         .setCredentialsProvider(new UsernamePasswordCredentialsProvider(pat, "Darshana@0406"))
            //         .call();
            
             Git targetGit = Git.cloneRepository()
                     .setURI(targetRepoURL)
                     .setCredentialsProvider(new UsernamePasswordCredentialsProvider(pat, "Darshana@0406"))
                     .call();

           

            // Add the downloaded file to the target Git repository
            targetGit.add()
                    .addFilepattern(targetFileName)
                    .call();

            // Commit the changes in the target Git repository
            targetGit.commit()
                    .setMessage("Add " + targetFileName)
                    .call();

            // Push the changes to the target GitHub repository
            PushCommand pushCommand = targetGit.push();
            pushCommand.setRemote("origin"); // Change to your remote name if needed
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(pat, "Darshana@0406"));
            pushCommand.call();

            System.out.println("File downloaded and uploaded to the target repository successfully.");
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(URL url, String fileName) throws IOException {
 
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
