package application.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class Agent {
    public static final List<Agent> connections = new ArrayList<>();
    private int x;
    private int y;
    private int daysLeft;
    private final Random rand = new Random();
    private boolean isUser;

    public Agent() {
        x = rand.nextInt() % 256;
        y = rand.nextInt() % 256;
        daysLeft = 0;
        isUser = false;
        connections.add(this);
    }

    public static void clearConnection() {
        connections.clear();
    }

    public void update() {
        if (isUser) {
            if (daysLeft != 0) {
                if (abs(rand.nextInt()) % 100 == 1) {
                    int r = abs(rand.nextInt());
                    if (!connections.get(r % connections.size()).isUser) {
                        connections.get(r % connections.size()).setUser();
                    }
                }
                daysLeft--;
            } else isUser = false;
        } else if (abs(rand.nextInt()) % 100 < 10) {
            setUser();
        }
    }

    public Agent copy() {
        Agent temp_agent = new Agent();
        temp_agent.x = x;
        temp_agent.y = y;
        temp_agent.isUser = isUser;
        temp_agent.daysLeft = daysLeft;
        connections.remove(temp_agent);
        return temp_agent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser() {
        isUser = true;
        daysLeft = abs(rand.nextInt()) % 5 + 1;
    }
}
