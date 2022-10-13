package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import org.dom4j.*;
import java.util.Map;


public record SaveRecordedGame(RecordedLevel level) {
    /**Saves the level to the document
     *
     *
     * @return document being saved
     */
    public Document saveLevel(){

        Document doc = DocumentHelper.createDocument();

        Element level=doc.addElement("level");
        level.addAttribute("name", level().levelName());
        level.setText(String.valueOf(level().levelName()));
        saveMoves(level);
        saveBugsMoves(level);
        return doc;
    }
    /**saves the moves to the document
     *
     * @param level the list of recorded moves being passed
     */
    private void saveMoves(Element level) {
        level().playerMoves().stream().forEach(m->saveMove(level,m));
    }
    /**Saves a move to the document
     *
     * @param move the being saved
     * @param level the level element of the document being saved
     */
    private void saveMove(Element level, RecordedMove move) {
        Element moveNode= level.addElement("move");
        moveNode.setText(move.direction().toString());
        moveNode.addAttribute("time",move.time()+"");

    }
    /**saves the all the bugs moves to the document
     *
     * @param level the list of recorded moves being passed
     */
    private void saveBugsMoves(Element level) {

        level().bugsMoves().stream().forEach(m->saveBugsMove(level,m));
    }
    /**Saves a move to the document
     *
     * @param move the being saved
     * @param level the level element of the document being saved
     */
    private void saveBugsMove(Element level, BugsMove move) {
        Element moveNode= level.addElement("bugmove");
        moveNode.addAttribute("time",move.getTime()+"");

        for (Map.Entry<Integer,MoveDirection> bugMove:move.getMoves().entrySet()){
            Node bugNode=moveNode.addElement("bug");
            ((Element) bugNode).addAttribute("id",bugMove.getKey().toString());
            bugNode.setText(bugMove.getValue()+"");

        }

    }
}

