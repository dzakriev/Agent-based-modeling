import java.io.IOException;

public class Main {
    private static GUI gui;

    static {
        try {
            gui = new GUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Agent[] create(int n){
        Agent[] agents = new Agent[n];
        for (int i = 0; i < n; i++){
            agents[i] = new Agent();
        }
        gui.clear();
        gui.setAgents(agents);
        cycle();
        gui.paintComponent();
        gui.setVisible(true);

        return agents;
    }
    public static void cycle(){

        int n = gui.getText();
        int[] active_data = new int [10];
        int[] inactive_data = new int [10];
        for(int j = 0; j <10; j++) {
            active_data[j] =  gui.getActiveAgents();
            inactive_data[j] = gui.getInactiveAgents();
            for (int i = 0; i < n; i++) {
                gui.getAgents()[i].update();
            }
        }
        gui.setData(active_data, inactive_data);
    }
    public static void main(String[] args){
        create(100);

    }
}
