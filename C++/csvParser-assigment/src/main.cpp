/**
 * Main file. Should not be modified.
 * @file main.cpp
 */

#include "parser.h"
#include "parser-test.h"
#include <vector>

/**
 * Main function. Takes the name of the csv-file as first commandline argument, 
 * uses "csv-file.csv" if none is specified. Calls TestParser with the filename.
 */
int main(int argv, char** argc)
{
    std::vector<std::string> arguments(argc, argc + argv);

    if(arguments.size() == 1)
        arguments.push_back("csv-file.csv");

    TestParser(arguments[1]);

    return 0;
}

