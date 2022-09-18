package nz.ac.vuw.ecs.swen225.gp22.Domain;

record Point(double x, double y){
    public Point add(double x,double y) {
      return new Point(x()+x, y()+y);
    }

    public Point add(Point p) {
      return add(p.x, p.y);
    }

    public Point times(double x, double y) {
      return new Point(x()*x, y()*y);
    }

    public Point times(double v) {
      return new Point(x()*v, y()*v);
    }
    
    public Point distance(Point other) {
      return this.add(other.times(-1));
    }
    
    public double size() {
      return Math.sqrt(x*x+y*y);
    }
}
