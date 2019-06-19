package com.shadorc.seeker.graphics;

import javax.swing.*;
import java.awt.*;

public class SeekerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_COLUMNS = 11;
    public static final int DEFAULT_ROWS = 11;

    public SeekerFrame(String title, int width, int height) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final GridPanel gridPanel = new GridPanel(DEFAULT_COLUMNS, DEFAULT_ROWS);
        final OptionsPanel options = new OptionsPanel(gridPanel);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(gridPanel, BorderLayout.CENTER);
        panel.add(options, BorderLayout.LINE_END);

        this.setContentPane(panel);
        this.pack();
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
    }
}