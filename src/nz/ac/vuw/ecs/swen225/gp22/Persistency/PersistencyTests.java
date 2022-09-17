package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import org.dom4j.DocumentException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.function.Consumer;

record PersistencyTests(){

    @Test
    void TestLoadXML1() throws DocumentException, IOException {
        Persistency p = new Persistency();
        p.loadXML("level1.xml");
        assert false : "XML file failed to parse";
    }

    @Test
    void TestLoadXML2(){
        assert false : "XML file failed to parse";
    }

    @Test
    void TestInvalidLoadXML1(){
        try{
            Persistency p = new Persistency();
            p.loadXML("level999.xml");
            assert false : "IO exception not thrown";
        }
        catch(ParserException | IOException | DocumentException e){return;}
    }

    @Test
    void TestInvalidLoadXML2(){
        try{
            assert false : "Parsing exception not thrown";
        }
        catch(ParserException e){return;}
    }
}