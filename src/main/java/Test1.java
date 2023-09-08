 import java.io.IOException;

public class Test1 {
    public static void main(String[] args) {
        try {
            String gitCommand = "git add /path/to/your/file.txt";
            Process process = Runtime.getRuntime().exec(gitCommand);

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("File added successfully.");
            } else {
                System.err.println("Failed to add file.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

    
}
