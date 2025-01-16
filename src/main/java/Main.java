import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        Set<String> commands = Set.of("echo", "exit", "type");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine().trim();

            if (input.startsWith("exit")) {
                int exitCode = input.equals("exit") ? 0 : Integer.parseInt(input.substring(5).trim());
                System.exit(exitCode);
            } else if (input.startsWith("echo ")) {
                System.out.println(input.substring(5).trim());
            } else if (input.startsWith("type ")) {
                String arg = input.substring(5).trim();

                if (commands.contains(arg)) {
                    System.out.printf("%s is a shell builtin%n", arg);
                } else {
                    String path = getPath(arg);

                    if (path == null) {
                        System.out.printf("%s: not found%n", arg);
                    } else {
                        System.out.printf("%s is %s%n", arg, path);
                    }
                }
            } else {
                // Handle external commands
                String command = input.split(" ")[0];
                String path = getPath(command);

                if (path == null) {
                    System.out.printf("%s: command not found%n", command);
                } else {
                    // check for external programs

                    String[] inputParse = input.split("\\s+");

                    // call function to check & execute external program
                    execute(inputParse);
                }
            }
        }
    }

    // Method to execute external program
    private static void execute(String[] inputParse) throws IOException {
        String path = System.getenv("PATH"); // get environment variable PATH
        String[] directories = path.split(":"); // get all directories in PATH

        // extract command
        String cmd = inputParse[0];

        // Search for the command in the PATH directories
        for (String dir : directories) {
            Path cmdPath = Paths.get(dir, cmd);

            if (Files.exists(cmdPath) && Files.isExecutable(cmdPath)) {
                // Execute the command with arguments using ProcessBuilder
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command(inputParse); // Add command & arguments
                processBuilder.redirectErrorStream(true); // Merge stdout & stderr

                Process process = processBuilder.start();

                // Read the output of the command (using BufferedReader)
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                try {
                    // Wait for the process to finish
                    process.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;

            }
        }

        // If command not found
        System.out.println(cmd + ": not found");

    }

    // Method to find a command in the PATH environment variable
    private static String getPath(String command) {
        String pathEnv = System.getenv("PATH");
        if (pathEnv == null || pathEnv.isEmpty()) {
            return null;
        }

        for (String path : pathEnv.split(":")) {
            Path fullPath = Path.of(path, command);
            if (Files.isRegularFile(fullPath) && Files.isExecutable(fullPath)) {
                return fullPath.toString();
            }
        }
        return null;
    }
}
