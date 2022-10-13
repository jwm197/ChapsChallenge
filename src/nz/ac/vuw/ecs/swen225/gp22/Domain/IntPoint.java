package nz.ac.vuw.ecs.swen225.gp22.Domain;

/**
 * Represents the position of an object with integer x and y coordinates.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public record IntPoint(int x, int y){
  /**
   * Return a new point with this point's x coordinate incremented by the first argument
   * and this point's y coordinate incremented by the second argument.
   * 
   * @param x The value to increment the x coordinate by.
   * @param y The value to increment the y coordinate by.
   * @return A new point with the updated x and y coordinates.
   */
  public IntPoint add(int x, int y) {
    return new IntPoint(x()+x, y()+y);
  }

  /**
   * Return a new point with this point's x coordinate incremented by the x coordinate of the given point
   * and this point's y coordinate incremented by the y coordinate of the given point.
   * 
   * @param p The point to add.
   * @return A new point with the updated x and y coordinates.
   */
  public IntPoint add(IntPoint p) {
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
  public IntPoint times(int x, int y) {
    return new IntPoint(x()*x, y()*y);
  }

  /**
   * Return a new point with this point's x coordinate multiplied by the given integer
   * and this point's y coordinate multiplied by the given integer.
   * 
   * @param v The value to multiply both the x and y coordinates by.
   * @return A new point with the updated x and y coordinates.
   */
  public IntPoint times(int v) {
    return new IntPoint(x()*v, y()*v);
  }
  
  /**
   * Return a new point that represents the distance between this point
   * and the given point.
   * 
   * @param other The point to calculate the distance to.
   * @return A new point that represents the distance between the two points.
   */
  public IntPoint distance(IntPoint other) {
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
  
  /**
   * Convert the point to a point with double x and y coordinates.
   * 
   * @return A new point with double x and y coordinates.
   */
  public DoublePoint toDoublePoint() {
    return new DoublePoint(x, y);
  }

  /**
   * Determine that this point is equal to the given point if they have the same
   * x and y coordinates and are of the same type.
   * 
   * @return True if the points have the same x and y coordinates and are of the same type and false if otherwise.
   */
  public boolean equals(Object other) {
    if (!(other instanceof IntPoint)) return false;
    IntPoint ip = (IntPoint) other;
    if (ip.x() != x || ip.y() != y) return false;
    return true;
  }
}
