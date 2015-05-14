package me.shadorc.pathfindor;

import javax.swing.JButton;

public class Node extends JButton {

	private static final long serialVersionUID = 1L;

	/*Parent, Node that allowed "to arrive" at current Node*/
	private Node p; 

	/*Distance from the starting point to arrive at the current Node*/
	private double g; 
	/*Distance as the crow flies between the Node and the finish Node*/
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

	/**
	 * @return Distance from the starting point to arrive at the current Node
	 */
	public double getG() {
		return g;
	}

	/**
	 * @return Distance as the crow flies between the Node and the finish Node
	 */
	public double getH() {
		return h;
	}

	/**
	 * @return Sum of G and H
	 */
	public double getF() {
		return f;
	}

	/**
	 * @return Node's parent (Node that allowed "to arrive" at current Node)
	 */
	public Node getP() {
		return p;
	}

	/**
	 * @return Whether or not the node is a wall
	 */
	public boolean isWall() {
		return isWall;
	}

	/**
	 * @param g Distance from the starting point to arrive at the current Node
	 */
	public void setG(double g) {
		this.g = g;
	}

	/**
	 * @param h Distance as the crow flies between the Node and the finish Node
	 */
	public void setH(double h) {
		this.h = h;
	}

	/**
	 * @param f Sum of G and H
	 */
	public void setF(double f) {
		this.f = f;
	}

	/**
	 * @param p Node's parent (Node that allowed "to arrive" at current Node)
	 */
	public void setP(Node p) {
		this.p = p;
	}

	/**
	 * @param isWall Determine if the Node is a wall
	 */
	public void isWall(boolean isWall) {
		this.isWall = isWall;
	}
}
