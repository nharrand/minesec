package se.su.nh;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ClassAdapterTest {

    @Test
    public void testClassReading() throws IOException {
        InputStream classFileInputStream = Files.newInputStream(new File("src/test/resources/classes/se/su/malijar/payloads/NetworkAccess.class").toPath());
        PrefixList pl = new PrefixList(Arrays.asList("java/net/URL.openStream", "java/io/InputStreamReader.<init>"));

        ClassReader cr = new ClassReader(classFileInputStream);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassAdapter(cw, pl);
        cr.accept(cv, 0);

        assertEquals("1,1",pl.print());

        System.out.println(pl.printHeader());
        System.out.println(pl.print());
    }

    @Test
    public void testClassReading2() throws IOException {
        InputStream classFileInputStream = Files.newInputStream(new File("src/test/resources/classes/se/su/malijar/payloads/NetworkAccess.class").toPath());
        PrefixList pl = new PrefixList(Arrays.asList("java/net/URL.openStream", "java/io/InputStreamReader.<init>"));

        ClassReader cr = new ClassReader(classFileInputStream);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassAdapter(cw, pl);
        cr.accept(cv, 0);

        assertEquals("1,1",pl.print());

        System.out.println(pl.printHeader());
        System.out.println(pl.print());
    }

}