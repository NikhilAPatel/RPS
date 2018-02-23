package powerups;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by harid on 2/23/2018.
 */

public class DoublePoints extends PowerUps{

    private static final int scoreCheckTime = 200;
    public DoublePoints(double duration)
    {
        super("Double Points", duration, "Doubles the controlling player's points");
    }
    public void action()
    {
        final long startTime = System.currentTimeMillis();
        Timer powerUpTimer = new Timer();
        TimerTask power = new TimerTask()
        {
            public void run() {
                long passedTime = System.currentTimeMillis() - startTime;
                if (passedTime>DoublePoints.super.getDuration())
                {
                    cancel();

                }
                else
                {
                    //run whatever the powerup is supposed to do
                }
            }

        };
        powerUpTimer.scheduleAtFixedRate(power, 0, scoreCheckTime);
    }
}
