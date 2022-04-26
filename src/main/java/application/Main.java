package application;

import application.agent.AgentBasedPanel;
import application.simulation.DynamicSimulation;

import java.io.IOException;

public class Main {
    private static AgentBasedPanel agentBasedPanel;
    private static DynamicSimulation dynamicSimulation;
    static {
            dynamicSimulation = new DynamicSimulation();
        try {
            agentBasedPanel = new AgentBasedPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI(agentBasedPanel, dynamicSimulation);
    }
}
