# LAWT
*A simple-to-use java graphics library.*

>Made for my AP COMP SCI class to speed up the process of creating graphical applications, and prototyping.

## Basic examples
It only takes one line to create a window -
```java
Window w = new Window(640, 480, "My window"); // creates a window with dimentions 640 x 480, and title "My window"
```

However, you can't do much with a static window, so you can update it via while loop -
```java
while(true){
  w.executeFrame(); //updates window W
}
```
LAWT will take care of closing the window for you, so it's okay to use a while(true) loop.
***
LAWT also supports multiple windows -
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
![image of particle simulation](https://raw.githubusercontent.com/NotWoowoo/LAWT/master/particle-entity-simulation.PNG)

## Util
If you poked around the source code, you might've noticed a Util class. This class contains various static fuctions for mostly mathematical opperations. These functions are avalible for you to use in your program. Some of the most useful ones are...
- random(...)
  - generates a random number from 0 to max

- random(...)
  - generates a random number from min to max
  
- wrap(...)
  - proportionally keeps a number in a range - if the range is 0 to 50, then 75 is set to 25, and 51 is set to 1
  
- clamp(...)
  - keeps a number in a range by making it higher than *lowerBound*, and lower than *upperBound*
  
- inRectangle(...) / inCircle(...)
  - determines if a point is inside a specified rectangle/circle
  
- clamp(double val, double lowerBound, double upperBound)
  - keeps a number in a range by making it higher than *lowerBound*, and lower than *upperBound*
  
- dist(...)
  - calculates the distance between two specified points
  
- fatan(...)
  - fast version of atan
  
- angle(...)
  - calculates the angle between two specified points
  
- smoothstep(...)
  - smoothly interpolates between 0 and 1 (argument expected to be between 0 and 1) - [graph example](https://www.desmos.com/calculator/xton9cizsw)
  
- lerp(...)
  - linearly interpolates between a and b by amt - [graph example](https://www.desmos.com/calculator/pia5oewqgz)

## Sprites and Images
Displaying an image is just as straightforward as displaying text and shapes. First, you need an external image file in your project directory, then you can simply create a SpriteEntity object to display that image -

```java
//Creates and displays the image stored in "resources/Img.png" at the position (100, 100) in the window
SpriteEntity img = new SpriteEntity(100, 100, "resources/Img.png");
```

You can manipulate SpriteEntity object like any other entity, with the added feature of resizing the image using scaleTo and scaleBy -
```java
SpriteEntity img = new SpriteEntity(100, 100, "resources/Img.png");
img.scaleBy(1.5, 1.5);
```

**The difference between scaleTo and scaleBy**
- scaleTo(int width, int height)
  - rescales an image to the dimentions specified by width, and height in pixels
  
- scaleBy(double width, double height)
  - rescales an image to its original dimentions multiplied by width, and height in the respective axis - (100 x 100) -> scaleBy(1.5, 1.6) -> (150 x 160)
  
You can also supply one parameter for both axis: scaleBy(10, 10) is the same as scaleBy(10)

### Spritesheets
Loading sprites from seperate image files is fine, but if you plan on using many diferent sprites, then it is far more efficient to load one image file that contains all the sprites you want to use, and cut all the sprites out of it. LAWT already does this for you, so loading many sprites from one image file (a spritesheet) is a simple process.

First you must import a spritesheet file via a Spritesheet object. The constructor for Spritesheet is supplied a filepath, and a sprite size in pixels for both dimentions. For this example, i'll use this sheet:

![sheet example](https://raw.githubusercontent.com/NotWoowoo/LAWT/master/sheet.png)

Once you've imported a sheet, you can use the getSprite method to get the sprite at a row, and collumn (collumn is supplied first).

```java
Spritesheet s = new Spritesheet("resources/sheet.png", 32); //load 32x32 sprites from sheet.png		
SpriteEntity entity = new SpriteEntity(100, 100, s.getSprite(1, 1)); //display sprite in collumn 1, row 1 at position 100, 100 in window
```

As you can see, we supply the spritesheet's constructor with the getSprite method which returns raw image data (BufferedImage) of the sprite. The result of the code above is the sprite at collumn 1, row 1 being displayed on the window:

![spritesheet sprite in window](https://raw.githubusercontent.com/NotWoowoo/LAWT/master/spritesheet-sprite.PNG)

## Camera movement
-todo

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
