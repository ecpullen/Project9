import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SampleRoom extends JPanel implements MouseListener{
	JFrame f;
	Line[] lines;
	double degree;
	public SampleRoom() {
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 800);
		f.add(this);
		f.setVisible(true);
		f.addMouseListener(this);
		lines = new Line[4];
		for(int i = 0; i < lines.length; i ++) {
			lines[i] = new Line(0,0,0,0);
		}
		degree = 3.14 + 6.28;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(getMousePosition()!=null)
			if(getMousePosition().getX() > 900) {
				degree -= .01;
			}
			else if(getMousePosition().getX() < 100) {
				degree += .01;
			}
			if(degree <= 0)
				degree += 6.28*2;
			g.setColor(new Color(250,235,215));
			g.fillRect(0, 0, 1000, 400);
			g.setColor(new Color(240,248,255));
			g.fillRect(0, 400, 1000, 800);
		//degree = degree % (2*Math.PI);
		
			lines[0] = new Line(degree,Color.RED);
			lines[0].drawLine(g);
			lines[0].connect(g, lines[1], lines[3], true,true);
		
		degree -= .5*Math.PI;
			lines[1]= new Line(degree,Color.blue);
			lines[1].drawLine(g);
			lines[1].connect(g, lines[2],lines[0],false,true);
		
		degree -= .5*Math.PI;
			lines[2] = new Line(degree,Color.gray);
			lines[2].drawLine(g);
			lines[2].connect(g, lines[3],lines[1],false,false);
			
		degree += 1.5*Math.PI;
			lines[3] = new Line(degree,Color.green);
			lines[3].drawLine(g);
			lines[3].connect(g, lines[0],lines[2],true,false);
		
		degree -= .5*Math.PI;
		System.out.println(degree % (2*Math.PI));
		repaint();
	}
	
	private class Line{
		int x1,x2,y1,y2;
		double degree;
		Color c;
		
		public Line(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y2 = y2;
			this.x2 = x2;
			this.y1 = y1;
		}
		
		public Line(double degree, Color c) {
			this.degree = degree;
			x1 = (int)(500-700*Math.cos(degree));
			y1 = (int) (100 + 175*Math.abs(Math.sin(degree)));
			x2 = (int)(500-700*Math.cos(degree));
			y2 = (int) (700-175*Math.abs(Math.sin(degree)));
			this.c = c;
		}
		
		public void drawLine(Graphics g){
			if(degree%(2*Math.PI) < Math.PI && degree%(2*Math.PI) > -Math.PI) {
				//g.setColor(c);
				g.drawLine(x1, y1, x2, y2);
				//g.setColor(Color.BLACK);
			}
		}
		
		public void connect(Graphics g, Line l1,Line l2) {		
				if(degree % (2*Math.PI) > 1.57/2 && degree % (2*Math.PI) < 3.14) {
					g.drawLine(x1, y2, l1.x1, l1.y2);
					g.drawLine(x1, y1, l1.x1, l1.y1);	
				}
				if(degree % (2*Math.PI) > 0 && degree % (2*Math.PI) < 3.14 - 1.57/2) {
					g.drawLine(x1, y2, l2.x1, l2.y2);
					g.drawLine(x1, y1, l2.x1, l2.y1);
				}
		}
		
		public void connect(Graphics g, Line l1,Line l2, boolean doorL, boolean doorR) {		
			if(degree % (2*Math.PI) > 1.57/2 && degree % (2*Math.PI) < 3.14) {
				g.drawLine(x1, y2, l1.x1, l1.y2);
				g.drawLine(x1, y1, l1.x1, l1.y1);
				int[] xt = {x1,l1.x1,l1.x1,x1};
				int[] yt = {y2,l1.y2,l1.y1,y1};
				fillTrap(g,xt,yt,Color.LIGHT_GRAY);
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
				fillTrap(g,xt,yt,Color.LIGHT_GRAY);
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		new SampleRoom();
	}
	
}
