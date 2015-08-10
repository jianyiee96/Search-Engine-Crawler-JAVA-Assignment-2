/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2final;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedList;
import javafx.animation.Animation;
/**
 *
 * @author JianYiee
 */
public class SearchEngine extends Thread {

    private String query;
    private LinkedList linkedUrl;
    private HashMap<String,Integer> antiDuplicate;
    final private String engine;
   
    public SearchEngine(String query, String engine, LinkedList linkedUrl,HashMap<String,Integer> antiDuplicate) {
        this.query = query;
        this.engine = engine;
        this.linkedUrl = linkedUrl;
        this.antiDuplicate = antiDuplicate;
    }

    @Override
    public void run() {
           query = query.replace(" ", "%20");
           StringBuilder source = PageRead.readPage(engine+query);
           
           
           Pattern pattern = Pattern.compile("<cite>.*?<\\/cite>");
           Matcher matcher = pattern.matcher(source);
           
           Pattern pattern2 = Pattern.compile("web-result-url.*?<\\/p>");
           Matcher matcher2 = pattern2.matcher(source);
           
           if (matcher.find()){
               String link = matcher.group();
               link = link.replace("<cite>", "");
               link = link.replace("<strong>","");
               link = link.replace("</strong>","");
               link = link.replace("</cite>","");
               
               if (!antiDuplicate.containsKey(link.toLowerCase())){
                    antiDuplicate.put(link.toLowerCase(), antiDuplicate.size());
                    linkedUrl.add("http://"+link);
               } 
           } else if(matcher2.find()) {
               String link = matcher2.group();
               link = link.replace("web-result-url\">", "");
               link = link.replace("</p>", "");
               
               if (!antiDuplicate.containsKey(link.toLowerCase())){
                    antiDuplicate.put(link.toLowerCase(), antiDuplicate.size());
                    linkedUrl.add("http://"+link);
               } 
               
           } 
                 
           
    }

}
