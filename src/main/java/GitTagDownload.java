import java.io.File;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;

public class GitTagDownload {

    public static void main(String[] args) throws Exception {
        // Get the Git tag name
        String tagName = "cct_ivr_billing_20230925212737";

        // Clone the Git repository
        CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setURI("https://github.com/darshana0406/CCT-Bots-Automation.git");
        cloneCommand.setDirectory(new File("c:\\Users\\gg\\Documents\\GITTags"));
        cloneCommand.setBranch(tagName);
        cloneCommand.call();

        // Checkout the Git tag
        Git git = Git.open(new File("c:\\Users\\gg\\Documents\\GITTags"));
        git.checkout().setName(tagName).call();

        // Close the Git repository
        git.close();
    }
}