import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Image {

	public BufferedImage img;
	public Color[][] image;
	private final String directory = "src/maps"; // "src/maps" if being run in eclipse, otherwise, just "maps"
	
	public Image(String fileName) {//Creates a new Color[][] based on each pixel of the image at fileName
		
		img = null;
		
		try{
			Path path = FileSystems.getDefault().getPath(directory, fileName);//creates a path to the image (if using terminal remove "src/")

			System.out.println(path);	
			img = ImageIO.read(path.toFile());//Creates a bufferedImage from the path
		}
		catch(IOException e) {
			System.out.println(e);//Prints an error if the image cannot be found
		}
		image = new Color[img.getWidth()][img.getHeight()];//Creates the Color[][] and sets all of the values
		for(int i = 0; i < image.length; i ++)
			for(int j = 0; j < image[i].length; j++) {
				image[i][j] = new Color(img.getRGB(i, j));
			}
	}
	
	public Image() {//takes a random map from maps and makes that the image.
		ArrayList <BufferedImage> imgs = new ArrayList<BufferedImage>();
		File directory = new File(this.directory);
		for(File f: directory.listFiles())
			try {
				imgs.add(ImageIO.read(f));
			} catch (IOException e) {
				System.out.println(f.getAbsolutePath());
				System.out.println(e);
			}
		img = imgs.get(new Random().nextInt(imgs.size()));
		if(img == null) {//If there is no image, it makes a new image\
			this.img = new Image().img;
	}
		
		image = new Color[img.getWidth()][img.getHeight()];//Creates the Color[][] and sets all of the values
		for(int i = 0; i < image.length; i ++)
			for(int j = 0; j < image[i].length; j++) {
				image[i][j] = new Color(img.getRGB(i, j));
			}
	}
	//Wumpus is green, Hunter is red, Rooms are Black
	static final Color WUMPUS = new Color(0,255,0);
	static final Color HUNTER = new Color(255,0,0);
	static final Color ROOM = Color.black;
	
	public Vertex[][] getMap() {//Creates a new landscape based on image
		Vertex[][] t = new Vertex[image.length][image[0].length];
		for(int i = 0; i < image.length; i ++) {
			for(int j = 0; j < image[0].length; j++) {
				Color temp = image[i][j];
				if(Math.sqrt((temp.getRed()-WUMPUS.getRed())*(temp.getRed()-WUMPUS.getRed())+(temp.getBlue()-WUMPUS.getBlue())*(temp.getBlue()-WUMPUS.getBlue())+(temp.getGreen()-WUMPUS.getGreen())*(temp.getGreen()-WUMPUS.getGreen())) < 25) {
					t[i][j] = new Wumpus(i,j);
					System.out.println("Wumpus");
				}
				if(Math.sqrt((temp.getRed()-HUNTER.getRed())*(temp.getRed()-HUNTER.getRed())+(temp.getBlue()-HUNTER.getBlue())*(temp.getBlue()-HUNTER.getBlue())+(temp.getGreen()-HUNTER.getGreen())*(temp.getGreen()-HUNTER.getGreen())) < 25) {
					t[i][j] = new Vertex(i,j);
					t[i][j].occupied = true;
				}
				if(Math.sqrt((temp.getRed()-ROOM.getRed())*(temp.getRed()-ROOM.getRed())+(temp.getBlue()-ROOM.getBlue())*(temp.getBlue()-ROOM.getBlue())+(temp.getGreen()-ROOM.getGreen())*(temp.getGreen()-ROOM.getGreen())) < 25) {
					t[i][j] = new Vertex(i,j);
				}
			}
		}
		return t;
	}
	
	/*public static void main(String[] args) {//Test method
		System.out.println(Color.WHITE);
		Image image1 = new Image();
		LandscapeDisplay display = new LandscapeDisplay(image1.getLandscape(), 20);
		display.repaint();
		
	}*/
	
}