import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        try(Scanner sc = new Scanner(System.in)){
            while (true) {
                System.out.print("$ ");
                String input = sc.nextLine();
                String[] parts = input.split(" ",2);
                String command = parts[0];
                switch (command) {
                    case "type" -> typeCheck(parts.length>1 ? parts[1] : "");
                    case "exit" -> System.exit(0);
                    case "echo" -> System.out.println(parts[1]);
                    default -> System.out.println(command + ": command not found");
                }
            }
        }
    }

    private static void typeCheck(String command) {
        if (BuiltinCommand.from(command) != null){
           System.out.println(command + " is a shell builtin");
        } else  {
            System.out.println(command + ": not found");
        }
    }
    private enum BuiltinCommand {
        ECHO("echo"),
        EXIT("exit"),
        TYPE("type");
        private final String name;

        BuiltinCommand(String name) {
            this.name = name;
        }

        public static BuiltinCommand from(String name) {
            for (BuiltinCommand command : BuiltinCommand.values()) {
                if (command.name.equals(name)) return command;
            }
            return null;
        }
    }
}
