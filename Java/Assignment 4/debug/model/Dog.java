package debug.model;

import debug.Animal;

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }


    @Override
    public String getName() {
        return super.getName(); //1) must call Getter of name.
    }

    @Override
    public int getArms() {
        return 0; //2) Dog has no arms only Legs!
    }

    @Override
    public int getLegs() {
        return 4;
    }

    @Override
    public String toString() {
                                               //3)first this should be legs     then here must be this.getLegs
        return String.format("%s is a dog with %d legs and %d arms.", this.getName(),  this.getLegs(), this.getArms());
    }
}
