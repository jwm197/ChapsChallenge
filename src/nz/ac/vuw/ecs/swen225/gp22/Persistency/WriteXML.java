package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import org.dom4j.Document;

import java.util.HashMap;
import java.util.Map;

record WriteXML() {
    public HashMap<String, Map<String, String>> write(String levelData) {
        return new HashMap<>();
    }
}
