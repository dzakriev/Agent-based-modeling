package application;

import application.agent.AgentBasedPanel;
import application.eventdriven.EventDrivenPanel;
import application.eventdriven.EventModel;
import application.simulation.DynamicSimulationPanel;

public class Main {
    private static AgentBasedPanel agentBasedPanel;
    private static DynamicSimulationPanel dynamicSimulationPanel;
    private static EventDrivenPanel eventDrivenPanel;
    static {
        try {
            eventDrivenPanel = new EventDrivenPanel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dynamicSimulationPanel = new DynamicSimulationPanel();
        agentBasedPanel = new AgentBasedPanel();
    }

    public static void main(String[] args) {
        GUI gui = new GUI(agentBasedPanel, dynamicSimulationPanel, eventDrivenPanel);
        EventModel model = new EventModel();
        System.out.println(model.Cycle(5));
    }
}
