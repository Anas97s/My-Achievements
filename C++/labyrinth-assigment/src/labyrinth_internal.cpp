#include "labyrinth.h"
#include <cassert>
#include <stdexcept>
#include <string>
#include <sstream>

enum Mark: int
{
    Nothing      = 1,
    Origin       = 2,
    Destination  = 4,
    Barrier      = 8,
    Visited      = 16,
    Path         = 32
};

Mark getMark(char sym)
{
    switch(sym)
    {
        case '-': return Mark::Nothing;
        case 'x': return Mark::Barrier;
        case 'D': return Mark::Destination;
        case 'O': return Mark::Origin;    
    }
    std::ostringstream is;
    is<<"illegal symbol "<<sym<<" found in input";
    throw std::runtime_error(is.str());
}

char getSymbol(char mark, bool visualizeVisited)
{
    if((mark & Mark::Path) && (mark & (Mark::Nothing | Mark::Barrier)))
        return 'p'; 
    else if(visualizeVisited && (mark & Mark::Visited) && (mark & (Mark::Nothing | Mark::Barrier)))
        return 'v';
        
    switch(mark & ~(Mark::Visited | Mark::Path))
    {
        case Mark::Nothing:     return '-';
        case Mark::Barrier:     return 'x';
        case Mark::Destination: return 'D';
        case Mark::Origin:      return 'O';
        default: return '|';
    }
}

Tile::Tile(char sym, size_t x, size_t y) :
        xPos(x), yPos(y), type(static_cast<char>(getMark(sym)))
{}

bool Tile::isVisited()     const { return this->type & Mark::Visited;     }
bool Tile::isOrigin()      const { return this->type & Mark::Origin;      }
bool Tile::isDestination() const { return this->type & Mark::Destination; }
bool Tile::isOnPath()      const { return this->type & Mark::Path; }
bool Tile::isBarrier()     const { return this->type & Mark::Barrier; }
bool Tile::isEmpty()       const { return this->type & Mark::Nothing; }


void Tile::print(std::ostream& os, bool visualizeVisited) const
{
    os << getSymbol(this->type, visualizeVisited);
}
    

std::ostream& operator<<(std::ostream& os, const Tile& tile) 
{ 
    tile.print(os, false);
    return os;
}

size_t Tile::getX() const { return this->xPos; }
size_t Tile::getY() const { return this->yPos; }

void Tile::addToPath()
{
    this->type   |= Mark::Path;
}
void Tile::removeFromPath()
{
    this->type   &= ~Mark::Path;
}
void Tile::visit()
{
    this->type |= Mark::Visited;
}
void Tile::resetVisited()
{
    this->type &= ~Mark::Visited;
}



std::vector<std::vector<Tile>> parse(std::istream& is)
{
    std::vector<std::vector<Tile>> result;
    std::string line;
    for(size_t y = 0; std::getline(is, line); ++y)
    {
        result.emplace_back();
        size_t x = 0;
        for(auto iter = line.begin(); iter != line.end(); ++iter, ++x)
            result.back().emplace_back(*iter, x, y);
    }
    return result;
}

