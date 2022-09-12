package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import java.io.*;

/**
 * Persistency module
 */

class Persistency{
    public boolean loadXML() throws IOException {
        try{
            File xmlFile = new File("/level1.xml");
            if (!xmlFile.exists()){
                System.out.println("XML file doesn't exist");
                return false;
            }
        }
        catch (IOException e){
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return false;
     } 
     
     public boolean saveXML(){
        return false;
     }
  }
 


 