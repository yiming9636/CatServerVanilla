package catserver.server.remapper;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;

import static org.objectweb.asm.Opcodes.*;

import net.minecraft.launchwrapper.IClassTransformer;

public class MethodTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) return null;

        if (transformedName.equals("net.minecraft.entity.Entity")) {
            basicClass = patchEntity(basicClass);
        }

        return basicClass;

    }

    private byte[] patchEntity(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        new ClassReader(basicClass).accept(classNode, 0);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        /*
         * public UUID getUniqueID() {
         *      this.func_110124_au();
         * }
         */

        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "getUniqueID", "()Ljava/util/UUID;", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/entity/Entity", "func_110124_au", "()Ljava/util/UUID;", false);
        mv.visitInsn(ARETURN);
        mv.visitEnd();

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
