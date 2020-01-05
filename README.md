# LWAT
*A simple-to-use java graphics library.*

>Made for my AP COMP SCI class to speed up the process of creating graphical applications, and prototyping.

## Basic examples
**It only takes one line to create a window -**
```java
Window w = new Window(640, 480, "My window"); // creates a window with dimentions 640 x 480, and title "My window"
```

**However, you can't do much with a static window, so you can update it via while loop -**
```java
while(true){
  w.executeFrame(); //updates window W
}
```
LAWT will take care of closing the window for you, so it's okay to use a while(true) loop.
***
**LAWT also supports multiple windows -**
```java
Window firstWindow = new Window(640, 480, "My window");
Window secondWindow = new Window(1280, 720, "My big window");

while(true){
  firstWindow.executeFrame();
  secondWindow.executeFrame();
}
```

Rather than calling *executeFrame()* on every window object, you can simply call the static *updateAll()* method in the window class-

```java
//window creation, ect...

while(true){
  Window.updateAll(); //Static method in window class invokes executeFrame() on all windows
}
```

### Entities
Windows are cool and all, but they wouldn't be useful without a way to display some neat shapes on them. Luckly, there is a way to do just that! **Entities** are the de facto practice for drawing to the window, along with [custom drawcalls](#draw-calls) which we'll discuss later. As far as entities are concerned, all drawing is done from Entity objects.

Along with creating entities, there are some standard entities you can instantiate without any additional setup:
* Circle
* Line
* Square
* Text
* ImageEntity

The Circle, Square, and Line entities simply draw the corosponding geometric shapes they reperesent. The Text entity will draw a string of text to the window at its position. The ImageEntity displays an external image file at its position.

The following will draw the string "Hello World!" at the position (100, 100) in the window -
```java
Text message = new Text(100, 100, "Hello World!");
```

#### Creating custom entities
If the standard entities aren't enough to suffice your needs, you can create your own entities by extending the abstract Entity class. The bare bones minimum for any entity is as follows -
```java
public class MyEntity extends Entity{

	public MyEntity(double x, double y, Color col) {
		super(x, y, col);
	}

	@Override
	public void update() {
		//move, set color, etc
	}

	@Override
	public void draw(Graphics2D g) {
		//draw using awt methods
	}

}
```
As you can see, every entity must have an X and Y position, and a color. You can also add your own instance fields if you desire. From here, you can program whatever functionallity you see fit! For example, here is a particle entity that is attracted to all other particle entities -
```java
public class Particle extends Entity{
	
	private double size = 7, spd = 0.1;
	private static List<Particle> particles = new ArrayList<Particle>();
	
	public Particle(double x, double y, Color col) {
		super(x, y, col);
		particles.add(this);
	}
	
	public void accelerate(double direction, double speed){
		accelerateX(Math.cos(direction)*speed);
		accelerateY(Math.sin(direction)*speed);
	}

	@Override
	public void update() {
		for(int i = 0; i < particles.size(); ++i){
			Particle p = particles.get(i);
			if(p != this){
				double dist = Util.dist(x, y, p.x, p.y);
				double theta = Util.angle(x, y, p.x, p.y);
				
				accelerate(theta, spd/(dist));
			}
		}
		
		double velVec = Util.dist(0, 0, getVelX(), getVelY());
		size = velVec + 2;
		col = Color.getHSBColor((float)(velVec/30.0) + 0.5f, 0.5f, 1.0f);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(col);
		g.fillOval((int)(x-size/2.0), (int)(y-size/2.0), (int)(size), (int)(size));
	}

}
```
The result of creating many particle entities in a window is a neat simulation:


## Draw calls
Remember when i said all drawing is done from objects? Well, that isn't entirely true. A window does, indeed, draw every entity object instance. However, there are some cases where you'll want to draw independent from an entity. In cases like these, you'll want to impliment your own drawing function via the DrawCall interface. Every window has its own draw call function called every time said window is updated. By default, a window's draw call will enable antialiasing, and draw a black background for every frame. If you want to change this default behaviour, then you'll have to set a window's draw call to your own custom draw call function -
```java
Window w = new Window(1280, 720, "cool window"); //create the window

//Impliment a draw call that draws a red oval background
Window.DrawCall myDrawCall = (Graphics g) -> {
	g.setColor(Color.RED);
	g.fillOval(0, 0, w.getWidth(), w.getHeight());
};

w.setDrawCall(myDrawCall); //set window's draw call to myDrawCall
```
Draw calls use the standard java awt drawing methods.
