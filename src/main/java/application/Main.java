package application;

import application.agent.AgentBased;

import java.io.IOException;

public class Main {
    private static AgentBased agentBased;

    static {
        try {
            agentBased = new AgentBased();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        GUI gui = new GUI(agentBased);
    }
}
