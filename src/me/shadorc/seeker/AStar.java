package me.shadorc.seeker;

import java.util.ArrayList;
import java.util.Collections;

public class AStar {

	private ArrayList <Node> openList, closeList;
	private Node start, end;
	private Node[][] grid;

	/**
	 * @param grid Map which we must determine the shortest path
	 * @param start Start Node
	 * @param end End Node
	 */
	public AStar(Node[][] grid, Node start, Node end) {
		this.grid = grid;
		this.start = start;
		this.end = end;

		openList = new ArrayList <Node>();
		closeList = new ArrayList <Node>();
	}

	/**
	 * @return The shortest way to go from start to end as an ArrayList of Node
	 */
	public ArrayList <Node> getPath() {
		openList.add(start);

		while(!closeList.contains(end)) {
			Node current = this.getCurrent();
			closeList.add(current);
			openList.remove(current);

			if(current == null) return null;

			for(Node node : this.getAround(current)) {

				if(node.isWall() || closeList.contains(node)) continue;

				if(!openList.contains(node)) {
					openList.add(node);
					node.setP(current);
					node.setG(this.calculateG(node));
					/*Distance between node and the end : sqrt((node.x-end.x)^2 + (node.y-end.y)^2))*/
					node.setH(Math.sqrt(Math.pow(node.getPosX() - end.getPosX(), 2) + Math.pow(node.getPosY() - end.getPosY(), 2)));
					node.setF(node.getG() + node.getH());
				} else {
					int g = this.calculateG(node);
					if(g < node.getG()) {
						node.setP(current);
						/*Distance between node and the end : sqrt((node.x-end.x)^2 + (node.y-end.y)^2))*/
						node.setH(Math.sqrt(Math.pow(node.getPosX() - end.getPosX(), 2) + Math.pow(node.getPosY() - end.getPosY(), 2)));
						node.setF(node.getG() + node.getH());
					}
					node.setG(g);
				}

				if(openList.size() == 0) return null;
			}
		}

		ArrayList <Node> path = new ArrayList <Node>();

		/*We start from the end and we go back to the beginning*/
		Node node = end;
		while(node != start) {
			path.add(node);
			node = node.getP();
		}

		/*The path goes from end to start, so we reverse*/
		Collections.reverse(path);

		return path;
	}

	/**
	 * @return Node with the smaller F
	 */
	private Node getCurrent() {
		Node current = null;
		for(Node node : openList) {
			if(current == null || node.getF() < current.getF()) {
				current = node;
			}
		}
		return current;
	}

	/**
	 * @param current The node that we must retrieve surrounding Nodes
	 * @return Surrounding Nodes 
	 */
	private ArrayList <Node> getAround(Node current) {
		ArrayList <Node> around = new ArrayList <Node>();

		int x = current.getPosX();
		int y = current.getPosY();

		if(this.isPossible(x, y + 1)) around.add(grid[x][y + 1]);
		if(this.isPossible(x, y - 1)) around.add(grid[x][y - 1]);
		if(this.isPossible(x + 1, y)) around.add(grid[x + 1][y]);
		if(this.isPossible(x - 1, y)) around.add(grid[x - 1][y]);

		if(OptionsPanel.checkDiagonal()) {
			if(this.isPossible(x + 1, y + 1)) around.add(grid[x + 1][y + 1]);
			if(this.isPossible(x + 1, y - 1)) around.add(grid[x + 1][y - 1]);
			if(this.isPossible(x - 1, y + 1)) around.add(grid[x - 1][y + 1]);
			if(this.isPossible(x - 1, y - 1)) around.add(grid[x - 1][y - 1]);
		}

		return around;
	}

	/**
	 * @return If coordinates are not out of bounds
	 */
	private boolean isPossible(int x, int y) {
		return (x >= 0 && x < grid[0].length) && (y >= 0 && y < grid.length);
	}

	/**
	 * @return Every move it tooks to arrive from this Node
	 */
	private int calculateG(Node current) {
		int step = 0;
		Node node = current;
		while(node != start) {
			step++;
			node = node.getP();
		}
		return step;
	}
}
