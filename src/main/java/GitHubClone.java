import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

 

public class GitHubClone {
	    public static void main(String[] args) throws IOException, GitAPIException {

	String REPO_URL = "https://darshana0406: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@github.com/darshana0406/CCT-Bots-Automation.git";
	String username = "darshana0406";
	String password = "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";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	String WORKSPACE = "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\Test";
	String GIT_TAG = "env" ;
	String TIMESTAMPS = dateFormat.format(new Date());
	String exportType = args[0];
        String env = args[1];
        
        if (args.length > 0 ) {
                System.out.println("Chosen Value: " + exportType); 
                 System.out.println("Chosen Value: " + env);              
            } else {
                System.out.println("No chosen value provided.");
            }

	ExportEVariable.setEnvironmentVariables(env, exportType);

	FileUtils.deleteDirectory(new File(WORKSPACE + "/TMP"));
	FileUtils.forceMkdir(new File(WORKSPACE + "/TMP"));
	Git git = Git.cloneRepository()
	        .setURI(REPO_URL)
	        .setDirectory(new File(WORKSPACE + "/TMP"))
	        .call();
	FileUtils.copyDirectory(new File(WORKSPACE + "/ExportBot"),new File(WORKSPACE +
			"/TMP/cct_ivr_billing/dev_nce/Export_All/ExportBot"));
	FileUtils.copyFile(new File(WORKSPACE+"\\fullexport.zip"), new File(WORKSPACE +
			"/TMP/cct_ivr_billing/dev_nce/Export_All/fullexport.zip"));

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
	        }
	    }