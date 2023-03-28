package ohm.softa.a03;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class State {
    protected static Logger logger = LogManager.getLogger();

    private int t = 0;
    private final int duration;

    protected State(int duration) {
        this.duration = duration;
    }

    final State tick(Cat cat) {
        logger.info("tick()");
        t++;

        if (t < duration){
            logger.info("Still in state: " + getClass().getSimpleName());
            return this;
        } else {
            return successor(cat);
        }
    }

    protected abstract State successor(Cat cat);

    public int getTime() {
        return t;
    }

    public int getDuration() {
        return duration;
    }
}
