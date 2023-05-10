/**
 * Main file. Should not be modified.
 * @file main.cpp
 */

#include "labyrinth.h"
#include <fstream>
#include <vector>

void testLabyrinth(const std::string& fileName)
{
    std::ifstream is(fileName);
    Labyrinth labyrinth = Labyrinth(is);
    std::cout<<"Input: \n";
    labyrinth.printLabyrinth(std::cout);
#if true
    labyrinth.searchShortestPath();
#else
    labyrinth.fastSearchShortestPath();
#endif
    std::cout<<"\nShortest Path: \n";
    labyrinth.printLabyrinth(std::cout);
}

/**
 * Main function.
 */
int main(int argv, char** argc)
{
    std::vector<std::string> arguments(argc, argc + argv);

    if(arguments.size() == 1)
        arguments.push_back("shortest_path_0.dat");

    testLabyrinth(arguments[1]);

    return 0;
}

