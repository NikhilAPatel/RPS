package powerups;

/**
 * Created by harid on 2/23/2018.
 */

public abstract class PowerUps {
    private String name;
    private double duration;
    private String description;
    private boolean activityState;
    private boolean hasRun;
    public PowerUps(String name, double duration, String description)
    {
        this.name=name;
        this.duration=duration;
        this.description=description;
        activityState=false;
        hasRun=false;
    }
    public String getName()
    {
        return name;
    }
    public double getDuration()
    {
        return duration;
    }
    public String getDescription()
    {
        return description;
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
