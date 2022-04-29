package application.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class Agent {
    private static List<Agent> connections = new ArrayList();
    public Agent next = null;
    int x;
    int y;
    int daysLeft;
    private final Random rand = new Random();
    boolean isUser;

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

    public void setUser() {
        isUser = true;
        daysLeft = abs(rand.nextInt()) % 5 + 1;
    }

    public void update() {
        if (isUser)
            if (daysLeft != 0) {
                if (abs(rand.nextInt()) % 100 == 1) {
                    int r = abs(rand.nextInt());
                    if (!connections.get(r % connections.size()).isUser) {
                        connections.get(r % connections.size()).setUser();
                    }
                }
                daysLeft--;
            } else isUser = false;
        else if (abs(rand.nextInt()) % 100 < 10) {
            setUser();
        }
    }
}
