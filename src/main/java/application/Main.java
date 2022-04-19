package application;

import application.agent.Agent;
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

    public static Agent[] create(int n){
        Agent[] agents = new Agent[n];
        for (int i = 0; i < n; i++){
            agents[i] = new Agent();
        }
        agentBased.clear();
        agentBased.setAgents(agents);
        cycle();
        agentBased.paintComponent();
        agentBased.setVisible(true);
        return agents;
    }
    public static void cycle(){
        int n = agentBased.getText();
        int[] active_data = new int [10];
        int[] inactive_data = new int [10];
        for(int j = 0; j <10; j++) {
            active_data[j] =  agentBased.getActiveAgents();
            inactive_data[j] = agentBased.getInactiveAgents();
            for (int i = 0; i < n; i++) {
                agentBased.getAgents()[i].update();
            }
        }
        agentBased.setData(active_data, inactive_data);
    }

    public static void main(String[] args){
        GUI gui = new GUI(agentBased);
    }
}
