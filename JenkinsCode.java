import org.eclipse.jgit.api.*;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class JenkinsCode {
    public static void main(String[] args) {
   	 //Define source and destination directories
		
    	 String sourceRepoURL = "https://github.com/darshana0406/Bot-Pipeline.git";
		 String targetRepoURL = "https://github.com/darshana0406/CCT-Bots-Automation.git";
	     String username = "darshana0406";
         String password = "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"; 
	     
        try {
            // Clone the source repository
            Git sourceGit = cloneRepository(sourceRepoURL, username, password);

            // Download the file from the source repository
            String filePathInSource = sourceGit.getRepository().getWorkTree().getAbsolutePath()+"\\fullexport.zip";
            System.out.println("filePathInSource  "+filePathInSource);

            downloadFile(sourceGit, filePathInSource);

            // Clone the target repository
            Git targetGit = cloneDestRepository(targetRepoURL, username, password);

            // Add the downloaded file to the target repository
            String filePathInTarget = targetGit.getRepository().getWorkTree().getAbsolutePath()+"\\fullexport.zip";
            System.out.println("filePathInTarget  "+filePathInTarget);

            addFileToRepository(targetGit, filePathInTarget);

            // Commit and push changes to the target repository
            commitAndPushChanges(targetGit, username, password);

            System.out.println("File copied and changes pushed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Git cloneRepository(String repoURL, String username, String password) throws Exception {
        Git git = null;
        try {
            CloneCommand cloneCommand = Git.cloneRepository()
                    .setURI(repoURL)
                    .setDirectory(new File("temp_repo"));

            // Set up credentials for authentication (if required)
            if (username != null && password != null) {
                CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
                cloneCommand.setCredentialsProvider(credentialsProvider);
            }

            git = cloneCommand.call();
        } catch (Exception e) {
            throw e;
        }
        return git;
    }

    private static Git cloneDestRepository(String repoURL, String username, String password) throws Exception {
        Git git = null;
        try {
            CloneCommand cloneCommand = Git.cloneRepository()
                    .setURI(repoURL)
                    .setDirectory(new File("temp_repo1"));

            // Set up credentials for authentication (if required)
            if (username != null && password != null) {
                CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
                cloneCommand.setCredentialsProvider(credentialsProvider);
            }

            git = cloneCommand.call();
        } catch (Exception e) {
            throw e;
        }
        return git;
    }

    private static void downloadFile(Git sourceGit, String filePathInSource) throws Exception {
        try {
            // Checkout the specific file from the source repository
            sourceGit.checkout()
                    .setName("main") // or any branch/commit where the file exists
                    .addPath(filePathInSource)
                    .call();
        } catch (Exception e) {
            throw e;
        }
    }

    private static void addFileToRepository(Git targetGit, String filePathInTarget) throws Exception {
        try {
            // Add the downloaded file to the target repository
            targetGit.add()
                    .addFilepattern(filePathInTarget)
                    .call();
        } catch (Exception e) {
        	e.printStackTrace();
            throw e;
        }
    }

    private static void commitAndPushChanges(Git targetGit, String username, String password) throws Exception {
        try {
            // Commit the changes
            targetGit.commit()
                    .setMessage("Added downloaded file")
                    .call();
            System.out.println("Added downloaded file to Target");

            // Push changes to the target repository
            targetGit.push()
                    .setCredentialsProvider
                    (new UsernamePasswordCredentialsProvider(username, password))
                    .call();
            
           /* // Push the changes to the target GitHub repository
            PushCommand pushCommand = targetGit.push();
            pushCommand.setRemote("origin"); // Change to your remote name if needed
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username,password));
            pushCommand.call();*/
            
        } catch (Exception e) {
        	e.printStackTrace();
            throw e;
        }
    }
}

