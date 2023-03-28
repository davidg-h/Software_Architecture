package ohm.softa.a03.states;

import ohm.softa.a03.Cat;
import ohm.softa.a03.State;

public class DigestingState extends State {

    private final int remainingAwakeTime;

    protected DigestingState(int duration, int remainingAwakeTime) {
        super(duration);
        this.remainingAwakeTime = remainingAwakeTime;
    }

    @Override
    protected State successor(Cat cat) {
        return new PlayfulState(this.remainingAwakeTime - cat.getDigest());
    }
}
