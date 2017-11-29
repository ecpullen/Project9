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

public class HuntTheWumpus extends JPanel implements MouseListener{
	Map m;
	int x,y;
	Vertex v;
	JPanel handler;
	CardLayout card;
	
	public HuntTheWumpus() {	
		card = new CardLayout();
		JFrame j = new JFrame();
		j.setSize(1000,800);
		handler = new JPanel();
		handler.setLayout(card);
		handler.add(new Open(this), "Open");
		handler.add(this, "Main");
		create();
		j.add(handler);
		j.addMouseListener(this);
		setVisible(true);
		j.setVisible(true);
		card.show(handler, "Open");
		String MOVE = "move";
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), MOVE);
		this.getActionMap().put(MOVE, new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {v = v.move();System.out.println("Key");}});
		String CHEAT = "cheat";
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), CHEAT);
		this.getActionMap().put(CHEAT, new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {Vertex.cheat = !Vertex.cheat;System.out.println("cheat");}});
		String SHOOT = "shoot";
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), SHOOT);
		this.getActionMap().put(SHOOT, new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {Vertex.shooting = !Vertex.shooting;System.out.println("shoot");}});
	}
	
	public void create() {
		m = new Map(20);
		v = m.getOn();
		new Time();
		card.show(handler, "Main");
	}
	
	public void create(String fileName) {
		m = new Map(fileName);
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
		g.fillRect(740, 540, 220, 220);
		for(int i = 0; i < m.getMap().length; i ++) {
			for(int j = 0; j < m.getMap()[i].length; j ++) {
				if(m.getMap()[i][j] != null)
					m.getMap()[i][j].draw(g, 750 + 200/m.getMap().length*i, 550 + 200/m.getMap()[i].length*j, 200/m.getMap().length);
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
				repaint();
				//System.out.println(getMousePosition().getX());
				Point p = getMousePosition();
				if(p != null) {
				if(p.getX() > 900) {
					v.right();
				}
				else if(p.getX() < 100) {
					v.left();
				}
				else {
					v.stop();
				}}
				try {
					t.sleep(20);
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
	
	private class Open extends JPanel implements ActionListener{
		JButton start;
		JTextField text;
		HuntTheWumpus h;
		public Open(HuntTheWumpus h) {
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
			}
		}
	}
}
