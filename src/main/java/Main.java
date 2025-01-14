import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // Uncomment this block to pass the first stage
        String input, typeSubstring;
        String[] commands = {"echo", "exit", "type"};
        while (true) {

            System.out.print("$ ");

            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();

            if (input.startsWith("exit")) {
                int exitCode = Integer.parseInt(input.substring(5));

                System.exit(exitCode);
            } else if (input.startsWith("echo")) {
                String output = input.substring(5);
                System.out.println(output);
            }else if (input.startsWith("type")) {
                typeSubstring = input.substring(5).trim();

                if (Arrays.asList(commands).contains(typeSubstring)) {
                    System.out.println(typeSubstring + " is a shell builtin");
                } else {
                    // Check in PATH for the command
                    String pathEnv = System.getenv("PATH");
                    if (pathEnv != null) {
                        String[] pathDirs = pathEnv.split(":");
                        String commandPath = findCommandInPath(typeSubstring, pathDirs);

                        if (commandPath != null) {
                            System.out.println(typeSubstring + " is " + commandPath);
                        } else {
                            System.out.println(typeSubstring + ": not found");
                        }
                    } else {
                        System.out.println(typeSubstring + ": not found");
                    }
                }
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
    private static String findCommandInPath(String command, String[] pathDirs) {
        for (String dir : pathDirs) {
            File file = new File(dir, command);
            if (file.exists() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return null; // Command not found
    }
}
