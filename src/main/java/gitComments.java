        import java.io.BufferedReader;
        import java.io.File;
        import java.io.IOException;
        import java.io.InputStreamReader;
        
       public class gitComments {


            public static void main(String[] args) {
                // Set the destination repository URL and download path
                String repositoryURL = "https://github.com/darshana0406/CCT-Bots-Automation.git";
                String downloadPath = "C:/Users/gg/Downloads/botexport_shell.txt";
        
                try {
                    // Create a ProcessBuilder to run the 'git add' command
                    ProcessBuilder processBuilder = new ProcessBuilder("git", "add", repositoryURL, downloadPath);
        
                    // Start the 'git add' process
                    Process process = processBuilder.start();
        
                    // Read and display the process output
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
        
                    // Wait for the process to complete
                    int exitCode = process.waitFor();
        
                    if (exitCode == 0) {
                        System.out.println("Repository add successfully to " + downloadPath);
                    } else {
                        System.err.println("Add failed with exit code " + exitCode);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        


    


    

