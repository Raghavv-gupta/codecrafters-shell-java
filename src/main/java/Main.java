import java.io.IOException;
import java.nio.file.*;
import java.util.*;

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
                    String[] fullCommand = input.split("\\s+"); // Split command and arguments
                    fullCommand[0] = path; // Replace command name with full path

                    try {
                        // Execute the external command
                        ProcessBuilder processBuilder = new ProcessBuilder(fullCommand);
                        processBuilder.inheritIO(); // Inherit input/output streams
                        Process process = processBuilder.start();
                        int exitCode = process.waitFor(); // Wait for the process to complete

                        if (exitCode != 0) {
                            System.out.printf("%s: exited with code %d%n", command, exitCode);
                        }
                    } catch (IOException | InterruptedException e) {
                        // Catch exceptions related to process execution
                        System.out.println("Error executing the command: " + e.getMessage());
                    }
                }
            }
        }
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
