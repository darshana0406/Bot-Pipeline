import org.eclipse.jgit.api.*;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import java.io.File;
import java.io.IOException;

public class Test8 {
    public static void main(String[] args) {
        // Source repository URL
        String sourceRepoURL = "https://github.com/darshana0406/Bot-Pipeline";

        // Target repository URL
        String targetRepoURL = "https://github.com/darshana0406/CCT-Bots-Automation";

        // File path in the source repository
        String sourceFilePath = "https://github.com/darshana0406/Bot-Pipeline/blob/javamain/fullexport.zip";

        // File path in the target repository
        String targetFilePath = "https://github.com/darshana0406/CCT-Bots-Automation/blob/javamain";

        // Credentials for both repositories
        CredentialsProvider sourceCredentials = new UsernamePasswordCredentialsProvider("darshana0406", "Darshana@0406");
        CredentialsProvider targetCredentials = new UsernamePasswordCredentialsProvider("darshana0406", "Darshana@0406");

        try {
            // Clone the source repository
            File sourceRepoDir = File.createTempFile("source-repo", "");
            sourceRepoDir.delete();
            Git sourceGit = Git.cloneRepository()
                    .setURI(sourceRepoURL)
                    .setDirectory(sourceRepoDir)
                    .setCredentialsProvider(sourceCredentials)
                    .call();

            // Open the cloned repository
            Repository sourceRepo = sourceGit.getRepository();

            // Copy the file from the source repository to a temporary directory
            File sourceFile = new File(sourceRepoDir, sourceFilePath);
            File targetFile = new File(targetFilePath);
            // Copy sourceFile to targetFile

            // Initialize a Git repository for the target repository
            Repository targetRepo = FileRepositoryBuilder.create(new File(targetRepoURL));
            Git targetGit = new Git(targetRepo);

            // Add the copied file to the target repository
            targetGit.add().addFilepattern(targetFilePath).call();

            // Commit the changes in the target repository
            targetGit.commit().setMessage("Added file from source repository").call();

            // Push the changes to the target repository
            PushCommand pushCommand = targetGit.push();
            pushCommand.setRemote(targetRepoURL);
            pushCommand.setCredentialsProvider(targetCredentials);
            pushCommand.call();

            System.out.println("File copied and pushed successfully.");
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}



