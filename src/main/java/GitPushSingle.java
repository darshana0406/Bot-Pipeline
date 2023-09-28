import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitPushSingle {

     public static void main(String[] args) throws IOException, GitAPIException {

	String REPO_URL = "https://darshana0406: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@github.com/darshana0406/CCT-Bots-Automation.git";
	String username = "darshana0406";
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
	Git git = Git.cloneRepository()
	        .setURI(REPO_URL)
	        .setDirectory(new File(WORKSPACE + "/TMP"))
	        .call();
	FileUtils.copyDirectory(new File(WORKSPACE + "/ExportBot"),new File(WORKSPACE +
			"/TMP/cct_ivr_billing/" + env +"_nce/" + exportType + "/ExportBot"));
	FileUtils.copyFile(new File(WORKSPACE+"\\fullexport.zip"), new File(WORKSPACE +
			"/TMP/cct_ivr_billing/" + env +"_nce/" + exportType +"/fullexport.zip"));

	// git.add().addFilepattern("ExportBot").call();

	// git.commit().setMessage("pushing bot configs").call();
	// System.out.println("Files are committed to target repo.");
    String filePath = "C:/ProgramData/Jenkins/.jenkins/workspace/Test";

     AddCommand add = git.add();
     add.addFilepattern(filePath);
     add.call();
     git.add().addFilepattern(filePath).call();

    // File localDir = new File(filePath);
    //     File[] localFiles = localDir.listFiles();

    //     if (localFiles != null) {
    //         for (File file : localFiles) {
    //             // Delete all files except the one to retain
    //             if (!file.getName().equals("ExportBot")) {
    //                 Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
    //             }
    //         }
    //     }
    //   git.add().addFilepattern(".").call();
    System.out.println("File added successfully");

	git.tag().setName(GIT_TAG + "_" + TIMESTAMPS).setMessage("tag " + GIT_TAG + "_" + TIMESTAMPS).call();
	git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).setRemote("origin").setRefSpecs(new RefSpec(GIT_TAG + "_" + TIMESTAMPS)).call();
	System.out.println("GIT Tag is created.");

	git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
			.setRemote("origin")
			.setRefSpecs(new RefSpec("main"))
			.call();
	System.out.println("Files are pushed to main branch of target repo.");

    
}
}