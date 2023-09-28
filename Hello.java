import java.io.*;

public class Hello {

  

    public static void main(String[] args) {

        String sourceRepoUrl = "https://github.com/vijayvelduti/spare-parts";

        String destinationRepoUrl = "https://github.com/vijayvelduti/Cummins";

        String fileName = "bags.html";

        String localDirectory = "c:\\Users\\gg\\Documents";

        try {

            // Clone source repository

            cloneRepository(sourceRepoUrl, localDirectory);

            // Copy the specific file to another local directory

            String filePath = localDirectory + File.separator + fileName;

            String newLocalDirectory = "C:\\Users\\gg\\Documents\\GITTags";

            String newFilePath = newLocalDirectory + File.separator + fileName;

            copyFile(filePath, newFilePath);

            // Initialize Git in the new directory

            executeCommand(newLocalDirectory, "git init");

            // Add all files and commit changes

            executeCommand(newLocalDirectory, "git add.");

            executeCommand(newLocalDirectory, "git commit -m\"initial commit\"");

            // Set the new repository as the remote

            executeCommand(newLocalDirectory, "git remote add origin" + destinationRepoUrl);

            // Push to the new repository

            executeCommand(newLocalDirectory, "git push -u origin master");

            System.out.println("Process completed successfully!");

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }

    }

    private static void cloneRepository(String sourceUrl, String destinationDirectory)
            throws IOException, InterruptedException {

        executeCommand(null, "git clone" + sourceUrl + "" + destinationDirectory);

    }

    private static void copyFile(String source, String destination) throws IOException {

        File sourceFile = new File(source);

        File destinationFile = new File(destination);

        InputStream in = new FileInputStream(sourceFile);

        OutputStream out = new FileOutputStream(destinationFile);

        byte[] buffer = new byte[1024];

        int length;

        while ((length = in.read(buffer)) > 0) {

            out.write(buffer, 0, length);

        }

        in.close();

        out.close();

    }

    private static void executeCommand(String workingDirectory, String command)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();

        if (workingDirectory != null) {

            processBuilder.directory(new File(workingDirectory));

        }

        processBuilder.command("bash", "-c", command);

        Process process = processBuilder.start();

        process.waitFor();

    }

}