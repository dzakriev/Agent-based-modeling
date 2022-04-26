package application.simulation;

import static java.lang.Math.*;

public class DynamicModel {
    private int populationCount;
    private int healthyCount;
    private int latentCount;
    private int sickCount;
    private int recoveredCount;
    private double infectious;
    private double contactRateInfectious;
    private int averageIncubationTime;
    private int averageIllnessDuration;
    private int[][] history;
    private int historyLength;
    public int getPopulationCount() {
        return populationCount;
    }
    public int getHealthyCount() {
        return healthyCount;
    }
    public int getLatentCount() {
        return latentCount;
    }
    public int getSickCount() {
        return sickCount;
    }
    public int getRecoveredCount() {
        return recoveredCount;
    }
    public double getInfectious() {
        return infectious;
    }
    public double getContactRateInfectious() {
        return contactRateInfectious;
    }
    public int getAverageIncubationTime() {
        return averageIncubationTime;
    }
    public int getAverageIllnessDuration() {
        return averageIllnessDuration;
    }
    public int getHistoryLength(){
        return historyLength;
    }
    public int[][] getHistory(){
        return history;
    }

    public DynamicModel(int populationCount){
        this.populationCount = populationCount;
        healthyCount = populationCount - 1;
        latentCount = 0;
        sickCount = 1;
        recoveredCount = 0;
        infectious = 0.6;
        contactRateInfectious = 1.25;
        averageIncubationTime = 10;
        averageIllnessDuration = 15;
        history = new int[100000][];
        for (int i = 0; i < 100000; i++){
            history[i] = new int[4];
        }
        historyLength = 0;
    }

    public void Tick(){
        int temp;

        temp = (int) ceil( (float) latentCount/averageIncubationTime);
        if (latentCount >= temp) {
            latentCount -= temp;
            sickCount += temp;
        } else {
            sickCount += latentCount;
            latentCount = 0;
        }

        temp = (int) ceil( (float) sickCount*contactRateInfectious*infectious*healthyCount/populationCount);
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

    public void Cycle(){
        int i = 0;
        history[i][0] = healthyCount;
        history[i][1] = latentCount;
        history[i][2] = sickCount;
        history[i][3] = recoveredCount;
        i++;
        while (recoveredCount < populationCount) {
            Tick();
            history[i][0] = healthyCount;
            history[i][1] = latentCount;
            history[i][2] = sickCount;
            history[i][3] = recoveredCount;
            i++;
        }
        historyLength = i-1;
        for (int j = 0; j < i; j++){
            for (int z = 0; z < 4; z++){
                System.out.print(history[j][z] + " ");
            }
            System.out.println();
        }
    }
}