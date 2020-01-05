package lawt.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class Window extends Canvas implements MouseListener, MouseWheelListener, KeyListener {
  private static final long serialVersionUID = 1L;
  private int width;
  private int height;
  private int cameraX = 0;
  private int cameraY = 0;
  private double cameraZoom = 0;
  private double scaleFactor = Math.pow(2, cameraZoom);
  private String title;
  private JFrame frame;
  private BufferedImage finalImg;
  public int fps = 60;
  public int step = 0;
  public long timeStepMillis = 1000 / fps;
  
  public boolean mouseInWindow = false;
  public double mouseScrollTotal = 0;
  public static List<Window> ALL_WINDOWS = new ArrayList<>();
  public List<Entity> entities = new ArrayList<>();
  public List<Integer> mouseButtonsDown = new ArrayList<>();
  public List<Integer> mouseButtonsClicked = new ArrayList<>();
  public List<Integer> keysDown = new ArrayList<>();
  
  @FunctionalInterface
  public static interface DrawCall {
    void draw(Graphics param1Graphics);
  }

  private DrawCall defaultDrawCall;

  public Window(int width, int height, String title) {
    this.width = width;
    this.height = height;
    this.title = title;
    setSize(this.width, this.height);
    addMouseListener(this);
    addMouseWheelListener(this);
    addKeyListener(this);
    this.finalImg = new BufferedImage(getWidth(), getHeight(), 1);
    
    this.frame = new JFrame(this.title);
    this.frame.add(this);
    this.frame.setResizable(false);
    this.frame.pack();
    this.frame.setVisible(true);
    this.frame.setLocationRelativeTo(null);
    this.frame.setDefaultCloseOperation(3);
    
    ALL_WINDOWS.add(this);
    
    this.defaultDrawCall = (g -> {
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics2D.setColor(Color.black);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
      });
  }

  public void executeFrame() {
    repaint();
    this.mouseButtonsClicked.clear();
    
    try { Thread.sleep((timeStepMillis)); } catch (InterruptedException e) { e.printStackTrace(); }
    
    this.step++;
  }

  public static void updateAll() {
    for (int i = 0; i < ALL_WINDOWS.size(); i++) {
      ((Window)ALL_WINDOWS.get(i)).executeFrame();
    }
  }
  
  private void draw(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	
    this.defaultDrawCall.draw(g);
    
    for (int i = 0; i < entities.size(); ++i) {
      Entity o = entities.get(i);
      if(o.isOnScreen()) {
	      g2.scale(getScaleFactor(), getScaleFactor());
	      g2.translate(-cameraX, -cameraY);
	      g2.rotate(o.rotation, o.x, o.y);
	      o.draw(g2);
	      g2.rotate(-o.rotation, o.x, o.y);
	      g2.translate(cameraX, cameraY);
	      g2.scale(1/getScaleFactor(), 1/getScaleFactor());
      }
      o.updateParent();
    }
  }

  @Override
  public void paint(Graphics g) {
    draw(this.finalImg.getGraphics());
    g.drawImage(this.finalImg, 0, 0, null);
  }

  @Override
  public void update(Graphics g) {
	  paint(g);
  }
  
  public double getMouseX() {
	  return MouseInfo.getPointerInfo().getLocation().getX() - this.getLocationOnScreen().x;
  }
  
  public double getMouseY() {
	  return MouseInfo.getPointerInfo().getLocation().getY() - this.getLocationOnScreen().y;
  }
  
  public void setDrawCall(DrawCall d) {
	  this.defaultDrawCall = d;
  }

  public DrawCall getDrawCall() {
	  return this.defaultDrawCall;
  }

  @Override
  public int getWidth() {
	  return this.width;
  }
  
  public double getViewWidth() {
	  return this.width*this.getScaleFactor();
  }

  public void setWidth(int width) {
    setSize(width, this.height);
    this.frame.pack();
    this.width = width;
  }

  @Override
  public int getHeight() {
	  return this.height;
  }
  
  public double getViewHeight() {
	  return this.height*this.getScaleFactor();
  }

  public void setHeight(int height) {
    setSize(this.width, height);
    this.frame.pack();
    this.height = height;
  }
  
  public String getTitle() {
	  return this.title;
  }

  public void setTitle(String title) {
    this.frame.setTitle(title);
    this.title = title;
  }

  public double getCameraZoom() {
	return cameraZoom;
  }

  public void setCameraZoom(double cameraZoom) {
	this.cameraZoom = cameraZoom;
	this.scaleFactor = Math.pow(2, this.cameraZoom);
  }
  
  public void addToCameraZoom(double cameraZoom) {
	this.cameraZoom += cameraZoom;
	this.scaleFactor = Math.pow(2, this.cameraZoom);
  }

  public int getCameraX() {
	return cameraX;
  }

  public int getCameraY() {
	return cameraY;
  }
  
  public void setCameraPos(int x, int y) {
	  this.cameraX = x;
	  this.cameraY = y;
  }
  
  public void centerCameraPos(double x, double y) {
	  this.cameraX = (int) (x - this.width/2 / this.getScaleFactor());
	  this.cameraY = (int) (y - this.height/2 / this.getScaleFactor());
  }
  
  public void addToCameraPos(int x, int y) {
	  this.cameraX += x;
	  this.cameraY += y;
  }

  public double getScaleFactor() {
	return scaleFactor;
  }

@Override
  public void mouseClicked(MouseEvent e) {
	  this.mouseButtonsClicked.add(Integer.valueOf(e.getButton()));
  }

  @Override
  public void mouseEntered(MouseEvent e) {
	  this.mouseInWindow = true;
  }

  @Override
  public void mouseExited(MouseEvent e) {
	  this.mouseInWindow = false;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (!this.mouseButtonsDown.contains(Integer.valueOf(e.getButton()))) {
      this.mouseButtonsDown.add(Integer.valueOf(e.getButton()));
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (this.mouseButtonsDown.contains(Integer.valueOf(e.getButton()))) {
      this.mouseButtonsDown.remove(Integer.valueOf(e.getButton()));
    }
  }
  
  public static int key(char ch) { return KeyEvent.getExtendedKeyCodeForChar(Character.toLowerCase(ch)); }

  @Override
  public void keyPressed(KeyEvent e) {
    if (!this.keysDown.contains(Integer.valueOf(e.getKeyCode()))) {
      this.keysDown.add(Integer.valueOf(e.getKeyCode()));
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (this.keysDown.contains(Integer.valueOf(e.getKeyCode())))
      this.keysDown.remove(Integer.valueOf(e.getKeyCode())); 
  }
  
  @Override
  public void keyTyped(KeyEvent e) {
	  
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
	  mouseScrollTotal += e.getPreciseWheelRotation();
  }
}
