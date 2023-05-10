/**
 * @file show.h
 */

/**
 * A Show instance represents a Monty Hall Show.
 * Door numbers are 1, 2, 3.
 */
class Show {
private:
    static std::default_random_engine re;
    /** Number of the door the car is behind. */
    int carDoor;
public:
    /** Constructor sets the door the car is behind. */
    Show();
    /** Depending on the first guess of the candidate the showmaster returns the number of a door with a goat behind. */
    int showGoatDoor(int firstGuess);
    /** Figures out if the car is behind the door given by finalGuess */
    bool finalResult(int finalGuess) { return (finalGuess == this->carDoor); }
};


