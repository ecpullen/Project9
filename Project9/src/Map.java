import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Map {

	private Vertex[][][] map;
	private Random rand;
	private int numLeft;//number of room left to create.
	public static int size;
	private ArrayList<Vertex> rooms;
	
	/*public Map(int width, int height,) {
		map = new Vertex[width][height];
		numLeft = (int) (width*height/1.75);
		rand = new Random();
		//start at top corner and spread out
		getMap()[0][0] = new Vertex(0,0);
		add(getMap(),0,0);
		while(numLeft > width*height/3) {
			System.out.println(numLeft);
			numLeft = (int) (width*height/1.75);
			map = new Vertex[width][height];
			map[0][0] = new Vertex(0,0);
			add(getMap(),0,0);
		}
		for(int i = 0; i < getMap().length; i ++)
			for(int j = 0; j < getMap()[i].length; j ++) {
				if(getMap()[i][j] != null) {
					try {getMap()[i][j].setN(getMap()[i][j-1]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j].setS(getMap()[i][j+1]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j].setE(getMap()[i+1][j]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j].setW(getMap()[i-1][j]);}catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		for(Vertex[] v: getMap()) {
			for(Vertex ve: v) {
				System.out.print(ve + "\t");
			}
			System.out.println();
		}
	}*/
	
	public Map(int size) {
		Map.size = size;
		rand = new Random();
		rooms = new ArrayList<>();
		map = new Vertex[size][size][size];
		for(int i = 0; i < size; i ++) {
			for(int j = 0; j < size; j ++) {
				for(int k = 0; k < size; k ++)
					if(rand.nextDouble()< (k%2 == 0 ? .4 : .6)) {
						map[i][j][k] = new Vertex(i,j,k);
						rooms.add(map[i][j][k]);
					}
			}
		}
		for(int i = 0; i < getMap().length; i ++)
			for(int j = 0; j < getMap()[i].length; j ++) 
			for(int k = 0; k < map[i][j].length; k ++) {
				if(getMap()[i][j][k] != null) {
					try {map[i][j][k].setN(map[i][j-1][k]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {map[i][j][k].setS(map[i][j+1][k]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {map[i][j][k].setE(map[i+1][j][k]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {map[i][j][k].setW(map[i-1][j][k]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {map[i][j][k].setA(map[i][j][k-1]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {map[i][j][k].setB(map[i][j][k+1]);}catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		ArrayList<Vertex> bigCollection = new ArrayList<>();
		ArrayList<Vertex> temp = new ArrayList<>();
		Vertex last = null;
		for(int i = 0; i < size; i ++)
		for(int j = 0; j < size; j ++) 
		for(int k = 0; k < size; k ++) {
			if(map[i][j][k] != null && !map[i][j][k].visited) {
				last = map[i][j][k];
				temp.add(last);
				Queue<Vertex> queue = new LinkedList<>();
				queue.offer(last);
				while(!queue.isEmpty()) {
					last = queue.poll();
					if(last != null && !last.visited) {
						temp.add(last);
						last.visited = true;
						queue.offer(last.getN());
						queue.offer(last.getS());
						queue.offer(last.getE());
						queue.offer(last.getW());
						queue.offer(last.getA());
						queue.offer(last.getB());
					}			
				}
				if(bigCollection.size() < temp.size()) {
					bigCollection.clear();
					for(Vertex v: temp) {
						bigCollection.add(v);
					}
				}		
				temp.clear();
				System.out.println(bigCollection);
			}
		}
		for(Vertex v: rooms)
			v.visited = false;
		Vertex wom = bigCollection.get(rand.nextInt(bigCollection.size()));
		putWumpus(wom.x, wom.y, wom.z);
		bigCollection.sort(new Comparator<Vertex>() {
			@Override
			public int compare(Vertex o1, Vertex o2) {return o2.cost - o1.cost;}});
		bigCollection.get(0).occupied = true;
		System.out.println(bigCollection.get(0));
	}
	
	public Map(int width, int height) {
		System.out.println("width " + width);
		Map.size = 0;
		rand = new Random();
		ArrayList<Vertex> rooms = new ArrayList<>();
		map = new Vertex[width][height][1];
		for(int i = 0; i < map.length; i ++) {
			for(int j = 0; j < map[i].length; j ++) {
					if(rand.nextDouble()< (.6)) {
						map[i][j][0] = new Vertex(i,j,0);
						rooms.add(map[i][j][0]);
					}
			}
		}
		for(int i = 0; i < getMap().length; i ++)
			for(int j = 0; j < getMap()[i].length; j ++) {
				if(getMap()[i][j][0] != null) {
					try {map[i][j][0].setN(map[i][j-1][0]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {map[i][j][0].setS(map[i][j+1][0]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {map[i][j][0].setE(map[i+1][j][0]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {map[i][j][0].setW(map[i-1][j][0]);}catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		ArrayList<Vertex> bigCollection = new ArrayList<>();
		ArrayList<Vertex> temp = new ArrayList<>();
		Vertex last = null;
		for(int i = 0; i < map.length; i ++)
		for(int j = 0; j < map[i].length; j ++) {
			if(map[i][j][0] != null && !map[i][j][0].visited) {
				last = map[i][j][0];
				temp.add(last);
				Queue<Vertex> queue = new LinkedList<>();
				queue.offer(last);
				while(!queue.isEmpty()) {
					last = queue.poll();
					if(last != null && !last.visited) {
						temp.add(last);
						last.visited = true;
						queue.offer(last.getN());
						queue.offer(last.getS());
						queue.offer(last.getE());
						queue.offer(last.getW());
					}			
				}
				if(bigCollection.size() < temp.size()) {
					bigCollection.clear();
					for(Vertex v: temp) {
						bigCollection.add(v);
					}
				}		
				temp.clear();
				System.out.println(bigCollection);
			}
		}
		for(Vertex v: rooms)
			v.visited = false;
		System.out.println(bigCollection);
		Vertex wom = bigCollection.get(rand.nextInt(bigCollection.size()));
		putWumpus(wom.x, wom.y, wom.z);
		bigCollection.sort(new Comparator<Vertex>() {
			@Override
			public int compare(Vertex o1, Vertex o2) {return o2.cost - o1.cost;}});
		bigCollection.get(0).occupied = true;
		System.out.println(bigCollection.get(0));
	}
	
	public Vertex getOn() {
		for(Vertex[][] va: map)
		for(Vertex[] vaa : va)
			for(Vertex v : vaa)
				if(v!= null && v.occupied)
					return v;
		System.out.println("None found");
		return null;
	}
	public Vertex getWumpus() {
		for(Vertex[][] va: map)
			for(Vertex[] vaa : va)
				for(Vertex v : vaa)
					if(v != null && v.isWumpus(true))
						return v;
		System.out.println("No wumpus found");
		return null;
	}
	
	public Map(String fileName) {
		Map.size = 0;
		map = (new Image(fileName)).getMap();
		for(int i = 0; i < getMap().length; i ++)
			for(int j = 0; j < getMap()[i].length; j ++) {
				if(getMap()[i][j][0] != null) {
					try {getMap()[i][j][0].setN(getMap()[i][j-1][0]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j][0].setS(getMap()[i][j+1][0]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j][0].setE(getMap()[i+1][j][0]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j][0].setW(getMap()[i-1][j][0]);}catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		Vertex v = getWumpus();
		cost(v.x,v.y,v.z);
	}
	
	/*private void add(Vertex[][] map, int i, int j) {
		if(i < map.length - 1 && rand.nextInt(map.length*map[i].length)< numLeft) {
			map[i+1][j] = new Vertex(i+1,j);
			map[i][j].setE(map[i+1][j]);
			numLeft--;
			add(map, i + 1, j);
		}
		if(j < map[i].length - 1 && rand.nextInt(map.length*map[i].length)< numLeft) {
			map[i][j+1] = new Vertex(i,j+1);
			map[i][j].setS(map[i][j+1]);
			numLeft--;
			add(map, i, j + 1);
		}
	}*/
	
	public void putWumpus(int x, int y, int z) {
		Wumpus w = new Wumpus(x,y,z);
		map[x][y][z] = w;
		try {map[x][y][z].setN(map[x][y-1][z]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {map[x][y][z].setS(map[x][y+1][z]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {map[x][y][z].setE(map[x+1][y][z]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {map[x][y][z].setW(map[x-1][y][z]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {map[x][y][z].setA(map[x][y][z-1]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {map[x][y][z].setB(map[x][y][z+1]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {map[x][y+1][z].setN(map[x][y][z]);}catch(Exception e) {}
		try {map[x][y-1][z].setS(map[x][y][z]);}catch(Exception e) {}
		try {map[x-1][y][z].setE(map[x][y][z]);}catch(Exception e) {}
		try {map[x+1][y][z].setW(map[x][y][z]);}catch(Exception e) {}
		try {map[x][y][z+1].setA(map[x][y][z]);}catch(Exception e) {}
		try {map[x][y][z-1].setB(map[x][y][z]);}catch(Exception e) {}
		cost(x,y,z);
	}
	
	public void cost(int x,int y,int z) {
		System.out.println("cost");
		for(int i = 0; i < map.length; i ++) {
			for(int j = 0; j < map[i].length; j ++) 
			for(int k = 0; k < map[i][j].length; k ++) {
				if(map[i][j][k] != null)
					map[i][j][k].cost = Integer.MAX_VALUE;
			}
		}	
		map[x][y][z].cost = 0;
		PriorityQueue<Vertex> p = new PriorityQueue<Vertex>(new Comparator<Vertex>(){
			@Override
			public int compare(Vertex o1, Vertex o2) {return o1.cost - o2.cost;}});
		
		p.add(map[x][y][z]);
		while(!p.isEmpty()) {
			Vertex v = p.poll();
			v.visited = true;
			for(Vertex w: v.getNbr()) {
				if(!w.visited && v.cost + 1 < w.cost) {
					w.cost = v.cost + 1;
					p.offer(w);
				}
			}
		}
		for(Vertex[][] vaa : map)
		for(Vertex[] va : vaa)
			for(Vertex v : va) {
				if(v != null) {
					v.visited = false;
					v.close = v.cost <= 2;
				}
			}
	}
	
	/**
	 * @return the map
	 */
	public Vertex[][][] getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Vertex[][][] map) {
		this.map = map;
	}

	public static void main(String [] args) {
		new Map(5);
	}
}
