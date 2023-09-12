import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;


public class GitTest3 {

    private static final String REMOTE_URL = "https://github.com/darshana0406/CCT-Bots-Automation.git";

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("C:/Users/gg/Documents/Darshana-infy/CCT-Bots-Automation", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        try (Git git = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("darshana0406", "Darshana@0406"))
                .setDirectory(localPath)
                .call()) {

            Repository repository = git.getRepository();
            // create the folder
            File theDir = new File(repository.getDirectory().getParent(), "dir1");
            theDir.mkdir();

         // create the file
            File myfile = new File(theDir, "C:/Users/gg/Downloads/botexport_shell");
            // if(!myfile.createNewFile()) {
            //     throw new IOException("Could not create file " + myfile);
            // }


            if (!myfile.exists()) {
                myfile.mkdirs(); // Create parent directory if it doesn't exist
            } else {
            File newFile = new File(myfile, "example.txt");
            }
            // Stage all files in the repo including new files
            git.add().addFilepattern(".").call();

            // and then commit the changes.
            git.commit().setMessage("Commit all changes including additions").call();

            try(PrintWriter writer = new PrintWriter(myfile)) {
                writer.append("Hello, world!");
            }
            // Stage all changed files, omitting new files, and commit with one command
            git.commit()
                    .setAll(true)
                    .setMessage("Commit changes to all files")
                    .call();
         // now open the created repository
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            try (Repository repository1 = builder.setGitDir(localPath)
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .build()) {

                //  try (Git git1 = new Git(repository1)) {
                //      git1.push().call();
                //  }





                System.out.println("Pushed from repository: " + repository1.getDirectory() + " to remote repository at " + REMOTE_URL);
            }
        }
    }

}
    

