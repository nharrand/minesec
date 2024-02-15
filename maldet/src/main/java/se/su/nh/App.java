package se.su.nh;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Hello world!
 *
 */
public class App {


    @Parameter(names = "-h", description = "Help", help = true)
    private boolean help = false;


    @Parameter(names = "-package-list", description = "(Mandatory) Comma separated list of package names to look for.")
    private String packageList;

    @Parameter(names = "-class-dir", description = "(Mandatory) Path to the directory holding the flassfiles to analyze")
    private String classDir;
    @Parameter(names = "-csv", description = "(Mandatory) Path to the result csv")
    private String csv;
    @Parameter(names = "-print-header", description = "Print the header of the csv result files. (If not, results will be appended")
    private boolean printHeader = false;

    public static void main( String[] args )
    {
        App main = new App();
        JCommander jc =JCommander.newBuilder()
                .addObject(main)
                .build();
        jc.parse(args);

        if (main.help || main.packageList == null || main.classDir == null|| main.csv == null) {
            jc.usage();
            System.exit(-1);
        }

        File classDir = new File(main.classDir);
        //get config and create pl
        Collection<File> classes = FileUtils.listFiles(
                classDir,
                new RegexFileFilter(".*.class"),
                DirectoryFileFilter.DIRECTORY
        );
        PrefixList pl = new PrefixList(Arrays.asList(main.packageList.split(",")));
        //list files
        for(File f: classes) {
            try (InputStream classFileInputStream = Files.newInputStream(f.toPath())) {
                System.out.println(f.getName() + ": processed");

                ClassReader cr = new ClassReader(classFileInputStream);
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                ClassVisitor cv = new ClassAdapter(cw, pl);
                cr.accept(cv, 0);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File csv = new File(main.csv);
        try (FileWriter fw = new FileWriter(csv, true)) {
            if(main.printHeader) {
                fw.write("Jar," + pl.printHeader() + "\n");
            }
            fw.append(classDir.getName()).append(",").append(pl.print()).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
