package lawt.engine;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Entity
{
  protected double x;
  protected double y;
  protected double xVel;
  protected double yVel;
  protected double friction;
  protected double rotation;
  protected Window currentWindow = Window.ALL_WINDOWS.get(0);
  protected Color col;

  protected EdgeBehavior edgeBehavior;
  
  public enum EdgeBehavior{ CLAMP, WRAP, NONE; }

  public Entity(double x, double y, Color col) {
    this.edgeBehavior = EdgeBehavior.CLAMP;
    this.xVel = 0.0D;
    this.yVel = 0.0D;
    this.friction = 0.1D;
    this.rotation = 0.0D;
    this.x = x;
    this.y = y;
    this.col = col;
    
    this.currentWindow.entities.add(this);
  }

public void setWindow(Window w) {
    this.currentWindow.entities.remove(this);
    this.currentWindow = w;
    this.currentWindow.entities.add(this);
  }

  public Window getWindow() {
	  return this.currentWindow;
  }
  
  void updateParent() {
    update();
    
    this.x += this.xVel;
    this.y += this.yVel;
    
    this.xVel /= 1.0D + this.friction;
    this.yVel /= 1.0D + this.friction;
    
    if (edgeBehavior != EdgeBehavior.NONE && !Util.inRectangle(this.x, this.y, 0.0D, 0.0D, this.currentWindow.getWidth(), this.currentWindow.getHeight())) {
      switch (this.edgeBehavior) {
        case CLAMP:
          this.x = Util.clamp(this.x, 0.0D, this.currentWindow.getWidth());
          this.y = Util.clamp(this.y, 0.0D, this.currentWindow.getHeight());
          break;
        
        case WRAP:
          this.x = Util.wrap(this.x, 0.0D, this.currentWindow.getWidth());
          this.y = Util.wrap(this.y, 0.0D, this.currentWindow.getHeight());
          break;
          
        default:
        	break;
      } 
    }
  }
  
  public boolean isOnScreen() {
	  return Util.inRectangle(
			  x, y,
			  currentWindow.getCameraX()-1,
			  currentWindow.getCameraY()-1,
			  currentWindow.getCameraX() + currentWindow.getWidth()/currentWindow.getScaleFactor() + 1,
			  currentWindow.getCameraY() + currentWindow.getHeight()/currentWindow.getScaleFactor() + 1);
  }
  
  public void accelerateX(double amt) { this.xVel += amt; }

  public void accelerateY(double amt) { this.yVel += amt; }

  public double getX() { return this.x; }

  public void setX(double x) { this.x = x; }

  public double getY() { return this.y; }

  public void setY(double y) { this.y = y; }

  public double getFriction() { return this.friction; }

  public void setFriction(double friction) { this.friction = friction; }

  public EdgeBehavior getEdgeBehavior() { return this.edgeBehavior; }

  public void setEdgeBehavior(EdgeBehavior edgeBehavior) { this.edgeBehavior = edgeBehavior; }

  public double getVelX() { return this.xVel; }
  
  public double getVelY() { return this.yVel; }
  
  public Color getColor() { return col; }

  public void setColor(Color col) { this.col = col; }
  
  public double getRotation() { return rotation; }

  public void setRotation(double rotation) { this.rotation = rotation; }
  
  public void addRotation(double rotation) { this.rotation += rotation; }

  public abstract void update();
  
  public abstract void draw(Graphics2D g);
}
