package application;

import application.agent.AgentBasedPanel;
import application.eventdriven.EventDrivenPanel;
import application.eventdriven.EventModel;
import application.simulation.DynamicSimulationPanel;

public class Main {
    private static final AgentBasedPanel agentBasedPanel;
    private static final DynamicSimulationPanel dynamicSimulationPanel;
    private static final EventDrivenPanel eventDrivenPanel;

    static {
        eventDrivenPanel = new EventDrivenPanel();
        dynamicSimulationPanel = new DynamicSimulationPanel();
        agentBasedPanel = new AgentBasedPanel();
    }

    public static void main(String[] args) {
        new GUI(agentBasedPanel, dynamicSimulationPanel, eventDrivenPanel);
        EventModel model = new EventModel();
    }
}
