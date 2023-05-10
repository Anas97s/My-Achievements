/**
 * @file parser-test.cpp
 */
 
#include "parser-test.h"
#include "parser.h"
#include <fstream>
#include <utility>
#include <string>

/**
 * Used to test the parser. Prints some splitted strings and parses the given input file, writing the result to the console
 */
void TestParser(const std::string& filename)
{
    std::ifstream fs(filename);
    if(!fs.is_open())
    {
        std::cout<<"File "<<filename<<" not found\n";
        return;
    }
    std::vector<IndexedString> entries = parse(fs);
    
    writeSentence(std::cout, entries);
}

