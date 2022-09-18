package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

record RecordedGame() implements RecordedEntity {
    static ArrayDeque<RecordedMove>doneMoves=new ArrayDeque<>();
    static ArrayDeque<RecordedMove>futureMoves=new ArrayDeque<>();
    public void loadRecordedGame(String fileName) throws IOException {

        try {
            File file = new File("filepath/" + fileName);
            if (!file.exists()) {
                throw new IOException("No file found named "+fileName);
            }
           
           

            parseEntity(new SAXReader().read(file));
            System.out.println("Completed recorded game parsing");
        } catch (IOException e) {
            throw new IOException("Parsing failed as: " + e.getMessage());
        }

    }
    

    public void saveRecordedGame() {
        
    }


    @Override
    public void parseEntity(Document document) throws IOException {
        Node root = document.selectSingleNode("recordedGame");
        String lvlName = root.attributeValue("levelName");
        //ToDo: swap level here
        
        
    }
}
