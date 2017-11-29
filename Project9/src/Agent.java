/**
 * Ethan Pullen
 * 10/9/17
 * Agent.java
 */
import java.awt.Graphics;

public abstract class Agent {
	
	private double x,y;
	
	public Agent(double x0, double y0) {//a constructor that sets the position.
		x = x0;
		y = y0;
	}
	public double getX() {//returns the x position.
		return x;
	}
	public double getY() {//returns the y position.
		return y;
	}
	public void setX( double newX ) {//sets the x position.
		x = newX;
	}//newX is eliminated
	public void setY( double newY ) {//sets the y position.
		y = newY;
	}//newY is eliminated
	public String toString() {//returns a String containing the x and y positions, e.g. "(3.024, 4.245)".
		return "(" + x + ", " + y + ")";
	}
	public abstract void draw(Graphics g); //to be implement by child
}
