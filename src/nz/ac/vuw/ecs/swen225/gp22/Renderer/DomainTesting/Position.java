package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

public record Position<T extends Number>(T x, T y) {
	
	public static Position<Double> tween(Position<?> a, Position<?> b, double tween) {
		return new Position<Double>(
			a.x().doubleValue()*tween + b.x().doubleValue()*(1-tween),
			a.y().doubleValue()*tween + b.y().doubleValue()*(1-tween)
		);
	}
	
	public Position<Double> doubleValue() {
		return new Position<Double>(this.x().doubleValue(), this.y().doubleValue());
	}
}