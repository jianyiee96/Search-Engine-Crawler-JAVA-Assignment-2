/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2final;

/**
 *
 * @author JianYiee
 */
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
 
public class PageRead {
    public static StringBuilder readPage(String pageAddr) {
        try {
            URL url = new URL(pageAddr);
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
          
            String line;
            StringBuilder sb=new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");
            }
 
            reader.close();

			return sb;            
        } catch (MalformedURLException e) {
            e.printStackTrace();
			return new StringBuilder("");
        }  catch (IOException e) {
            e.printStackTrace();
			return new StringBuilder("");
        }
    }
}