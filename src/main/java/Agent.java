import java.util.Random;

import static java.lang.StrictMath.abs;

public class Agent {
    int x;
    int y;
    int daysLeft;
    Random rand = new Random();
    boolean isUser = false;
    public Agent(){
        x = rand.nextInt()%256;
        y = rand.nextInt()%256;
        int daysLeft = 0;
        boolean isUser = false;
    }
    public void setUser(boolean isUser){
        this.isUser = isUser;
        int daysLeft = rand.nextInt();
    }
    public void update() {
        if (isUser)
            if (daysLeft != 0)
                daysLeft--;
            else isUser = false;
        else if (abs(rand.nextInt())%100<10){
            isUser = true;
            daysLeft = rand.nextInt()%5+1;
        }
    }
}
