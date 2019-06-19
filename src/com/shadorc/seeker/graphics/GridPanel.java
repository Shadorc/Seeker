package com.shadorc.seeker.graphics;

import com.shadorc.seeker.AStar;
import com.shadorc.seeker.Node;
import com.shadorc.seeker.graphics.event.NodeMouseListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class GridPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static final Color START_COLOR = Color.RED;
    public static final Color END_COLOR = Color.GREEN;
    public static final Color WALL_COLOR = Color.BLACK;
    public static final Color PATH_COLOR = Color.YELLOW;
    public static final Color EMPTY_COLOR = Color.WHITE;

    private final AtomicBoolean isMousePressed;

    private int columns;
    private int rows;
    private Node[][] grid;
    private Node startNode;
    private Node endNode;

    public GridPanel(int columns, int rows) {
        this.isMousePressed = new AtomicBoolean(false);
        this.setGridSize(columns, rows);
    }

    public void setGridSize(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;

        this.setLayout(new GridLayout(rows, columns));
        this.generateGrid();

        if (this.getParent() != null) {
            // Refresh main frame
            this.getParent().revalidate();
            this.getParent().repaint();
        }
    }

    public void generateGrid() {
        this.removeAll();

        this.grid = new Node[this.columns][this.rows];

        // Generate grid
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.columns; x++) {
                final Node node = new Node(x, y);
                node.setBackground(EMPTY_COLOR);
                node.addMouseListener(new NodeMouseListener(this.isMousePressed));
                this.grid[x][y] = node;
                this.add(this.grid[x][y]);
            }
        }

        this.startNode = this.grid[this.columns / 2][this.rows - 1];
        this.startNode.setBackground(START_COLOR);
        this.startNode.setForeground(Color.BLACK);
        this.startNode.setText("Start");

        this.endNode = this.grid[this.columns / 2][0];
        this.endNode.setBackground(END_COLOR);
        this.endNode.setForeground(Color.BLACK);
        this.endNode.setText("End");

        OptionsPanel.setText("\"Draw\" walls", false);
    }

    public void start() {
        OptionsPanel.setText("Searching...", false);

        // Clean yellow traces
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.columns; x++) {
                if (this.grid[x][y].getBackground().equals(PATH_COLOR)) {
                    this.grid[x][y].setBackground(EMPTY_COLOR);
                }
            }
        }

        this.endNode.setBackground(END_COLOR);

        final Thread thread = new Thread(() -> {
            final long startTime = System.currentTimeMillis();

            final AStar aStar = new AStar(this.grid, this.startNode, this.endNode);
            final List<Node> path = aStar.getPath();

            if (path != null) {
                final long elapsedTime = System.currentTimeMillis() - startTime;
                OptionsPanel.setText("A path has been found in " + elapsedTime + "ms !", false);

                for (final Node node : path) {
                    node.setBackground(PATH_COLOR);
                    try {
                        Thread.sleep(OptionsPanel.getWaitingTime());
                    } catch (final InterruptedException err) {
                        OptionsPanel.setText(err.getMessage(), true);
                    }
                }
            } else {
                OptionsPanel.setText("No existing path.", true);
            }
        });
        thread.start();
    }

    public void generateRandomWalls() {
        this.generateGrid();

        final Random rand = new Random();

        final int wallsToPlace = this.columns * this.rows * OptionsPanel.getWallRatio() / 100;
        int wallsPlaced = 0;

        while (wallsPlaced != wallsToPlace) {
            final int randX = rand.nextInt(this.columns);
            final int randY = rand.nextInt(this.rows);

            final Node node = this.grid[randX][randY];

            if (!node.isWall() && !node.equals(this.startNode) && !node.equals(this.endNode)) {
                node.setBackground(WALL_COLOR);
                node.isWall(true);
                wallsPlaced++;
            }
        }
    }
}
