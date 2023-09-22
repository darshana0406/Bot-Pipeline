import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GitCommit {

    public static void main(String[] args) {
        // Replace with your GitHub repository details and personal access token (PAT)
        String sourceRepoURL = "https://gitlab.spectrumflow.net/c-parameswari.uthirapathi/bot1";
        String targetRepoURL = "https://gitlab.spectrumflow.net/c-parameswari.uthirapathi/bot9";
        String sourceFileURL = "https://gitlab.spectrumflow.net/c-parameswari.uthirapathi/bot1/-/blob/main/Test.java";
        String targetFileName = "fullexport.txt"; //C:/Users/P3199015/Downloads/Test/fullexport.txt
        String pat = "h_bFUfsaKtPLqLXXBzz2"; // Your personal access token
        File file = new File(targetRepoURL);

        if(file.exists()){
            if(file.isDirectory()){
                deleteDirectory(file);
            } else{
                deleteDirectory(file);
            }

        }

        try {
            // Download the file from the source GitHub URL
           
            
            try {
                downloadFile(sourceFileURL,targetFileName);
                System.out.println("File downloaded successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            

            // Initialize JGit repositories
            //  Git sourceGit = Git.cloneRepository()
            //          .setURI(sourceRepoURL)
            //          .setCredentialsProvider(new UsernamePasswordCredentialsProvider("@c-parameswari.uthirapathi", pat))
            //          .call();
           
   //          deleteDirectory(file);

             Git targetGit = Git.cloneRepository()
                     .setURI(targetRepoURL)
                     .setCredentialsProvider(new UsernamePasswordCredentialsProvider("@c-parameswari.uthirapathi" , pat))
                     .call();

           
            // Add the downloaded file to the target Git repository
            targetGit.add()
                    .addFilepattern("C:/Users/P3199015/Downloads/bot1-main/bot1-main/fullexport.txt")
                    .call();

            // Commit the changes in the target Git repository
            targetGit.commit()
                    .setMessage("Add " + targetFileName)
                    .call();

            // Push the changes to the target GitHub repository
            PushCommand pushCommand = targetGit.push();
            pushCommand.setRemote("origin"); // Change to your remote name if needed
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("@c-parameswari.uthirapathi",pat));
            pushCommand.call();

            System.out.println("File downloaded and uploaded to the target repository successfully.");
        } catch ( GitAPIException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String url, String destinationPath) throws IOException {
        URL fileUrl = new URL(url);
        try (InputStream in = new BufferedInputStream(fileUrl.openStream());
             FileOutputStream out = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
}
    public static void deleteDirectory(File file) {
        
        
        for (File subfile : file.listFiles()) {
 
            // if it is a subfolder,e.g Rohan and Ritik,
            //  recursively call function to empty subfolder
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
 
            // delete files and empty subfolders
            subfile.delete();
           
        

        }
    
}
