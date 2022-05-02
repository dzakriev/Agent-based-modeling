package application.simulation;

import java.util.ArrayList;

import static java.lang.Math.*;

public class DynamicModel {
    private final int populationCount;
    private int healthyCount;
    private int latentCount;
    private int sickCount;
    private int recoveredCount;
    private final double infectious;
    private final double contactRate;
    private final int averageIncubationTime;
    private final int averageIllnessDuration;
    private final ArrayList<ArrayList<Integer>> history;
    private int historyLength;

    public int getHistoryLength() {
        return historyLength;
    }

    public ArrayList<ArrayList<Integer>> getHistory() {
        return history;
    }

    public DynamicModel(int populationCount, double infectious, double contactRate, int averageIncubationTime, int averageIllnessDuration) {
        this.populationCount = populationCount;
        healthyCount = populationCount - 1;
        latentCount = 0;
        sickCount = 1;
        recoveredCount = 0;
        this.infectious = infectious;
        this.contactRate = contactRate;
        this.averageIncubationTime = averageIncubationTime;
        this.averageIllnessDuration = averageIllnessDuration;
        history = new ArrayList<>();
        Cycle();
    }

    private void Tick() {
        int temp;
        temp = (int) ceil((float) latentCount / averageIncubationTime);
        if (latentCount >= temp) {
            latentCount -= temp;
            sickCount += temp;
        } else {
            sickCount += latentCount;
            latentCount = 0;
        }

        temp = (int) ceil((float) sickCount * contactRate * infectious * healthyCount / populationCount);
        if (healthyCount >= temp) {
            healthyCount -= temp;
            latentCount += temp;
        } else {
            latentCount += healthyCount;
            healthyCount = 0;
        }

        temp = sickCount / averageIllnessDuration;
        if (healthyCount == 0 && latentCount == 0) temp++;
        if (sickCount >= temp) {
            sickCount -= temp;
            recoveredCount += temp;
        } else {
            recoveredCount += sickCount;
            sickCount = 0;
        }
    }

    private void Cycle() {
        int i;
        for (i = 0; recoveredCount < populationCount; i++) {
            if (i != 0) Tick();
            history.add(new ArrayList<>());
            history.get(i).add(healthyCount);
            history.get(i).add(latentCount);
            history.get(i).add(sickCount);
            history.get(i).add(recoveredCount);
        }
        historyLength = i - 1;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Healthy Latent Sick Recovered\n");
        for (int i = 0; i < historyLength; i++) {
            for (int j = 0; j < 4; j++) {
                str.append(history.get(i).get(j)).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}