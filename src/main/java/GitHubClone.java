import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.api.CloneCommand;

 

public class GitHubClone {
	    public static void main(String[] args) throws IOException, GitAPIException {

	String REPO_URL = "https://darshana0406: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@github.com/darshana0406/CCT-Bots-Automation.git";
	String username = "darshana0406";
	// String password = "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";
	String password = "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";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	String WORKSPACE = "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\Test";
	String GIT_TAG = "cct_ivr_billing" ;
	String TIMESTAMPS = dateFormat.format(new Date());
	
	String exportType = "Export_Tasks";
    String env = "prod";
        
        if (args.length > 0 ) {
			    exportType=args[1];
				env=args[0];
                System.out.println("Chosen Value: " + exportType); 
                 System.out.println("Chosen Value: " + env);              
            } else {
                System.out.println("No chosen value provided.");
            }
	   GIT_TAG = GIT_TAG + "-" + env + "-" + exportType; 
	ExportEVariable.setEnvironmentVariables(env, exportType);

	FileUtils.deleteDirectory(new File(WORKSPACE + "/TMP"));
	FileUtils.forceMkdir(new File(WORKSPACE + "/TMP"));

		// 	// Create a new Git repository object.
        // Repository repository = new RepositoryBuilder().build();

		// 	 // Filter the contents of the local repository to only include the desired folder.
		//  repository.getRefDatabase().updateRef("HEAD", "refs/heads/master^{tree}");
		//  repository.getConfig().setString("core.sparsecheckout", "true");
		//  repository.getConfig().setString("core.sparsecheckout", "/path/to/desired/folder");
		//  repository.getConfig().save();	

	Git git = Git.cloneRepository()
	        .setURI(REPO_URL)
	        .setDirectory(new File(WORKSPACE + "/TMP"))
	        .call();

	FileUtils.copyDirectory(new File(WORKSPACE + "/ExportBot"),new File(WORKSPACE +
			"/TMP/cct_ivr_billing/" + env +"_nce/" + exportType + "/ExportBot"));
	FileUtils.copyFile(new File(WORKSPACE+"\\fullexport.zip"), new File(WORKSPACE +
			"/TMP/cct_ivr_billing/" + env +"_nce/" + exportType +"/fullexport.zip"));

	git.add().addFilepattern(".").call();

	git.commit().setMessage("pushing bot configs").call();
	System.out.println("Files are committed to target repo.");

	git.tag().setName(GIT_TAG + "_" + TIMESTAMPS).setMessage("tag " + GIT_TAG + "_" + TIMESTAMPS).call();
	git.push().setRemote("origin").setRefSpecs(new RefSpec(GIT_TAG + "_" + TIMESTAMPS)).call();
	System.out.println("GIT Tag is created.");

	git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
			.setRemote("origin")
			.setRefSpecs(new RefSpec("main"))
			.call();
	System.out.println("Files are pushed to main branch of target repo.");

	// FileUtils.deleteDirectory(new File(WORKSPACE + "/TMP"));
	// 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
	        }
	    }