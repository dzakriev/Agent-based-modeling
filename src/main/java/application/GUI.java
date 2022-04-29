package application;

import application.agent.AgentBasedPanel;
import application.eventdriven.EventDrivenPanel;
import application.simulation.DynamicSimulationPanel;
import javax.swing.*;
import javax.swing.JFrame;

public class GUI extends JFrame {
    public GUI(AgentBasedPanel agentBasedPanel, DynamicSimulationPanel dynamicSimulationPanel, EventDrivenPanel eventDrivenPanel) {
        this.setTitle("Application");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Agent Based", null, agentBasedPanel,
                "Models agent based system");
        tabbedPane.addTab("Dynamic Simulation", null, dynamicSimulationPanel,
                "Models dynamic simulation system");
        tabbedPane.addTab("Event-driven", null, eventDrivenPanel,
                "Models event-driven system");
        add(tabbedPane);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
