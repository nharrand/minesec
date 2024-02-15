package se.su.nh;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassAdapter extends ClassVisitor implements Opcodes {
    PrefixList pl;

    protected ClassAdapter(ClassVisitor classVisitor, PrefixList pl) {
        super(Opcodes.ASM9, classVisitor);
        this.pl = pl;
    }


    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions)
    {
        pl.containsPrefix(desc == null ? "" : desc);
        pl.containsPrefix(signature == null ? "" : signature);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return mv == null ? null : new MethodAdapter(mv, pl);
    }


}