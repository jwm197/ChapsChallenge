package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.LayeredTexture;

@Deprecated
public record Tile(Position<Integer> pos, LayeredTexture texture) {}
