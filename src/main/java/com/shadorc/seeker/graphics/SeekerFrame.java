package com.shadorc.seeker.graphics;

import com.shadorc.seeker.graphics.panel.GridPanel;
import com.shadorc.seeker.graphics.panel.OptionsPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;

public class SeekerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_COLUMNS = 11;
    public static final int DEFAULT_ROWS = 11;

    private final GridPanel gridPanel;
    private final OptionsPanel optionsPanel;

    public SeekerFrame(String title, int width, int height) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.optionsPanel = new OptionsPanel(this);
        this.gridPanel = new GridPanel(this, DEFAULT_COLUMNS, DEFAULT_ROWS);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(this.gridPanel, BorderLayout.CENTER);
        panel.add(this.optionsPanel, BorderLayout.LINE_END);

        this.setContentPane(panel);
        this.pack();
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
    }

    public GridPanel getGridPanel() {
        return this.gridPanel;
    }

    public OptionsPanel getOptionsPanel() {
        return this.optionsPanel;
    }
}