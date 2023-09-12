import java.io.*;
import java.util.*;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GItTest {
    public static void main(String[] args) {
        try {
            // Replace with the path to your Git repository
            String repoPath = "https://github.com/darshana0406/CCT-Bots-Automation.git";

            // Open the Git repository
            Repository repo = new FileRepositoryBuilder()
                    .setGitDir(new File(repoPath + "/.git"))
                    .build();

            Git git = new Git(repo);

            // Specify the path to the file you want to add
            String filePath = "C:/Users/gg/Downloads/botexport_shell.txt";

            // Add the file to the Git index
            AddCommand add = git.add();
            add.addFilepattern(filePath).call();

            // Commit the changes
            git.commit()
                .setMessage("Added file: " + filePath)
                .call();

            // Close the Git repository
            git.close();

            System.out.println("File added successfully.");
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}

    

