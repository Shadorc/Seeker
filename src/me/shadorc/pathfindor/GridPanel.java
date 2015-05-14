package me.shadorc.pathfindor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private boolean mousePressed = false;

	private Color START_COLOR = Color.RED;
	private Color END_COLOR = Color.GREEN;
	private Color WALL_COLOR = Color.BLACK;
	private Color PATH_COLOR = Color.YELLOW;
	private Color EMPTY_COLOR = Color.WHITE;

	private static int columns = 11;
	private static int rows = 11;

	private Node[][] grid;
	private Node start, end;

	GridPanel() {
		super(new GridLayout(rows, columns));
		this.init();
	}

	public void init() {
		this.removeAll();

		grid = new Node[columns][rows];

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
								node.setBackground(node.isWall() ? EMPTY_COLOR : WALL_COLOR);
								node.isWall(node.getBackground() == WALL_COLOR);
							}
							mousePressed = true;
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						Node node = (Node) e.getSource();
						if(mousePressed && node.getBackground() != START_COLOR && node.getBackground() != END_COLOR) {
							node.setBackground(WALL_COLOR);
							node.isWall(true);
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

		OptionsPanel.setText("\"Paint\" walls", false);
	}

	public void start() {
		OptionsPanel.setText("Searching...", false);

		/*Clean yellow traces*/
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < columns; x++) {
				if(grid[x][y].getBackground() == PATH_COLOR) {
					grid[x][y].setBackground(EMPTY_COLOR);
				}
			}
		}

		end.setBackground(END_COLOR);

		new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();

				AStar aStar = new AStar(grid, start, end);
				ArrayList <Node> path = aStar.getPath();

				if(path != null) {
					OptionsPanel.setText("A path has been found in " + (System.currentTimeMillis() - startTime) + "ms !", false);

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
		this.setLayout(new GridLayout(rows, columns));
		this.init();
		/*Refreshes the frame*/
		this.getParent().revalidate();
		this.getParent().repaint();
	}

	public void generateWalls() {
		this.init();

		Random rand = new Random();

		int wallsToPlace = (columns*rows) * OptionsPanel.getWallsNum() / 100;
		int wallsPlaced = 0;

		while(wallsPlaced != wallsToPlace) {
			int randX = rand.nextInt(columns);
			int randY = rand.nextInt(rows);

			Node node = grid[randX][randY];

			if(!node.isWall() && node != start && node != end) {
				node.setBackground(WALL_COLOR);
				node.isWall(true);
				wallsPlaced++;
			}
		}
	}
}
