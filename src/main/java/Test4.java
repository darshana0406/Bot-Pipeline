import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;

public class Test4 {
    public static void main(String[] args) {
        // Replace these with your repository URL and local path
        String remoteRepositoryURL = "https://github.com/darshana0406/CCT-Bots-Automation.git";
        String username = "darshana0406";
        String password = "Darshana@0406";
        String localPath = "C:/Users/gg/Documents/Darshana-infy/CCT-Bots-Automation"; // Change this to your desired local path

        try {
            // Open the existing repository without cloning
            Repository repository = Git.open(new File(localPath)).getRepository();
            Git git = new Git(repository);

            // Perform operations on the repository, e.g., add comments
            // For example, you can list commits
            Iterable<RevCommit> commits = git.log().call();
            for (RevCommit commit : commits) {
                System.out.println("Commit: " + commit.getName() + ", Message: " + commit.getFullMessage());
            }

            // You can also add comments or perform other Git operations here

            // Dispose of the repository when done
            repository.close();

            System.out.println("Repository operations completed.");
        } catch (TransportException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    

