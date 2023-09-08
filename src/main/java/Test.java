    import java.io.*;
    import java.net.URL;
    
   public class Test {
    
        public static void main(String[] args) {
            String fileUrl = "C:/Users/gg/Downloads/botexport_shell.txt"; // Replace with the URL of the file you want to download
            String destinationPath = "https://github.com/darshana0406/CCT-Bots-Automation/tree/javamain/CCT_Billing/Dev_NCE"; // Replace with the desired destination path in the local Git repository
    
            try {
                downloadFile(fileUrl, destinationPath);
                System.out.println("File downloaded successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public static void downloadFile(String fileUrl, String destinationPath) throws IOException {
            URL url = new URL(fileUrl);
            try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(destinationPath)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            }
        }
    }
    



    

