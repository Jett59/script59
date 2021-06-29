package app.cleancode.script59.serialize;

import app.cleancode.script59.api.Api;

public class ReturnInstruction implements Instruction {

    @Override
    public void execute() throws Throwable {
        Api.getInstance().returnFromCall();
    }

    @Override
    public String toString() {
        return "return";
    }

}
