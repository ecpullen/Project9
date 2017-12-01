import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wumpus extends Vertex implements Drawable{
	boolean dead;
	public Wumpus(int x, int y, int z) {
		super(x,y,z);
		System.out.println(x + " " + y);
		visited = true;
	}

	public void draw(Graphics g, int x, int y, int scale) {
		if(cheat)
		try {
			g.drawImage(ImageIO.read(new File("src/Wumpus.png")), x, y, scale, scale, null);
		} catch (IOException e) {System.out.println(e);}
		else {
			g.setColor(Map.size == 0 ? Color.BLACK : Color.DARK_GRAY);
			g.fillRect(x,y,scale,scale);
			g.setColor(Color.black);
		}
	}
	
	public void paintComponent(Graphics g) {
		Vertex.cheat = true;
		if(!dead) {
			g.setColor(Color.red);
			g.fillRect(0, 0, 1000, 800);
			try {
				g.drawImage(ImageIO.read(new File("src/BigWumpus.png")), 100, 100, 800, 600, null);
			} catch (IOException e) {System.out.println(e);}
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SERIF,0, 36));
			g.drawString("YOU LOSE!", 400, 100);
		}
		else {
			g.setColor(Color.red);
			g.fillRect(0, 0, 1000, 800);
			try {
				g.drawImage(ImageIO.read(new File("src/DeadMonster.png")), 0, 0, 1000, 800, null);
			} catch (IOException e) {System.out.println(e);}
			g.setColor(Color.RED);
			g.setFont(new Font(Font.SERIF,0, 36));
			g.drawString("YOU WIN!", 400, 100);
		}
	}
	
	@Override
	public boolean isWumpus() {
		dead = true;
		return true;
	}
	
	@Override
	public boolean isWumpus(boolean pointless) {
		return true;
	}
}
