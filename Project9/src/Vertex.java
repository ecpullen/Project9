import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javax.imageio.ImageIO;

public class Vertex implements Drawable{
	
	private static double degree;
	private Vertex a,b,n,s,e,w;
	boolean visited,close,occupied;
	int x,y,z,cost;
	double vel;
	Line[] lines;
	Color c;
	
	static boolean shooting;
	boolean toggle;
	public static boolean cheat;
	public Vertex() {
		setN(null);
		setS(null);
		setE(null);
		setW(null);
		degree = 3.14;
	}
	
	public Vertex(int x, int y,int z) {
		setN(null);
		setS(null);
		setE(null);
		setW(null);
		a = null;
		b = null;
		degree = 3.14;
		this.x = x;
		this.y = y;
		this.z = z;
		lines = new Line[4];
		for(int i = 0; i < lines.length; i ++) {
			lines[i] = new Line(0);
		}
		Random rand = new Random();
		c = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
	}
	
	public ArrayList<Vertex> getNbr(){
		ArrayList<Vertex> temp = new ArrayList<>();
		if(n != null)
			temp.add(n);
		if(e != null)
			temp.add(e);
		if(s != null)
			temp.add(s);
		if(w != null)
			temp.add(w);
		if(a != null)
			temp.add(a);
		if(b != null)
			temp.add(b);
		return temp;
	}
	
	public void left() {
		vel = .06;
		System.out.println(degree);
	}
	
	public void right() {
		vel = -.06;
		System.out.println(degree);
	}
	
	public void stop() {
		vel = 0;
	}
		
	public void setN(String pos, Vertex n) {
		switch(pos) {
		case "n":
			this.n = n;
			break;
		case "s":
			this.setS(n);
			break;
		case "e":
			this.setE(n);
			break;
		case "w":
			this.setW(n);
			break;
		}
	}
	
	public static void drawNull(Graphics g, int x,int y,int scale) {
		g.setColor(Color.BLACK);
		g.fillRect(x,y,scale,scale);
	}
	
	public void draw(Graphics gt, int x,int y,int scale) {
		Graphics2D g = (Graphics2D) gt;
		g.setColor(toggle? Color.YELLOW: visited ? close ? Color.green:Color.orange : Map.size == 0 ? cheat ? Color.DARK_GRAY : Color.BLACK : cheat && close ? Color.green : Color.DARK_GRAY);
		toggle = !toggle;

		g.fillRect(x,y,scale,scale);
		if(visited || cheat) {}
		g.setColor(Color.black);
		if(occupied) {
			//System.out.println(this);
			g.setColor(Color.red);
			g.setStroke(new BasicStroke(2));
			double angle = ((-degree + 5.46 - 1.57) %6.28 + 6.28)%6.28;
			g.drawLine(x + scale/2, y + scale/2, (int)(x + scale/2 + scale/2*Math.cos(angle)), (int)(y + scale/2 + scale/2*Math.sin(angle)));
			//g.drawLine(x + scale/2, y + scale/2, (int)(x + scale/2*Math.cos(degree - 5.46)), (int)(y + scale/2*Math.sin(degree-5.46)));
			if(cheat) {
				ArrayList<Vertex> nbr = getNbr();
				nbr.sort(new Comparator<Vertex>() {
					@Override
					public int compare(Vertex o1, Vertex o2) {return o1.cost - o2.cost;}});
				Vertex v = nbr.get(0);
				if(v == n) {
					g.setColor(Color.red);
					g.fillRect(x, y, scale, 1);
				}
				if(v == e) {
					g.setColor(Color.red);
					g.fillRect(x+ scale - 1, y , 1, scale);
				}
				if(v == s) {
					g.setColor(Color.red);
					g.fillRect(x, y+ scale - 1, scale, 1);
				}
				if(v == w) {
					g.setColor(Color.red);
					g.fillRect(x, y, 1, scale);
				}
				g.setColor(Color.BLACK);
			}
		}
		else {
			toggle = false;
		}
	}
	
	public boolean isWumpus() {
		return false;
	}
	
	public boolean isWumpus(boolean pointless) {
		return false;
	}
	
	public Vertex move() {
		System.out.println("move " + this);
		occupied = false;
		Vertex ret = null;
		double angle = (degree + 6.28)%6.28;
		System.out.println(angle);
		if(angle > 0 && angle < 3.14/2 && w != null) {
			System.out.println("West " + w);
			ret = w;
		}
		else if(angle > 3.14/2 && angle < 3.2 && s != null) {
			System.out.println("South " + s);
			ret = s;
		}
		else if(angle > 3.25 && angle < 4.74 && e != null) {
			System.out.println("East " + e);
			ret = e;
		}
		else if(angle > 4.80 && angle < 6.2 && n != null) {
			System.out.println("North " + n);
			ret = n;
		}
		if(ret == null) {
			this.occupied = true;
			return this;
		}
		if(shooting && ret.isWumpus()) {
			return ret;
		}
		if(shooting && !ret.isWumpus()) {
			return new Wumpus(0,0,0);
		}
		ret.occupied = true;
		return ret;
	}
	
	public Vertex up(boolean up) {
		occupied = false;
		Vertex ret = null;
		if(up) {
			ret = a;
		}
		else
			ret = b;
		if(ret == null) {
			occupied = true;
			return this;
		}
		if(shooting && ret.isWumpus()) {
			return ret;
		}
		if(shooting && !ret.isWumpus()) {
			return new Wumpus(0,0,0);
		}
		return ret;
	}

