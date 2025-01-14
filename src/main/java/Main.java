import java.nio.file.Files;
import java.nio.file.Path;
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
                        // String[] pathDirs = pathEnv.split(":");
                        String commandPath = getPath(typeSubstring);

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
                String command = input.split(" ")[0];
                String path = getPath(command);
                if (path == null) {
                  System.out.printf("%s: command not found%n", command);
                } else {
                  String fullPath = path + input.substring(command.length());
                  Process p = Runtime.getRuntime().exec(fullPath.split(" "));
                  p.getInputStream().transferTo(System.out);
                }
            }
        }
    }
    private static String getPath(String command) {
        for (String path : System.getenv("PATH").split(":")) {
          Path fullPath = Path.of(path, command);
          if (Files.isRegularFile(fullPath)) {
            return fullPath.toString();
          }
        }
        return null;
    }
}
