import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        try(Scanner sc = new Scanner(System.in)){
            while (true) {
                System.out.print("$ ");
                String input = sc.nextLine();
                String[] parts = input.split(" ",2);
                String[] arguments = input.split(" ");
                String command = parts[0];
                if (BuiltinCommand.from(command)!=null) handleBuiltin(command, parts);
                else handleExternal(command, arguments);
            }
        }
    }

    private static void handleExternal(String command, String[] args) {
        String fullPath = getFullPath(command);

        if (fullPath != null){
            try {
                ProcessBuilder pb = new ProcessBuilder(args);
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println(command + ": command not found");
        }

    }

    private static void handleBuiltin(String command, String[] parts) {
        switch (command) {
            case "type" -> typeCheck(parts.length>1 ? parts[1] : "");
            case "exit" -> System.exit(0);
            case "echo" -> System.out.println(parts[1]);
            default -> System.out.println(command + ": command not found");
        }
    }

    private static void typeCheck(String command) {
        if (BuiltinCommand.from(command) != null){
           System.out.println(command + " is a shell builtin");
           return;
        }
        String fullPath = getFullPath(command);
        if (fullPath != null){
            System.out.println(command + " is " + fullPath);
        } else{
            System.out.println(command + ": not found");
        }
    }

    private static String getFullPath(String command) {
        String pathEnv = System.getenv("PATH");
        if (pathEnv != null) {
            String[] paths = pathEnv.split(File.pathSeparator);
            for (String path : paths) {
                Path fullPath = Paths.get(path, command);
                if (Files.exists(fullPath) && Files.isExecutable(fullPath)) {
                    return fullPath.toString();
                }
            }
        }
        return null;
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
