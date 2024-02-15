package se.su.malijar.payloads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkAccess implements Runnable {


    public NetworkAccess() {}

    @Override
    public void run() {
        String url = "http://perdu.com";
        try {
            String pageContent = downloadPage(url);
            System.out.println("Page content:\n" + pageContent);
        } catch (IOException e) {
            System.err.println("Error downloading the page: " + e.getMessage());
        }
    }

    private static String downloadPage(String urlString) throws IOException {
        URL url = new URL(urlString);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        }
    }
}
