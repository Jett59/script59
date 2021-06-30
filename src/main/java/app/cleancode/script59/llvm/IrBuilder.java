package app.cleancode.script59.llvm;

import app.cleancode.script59.serialize.BlockStart;
import app.cleancode.script59.serialize.CallInstruction;
import app.cleancode.script59.serialize.FunctionDeclaration;
import app.cleancode.script59.serialize.LanguageComponent;
import app.cleancode.script59.values.ValueType;
import java.util.List;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.llvm.LLVMCore.*;

public class IrBuilder {
    public long buildIrModule(List<LanguageComponent> components, String fileName) {
        long module = LLVMModuleCreateWithName(fileName);
        LLVMSetSourceFileName(module, fileName);
        long builder = LLVMCreateBuilder();
        for (int i = 0; i < components.size(); i++) {
            LanguageComponent component = components.get(i);
            switch (component.getClass().getSimpleName()) {
                case "FunctionDeclaration": {
                    FunctionDeclaration declaration = (FunctionDeclaration) component;
                    LLVMGetOrInsertFunction(module, declaration.name, getFunctionType(declaration));
                    break;
                }
                case "BlockStart": {
                    BlockStart block = (BlockStart) component;
                    FunctionDeclaration declaration = (FunctionDeclaration) components.get(++i);
                    long function = LLVMGetOrInsertFunction(module, declaration.name,
                            getFunctionType(declaration));
                    long codeBlock = LLVMAppendBasicBlock(function, block.name);
                    LLVMPositionBuilderAtEnd(builder, codeBlock);
                    break;
                }
                case "CallInstruction": {
                    CallInstruction call = (CallInstruction) component;
                    long function = LLVMGetNamedFunction(module, call.functionSymbol.name());
                    long[] args = new long[call.args.size()];
                    try {
                        for (int j = 0; j < call.args.size(); j++) {
                            Object arg = call.args.get(j);
                            if (arg instanceof Integer) {
                                int value = (Integer) arg;
                                long pointer = LLVMConstInt(LLVMInt32Type(), value, false);
                                args[j] = pointer;
                            } else if (arg instanceof Short) {
                                short value = (Short) arg;
                                long pointer = LLVMConstInt(LLVMInt16Type(), value, false);
                                args[j] = pointer;
                            } else if (arg instanceof Long) {
                                long value = (Long) arg;
                                long pointer = LLVMConstInt(LLVMInt64Type(), value, false);
                                args[j] = pointer;
                            } else {
                                System.err.println("Warning: argument " + arg + "is not valid");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        LLVMBuildCall(builder, function, stack.pointers(args), "tmp");
                    }
                    break;
                }
                default:
                    break;
            }
        }
        LLVMDumpModule(module);
        return module;
    }

    public long LLVMGetOrInsertFunction(long m, CharSequence name, long functionTy) {
        long result = LLVMGetNamedFunction(m, name);
        if (result == 0) {
            result = LLVMAddFunction(m, name, functionTy);
        }
        return result;
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
