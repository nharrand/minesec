package se.su.nh;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        //File input = new File("/home/lyadis/Documents/Minecraft-SSC/u-1.html");
        File input = new File("/home/lyadis/Documents/Minecraft-SSC/scrap/s-1.html");

        System.out.println("Done");

        BufferedWriter output = new BufferedWriter(new FileWriter("/home/lyadis/Documents/Minecraft-SSC/mod-list.csv", true));
        output.write("\"Title\"," + "\"Author\"," + "\"Download_link\"," + "\"Download_nb\"," + "\"Created\","  + "\"Updated\"," + "\"Size\"," + "\"Game_Version\"," + "\"Flavor\"," + "\"Tags\"\n" );

        File outputFolder = new File("/home/lyadis/Documents/Minecraft-SSC/scrap/pages");

        for(File page: outputFolder.listFiles()) {
            System.out.println("Processing page: " + page.getName());
            processPage( page, output );
        }

        //processPage( new File("/home/lyadis/Documents/Minecraft-SSC/scrap/pages/s-30.html"), output );

        output.close();
    }


    public static void processPage( File p, BufferedWriter output ) throws IOException {
        boolean debug = false;
        Document page = Jsoup.parse(p);

        Elements mods = page.select(".project-card");
        for (Element mod : mods) {

            if(debug) System.out.println(" ----------------------------------- ");

            //Title
            Elements elemHolder =  mod.select(".ellipsis");
            String title = elemHolder.get(0).text().replace("\"", "");
            if(debug) System.out.println("mod title: " + title);
            //author
            elemHolder =  mod.select(".author-name");
            String author = elemHolder.get(0).text().replace("\"", "");
            if(debug) System.out.println("mod author: " + author);
            //link to page
            //link to download

            elemHolder =  mod.select(".download-cta");
            String download = elemHolder.get(0).attr("href");
            if(debug) System.out.println("dl link: " + download);



            //dl nb
            elemHolder =  mod.select(".detail-downloads");
            String dlnb = elemHolder.get(0).text();
            if(debug) System.out.println("mod dlnb: " + dlnb);
            //date
            elemHolder =  mod.select(".detail-created");
            String date = elemHolder.get(0).text();
            if(debug) System.out.println("mod created: " + date);
            //last update
            elemHolder =  mod.select(".detail-updated");
            String update = elemHolder.get(0).text();
            if(debug) System.out.println("mod updated: " + update);
            //size
            elemHolder =  mod.select(".detail-size");
            String size = elemHolder.get(0).text();
            if(debug) System.out.println("mod size: " + size);
            //game version
            elemHolder =  mod.select(".detail-game-version");
            String gameVer = elemHolder.get(0).text();
            if(debug) System.out.println("mod game version: " + gameVer);
            //quilt
            elemHolder =  mod.select(".detail-flavor");
            String flavor = "";
            if(elemHolder.size() > 0) {
                flavor = elemHolder.get(0).text();
                if (debug) System.out.println("mod flavor: " + flavor);
            }


            //tags
            elemHolder =  mod.select(".categories");
            String tags = "";
            Elements els = elemHolder.get(0).select("li");
            for(Element el: els) {
                tags += ", " + el.text();
            }
            if (tags.length() > 0) tags = tags.substring(2);
            if(debug) System.out.println("mod tags: " + tags);


            output.append("\"" + title + "\",\"" + author + "\",\"" + download + "\",\"" + dlnb + "\",\"" + date + "\",\"" + update + "\",\"" + size + "\",\"" + gameVer  + "\",\"" + flavor + "\",\"" + tags + "\"\n" );
        }
    }

}
