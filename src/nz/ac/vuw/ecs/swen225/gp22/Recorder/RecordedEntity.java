package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.io.IOException;


interface RecordedEntity{
    /**Parse the entity from the xml file
     * @param Document the xml document passed from 
    */
     public void parseEntity(Document document) throws IOException;

}