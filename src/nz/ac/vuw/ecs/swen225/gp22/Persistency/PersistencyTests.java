package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import org.junit.jupiter.api.*;

import java.util.function.Consumer;

record PersistencyTests(){

    @Test
    void TestLoadXML1(){
        Persistency p = new Persistency();
        p.loadXML();
        assert false : "XML file failed to parse";
    }

    @Test
    void TestLoadXML2(){
        assert false : "XML file failed to parse";
    }

    @Test
    void TestInvalidLoadXML1(){
        try{
            assert false : "Parsing exception not thrown";
        }
        catch(ParserException e){return;}
    }

    @Test
    void TestInvalidLoadXML2(){
        try{
            assert false : "Parsing exception not thrown";
        }
        catch(ParserException e){return;}
    }
}