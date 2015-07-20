/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2final;

import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author JianYiee
 */
public class newSearch extends Thread {

    public String bing;
    public String yahoo;
    public String query;
    public String engine;

    public newSearch(String query) {
        this.query = query;
    }

    public void initSearch(String query, String engine) {
        System.out.println("Initialising search for " + query);
        if (engine.contains("bing")) {
            this.engine = "https://www.bing.com/search?q=";
        } else if (engine.contains("yahoo")){
            this.engine = "https://sg.search.yahoo.com/search?p=";
        } else {
            System.out.println("Error");
        }

    }

    @Override
    public void run() {
        ArrayList<String> results = new ArrayList<>();
        ArrayList<String> meaningfulResults = new ArrayList<>();
        System.out.println("Begin to crawl " + engine + query + " (Threading...)");
        StringBuilder source = PageRead.readPage(engine + query);
        Pattern pattern = Pattern.compile("href=\\\"https?:\\/\\/[a-zA-Z-_]*\\.[a-zA-Z0-9-_]*\\.[a-zA-Z0-9-_]*(/[a-zA-Z0-9\\.\\?=&-_]*)*");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            results.add(matcher.group().substring(6));
            //System.out.println(engine + " " + matcher.group().substring(6));
        }
            query = query.replaceAll("%20", " ");
            StringTokenizer st = new StringTokenizer(query," ");
            ArrayList<String> subquery = new ArrayList<>();
            
            while (st.hasMoreElements()){
                subquery.add(st.nextToken());
            }
            for(String s : subquery) {
                System.out.println(s);
            }
            
            
        for (String s : results) {
               for (String s2 : subquery) {
            if (s.toLowerCase().contains(s2.toLowerCase())) {

                Pattern domainPattern = Pattern.compile("\\/.*\\.[a-zA-Z0-9]*");
                Matcher domainMatcher = domainPattern.matcher(s);
                if (domainMatcher.find()) {
                    String domain = domainMatcher.group().substring(2);

                    //System.out.println(domain);
                    int exitCode = 0;
                    for (String s3 : meaningfulResults) {
                        if (s3.contains(domain)) {
                            exitCode = 1;
                            break;
                        }
                    }
                    if (exitCode == 0) {
                        meaningfulResults.add(s);
                    }
                }

            }

        }
        }
        for (String s : meaningfulResults) {
            System.out.println(s);
        }

    }

}
