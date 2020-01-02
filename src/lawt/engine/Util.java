package lawt.engine;

public final class Util
{
  public static double random(double min, double max) {
	  return lerp(min, max, Math.random());
  }
  
  public static double random(double max) {
	  return Math.random()*max;
  }
  
  public static double mod(double x, double y) {
	  return (x % y + y) % y;
  }
  
  public static double wrap(double val, double lowerBound, double upperBound) {
	  return mod(val - upperBound, upperBound - lowerBound) + lowerBound;
  }

  public static double clamp(double val, double lowerBound, double upperBound) {
	  return Math.min(Math.max(val, lowerBound), upperBound);
  }

  public static boolean inRectangle(double x, double y, double x1, double y1, double x2, double y2) {
	  return (x > x1 && x < x2 && y > y1 && y < y2);
  }

  public static boolean inCircle(double x, double y, double centerX, double centerY, double r) {
	  return (dist(x, y, centerX, centerY) < r);
  }

  public static double dist(double x1, double y1, double x2, double y2) {
	  return Math.sqrt(Math.pow(x2 - x1, 2.0D) + Math.pow(y2 - y1, 2.0D));
  }
  
  public static double angle(double x1, double y1, double x2, double y2) {
	  double ang = Math.atan((y2 - y1) / (x2 - x1));
	  if (x2 < x1) {
		  ang += Math.PI;
	  }
	  if (x2 == x1) {
		  ang = 0.0D;
	  }
	  return ang;
  	}
  
  public static double smoothstep(double val){ return Math.pow(3*val, 2) - Math.pow(2*val, 3); }
  
  public static double lerp(double a, double b, double amt) { return amt * (b-a)+a; }
}
