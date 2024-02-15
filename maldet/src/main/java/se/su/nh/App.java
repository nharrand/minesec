package se.su.nh;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        //get config and create pl
        //list files
    }





    public static void visitJar(String pathToJar, PrefixList pl, String pathToCsv) throws IOException {
        JarFile jarFile = new JarFile(new File(pathToJar));
        Enumeration<JarEntry> entries = jarFile.entries();
        //DependencyTree dt = new DependencyTree();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            if (entryName.endsWith(".class")) {
                //DependencyTree dt = new DependencyTree();
                InputStream classFileInputStream = jarFile.getInputStream(entry);
                try {
                    ClassReader cr = new ClassReader(classFileInputStream);
                    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    ClassVisitor cv = new ClassAdapter(cw, pl);
                    cr.accept(cv, 0);

                } finally {
                    classFileInputStream.close();
                }
            }
        }
    }
}
