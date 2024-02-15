package se.su.malijar;

import se.su.malijar.payloads.BytecodeLoader;
import se.su.malijar.payloads.FileAccess;
import se.su.malijar.payloads.NetworkAccess;
import se.su.malijar.payloads.ProcessSpawner;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        List<Runnable> toRun = new ArrayList<>();
        //toRun.add(new NetworkAccess());
        //toRun.add(new BytecodeLoader());
        //toRun.add(new FileAccess());
        toRun.add(new ProcessSpawner());

        for(Runnable r: toRun) {
            r.run();
        }
    }
}
