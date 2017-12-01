import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class HuntTheWumpus extends JPanel{
	Map m;
	int x,y;
	Vertex v;
	JPanel handler;
	CardLayout card;
	Time t;
	JFrame j;
	
	public HuntTheWumpus() {	
		card = new CardLayout();
		j = new JFrame();
		j.setSize(1000,800);
		handler = new JPanel();
		handler.setLayout(card);
		handler.add(new Open(this), "Open");
		handler.add(this, "Main");
		j.add(handler);
		setVisible(true);
		j.setVisible(true);
		card.show(handler, "Open");
		String MOVE = "move";
		j.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), MOVE);
		j.getRootPane().getActionMap().put(MOVE, new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {v = v.move();System.out.println("Key");}});
		String CHEAT = "cheat";
		j.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), CHEAT);
		j.getRootPane().getActionMap().put(CHEAT, new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {Vertex.cheat = !Vertex.cheat;System.out.println("cheat");}});
		String SHOOT = "shoot";
		j.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), SHOOT);
		j.getRootPane().getActionMap().put(SHOOT, new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {Vertex.shooting = !Vertex.shooting;System.out.println("shoot");}});
		String UP = "up";
		j.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), UP);
		j.getRootPane().getActionMap().put(UP, new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {v = v.up(true);System.out.println("up");}});
		String DOWN = "down";
		j.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("X"), DOWN);
		j.getRootPane().getActionMap().put(DOWN, new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {v = v.up(false);System.out.println("down");}});
	}
	
	public void create() {
		m = new Map(10);
		v = m.getOn();
		new Time();
		card.show(handler, "Main");
	}
	
	public void create(String fileName) {
		if(fileName.contains(".")) {
		m = new Map(fileName);
		}
		else {
			String[] strs = fileName.split("[x]");
			if(strs.length == 1) {
				m = new Map(Integer.parseInt(strs[0]));
			}
			else {
				m = new Map(Integer.parseInt(strs[0]),Integer.parseInt(strs[1]));
			}
		}
		v = m.getOn();
		new Time();
		card.show(handler, "Main");
	}
	
	/*public HuntTheWumpus(String fileName) {
		
		JFrame j = new JFrame();
		j.setSize(1000,800);
		j.add(this);
		j.addMouseListener(this);
		j.addKeyListener(this);
		setVisible(true);
		j.setVisible(true);
	}*/
	
	@Override
	public void paintComponent(Graphics g) {
		if(v!=null) {
		v.visited = true;
		v.paintComponent(g);
		g.setColor(m.getWumpus().z == v.z ? Color.RED : Color.BLACK);
		g.fillRect(740, 540, 220, 220);
		for(int i = 0; i < m.getMap().length; i ++) {
			for(int j = 0; j < m.getMap()[i].length; j ++) {
				if(m.getMap()[i][j][v.z] != null)
					m.getMap()[i][j][v.z].draw(g, 750 + 200/m.getMap().length*i, 550 + 200/m.getMap()[i].length*j, 200/m.getMap().length);
				else
					Vertex.drawNull(g, 750 + 200/m.getMap().length*i, 550 + 200/m.getMap()[i].length*j, 200/m.getMap().length);
			}
		}
		}
	}
	
	private class Time implements Runnable {
		Thread t;
		
		public Time() {
			start();
		}
		
		public void run() {
			while(true) {
				//System.out.println(getMousePosition().getX());
				if(v!= null) {
					repaint();
				Point p = j.getMousePosition();
				if(p != null) {
				if(p.getX() > 900) {
					v.right();
				}
				else if(p.getX() < 100) {
					v.left();
				}
				else {
					v.stop();
				}}}
				try {
					  t.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void start() {
			t = new Thread(this);
			t.start();
		}
	}
	
	public static void main(String [] args) {
		HuntTheWumpus h = new HuntTheWumpus();
	}
	
	private class Open extends JPanel implements ActionListener{
		JButton start;
		JTextField text;
		HuntTheWumpus h;
		public Open(HuntTheWumpus h) {
			setBackground(Color.BLACK);
			setLayout(new BorderLayout());
			JLabel l = new JLabel("Hunt The Wumpus");
			l.setFont(new Font(Font.SANS_SERIF, 0, 72));
			l.setHorizontalAlignment(JLabel.CENTER);
			l.setForeground(Color.GREEN);
			add(l,BorderLayout.CENTER);
			start = new JButton("START");
			text = new JTextField();
			text.setColumns(20);
			JPanel temp = new JPanel();
			temp.add(start);
			temp.add(text);
			add(temp,BorderLayout.SOUTH);
			this.h = h;
			start.addActionListener(this);
			text.addActionListener(this);
			setVisible(true);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(start)) {
				if(text.getText().length() > 0) {
					create(text.getText());
				}
				else {
					create();
				}
				start.setEnabled(false);
				text.setEnabled(false);
			}
		}
	}
}
