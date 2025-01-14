import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // Uncomment this block to pass the first stage
        while(true){

            System.out.print("$ ");
    
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
    
            if(input.startsWith("exit")){
                int exitCode = Integer.parseInt(input.substring(5));

                System.exit(exitCode);
            }else{

                System.out.println(input + ": command not found");
            }
        }
    }
}
