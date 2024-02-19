package se.su.nh;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

class MethodAdapter extends MethodVisitor implements Opcodes {
    PrefixList pl;
    String className;

    public MethodAdapter(final MethodVisitor mv, PrefixList pl, String className) {
        super(ASM9, mv);
        this.pl = pl;
        this.className = className;
    }


    @Override
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String descriptor)
    {
        //processUsage(owner, name, descriptor);
        //pl.containsPrefix(descriptor);
        pl.startWithPrefix(owner + "." + name, className);
        mv.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, final String descriptor, boolean itf)
    {
        pl.startWithPrefix(owner + "." + name, className);
        //pl.startWithPrefix(descriptor);
        mv.visitMethodInsn(opcode, owner, name, descriptor, itf);
    }
}
