package application;

import application.agent.AgentBased;

import javax.swing.*;
import javax.swing.JFrame;

public class GUI extends JFrame {
    public GUI(AgentBased agentBased) {
        this.setTitle("Application");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Agent Based", null, agentBased,
                "Models agent based system");
        JFrame frame = new JFrame();
        frame.add(tabbedPane);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
