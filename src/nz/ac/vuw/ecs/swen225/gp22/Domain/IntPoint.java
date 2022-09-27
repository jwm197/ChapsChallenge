package nz.ac.vuw.ecs.swen225.gp22.Domain;

public record IntPoint(int x, int y){
    public IntPoint add(int x, int y) {
      return new IntPoint(x()+x, y()+y);
    }

    public IntPoint add(IntPoint p) {
      return add(p.x, p.y);
    }

    public IntPoint times(int x, int y) {
      return new IntPoint(x()*x, y()*y);
    }

    public IntPoint times(int v) {
      return new IntPoint(x()*v, y()*v);
    }
    
    public IntPoint distance(IntPoint other) {
      return this.add(other.times(-1));
    }
    
    public double size() {
      return Math.sqrt(x*x+y*y);
    }
    
    public DoublePoint toDoublePoint() {
    	return new DoublePoint(x, y);
    }
}
