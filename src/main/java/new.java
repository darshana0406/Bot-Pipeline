// import org.eclipse.jgit.api.*;
// import org.eclipse.jgit.lib.Repository;
// import org.eclipse.jgit.transport.URIish;
// import org.eclipse.jgit.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class new {
    public static void main(String[] args) {
        try {
            // Define source and destination repository URLs
            String sourceRepoURL = "https://github.com/darshana0406/Bot-Pipeline.git";
            String destinationRepoURL = "https://github.com/darshana0406/CCT-Bots-Automation.git";

            // Define the file path to be copied
            String sourceFilePath = "fullexport.zip";
            String destinationFilePath = "CCT-Bots-Automation/CCT_Billing/fullexport.zip";

            // Clone the source repository
            Repository sourceRepo = cloneRepository(sourceRepoURL);

            // Clone the destination repository
            Repository destinationRepo = cloneRepository(destinationRepoURL);

            // Read the file from the source repository
            Path sourceFile = sourceRepo.getDirectory().getParentFile().toPath().resolve(sourceFilePath);

            // Write the file to the destination repository
            Path destinationFile = destinationRepo.getDirectory().getParentFile().toPath().resolve(destinationFilePath);
            FileUtils.copy(sourceFile.toFile(), destinationFile.toFile());

            // Commit and push the changes to the destination repository
            Git git = new Git(destinationRepo);
            git.add().addFilepattern(destinationFilePath).call();
            git.commit().setMessage("Copy file from source repo").call();
            git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider("darshana0406", "Darshana@0406")).call();

            // Clean up
            sourceRepo.close();
            destinationRepo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Repository cloneRepository(String repoURL) throws IOException, GitAPIException {
        File localPath = File.createTempFile("gitrepo", "");
        localPath.delete();

        // Clone the repository
        Git.cloneRepository()
                .setURI(repoURL)
                .setDirectory(localPath)
                .call();

        return Git.open(localPath).getRepository();
    }
}
