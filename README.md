# LWAT
*a simple-to-use java graphics library*

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
