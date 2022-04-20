package application;

import application.agent.AgentBased;

import javax.swing.*;
import javax.swing.JFrame;

public class GUI extends JFrame {
    public GUI(AgentBased agentBased) {

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Agent Based", null, agentBased,
                "Models agent based system");
        JFrame frame = new JFrame();
        frame.add(tabbedPane);
        frame.setVisible(true);
        frame.pack();
        //frame.setSize(1800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
