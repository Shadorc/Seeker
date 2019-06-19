package com.shadorc.seeker;

import javax.swing.*;

public class Node extends JButton {

    private static final long serialVersionUID = 1L;

    // Parent, Node that allowed "to arrive" to this Node
    private Node p;

    // Distance from the starting point to this Node
    private double g;
    // Distance as the crow flies between this Node and the end Node
    private double h;
    // Sum of G and H
    private double f;

    // Is this Node a wall ?
    private boolean isWall;

    private final int x;
    private final int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getPosX() {
        return this.x;
    }

    public int getPosY() {
        return this.y;
    }

    /**
     * @return Distance from the starting point to this Node
     */
    public double getG() {
        return this.g;
    }

    /**
     * @return Distance as the crow flies between this Node and the finish Node
     */
    public double getH() {
        return this.h;
    }

    /**
     * @return Sum of G and H
     */
    public double getF() {
        return this.f;
    }

    /**
     * @return Node's parent (Node that allowed "to arrive" to this Node)
     */
    public Node getP() {
        return this.p;
    }

    /**
     * @return true if the node is a wall, false otherwise
     */
    public boolean isWall() {
        return this.isWall;
    }

    /**
     * @param g Distance from the starting point to this Node
     */
    public void setG(double g) {
        this.g = g;
    }

    /**
     * @param h DDistance as the crow flies between this Node and the finish Node
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
     * @param p Node's parent (Node that allowed "to arrive" to this Node)
     */
    public void setP(Node p) {
        this.p = p;
    }

    /**
     * @param isWall true if the node is a wall, false otherwise
     */
    public void isWall(boolean isWall) {
        this.isWall = isWall;
    }
}
