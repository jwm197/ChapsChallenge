package nz.ac.vuw.ecs.swen225.gp22.Domain;

public record DoublePoint(double x, double y){
    public DoublePoint add(double x, double y) {
      return new DoublePoint(x()+x, y()+y);
    }

    public DoublePoint add(DoublePoint p) {
      return add(p.x, p.y);
    }

    public DoublePoint times(double x, double y) {
      return new DoublePoint(x()*x, y()*y);
    }

    public DoublePoint times(double v) {
      return new DoublePoint(x()*v, y()*v);
    }
    
    public DoublePoint distance(DoublePoint other) {
      return this.add(other.times(-1));
    }
    
    public double size() {
      return Math.sqrt(x*x+y*y);
    }
    
    public static DoublePoint tween(DoublePoint a, DoublePoint b, double tween) {
		return new DoublePoint(
			a.x()*(1-tween) + b.x()*tween,
			a.y()*(1-tween) + b.y()*tween
		);
	}
    
    public IntPoint toIntPoint() {
    	return new IntPoint((int)Math.round(x), (int)Math.round(y));
    }

    public boolean equals(Object other) {
      if (!(other instanceof DoublePoint)) return false;
      DoublePoint dp = (DoublePoint) other;
      if (dp.x() != x || dp.y() != y) return false;
      return true;
    }
}