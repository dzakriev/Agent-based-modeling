package application.eventdriven;

import java.util.Random;

import static java.lang.StrictMath.abs;

public class EventModel {
    private int leftCoastCount;
    private int rightCoastCount;
    private int raftCount;
    private boolean isRight;
    private final Random rand = new Random();
    private boolean isLeft;
    private int iterations;

    public EventModel(){
        leftCoastCount = 0;
        rightCoastCount = 0;
        raftCount = 0;
        iterations = 0;
        isRight = true;
        isLeft = false;
    }

    public void toRight(){
        isLeft = false;
        isRight = true;
        iterations++;
    }

    public void toLeft(){
        isLeft = true;
        isRight = false;
        iterations++;
    }

    public void addRight(){
        rightCoastCount += abs(rand.nextInt()) % 4 + 1;
        if (rightCoastCount > 4) rightCoastCount = 4;
    }

    public void addLeft(){
        leftCoastCount += abs(rand.nextInt()) % 4 + 1;
        if (leftCoastCount > 4) leftCoastCount = 4;
    }

    public void exit(){
        raftCount = 0;
    }

    public void onBoard(){
        if (isRight){
            raftCount = rightCoastCount;
            rightCoastCount = 0;
        }
        if (isLeft){
            raftCount = leftCoastCount;
            leftCoastCount = 0;
        }
    }

    public String Cycle(int iterationsToDo){
        StringBuilder ret = new StringBuilder();
        while(iterations < iterationsToDo){
            if (isRight) {
                if (raftCount != 0){
                    exit();
                    ret.append("exit\n");
                }

                if (rightCoastCount == 0) {
                    addRight();
                    ret.append("addRight\n");
                }

                onBoard();
                toLeft();
                ret.append("onBoard\n").append("toLeft\n");
            } else if (isLeft){
                if (raftCount != 0){
                    exit();
                    ret.append("exit\n");
                }

                if (leftCoastCount == 0) {
                    addLeft();
                    ret.append("addLeft\n");
                }

                onBoard();
                toRight();
                ret.append("onBoard\n").append("toRight\n");
            }
        }
        return ret.toString();
    }
}
