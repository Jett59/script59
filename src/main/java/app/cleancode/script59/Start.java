package app.cleancode.script59;

public class Start {
    public static void main(String[] args) {
        ExecutionProperties execution = ExecutionProperties.valueOf(args);
        switch (execution.action) {
            case COMPILE: {
                throw new IllegalArgumentException(
                        "Error: compile mode is currently not supported");
                break;
            }
            case EXECUTE: {
                break;
            }
        }
    }
}
