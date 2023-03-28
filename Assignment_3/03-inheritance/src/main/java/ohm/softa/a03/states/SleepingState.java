package ohm.softa.a03.states;

import ohm.softa.a03.Cat;
import ohm.softa.a03.State;

public class SleepingState extends State {

    public SleepingState(int duration){
        super(duration);
    }
    @Override
    protected State successor(Cat cat) {
        return new HungryState(cat.getAwake());
    }
}
