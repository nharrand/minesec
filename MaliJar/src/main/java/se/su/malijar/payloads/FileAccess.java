package se.su.malijar.payloads;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class FileAccess implements Runnable {
    @Override
    public void run() {
        Path parent = FileSystems.getDefault().getPath("").toAbsolutePath().getParent();
        try {
            Files.find(parent, 999, (p, bfa) -> bfa.isRegularFile()).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
