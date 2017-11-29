import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Map {

	private Vertex[][] map;
	private Random rand;
	private int numLeft;//number of room left to create.
	
	public Map(int width, int height) {
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
	}
	
	public Map(int size) {
		rand = new Random();
		ArrayList<Vertex> rooms = new ArrayList<>();
		map = new Vertex[size][size];
		for(int i = 0; i < size; i ++) {
			for(int j = 0; j < size; j ++) {
				if(rand.nextDouble()<.75) {
					map[i][j] = new Vertex(i,j);
					rooms.add(map[i][j]);
				}
			}
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
		ArrayList<Vertex> bigCollection = new ArrayList<>();
		ArrayList<Vertex> temp = new ArrayList<>();
		Vertex last = null;
		for(int i = 0; i < size; i ++)
		for(int j = 0; j < size; j ++) {
			if(map[i][j] != null && !map[i][j].visited) {
				last = map[i][j];
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
		Vertex wom = bigCollection.get(rand.nextInt(bigCollection.size()));
		putWumpus(wom.x, wom.y);
		bigCollection.sort(new Comparator<Vertex>() {
			@Override
			public int compare(Vertex o1, Vertex o2) {return o2.cost - o1.cost;}});
		bigCollection.get(0).occupied = true;
		System.out.println(bigCollection.get(0));
	}
	
	public Vertex getOn() {
		for(Vertex[] va: map)
			for(Vertex v : va)
				if(v!= null && v.occupied)
					return v;
		System.out.println("None found");
		return null;
	}
	public Vertex getWumpus() {
		for(Vertex[] va: map)
			for(Vertex v : va)
				if(v != null && v.isWumpus(true))
					return v;
		System.out.println("None found");
		return null;
	}
	
	public Map(String fileName) {
		map = (new Image(fileName)).getMap();
		for(int i = 0; i < getMap().length; i ++)
			for(int j = 0; j < getMap()[i].length; j ++) {
				if(getMap()[i][j] != null) {
					try {getMap()[i][j].setN(getMap()[i][j-1]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j].setS(getMap()[i][j+1]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j].setE(getMap()[i+1][j]);}catch(ArrayIndexOutOfBoundsException e) {}
					try {getMap()[i][j].setW(getMap()[i-1][j]);}catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		cost(getWumpus().x,getWumpus().y);
	}
	
	private void add(Vertex[][] map, int i, int j) {
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
	}
	
	public void putWumpus(int x, int y) {
		Wumpus w = new Wumpus(x,y);
		map[x][y] = w;
		try {getMap()[x][y].setN(getMap()[x][y-1]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {getMap()[x][y].setS(getMap()[x][y+1]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {getMap()[x][y].setE(getMap()[x+1][y]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {getMap()[x][y].setW(getMap()[x-1][y]);}catch(ArrayIndexOutOfBoundsException e) {}
		try {getMap()[x][y+1].setN(getMap()[x][y]);}catch(Exception e) {}
		try {getMap()[x][y-1].setS(getMap()[x][y]);}catch(Exception e) {}
		try {getMap()[x-1][y].setE(getMap()[x][y]);}catch(Exception e) {}
		try {getMap()[x+1][y].setW(getMap()[x][y]);}catch(Exception e) {}
		cost(x,y);
	}
	
	public void cost(int x,int y) {
		for(int i = 0; i < map.length; i ++) {
			for(int j = 0; j < map[i].length; j ++) {
				if(map[i][j] != null)
					map[i][j].cost = Integer.MAX_VALUE;
			}
		}
		map[x][y].cost = 0;
		PriorityQueue<Vertex> p = new PriorityQueue<Vertex>(new Comparator<Vertex>(){
			@Override
			public int compare(Vertex o1, Vertex o2) {return o1.cost - o2.cost;}});
		
		p.add(map[x][y]);
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
		for(Vertex[] va : map)
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
	public Vertex[][] getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Vertex[][] map) {
		this.map = map;
	}

	public static void main(String [] args) {
		new Map(5);
	}
}
