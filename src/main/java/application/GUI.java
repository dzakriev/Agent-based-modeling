package application;

import application.agent.AgentBasedPanel;
import application.simulation.DynamicSimulation;
import javax.swing.*;
import javax.swing.JFrame;

public class GUI extends JFrame {
    public GUI(AgentBasedPanel agentBasedPanel, DynamicSimulation dynamicSimulation) {
        this.setTitle("Application");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Agent Based", null, agentBasedPanel,
                "Models agent based system");
        tabbedPane.addTab("Dynamic Simulation", null, dynamicSimulation,
                "Models dynamic simulation system");
        add(tabbedPane);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
