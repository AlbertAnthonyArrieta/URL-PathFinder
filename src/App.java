import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

/**
 * CPSC3750 Assignment 1 Programming Part: URL path finder. 
 * Author: Albert Anthony Arrieta
 */


public class App {
    public static void main(String[] args) throws Exception {
        String link1;
        String link2;
        Scanner scan = new Scanner(System.in);
        ArrayList<String> queue = new ArrayList<String>();

        System.out.println("Enter First Link: ");
        link1 = scan.nextLine();
        System.out.println("\n Enter Second Link: ");
        link2 = scan.nextLine();

        scan.close();
        queue.add(link1);

        findPath(queue, link2, 0);        
    }

    private static void findPath(ArrayList<String> q, String l2, int depth) {
        System.out.println("Searching");
        System.out.println("Depth: " + depth);
        depth++;
        boolean found = false;
        ArrayList<String> subQueue = new ArrayList<String>();
        int size = q.size();
        //For each link in the queue...
        for (String link : q) {
            size--;
            System.out.println("inspecting." + "Links left...: " + size);
            Elements subLinks = getLinks(link);

            if (subLinks != null) {
                for (Element sl : subLinks) {
                    subQueue.add(sl.attr("abs:href"));
                    if (sl.attr("abs:href").equals(l2)){
                        found = true;
                    }
                }
            }
        }

        //if found, stop and show results
        if (found == true) {
            System.out.println("LINK FOUND!");
            System.out.println("Total Distance (Number of links to visit): " + depth);
        } else {
            //if still not found, search in the next depth of links.
            findPath(subQueue, l2, depth);
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    /**
     * 
     * Gather all the available links in the url provided
     * @param url
     */
    private static Elements getLinks(String url) {
        Elements links;
        try{
        Document doc = Jsoup.connect(url).get();

        if (doc != null){
            links = doc.select("a[href]");
        } else {
            links = null;
        }

        return links;
        } catch (IOException e){
            System.out.println("INVALID URL: " + url);
            return null;
        }

    }
}


