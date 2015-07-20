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
public class Assignment2Final {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       String query = "msi";
       query = query.replaceAll(" ", "%20");
       newSearch searcher = new newSearch(query);
       newSearch searcher2 = new newSearch(query);
       searcher.initSearch(query,"bing");
       searcher2.initSearch(query,"yahoo");
       searcher.start();
       searcher2.start();
       
    }
    
}
