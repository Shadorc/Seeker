package com.shadorc.seeker.graphics.event;

import com.shadorc.seeker.Node;
import com.shadorc.seeker.graphics.panel.GridPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class NodeMouseListener extends MouseAdapter {

    private final AtomicBoolean isMousePressed;

    public NodeMouseListener(AtomicBoolean isMousePressed) {
        this.isMousePressed = isMousePressed;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            final Node node = (Node) e.getSource();
            if (!NodeMouseListener.isEndOrStartNode(node)) {
                node.setBackground(node.isWall() ? GridPanel.EMPTY_COLOR : GridPanel.WALL_COLOR);
                node.isWall(node.getBackground() == GridPanel.WALL_COLOR);
            }
            this.isMousePressed.set(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.isMousePressed.set(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        final Node node = (Node) e.getSource();
        if (this.isMousePressed.get() && !NodeMouseListener.isEndOrStartNode(node)) {
            node.setBackground(GridPanel.WALL_COLOR);
            node.isWall(true);
        }
    }

    private static boolean isEndOrStartNode(Node node) {
        return node.getBackground().equals(GridPanel.START_COLOR) || node.getBackground().equals(GridPanel.END_COLOR);
    }
}
