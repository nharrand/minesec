package se.su.nh;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class PrefixList {
    
    List<String> prefixes;
    Map<String, Integer> occurrences = new HashMap<>();
    Map<String, MutablePair<Integer,Set<String>>> occurrencesTrack = new HashMap<>();

    public PrefixList(List<String> prefixes) {
        this.prefixes = prefixes;
    }

    static PrefixList getDefaultSuspiciousAPI() {
        return new PrefixList(Arrays.asList(
                "java/lang/Runtime.exec",
                "java/lang/ProcessBuilder.<init>",
                "java/lang/ProcessBuilder.command",
                "java/lang/ProcessBuilder.start",
                "java/lang/System.load",
                "java/lang/System.loadLibrary",
                "java/awt/Desktop.open",
                "jdk/JShell.eval",
                "javax/script/ScriptEngine.eval",
                "java/util/Base64$Decoder.decode",
                "java/util/Base64$Encoder.encode",
                "java/util/Base64$Encoder.encodeToString",
                "java/net/Socket.<init>",
                "java/net/Socket.getInputStream",
                "java/net/Socket.getOutputStream",
                "java/net/URL.<init>",
                "java/net/URL.openConnection",
                "java/net/URL.openStream",
                "java/net/URI.<init>",
                "java/net/URI.create",
                "java/net/URLConnection.getInputStream",
                "java/net/http/HttpRequest$Builder.GET",
                "java/net/http/HttpRequest$Builder.POST",
                "java/net/URLClassLoader.<init>",
                "java/lang/ClassLoader.loadClass",
                "java/lang/Class.forName",
                "java/lang/Class.getDeclaredMethod",
                "java/lang/Class.getDeclaredField",
                "java/lang/Class.newInstance",
                "java/lang/Method.invoke",
                "java/beans/Introspector.getBeanInfo",
                "java/lang/System.getProperty",
                "java/lang/System.getProperties",
                "java/lang/System.getEnv",
                "java/net/InetAddress.getHostName",
                "java/io/File.<init>",
                "java/io/FileOutputStream.<init>",
                "java/io/FileOutputStream.write",
                "java/nio/Files.newBufferedWriter",
                "java/nio/Files.newOutputStream",
                "java/nio/Files.write",
                "java/nio/Files.writeString",
                "java/nio/Files.copy",
                "java/io/FileWriter.write",
                "java/io/BufferedWriter.write",
                "java/io/RandomAccessFile.write",
                "java/io/RandomAccessFile.read",
                "java/io/RandomAccessFile.readFully",
                "java/nio/Files.newInputStream",
                "java/nio/Files.newBufferedReader",
                "java/nio/Files.readAllBytes",
                "java/nio/Files.readAllLines",
                "java/io/FileInputStream.<init>",
                "java/io/FileInputStream.read",
                "java/io/FileReader.read",
                "java/io/BufferedWriter.write",
                "java/util/Scanner.<init>",
                "java/io/BufferedReader.read"
            ));
    }

    public boolean startWithPrefix(String candidate, String origin) {
        for(String prefix: prefixes) {
            if(candidate.startsWith(prefix)) {
                MutablePair<Integer,Set<String>> e = occurrencesTrack.computeIfAbsent(prefix,(String s) -> new MutablePair<>(0, new HashSet<>()));
                e.setLeft(e.getLeft() + 1);
                e.getRight().add(origin);
                occurrencesTrack.put(prefix, e);
                //occurrences.put(prefix, occurrences.computeIfAbsent(prefix,(String s) -> 0) + 1);
                return true;
            }
        }
        return false;
    }

    public boolean containsPrefix(String candidate, String origin) {
        for(String prefix: prefixes) {
            if(candidate.contains(prefix)) {
                MutablePair<Integer,Set<String>> e = occurrencesTrack.computeIfAbsent(prefix,(String s) -> new MutablePair<>(0, new HashSet<>()));
                e.setLeft(e.getLeft() + 1);
                e.getRight().add(origin);
                occurrencesTrack.put(prefix, e);
                //occurrences.put(prefix, occurrences.computeIfAbsent(prefix,(String s) -> 0) + 1);
                return true;
            }
        }
        return false;
    }

    public String print() {
        String output = "";
        for(String prefix: prefixes) {
            if(output.length() > 0) {
                output += ",";
            }
            //output += occurrences.computeIfAbsent(prefix,(String s) -> 0);
            output += occurrencesTrack.containsKey(prefix) ? occurrencesTrack.get(prefix).getLeft() : 0;
        }
        return output;
    }

    public String printHeader() {
        String output = "";
        for(String prefix: prefixes) {
            if(output.length() > 0) {
                output += ",";
            }
            output += prefix;
        }
        return output;
    }

    public String printDetails() {
        Map<String, Set<String>> fileAPIFound = new HashMap<>();

        for(String api: occurrencesTrack.keySet()) {
            for(String origin: occurrencesTrack.get(api).getRight()) {
                Set<String> found =  fileAPIFound.computeIfAbsent(origin, str -> new HashSet<>());
                found.add(api);
            }
        }

        String output = "";
        for(String file: fileAPIFound.keySet()) {
            output += "{" + file.replace(".class", "") + "}: \n";
            for(String api: fileAPIFound.get(file)) {
                String[] apiClean = api.split("/");
                output += "\t\t\t\t* |" + apiClean[apiClean.length-1] + "|\n";
            }
        }
        return output;
    }
}
