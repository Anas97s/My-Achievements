/**
 * @file labyrinth.h
 */

#ifndef LABYRINTH_H
#define LABYRINTH_H


#include <vector>
#include <iostream>

/**
 * Instances of class Tile represent the basic elements which configure a Labyrinth.
 * 
 * A Tile can be either empty, a barrier, the origin or the destination. Additionally a Tile can be marked
 * as visited and / or belonging to the path at any time.
 * This class is implemented in liblabyrinth.a
 */
class Tile
{
private:
    size_t xPos;
    size_t yPos;
    char   type;
public:
    Tile(char sym, size_t x, size_t y);
    friend std::ostream& operator<<(std::ostream& os, const Tile& tile);
    
     /**
     * Inserts the textual representation of this Tile
     * into the given output stream.
     * 
     * @param os The stream to write the Tile to
     * @param visualizeVisited If true and this Tile has been visited and is not on the path, Origin or Destination it 
     * will be printed with v, otherwise it will be printed according to its normal type
     */
    void print(std::ostream& os, bool visualizeVisited = false) const;
    
    /** Marks this Tile as being on the chosen path. */
    void addToPath();
    /** Marks this Tile with has been added to the path as no longer belonging to the path*/
    void removeFromPath();
    /** Marks this Tile as having been visited. */
    void visit();
    /** Removes the visited mark from this Tile*/
    void resetVisited();

    /** Checks if this Tile is visited*/
    bool isVisited()     const;
    /** Checks if this Tile is the origin*/
    bool isOrigin()      const;
    /** Checks if this Tile is the destination*/
    bool isDestination() const;
    /** Checks if this Tile is marked as being on the path*/
    bool isOnPath()      const;
    /** Checks if this Tile is a barrier*/
    bool isBarrier()     const;
    /** Checks if this Tile is empty*/
    bool isEmpty()       const;
    /** Returns the index of column which contains this Tile inside a Labyrinth. */
    size_t getX() const;
    /** Returns the index of row which contains this Tile inside a Labyrinth. */
    size_t getY() const;
};

/**
 * Parses the textual representation of a Labyrinth
 * into a two-dimensional vector containing the grid of Tile instances
 * making up the Labyrinth's floor. This function is implemented in liblabyrinth.a 
 * 
 * @return Two-dimensional vector representing a Labyrinth's floor
 */
std::vector<std::vector<Tile>> parse(std::istream& is);

/**
 * Instances of class Labyrinth are used to determine the shortest path
 * in a rectangular but otherwise arbitrary arrangement of Tile instances.
 */
class Labyrinth
{
private:
    /**
     * Two-dimensional vector containing the grid of Tile instances
     * making up this Labyrinth's floor.
     */
    std::vector<std::vector<Tile>>    floor;
public:
    Labyrinth(std::istream& is) :
            floor(parse(is))
    {}

    /**
     * Searches for the Tile that is supposed to be the starting point of this Labyrinth.
     * @return Pointer to the Tile marked origin
     */
    Tile* getOrigin();

    /**
     * Searches for the shortest path through this Labyrinth
     * and marks all the Tiles on it accordingly.
     */
    void searchShortestPath();

    /**
     * Inserts the textual representation of this Labyrinth
     * into the given output stream.
     * 
     * @param os The stream to write the Labyrinth to
     * @param visualizeVisited If true prints Tiles which have been visited (isVisited() is true) with v, 
     * else prints those according to their type
     */
    void printLabyrinth(std::ostream& os, bool visualizeVisited = false) const;
    
};

#endif /* LABYRINTH_H */

