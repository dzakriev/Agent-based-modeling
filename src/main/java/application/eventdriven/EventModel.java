package application.eventdriven;

import java.util.*;

import static java.lang.StrictMath.abs;

public class EventModel {
    private int leftCoastCount;
    private int rightCoastCount;
    private int raftCount;
    private boolean isRight;
    private final Random rand = new Random();
    private boolean isLeft;
    private int iterations;
    private final List<Integer> history;

    public int getRaftCount() {
        return raftCount;
    }

    public String whereIsRaft() {
        if (isLeft) return ("left");
        else if (isRight) return ("right");
        else return ("sea");
    }

    public List<Integer> getHistory() {
        return history;
    }

    public EventModel() {
        leftCoastCount = 0;
        rightCoastCount = 0;
        raftCount = 0;
        iterations = 0;
        isRight = true;
        isLeft = false;
        history = new ArrayList<Integer>();
    }

    public void toRight() {
        isLeft = false;
        isRight = true;
        history.add(raftCount);
        iterations++;
    }

    public void toLeft() {
        isLeft = true;
        isRight = false;
        history.add(raftCount);
        iterations++;
    }

    public void addRight() {
        rightCoastCount += abs(rand.nextInt()) % 4 + 1;
        if (rightCoastCount > 4) rightCoastCount = 4;
        history.add(rightCoastCount);
    }

    public void addLeft() {
        leftCoastCount += abs(rand.nextInt()) % 4 + 1;
        if (leftCoastCount > 4) leftCoastCount = 4;
        history.add(leftCoastCount);
    }

    public void exit() {
        history.add(raftCount);
        raftCount = 0;
    }

    public void onBoard() {
        if (isRight) {
            raftCount = rightCoastCount;
            rightCoastCount = 0;
            history.add(rightCoastCount);
        }
        if (isLeft) {
            raftCount = leftCoastCount;
            leftCoastCount = 0;
            history.add(leftCoastCount);
        }
    }

    public String Cycle(int iterationsToDo) {
        StringBuilder ret = new StringBuilder();
        while (iterations < iterationsToDo) {
            if (isRight) {
                if (raftCount != 0) {
                    exit();
                    ret.append("exitRight\n");
                }

                if (rightCoastCount == 0) {
                    addRight();
                    ret.append("addRight\n");
                }

                onBoard();
                toLeft();
                ret.append("onBoard\n").append("toLeft\n");
            } else if (isLeft) {
                if (raftCount != 0) {
                    exit();
                    ret.append("exitLeft\n");
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
        exit();
        ret.append((isLeft ? "exitLeft\n" : "exitRight\n"));
        return ret.toString();
    }
}
