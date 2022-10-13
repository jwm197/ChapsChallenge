package nz.ac.vuw.ecs.swen225.gp22.App;

/**
 * Representation of a pair class containing two generic fields
 *
 * @author anfri
 *
 * @param <T> first element of pair
 * @param <U> second element of pair
 */
public record Pair<T, U>(T first, U second) {}