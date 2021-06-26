package app.cleancode.script59;

import java.util.ArrayList;
import java.util.List;

public class ExecutionProperties {
    public static ExecutionProperties valueOf(String[] args) {
        ExecutionProperties result = new ExecutionProperties();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                switch (arg) {
                    case "-c": {
                        break;
                    }
                    default:
                        System.err.println("Warning: unknown parameter '" + arg + "'");
                        break;
                }
            } else {
                result.inputFiles.add(arg);
            }
        }
        return result;
    }

    public RunAction action = RunAction.EXECUTE;
    public List<String> inputFiles = new ArrayList<>();

}
