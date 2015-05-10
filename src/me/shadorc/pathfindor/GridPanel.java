package me.shadorc.pathfindor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private boolean mousePressed = false;

	private ArrayList <Node> openList, closeList;
	private Node start, end;

	private Color START_COLOR = Color.RED;
	private Color END_COLOR = Color.GREEN;
	private Color WALL_COLOR = Color.BLACK;
	private Color PATH_COLOR = Color.YELLOW;
	private Color EMPTY_COLOR = Color.WHITE;

	private static int columns = 11;
	private static int rows = 11;
	private Node[][] grid;

	GridPanel() {
		super(new GridLayout(rows, columns));
		this.init();
	}

	public void init() {
		this.removeAll();

		grid = new Node[columns][rows];

		openList = new ArrayList <Node>();
		closeList = new ArrayList <Node>();

		//Initialization
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < columns; x++) {
				Node node = new Node(x, y);
				node.setBackground(EMPTY_COLOR);
				node.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if(e.getButton() == MouseEvent.BUTTON1) {
							Node node = (Node) e.getSource();
							if(node.getBackground() != END_COLOR && node.getBackground() != START_COLOR) {
								node.setBackground(node.getBackground() == WALL_COLOR ? EMPTY_COLOR : WALL_COLOR);
							}
							mousePressed = true;
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						Node node = (Node) e.getSource();
						if(mousePressed && node.getBackground() != START_COLOR && node.getBackground() != END_COLOR) {
							node.setBackground(WALL_COLOR);
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						mousePressed = false;
					}
				});
				grid[x][y] = node;
				this.add(grid[x][y]);
			}
		}

		start = grid[columns / 2][rows - 1];
		start.setBackground(START_COLOR);
		start.setForeground(Color.BLACK);
		start.setText("Start");

		end = grid[columns / 2][0];
		end.setBackground(END_COLOR);
		end.setForeground(Color.BLACK);
		end.setText("End");

		OptionsPanel.setText("\"Paint\" obstacles", false);;
	}

	//A* algorithm part
	private ArrayList <Node> getPath() {
		openList.clear();
		closeList.clear();

		openList.add(start);

		while(!closeList.contains(end)) {
			Node current = this.getCurrent();
			closeList.add(current);
			openList.remove(current);

			if(current == null) return null;

			for(Node node : this.getAround(current)) {

				if(node.getBackground() == WALL_COLOR || closeList.contains(node)) continue;

				if(!openList.contains(node)) {
					openList.add(node);
					node.setP(current);
					node.setG(this.calculateG(node));
					node.setH(Math.sqrt(Math.pow(node.getPosX() - end.getPosX(), 2) + Math.pow(node.getPosY() - end.getPosY(), 2)));
					node.setF(node.getG() + node.getH());
				} else {
					int g = this.calculateG(node);
					if(g < node.getG()) {
						node.setP(current);
						node.setH(Math.sqrt(Math.pow(node.getPosX() - end.getPosX(), 2) + Math.pow(node.getPosY() - end.getPosY(), 2)));
						node.setF(node.getG() + node.getH());
					}
					node.setG(g);
				}

				if(openList.size() == 0) return null;
			}
		}

		ArrayList <Node> path = new ArrayList <Node>();

		Node node = end;
		while(node != start) {
			path.add(node);
			node = node.getP();
		}

		//The path from the start to go until arriving, so we reverse
		Collections.reverse(path);

		return path;
	}

	//Calculates every move it took to get to this Node
	private int calculateG(Node current) {
		int step = 0;
		Node node = current;
		while(node != start) {
			step++;
			node = node.getP();
		}
		return step;
	}

	private ArrayList <Node> getAround(Node current) {
		ArrayList <Node> around = new ArrayList <Node>();

		int x = current.getPosX();
		int y = current.getPosY();

		if(!this.isImpossible(x, y + 1))	around.add(grid[x][y + 1]);
		if(!this.isImpossible(x, y - 1))	around.add(grid[x][y - 1]);
		if(!this.isImpossible(x + 1, y))	around.add(grid[x + 1][y]);
		if(!this.isImpossible(x - 1, y))	around.add(grid[x - 1][y]);

		if(OptionsPanel.chechDiagonal()) {
			if(!this.isImpossible(x + 1, y + 1))	around.add(grid[x + 1][y + 1]);
			if(!this.isImpossible(x + 1, y - 1))	around.add(grid[x + 1][y - 1]);
			if(!this.isImpossible(x - 1, y + 1))	around.add(grid[x - 1][y + 1]);
			if(!this.isImpossible(x - 1, y - 1))	around.add(grid[x - 1][y - 1]);
		}

		return around;
	}

	private boolean isImpossible(int x, int y) {
		//Check whether it's not outside the window
		return (x < 0 || x >= columns) || (y < 0 || y >= rows);
	}

	//Return the Node with the smaller F
	private Node getCurrent() {
		Node current = null;
		for(Node node : openList) {
			if(current == null || node.getF() < current.getF()) {
				current = node;
			}
		}
		return current;
	}

	public void start() {
		OptionsPanel.setText("Searching...", false);

		//Clean yellow traces
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < columns; x++) {
				if(grid[x][y].getBackground() == PATH_COLOR) {
					grid[x][y].setBackground(EMPTY_COLOR);
				}
			}
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				ArrayList <Node> path = GridPanel.this.getPath();
				if(path != null) {
					OptionsPanel.setText("A path has been found in " + (System.currentTimeMillis() - start) + "ms !", false);

					for(Node node : path) {
						node.setBackground(PATH_COLOR);
						try {
							Thread.sleep(OptionsPanel.getWaitingTime());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					OptionsPanel.setText("No existing path.", true);
				}
			}
		}).start();
	}

	public void setGridSize(int columns, int rows) {
		GridPanel.columns = columns;
		GridPanel.rows = rows;
		this.setLayout(new GridLayout(rows, columns));;
		this.init();
		//Refreshes the frame
		this.getParent().repaint();
	}

	public void generateWall() {
		this.init();

		int walls = 0;
		Random rand = new Random();

		while(walls != (columns*rows) * OptionsPanel.getWalls() / 100) {
			int randX = rand.nextInt(columns);
			int randY = rand.nextInt(rows);

			JButton bu = grid[randX][randY];

			if(bu.getBackground() != WALL_COLOR && bu != start && bu != end) {
				bu.setBackground(WALL_COLOR);
				walls++;
			}
		}
	}
}
