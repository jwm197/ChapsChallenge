package nz.ac.vuw.ecs.swen225.gp22.Rendering;

enum Img {
	Debug,
	Debug2,
	Debug3,
	Debug4;
}

enum ImgSequence {
	DebugSequence(Img.Debug,Img.Debug2,Img.Debug3);
	
	public final Img[] frames;
	ImgSequence(Img... frames) {
		this.frames = frames;
	}
}