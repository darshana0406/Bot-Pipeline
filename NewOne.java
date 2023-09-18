import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewOne {
    public static void main(String[] args) {
        // Replace these variables with your specific values
        String gitUrl = "git@github.com:darshana0406/Bot-Pipeline.git";
        String user = "darshana0406";
        String repo = "main";
        String keyFile = "https://github.com/darshana0406/Bot-Pipeline/blob/main/fullexport.zip";
        String workspaceTmp = "C:/Users/gg/Documents/Darshana-infy";
        String workspace = "C:/Users/gg/Documents/Darshana-infy";
        String gitTag = "v1.123456";


        String newWorkingDirectory = "C:/Users/gg/Documents/Darshana-infy";

        // Use the Paths and FileSystem classes to change the working directory
        Path newDir = Paths.get(newWorkingDirectory);
        FileSystem fileSystem = FileSystems.getDefault();

        try {
            fileSystem.provider().checkAccess(newDir);
            fileSystem.provider().setAttribute(newDir, "dos:hidden", true);
            System.setProperty("user.dir", newWorkingDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now, the working directory has been changed to the specified path
        System.out.println("New working directory: " + System.getProperty("user.dir"));


        try {
            // Clone the repository with SSH key
            String cloneCommand = "git clone -c core.sshCommand=\"ssh -i " + keyFile + "\" " + gitUrl + ":" + user + "/" + repo + ".git " + workspaceTmp;
            executeShellCommand(cloneCommand);

            // Copy files from ExportBot to workspaceTmp
            // (You need to implement this part based on your requirements)

            // Commit and push changes
            String commitCommand = "cd " + workspaceTmp + " && git add . && git commit -m \"pushing bot configs\"";
            executeShellCommand(commitCommand);

            String pushCommand = "cd " + workspaceTmp + " && git -c core.sshCommand=\"ssh -i " + keyFile + "\" push origin main";
            executeShellCommand(pushCommand);

            String tagCommand = "cd " + workspaceTmp + " && git tag " + gitTag;
            executeShellCommand(tagCommand);

            String pushTagCommand = "cd " + workspaceTmp + " && git -c core.sshCommand=\"ssh -i " + keyFile + "\" push origin " + gitTag;
            executeShellCommand(pushTagCommand);

            // Clean up
            String cleanupCommand = "rm -f " + keyFile;
            executeShellCommand(cleanupCommand);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void executeShellCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        // Read the output of the command
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // Wait for the command to complete
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Command exited with error code: " + exitCode);
        }
    }
}