#include "labyrinth.h"
#include <iostream>
#include <vector>
#include <queue>
#include <stdexcept>

Tile* Labyrinth::getOrigin()
{
 
	for(unsigned int i = 0; i < floor.size(); i++){
		for(unsigned int j = 0; j < floor[i].size(); j++){
			if(floor[i][j].isOrigin()){
				return &floor[i][j];
			}
		}
	}
	
	return NULL;
}

/** Helper function that is supposed to be used to process the neighbors of a Tile. */
void emplaceNeighbor(std::vector<std::vector<Tile>>& floor,
                     std::vector<std::vector<Tile*>>& predecessors,
                     std::queue<Tile*>& pending,
                     Tile* current, size_t x, size_t y)
{
  
	for(unsigned int i = 0; i < x; i++){
		for(unsigned int j = 0; j < y; j++){
			if(floor[i][j].isVisited() || !floor[i][j].isBarrier()){
				pending.emplace(&floor[i][j]);
				predecessors[i][j] = current;
			}else{
				predecessors[i][j] = nullptr;
			}
		}
	}
}

void Labyrinth::searchShortestPath()
{
    std::queue<Tile*> pending;
    std::vector<std::vector<Tile*>> predecessors(this->floor.size(), std::vector<Tile*>(this->floor[0].size(), nullptr));
    
	for(unsigned int i = 0; i < floor.size(); i++){
		for(unsigned int j = 0; j < floor[i].size(); j++){
			std::cout << "keine Ahnung was soll ich hier weitermachen!" << std::endl;
		}
	}
	
}


void Labyrinth::printLabyrinth(std::ostream& os, bool visualizeVisited) const
{
    for(const std::vector<Tile>& row: this->floor)
    {
        for(const Tile& tile: row)
            tile.print(os, visualizeVisited);
        os<<"\n";
    }
}

