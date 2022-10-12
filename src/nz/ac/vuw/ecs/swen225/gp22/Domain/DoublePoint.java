package nz.ac.vuw.ecs.swen225.gp22.Domain;

/**
 * Represents the position of an object with double x and y coordinates.
 * 
 * @author sidoroyuri
 * 
 */
public record DoublePoint(double x, double y){
  /**
   * Return a new point with this point's x coordinate incremented by the first argument
   * and this point's y coordinate incremented by the second argument.
   * 
   * @param x The value to increment the x coordinate by.
   * @param y The value to increment the y coordinate by.
   * @return A new point with the updated x and y coordinates.
   */
  public DoublePoint add(double x, double y) {
    return new DoublePoint(x()+x, y()+y);
  }

  /**
   * Return a new point with this point's x coordinate incremented by the x coordinate of the given point
   * and this point's y coordinate incremented by the y coordinate of the given point.
   * 
   * @param p The point to add.
   * @return A new point with the updated x and y coordinates.
   */
  public DoublePoint add(DoublePoint p) {
    return add(p.x, p.y);
  }

  /**
   * Return a new point with this point's x coordinate multiplied by the first argument
   * and this point's y coordinate multiplied by the second argument.
   * 
   * @param x The value to multiply the x coordinate by.
   * @param y The value to multiply the y coordinate by.
   * @return A new point with the updated x and y coordinates.
   */
  public DoublePoint times(double x, double y) {
    return new DoublePoint(x()*x, y()*y);
  }

  /**
   * Return a new point with this point's x coordinate multiplied by the given double
   * and this point's y coordinate multiplied by the given double.
   * 
   * @param v The value to multiply both the x and y coordinates by.
   * @return A new point with the updated x and y coordinates.
   */
  public DoublePoint times(double v) {
    return new DoublePoint(x()*v, y()*v);
  }
  
  /**
   * Return a new point that represents the distance between this point
   * and the given point.
   * 
   * @param other The point to calculate the distance to.
   * @return A new point that represents the distance between the two points.
   */
  public DoublePoint distance(DoublePoint other) {
    return this.add(other.times(-1));
  }
  
  /**
   * Get the size of the point vector.
   * 
   * @return The size of the point vector.
   */
  public double size() {
    return Math.sqrt(x*x+y*y);
  }
  
  public static DoublePoint tween(DoublePoint a, DoublePoint b, double tween) {
    return new DoublePoint(
      a.x()*(1-tween) + b.x()*tween,
      a.y()*(1-tween) + b.y()*tween
    );
  }
  
  /**
   * Convert the point to a point with integer x and y coordinates.
   * 
   * @return A new point with integer x and y coordinates.
   */
  public IntPoint toIntPoint() {
    return new IntPoint((int)Math.round(x), (int)Math.round(y));
  }

  /**
   * Determine that this point is equal to the given point if they have the same
   * x and y coordinates and are of the same type.
   * 
   * @return True if the points have the same x and y coordinates and are of the same type and false if otherwise.
   */
  public boolean equals(Object other) {
    if (!(other instanceof DoublePoint)) return false;
    DoublePoint dp = (DoublePoint) other;
    if (dp.x() != x || dp.y() != y) return false;
    return true;
  }
}
