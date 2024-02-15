package se.su.malijar.payloads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessSpawner implements Runnable {
    @Override
    public void run() {
        // Replace this with the command you want to run
        String command = "ls -l";

        try {
            Process process = new ProcessBuilder().command("bash", "-c", command).start();

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the command to complete
            int exitCode = process.waitFor();
            System.out.println("Command exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
