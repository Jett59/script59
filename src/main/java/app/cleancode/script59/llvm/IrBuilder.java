package app.cleancode.script59.llvm;

import app.cleancode.script59.serialize.FunctionStart;
import app.cleancode.script59.serialize.CallInstruction;
import app.cleancode.script59.serialize.FunctionDeclaration;
import app.cleancode.script59.serialize.LanguageComponent;
import app.cleancode.script59.serialize.Value;
import app.cleancode.script59.values.ValueType;
import java.util.List;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.llvm.LLVMCore.*;

public class IrBuilder {
    public long buildIrModule(List<LanguageComponent> components, String fileName) {
        long module = LLVMModuleCreateWithName(fileName);
        LLVMSetSourceFileName(module, fileName);
        long builder = LLVMCreateBuilder();
        try {
            for (int i = 0; i < components.size(); i++) {
                LanguageComponent component = components.get(i);
                switch (component.getClass().getSimpleName()) {
                    case "FunctionDeclaration": {
                        FunctionDeclaration declaration = (FunctionDeclaration) component;
                        LLVMGetOrInsertFunction(module, declaration.name,
                                getFunctionType(declaration));
                        break;
                    }
                    case "FunctionStart": {
                        FunctionStart functionStart = (FunctionStart) component;
                        FunctionDeclaration declaration = functionStart.declaration;
                        long function = LLVMGetOrInsertFunction(module, declaration.name,
                                getFunctionType(declaration));
                        long codeBlock = LLVMAppendBasicBlock(function, "entry");
                        LLVMPositionBuilderAtEnd(builder, codeBlock);
                        break;
                    }
                    case "CallInstruction": {
                        CallInstruction call = (CallInstruction) component;
                        long function = LLVMGetNamedFunction(module, call.functionSymbol.name());
                        long[] args = new long[call.args.size()];
                        for (int j = 0; j < call.args.size(); j++) {
                            LanguageComponent arg = call.args.get(j);
                            args[j] = buildExpression(arg, builder, module);
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
        } catch (Throwable e) {
            LLVMDisposeModule(module);
            LLVMDisposeBuilder(builder);
            throw new RuntimeException(e);
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
            case STRING -> LLVMPointerType(LLVMInt8Type(), 0);
            default -> -1;
        };
    }

    public long LLVMConstGlobalString(long m, String data, CharSequence name) {
        byte[] bytes = data.getBytes();
        long array = LLVMAddGlobal(m, LLVMArrayType(LLVMInt8Type(), bytes.length + 1), name);
        long[] elements = new long[bytes.length + 1];
        for (int i = 0; i < bytes.length; i++) {
            elements[i] = LLVMConstInt(LLVMInt8Type(), bytes[i], false);
        }
        elements[elements.length - 1] = LLVMConstInt(LLVMInt8Type(), 0, false);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LLVMSetInitializer(array, LLVMConstArray(LLVMInt8Type(), stack.pointers(elements)));
        }
        return array;
    }

    public long buildExpression(LanguageComponent component, long builder, long module) {
        if (component instanceof Value) {
            Value val = (Value) component;
            long result = 0;
            switch (val.type()) {
                case BOOLEAN: {
                    result = LLVMConstInt(LLVMInt1Type(), (Boolean) val.value() ? 1 : 0, false);
                    break;
                }
                case BYTE: {
                    result = LLVMConstInt(LLVMInt8Type(), (Byte) val.value(), false);
                    break;
                }
                case CHAR: {
                    result = LLVMConstInt(LLVMInt8Type(), (Character) val.value(), false);
                    break;
                }
                case DOUBLE: {
                    result = LLVMConstReal(LLVMDoubleTypeKind, (Double) val.value());
                    break;
                }
                case FLOAT: {
                    result = LLVMConstReal(LLVMFloatType(), (Float) val.value());
                    break;
                }
                case INT: {
                    result = LLVMConstInt(LLVMInt32Type(), (Integer) val.value(), false);
                    break;
                }
                case LONG: {
                    result = LLVMConstInt(LLVMInt64Type(), (Long) val.value(), false);
                    break;
                }
                case SHORT: {
                    result = LLVMConstInt(LLVMInt16Type(), (Short) val.value(), false);
                    break;
                }
                case STRING: {
                    result = LLVMConstPointerCast(
                            LLVMConstGlobalString(module, (String) val.value(), "str"),
                            LLVMPointerType(LLVMInt8Type(), 0));
                    break;
                }
                case VOID: {
                    throw new IllegalArgumentException(
                            "Error: Void is not a valid type for value expressions");
                }
            }
            return result;
        } else {
            throw new UnsupportedOperationException("Error: language component '" + component
                    + "' is not recognised by the ir builder");
        }
    }

}
