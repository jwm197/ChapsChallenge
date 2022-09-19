package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import org.dom4j.DocumentException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.function.Consumer;

record PersistencyTests(){

    @Test
    void TestLoadXML1() {
        try{
            Persistency p = new Persistency();
            p.loadXML("level1.xml");
        }
        catch(Exception e){
            assert false : e.getMessage();
        }

    }

    @Test
    void TestLoadXML2(){
        try{
            Persistency p = new Persistency();
            p.loadXML("level2.xml");
        }
        catch(Exception e){
            assert false : e.getMessage();
        }
    }

    @Test
    void TestInvalidLoadXML1(){
        try{
            Persistency p = new Persistency();
            p.loadXML("level999.xml");
            assert false : "IO exception not thrown";
        }
        catch(Exception e){return;}
    }

    @Test
    void TestInvalidLoadXML2(){
        try{
            assert false : "Parsing exception not thrown";
        }
        catch(Exception e){return;}
    }
}