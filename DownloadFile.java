import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FileUtils;

public class DownloadFile {


     public static void main(String[] args) {
    	 //Define source and destination directories
		 String sourceRepoURL = "https://github.com/darshana0406/Bot-Pipeline.git";
		 String targetRepoURL= "https://github.com/darshana0406/CCT-Bots-Automation.git";
	     String username = "darshana0406";
	     String password = "Darshana@0406"; 
		 
	        try {
	            // Clone the source repository
	            Git sourceGit = cloneRepository(sourceRepoURL, username, password);

	            // Clone the target repository
	            Git targetGit = cloneRepository(targetRepoURL, username, password);

	          //List of Files to copy
	    		 List<String> filesToCopy = List.of("fullexport.zip");
	    		 
	            // Copy files from source to target repository
	            copyFilesBetweenRepositories(sourceGit, targetGit,filesToCopy);

	            // Push changes to the target repository
	            pushChanges(targetGit, username, password);

	            System.out.println("Files copied and changes pushed successfully.");
	        } catch (IOException | GitAPIException e) {
	            e.printStackTrace();
	        }
	    }

	    private static Git cloneRepository(String repoURL, String username, String password) throws GitAPIException, IOException {
	       
	    	CloneCommand cloneCommand = null ;
	    	File targetDir = new File("temp_repo");
	    	
	    	if(targetDir.exists()) {
	    		System.out.println("targetDir already exists ");
	    		FileUtils.delete(targetDir);
	    	}	
	    	
	    	cloneCommand = Git.cloneRepository()
	    				.setURI(repoURL)
	    				.setDirectory(new File("temp_repo"));

	    		// Set up credentials for authentication 
	    		if (username != null && password != null) {
	    			CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
	    			cloneCommand.setCredentialsProvider(credentialsProvider);
	    		}
	    	
	        return cloneCommand.call();
	        
	    }

	    private static void copyFilesBetweenRepositories(Git sourceGit, Git destGit, List<String> filesToCopy) throws IOException, GitAPIException {
	       		
	    	for(String file: filesToCopy) {
				Path sourcePath = sourceGit.getRepository().getDirectory().toPath().
						resolve("worktree").resolve(file);
								
				Path destPath = destGit.getRepository().getDirectory().toPath().
						resolve("worktree").resolve(file);
				
				//Copy the file from source to target repository
				Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
				
				//Stage the changes in target repository
				destGit.add().addFilepattern(file).call();
			}
	    }

	    private static void pushChanges(Git targetGit, String username, String password) throws GitAPIException {
	      
	    	// Push changes to the target repository
	        PushCommand pushCommand = targetGit.push();
	        
	        // Set up credentials for authentication 
	        if (username != null && password != null) {
	            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
	            pushCommand.setCredentialsProvider(credentialsProvider);
	        }
	        pushCommand.call();
	    }
	}

    

