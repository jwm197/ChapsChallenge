package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

@Deprecated
public record Position<T extends Number>(T x, T y) {
	
	public static Position<Double> tween(Position<?> a, Position<?> b, double tween) {
		return new Position<Double>(
			a.x().doubleValue()*(1-tween) + b.x().doubleValue()*tween,
			a.y().doubleValue()*(1-tween) + b.y().doubleValue()*tween
		);
	}
	
	public Position<Double> doubleValue() {
		return new Position<Double>(this.x().doubleValue(), this.y().doubleValue());
	}

	public Position<Integer> intValue() {
		return new Position<Integer>((int)Math.round(this.x().doubleValue()), (int)Math.round(this.y().doubleValue()));
	}
}