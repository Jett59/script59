package app.cleancode.script59.llvm;

import app.cleancode.script59.serialize.FunctionDeclaration;
import app.cleancode.script59.serialize.LanguageComponent;
import app.cleancode.script59.values.ValueType;
import java.util.List;
import org.lwjgl.PointerBuffer;
import static org.lwjgl.llvm.LLVMCore.*;

public class IrBuilder {
    public long buildIrModule(List<LanguageComponent> components, String fileName) {
        long module = LLVMModuleCreateWithName(fileName);
        LLVMSetSourceFileName(module, fileName);
        for (LanguageComponent component : components) {
            switch (component.getClass().getSimpleName()) {
                case "FunctionDeclaration": {
                    FunctionDeclaration declaration = (FunctionDeclaration) component;
                    if (LLVMGetNamedFunction(module, declaration.name) == 0) {
                        LLVMAddFunction(module, declaration.name, getFunctionType(declaration));
                    }
                    break;
                }
            }
        }
        LLVMDumpModule(module);
        return module;
    }

    public long getFunctionType(FunctionDeclaration declaration) {
        PointerBuffer argTypes = null;
        try {
            argTypes = PointerBuffer.allocateDirect(declaration.arguments.size());
            for (int i = 0; i < declaration.arguments.size(); i++) {
                argTypes.put(llvmValueType(declaration.arguments.get(i).type()));
            }
            argTypes.flip();
            return LLVMFunctionType(llvmValueType(declaration.returnType), argTypes, false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (argTypes != null) {
                argTypes.free();
            }
        }
        return -1;
    }

    public long llvmValueType(ValueType type) {
        return switch (type) {
            case VOID -> LLVMVoidType();
            case BOOLEAN -> LLVMInt1Type();
            case BYTE -> LLVMInt8Type();
            case CHAR -> LLVMInt8Type();
            case DOUBLE -> LLVMDoubleType();
            case FLOAT -> LLVMFloatType();
            case INT -> LLVMInt32Type();
            case LONG -> LLVMInt64Type();
            case SHORT -> LLVMInt16Type();
            default -> -1;
        };
    }

}
