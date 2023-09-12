 import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.core.util.FileUtils;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class Test1 {
    public static void main(String[] args) throws IOException {
    //     try {
    //         String gitCommand = "git add C:/Users/gg/Documents/Darshana-infy/Bot-Pipeline/ExportBot";
    //         Process process = Runtime.getRuntime().exec(gitCommand);

    //         int exitCode = process.waitFor();

    //         if (exitCode == 0) {
    //             System.out.println("File added successfully.");
    //         } else {
    //             System.err.println("Failed to add file.");
    //         }
    //     } catch (IOException | InterruptedException e) {
    //         e.printStackTrace();
    //     }


// Replace with the correct local file path
String filePathToAdd = "C:/Users/gg/Downloads/botexport_shell.txt"; // On Windows





String repoPath = "https://github.com/darshana0406/CCT-Bots-Automation.git";

try {
    
    // Open the Git repository
    // Repository repository = new FileRepositoryBuilder()
    //         .setGitDir(new File(repoPath + "/.git"))
    //         .build();

    // // Initialize a Git instance
    // Git git = new Git(repository);


    File localPath = new File("C:/Users/gg/Downloads/Testing/test1", "");
          if(!localPath.createNewFile()) {
              throw new IOException("Could not delete temporary file " + localPath);
          }

        
        // then clone
        System.out.println("Downloading  from ----->" + repoPath + " to -----> " + localPath);
         Git git = Git.cloneRepository()
                 .setURI(repoPath)
                 .setCredentialsProvider(new UsernamePasswordCredentialsProvider("darshana0406", "Darshana@0406"))
                 .setDirectory(localPath)
                 .call();

      
    // Create an AddCommand to add the file
    AddCommand add = git.add();

    // Add the file to the index (staging area)
    add.addFilepattern(filePathToAdd).call();

    // Commit the changes
     git.commit()
            .setMessage("Added file: " + filePathToAdd)
            .call();
    //   git.push().call();

         System.out.println("File added to Git repository and committed successfully.");

    // Close the Git instance
      git.close();
        }  catch ( GitAPIException e) {
        e.printStackTrace();
    }

    }
}

    