	public void paintComponent(Graphics g) {
		visited = true;
		occupied = true;
		degree += vel;
		degree = degree%6.28 + 6.28;
		//System.out.println(degree);
		//if(degree <= 3.14)
		//	degree += 6.28*5;
		g.setColor(new Color(250,235,215));
		g.fillRect(0, 0, 1000, 400);
		g.setColor(close?Color.red:new Color(240,248,255));
		g.fillRect(0, 400, 1000, 800);
		g.setColor(Color.black);
	
		//0-1:n 1-2:e 2-3:s 3-0:w
		lines[0] = new Line(degree);
		lines[0].drawLine(g);
		lines[0].connect(g, lines[1], lines[3], getS() != null,getW() != null);
	
		degree -= .5*Math.PI;
		lines[1]= new Line(degree);
		lines[1].drawLine(g);
		lines[1].connect(g, lines[2],lines[0],getE() != null,getS()!= null);
	
		degree -= .5*Math.PI;
		lines[2] = new Line(degree);
		lines[2].drawLine(g);
		lines[2].connect(g, lines[3],lines[1],getN()!= null,getE() != null);
		
		degree += 1.5*Math.PI;
		lines[3] = new Line(degree);
		lines[3].drawLine(g);
		lines[3].connect(g, lines[0],lines[2],getW()!= null,getN()!= null);
	
		degree -= .5*Math.PI;
		if(a != null) {
			g.fillOval(300, -150, 400, 200);
		}
		if(b != null) {
			g.fillOval(300, 700, 400, 200);
		}
		
		if(shooting) {
			try {
				g.drawImage(ImageIO.read(new File("src/Bow.png")), 200, 0, 600, 800, null);
			} catch (IOException e) {System.out.println(e);}
		}
		
		g.drawString("Floor", 965, 750);
		g.drawString("" + (Map.size - z), 970, 760);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ") " + cost;
	}
	
	public String nbr() {
		return "(" + x + ", " + y + ")" + "n:" + n + "\tw:" + w + "\ts:" + s + "\te:" + e;
	}
	/**
	 * @return the n
	 */
	public Vertex getA() {
		return a;
	}

	/**
	 * @param n the n to set
	 */
	public void setA(Vertex a) {
		this.a = a;
	}/**
	 * @return the n
	 */
	public Vertex getB() {
		return b;
	}

	/**
	 * @param n the n to set
	 */
	public void setB(Vertex b) {
		this.b = b;
	}
	/**
	 * @return the n
	 */
	public Vertex getN() {
		return n;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(Vertex n) {
		this.n = n;
	}

	/**
	 * @return the w
	 */
	public Vertex getW() {
		return w;
	}

	/**
	 * @param w the w to set
	 */
	public void setW(Vertex w) {
		this.w = w;
	}

	/**
	 * @return the e
	 */
	public Vertex getE() {
		return e;
	}

	/**
	 * @param e the e to set
	 */
	public void setE(Vertex e) {
		this.e = e;
	}

	/**
	 * @return the s
	 */
	public Vertex getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 */
	public void setS(Vertex s) {
		this.s = s;
	}

	private class Line{
		int x1,x2,y1,y2;
		double degree;
		
		public Line(double degree) {
			this.degree = degree;
			x1 = (int)(500-700*Math.cos(degree));
			y1 = (int) (100 + 175*Math.abs(Math.sin(degree)));
			x2 = (int)(500-700*Math.cos(degree));
			y2 = (int) (700-175*Math.abs(Math.sin(degree)));
		}
		
		public void drawLine(Graphics g){
			if(degree%(2*Math.PI) < Math.PI && degree%(2*Math.PI) > -Math.PI) {
				g.drawLine(x1, y1, x2, y2);
			}
		}
		
		public void connect(Graphics g, Line l1,Line l2, boolean doorL, boolean doorR) {		
			if(degree % (2*Math.PI) > 1.57/2 && degree % (2*Math.PI) < 3.14) {
				g.drawLine(x1, y2, l1.x1, l1.y2);
				g.drawLine(x1, y1, l1.x1, l1.y1);
				int[] xt = {x1,l1.x1,l1.x1,x1};
				int[] yt = {y2,l1.y2,l1.y1,y1};
				fillTrap(g,xt,yt,c);
				if(doorL) {
					int[] x = {x1 + (l1.x1-x1)*4/6,x1 + (l1.x1-x1)*2/6,x2 + (l1.x2-x2)*2/6,x2 + (l1.x2-x2)*4/6};
					int[] y = {y1 + (l1.y1-y1)*4/6,y1 + (l1.y1-y1)*2/6,y2 + (l1.y2-y2)*2/6,y2 + (l1.y2-y2)*4/6};
					fillTrap(g,x,y,new Color(244,164,96));
					g.drawLine(x1, y1, x2, y2);
				}
			}
			if(degree % (2*Math.PI) > 0 && degree % (2*Math.PI) < 3.14 - 1.57/2) {
				g.drawLine(x1, y2, l2.x1, l2.y2);
				g.drawLine(x1, y1, l2.x1, l2.y1);
				int[] xt = {x1,l2.x1,l2.x1,x1};
				int[] yt = {y2,l2.y2,l2.y1,y1};
				fillTrap(g,xt,yt,c);
				if(doorR) {
					int[] x = {x1 + (l2.x1-x1)*4/6,x1 + (l2.x1-x1)*2/6,x2 + (l2.x2-x2)*2/6,x2 + (l2.x2-x2)*4/6};
					int[] y = {y1 + (l2.y1-y1)*4/6,y1 + (l2.y1-y1)*2/6,y2 + (l2.y2-y2)*2/6,y2 + (l2.y2-y2)*4/6};
					fillTrap(g,x,y,new Color(244,164,96));
					g.drawLine(x1, y1, x2, y2);
				}
			}
		}
		
		public void fillTrap(Graphics g, int[] x, int[] y, Color c) {
			g.setColor(c);
			g.fillPolygon(x, y, 4);
			g.setColor(Color.black);
		}
	}
}
