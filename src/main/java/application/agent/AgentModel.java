package application.agent;

import java.util.ArrayList;
import java.util.Arrays;

public class AgentModel {

    private final ArrayList<Agent> agents;
    private final ArrayList<ArrayList<Agent>> agentsHistory;
    private final int agentsCount;
    private final int iterations;
    private int[] activeData;
    private int[] inactiveData;


    public AgentModel(int agentsCount, int iterations) {
        this.agentsCount = agentsCount;
        this.iterations = iterations;
        Agent.clearConnection();
        agents = new ArrayList<>();
        for (int i = 0; i < agentsCount; i++) {
            agents.add(new Agent());
        }
        agentsHistory = new ArrayList<>();
        cycle();
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public int getActiveAgents() {
        int i = 0;
        for (Agent agent : agents) {
            if (agent.isUser()) i++;
        }
        return i;
    }

    public int getInactiveAgents() {
        int i = 0;
        for (Agent agent : agents) {
            if (!agent.isUser()) i++;
        }
        return i;
    }

    public int[] getActiveData() {
        return activeData;
    }

    public int[] getInactiveData() {
        return inactiveData;
    }

    public ArrayList<ArrayList<Agent>> getHistory() {
        return agentsHistory;
    }

    /**
     * Makes iterations in the model, saves data.
     */
    private void cycle() {
        int[] activeData = new int[iterations];
        int[] inactiveData = new int[iterations];
        for (int i = 0; i < iterations; i++) {
            agentsHistory.add(new ArrayList<>());
            for (int j = 0; j < agentsCount; j++) {
                agentsHistory.get(i).add(agents.get(j).copy());
                Agent.connections.remove(agentsHistory.get(i).get(j));
            }
            activeData[i] = getActiveAgents();
            inactiveData[i] = getInactiveAgents();
            if (i != iterations - 1) {
                for (int j = 0; j < agentsCount; j++) {
                    agents.get(j).update();
                }
            }
        }
        this.activeData = activeData;
        this.inactiveData = inactiveData;
    }
}
