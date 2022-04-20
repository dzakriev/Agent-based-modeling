package application;

import application.agent.AgentBased;
import application.simulation.DynamicSimulation;

import java.io.IOException;

public class Main {
    private static AgentBased agentBased;
    private static DynamicSimulation dynamicSimulation = new DynamicSimulation();
    static {
        try {
            agentBased = new AgentBased();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI(agentBased, dynamicSimulation);
    }
}
