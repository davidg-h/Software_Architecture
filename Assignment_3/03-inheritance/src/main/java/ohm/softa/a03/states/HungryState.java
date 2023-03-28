package ohm.softa.a03.states;

import ohm.softa.a03.Cat;
import ohm.softa.a03.State;

public class HungryState extends State{
    protected HungryState(int duration) {
        super(duration);
        logger.info("hungry");
    }

    public State feed(Cat cat){
        return new DigestingState(cat.getDigest(), cat.getAwake() - getDuration());
    }

    @Override
    protected State successor(Cat cat) {
        return new DeathState();
    }
}
