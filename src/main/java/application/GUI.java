package application;

import application.agent.AgentBased;
import application.simulation.DynamicSimulation;
import javax.swing.*;
import javax.swing.JFrame;

public class GUI extends JFrame {
    public GUI(AgentBased agentBased, DynamicSimulation dynamicSimulation) {
        this.setTitle("Application");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Agent Based", null, agentBased,
                "Models agent based system");
        tabbedPane.addTab("Dynamic Simulation", null, dynamicSimulation,
                "Models dynamic simulation system");
        add(tabbedPane);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
