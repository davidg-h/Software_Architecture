package ohm.softa.a03.states;

import ohm.softa.a03.Cat;
import ohm.softa.a03.State;

public class PlayfulState extends State {
    protected PlayfulState(int duration) {
        super(duration);
    }

    @Override
    protected State successor(Cat cat) {
        return new SleepingState(cat.getSleep());
    }
}
