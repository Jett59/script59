package app.cleancode.script59.llvm;

import app.cleancode.script59.serialize.LanguageComponent;
import java.util.List;

import static org.lwjgl.llvm.LLVMCore.*;

public class IrBuilder {
    public long buildIrModule(List<LanguageComponent> components, String fileName) {
        long module = LLVMModuleCreateWithName(fileName.split(".")[0]);
        LLVMSetSourceFileName(module, fileName);

        return module;
    }
}
