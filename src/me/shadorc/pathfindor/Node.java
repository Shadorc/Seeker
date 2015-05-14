package me.shadorc.pathfindor;

import javax.swing.JButton;

public class Node extends JButton {

	private static final long serialVersionUID = 1L;

	/*Parent, Node that allowed "to arrive" at current Node*/
	private Node p; 

	/*Distance from the starting point to arrive at the current Node*/
	private double g; 
	/*Distance as the crow flies between the Node and the current finish Node*/
	private double h; 
	/*Sum of G and H*/
	private double f; 

	/*The Node is a wall*/
	private boolean isWall;

	private int x, y;

	Node(int x, int y) {
		super();

		this.x = x;
		this.y = y;
	}

	public int getPosX() {
		return x;
	}

	public int getPosY() {
		return y;
	}

	public double getG() {
		return g;
	}

	public double getH() {
		return h;
	}

	public double getF() {
		return f;
	}

	public Node getP() {
		return p;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setG(double g) {
		this.g = g;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void setF(double f) {
		this.f = f;
	}

	public void setP(Node p) {
		this.p = p;
	}

	public void isWall(boolean isWall) {
		this.isWall = isWall;
	}
}
