import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.StringUtils;

import com.jcraft.jsch.Session;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Test9 {
    public static void main(String[] args) {
        // Replace these variables with your specific values


        String gitUrl = "https://github.com/darshana0406/CCT-Bots-Automation";
        String user = "darshana0406";
        String repo = "javamain";
        String privateKeyPath = "https://github.com/darshana0406/Bot-Pipeline/blob/javamain/fullexport.zip";
        String workspaceTmp = "C:/Users/gg/Downloads/Test1/test.txt";
        String workspace = "C:/Users/gg/Downloads/Test1";
        String gitTag = "v1.123456";


        try {
            // Clone the repository with SSH key
            cloneRepository(gitUrl, user, repo, privateKeyPath, workspaceTmp);

            // Commit and push changes
            commitChanges(workspaceTmp);
            pushChanges(workspaceTmp);

            // Tag and push the tag
            tagRepository(workspaceTmp, gitTag);

            // Clean up
            cleanup(privateKeyPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void cloneRepository(String gitUrl, String user, String repo, String privateKeyPath, String workspaceTmp) throws GitAPIException {
        Git.cloneRepository()
                .setURI(gitUrl)
                .setDirectory(new File(workspaceTmp))
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, "SHA256:bWvp7yTPArRSHiJ3vuE90btXv2YmkHq6qWJ4OFkMvO0"))
                .call();
    }

    private static void commitChanges(String workspaceTmp) throws GitAPIException, IOException {
        Git git = Git.open(new File(workspaceTmp));
        git.add().addFilepattern(".").call();
        git.commit().setMessage("pushing bot configs").call();
        git.close();
    }

    private static void pushChanges(String workspaceTmp) throws GitAPIException, IOException {
        Git git = Git.open(new File(workspaceTmp));
        git.push()
                .setRemote("origin")
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, "SHA256:bWvp7yTPArRSHiJ3vuE90btXv2YmkHq6qWJ4OFkMvO0"))
                .setRefSpecs(new RefSpec("refs/heads/main"))
                .call();
        git.close();
    }

    private static void tagRepository(String workspaceTmp, String gitTag) throws GitAPIException, IOException {
        Git git = Git.open(new File(workspaceTmp));
        git.tag().setName(gitTag).call();
        git.push()
                .setRemote("origin")
                .setRefSpecs(new RefSpec("refs/tags/" + gitTag))
                .call();
        git.close();
    }

    private static void cleanup(String privateKeyPath) throws Exception {
        Files.deleteIfExists(Paths.get(privateKeyPath));
    }
    }
  
    

