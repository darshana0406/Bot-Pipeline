import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.URIish;

import java.io.File;

public class Test7 {
    public static void main(String[] args) {
        String localPath = "C:/Users/gg/Documents/Darshana-infy/Bot-Pipeline";

        // Relative file path to add
        String filePathToAdd = "https://github.com/darshana0406/CCT-Bots-Automation/blob/javamain";

        // Remote Git repository URL
        String remoteRepoURL = "https://github.com/darshana0406/CCT-Bots-Automation";

        try {
            // Open the local Git repository
            FileRepository localRepo = new FileRepository(localPath + "/.git");
            Git git = new Git(localRepo);

            // Add the file to the Git staging area
            git.add().addFilepattern(filePathToAdd).call();

            // Commit the changes
            git.commit().setMessage("Added a new file").call();

            // Push the changes to the remote repository
            PushCommand pushCommand = git.push();
            pushCommand.setRemote(remoteRepoURL);

            // Set credentials (username and password)
            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("darshana0406", "SHA256:bWvp7yTPArRSHiJ3vuE90btXv2YmkHq6qWJ4OFkMvO0" );
            pushCommand.setCredentialsProvider(credentialsProvider);

            pushCommand.call();

            System.out.println("File added and pushed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    

