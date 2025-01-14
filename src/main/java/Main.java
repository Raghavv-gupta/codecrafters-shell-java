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
            }else if (input.startsWith("type")){
                typeSubstring = input.substring(5);
                if(Arrays.asList(commands).contains(typeSubstring)){
                    System.out.println(typeSubstring + " is a shell builtin");
                }else {
                    System.out.println(typeSubstring + " not found");
                }
            } else {

                System.out.println(input + ": command not found");
            }
        }
    }
}
