package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

/**
 * An empty interface capturing the main aspects of an entity.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public interface Entity {
    /**
     * Get the entity's texture.
     * 
     * @return The entity's texture.
     */
    LayeredTexture texture();

    /**
     * Get the entity's position.
     * 
     * @return The entity's position.
     */
    IntPoint location();

    /**
     * Move the entity in the direction given.
     * 
     * @param d The direction the entity should move in.
     * @param m The model the entity is a part of.
     */
    void move(Direction d, Model m);

    /**
     * Action performed by the entity at each tick of the game's timer.
     * 
     * @param m The model the entity is a part of.
     */
    void tick(Model m);
}
