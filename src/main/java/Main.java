import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        try(Scanner sc = new Scanner(System.in)){
            System.out.print("$ ");
            while (sc.hasNextLine()) {

                String input = sc.nextLine();
                String command = input.split(" ")[0];
                switch (command) {
                    case "exit" -> System.exit(0);
                    case "echo" -> System.out.println(input.split(" ",2)[1]);
                    default -> System.out.println(command + ": command not found");
                }

                System.out.print("$ ");

            }

        }
    }
}
