import java.io.*;

public class JenkinsCode {
    public static void main(String[] args) {
      
String REPO_URL = "https://darshana0406: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@github.com/darshana0406/CCT-Bots-Automation.git";
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
String TIMESTAMPS = dateFormat.format(new Date());
FileUtils.deleteDirectory(new File(WORKSPACE + "/TMP"));
FileUtils.forceMkdir(new File(WORKSPACE + "/TMP"));
Git.cloneRepository()
        .setURI(REPO_URL)
        .setDirectory(new File(WORKSPACE + "/TMP"))
        .call();
FileUtils.copyDirectory(new File(WORKSPACE + "/ExportBot"), new File(WORKSPACE + "/TMP/conv_billing_mgr/dev_nce/Export_All"));
Git git = Git.open(new File(WORKSPACE + "/TMP/conv_billing_mgr/dev_nce/Export_All"));
StoredConfig config = git.getRepository().getConfig();
config.setString("credential", null, "helper", "store");
config.save();
git.add().addFilepattern(".").call();
git.commit().setMessage("pushing bot configs").call();
git.push().setRemote("origin").setRefSpecs(new RefSpec("main")).call();
git.tag().setName(GIT_TAG + "-" + TIMESTAMPS).setMessage("tag " + GIT_TAG + "-" + TIMESTAMPS).call();
git.push().setRemote("origin").setRefSpecs(new RefSpec(GIT_TAG + "-" + TIMESTAMPS)).call();
FileUtils.deleteDirectory(new File(WORKSPACE + "/TMP"));
        }
    }