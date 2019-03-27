package com.epam;

import java.util.Properties;

public class Setup {

    private boolean onlyRecentPrediction;
    private int intervalInMinutes;
    private int frameInDays;

    public Setup(Properties properties) {
        this.onlyRecentPrediction = Boolean.valueOf(properties.getProperty("onlyRecentPrediction"));
        this.intervalInMinutes = Integer.valueOf(properties.getProperty("interval.minutes"));
        this.frameInDays = Integer.valueOf(properties.getProperty("frame.days"));
    }

    public boolean isOnlyRecentPrediction() {
        return onlyRecentPrediction;
    }

    public int getIntervalInMinutes() {
        return intervalInMinutes;
    }

    public int getFrameInDays() {
        return frameInDays;
    }
}
