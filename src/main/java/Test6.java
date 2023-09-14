import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class Test6 {


     public static void main(String[] args) {
        // Replace with your GitHub repository details and personal access token (PAT)
        String sourceRepoURL = "https://github.com/darshana0406/Bot-Pipeline";
        String targetRepoURL = "https://github.com/darshana0406/CCT-Bots-Automation";
        String sourceFileURL = "https://github.com/darshana0406/Bot-Pipeline/blob/javamain/fullexport.zip";
        String targetFileName = "https://github.com/darshana0406/CCT-Bots-Automation/blob/javamain"; //C:/Users/gg/Downloads/Test1/test.txt
        String pat = "SHA256:bWvp7yTPArRSHiJ3vuE90btXv2YmkHq6qWJ4OFkMvO0"; // Your personal access token

        try {
            // Download the file from the source GitHub URL
            
            try {
                downloadFile(sourceFileURL, targetFileName);
                System.out.println("File downloaded successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
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
        } catch ( GitAPIException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String url, String destinationPath) throws IOException {
        URL fileUrl = new URL(url);
        try (InputStream in = new BufferedInputStream(fileUrl.openStream());
             FileOutputStream out = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
}
}
