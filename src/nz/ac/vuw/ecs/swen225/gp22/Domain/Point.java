package nz.ac.vuw.ecs.swen225.gp22.Domain;

public record Point(int x, int y){
    public Point add(int x, int y) {
      return new Point(x()+x, y()+y);
    }

    public Point add(Point p) {
      return add(p.x, p.y);
    }

    public Point times(int x, int y) {
      return new Point(x()*x, y()*y);
    }

    public Point times(int v) {
      return new Point(x()*v, y()*v);
    }
    
    public Point distance(Point other) {
      return this.add(other.times(-1));
    }
    
    public double size() {
      return Math.sqrt(x*x+y*y);
    }
}
