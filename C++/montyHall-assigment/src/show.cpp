/**
 * @file show.cpp
 */

#include <random>
#include <cassert>
#include "show.h"
#include <cstdbool>


static std::random_device rd;
std::default_random_engine Show::re(rd());

Show::Show()
{
    std::uniform_int_distribution<int> dist(1, 3);
    carDoor = dist(re);
}

int Show::showGoatDoor(int firstGuess)
{
    
    std::uniform_int_distribution<int> random(1, 3);
	int door;
	bool running = true;
	while(running){
		door = random(re);
		if(door != carDoor && door != firstGuess){
			running = false;
		}
	}
	
	return door;
}



