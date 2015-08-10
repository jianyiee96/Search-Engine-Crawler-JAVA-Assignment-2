/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2final;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sound.midi.Patch;

/**
 *
 * @author JianYiee
 */
public class CrawlThread extends Thread {

    private String buffer;
    private int pageRequired;
    private ArrayList<String> liveThread;
    private LinkedList linkedUrl;
    private HashMap<String, Integer> antiDuplicate;
    private HashMap<String, String> urlProcessed = new HashMap<>();

    public CrawlThread(String buffer, HashMap<String, String> urlProcessed, int pageRequired, LinkedList linkedUrl, HashMap<String, Integer> antiDuplicate, ArrayList<String> liveThread) {
        this.buffer = buffer;
        this.liveThread = liveThread;
        this.urlProcessed = urlProcessed;
        this.pageRequired = pageRequired;
        this.linkedUrl = linkedUrl;
        this.antiDuplicate = antiDuplicate;

    }

    @Override
    public void run() {
        liveThread.add(buffer);
        buffer = buffer.replace(" ", "%20");
        System.out.println(buffer);

        StringBuilder source = PageRead.readPage(buffer);

        if (source.toString().equals("")) {
            buffer = buffer.replace("http", "https");
            source = PageRead.readPage(buffer);

            if (source.toString().equals("")) {
                buffer = buffer.replace("https://", "");

            source = PageRead.readPage(buffer);

            }

        }
        urlProcessed.put(buffer, source.toString());
      
        if (urlProcessed.size() < pageRequired) {
           // String regex ="^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            // Pattern pattern = Pattern.compile(regex);
            Pattern pattern = Pattern.compile("href=\\\"https?:\\/\\/[a-zA-Z-_]*\\.[a-zA-Z0-9-_]*\\.[a-zA-Z0-9-_]*(/[a-zA-Z0-9\\.\\?=&-_]*)*");
            Matcher matcher = pattern.matcher(source);

            while (matcher.find()) {
                String link = matcher.group().substring(6);
                if (!antiDuplicate.containsKey(link.toLowerCase()) && !link.contains(".css")) {
                    antiDuplicate.put(link.toLowerCase(), antiDuplicate.size());
                    linkedUrl.add(link);

                }

            }

        }

        liveThread.remove(0);

    }
}
