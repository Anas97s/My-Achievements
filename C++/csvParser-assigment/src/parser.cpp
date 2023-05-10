/**
 * @file parser.cpp
 */
#include "parser.h"
#include <sstream>

std::vector<std::string> split(std::istream& is, char delim)
{
    std::vector<std::string> result;
    
	std::string word;
	while(std::getline(is, word, delim)){
		if(word != " "){
			result.push_back(word);
		}
	}
    
    return result;
}

std::vector<IndexedString> parse(std::istream& is)
{
    std::vector<IndexedString> strings;
    
    std::vector<std::string> splitted = split(is, ';');
	
	for(unsigned int i = 0; i < splitted.size(); i++){
		if(!(splitted.at(i).empty()) && (std::isdigit(splitted.at(i)[0]))){
			strings.emplace_back(std::stoi(splitted.at(i)), splitted.at(i + 1));	
		}
	}
	std::sort(strings.begin(), strings.end());
    return strings;
}

void writeSentence(std::ostream& os, const std::vector<IndexedString>& strings)
{

    for(unsigned int i = 0; i < strings.size(); i++){
		os << std::get<1>(strings.at(i));
	}
}



