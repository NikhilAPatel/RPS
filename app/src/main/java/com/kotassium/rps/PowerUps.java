package com.kotassium.rps;

/**
 * Created by harid on 2/23/2018.
 */

public class PowerUps {
    public enum Boosts {Doubler};
    private Boosts boost;
    private double duration;
    private boolean activityState;
    private boolean hasRun;
    public static double DoublerDuration=.1;
    public PowerUps(Boosts boost, double duration)
    {
        this.boost=boost;
        this.duration=duration;
        activityState=false;
        hasRun=false;
    }
    public Boosts getName()
    {
        return boost;
    }
    public static double getDuration(int bonusID)
    {
        switch (bonusID){
        case 1:
            return DoublerDuration;
        default:
            return -1.0;
    }
    }
    public boolean isActive()
    {
        return activityState;
    }
    public boolean getHasRun()
    {
        return hasRun;

    }
    public void setHasRun(boolean newRun)
    {
        hasRun=newRun;
    }
    public void setActivityState(boolean newState)
    {
        activityState=newState;
    }
}
