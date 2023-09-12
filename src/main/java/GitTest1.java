import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import java.io.File;
import java.io.IOException;
public class GitTest1 {
    public static void main(String[] args) {

        String repoPath ="https://github.com/darshana0406/CCT-Bots-Automation.git";
        String filePathToAdd = "C:/Users/gg/Downloads/botexport_shell.txt";
        String branchName = "javamain"; 
       

        try {
            // Open the existing Git repository
          //  Repository repository = new RepositoryBuilder().setGitDir(new File(repoPath)).build();
            FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
            Repository repository;
            // Repository repository = repositoryBuilder.setGitDir(new File(repoPath))
            //                                         .readEnvironment()
            //                                         .findGitDir()
            //                                         .build();
 
           if (repoPath.startsWith("http://") || repoPath.startsWith("https://")) {
             // Remote repository (URL)
             repository = repositoryBuilder.setGitDir(new File(repoPath)).readEnvironment().findGitDir().build();
             
         } else {
             // Local repository (file path)
             repository = repositoryBuilder.setWorkTree(new File(repoPath)).readEnvironment().findGitDir().build();
         }

            // Initialize a Git instance
            Git git = new Git(repository);

            // Create a new branch

            git.checkout().setName(branchName).call();

            // Create an AddCommand to add the file
            AddCommand add = git.add();

            // Add the file to the index (staging area)
            add.addFilepattern(filePathToAdd).call();

            // Commit the changes
            git.commit()
                    .setMessage("Added file: " + filePathToAdd)
                    .call();

            // Push the changes to the repository
            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("darshana0406", "Darshana@0406"); // Replace with your credentials
            git.push()
                    .setRemote("origin") // Replace with your remote name
                    .setCredentialsProvider(credentialsProvider)
                    .setRefSpecs(new RefSpec(branchName + ":" + branchName))
                    .call();

            System.out.println("File added to Git repository and pushed to the remote branch.");

            // Close the Git instance
            git.close();
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}

    


