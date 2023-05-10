#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

std::vector<std::string> split(std::istream& is, char delim)
{
    std::vector<std::string> result;
    std::string elem;

    while(std::getline(is, elem, delim))
    {
        if(elem != "")
            result.push_back(std::move(elem));
    }
    return result;
}

std::vector<std::string> split(const std::string& val, char delim)
{
    std::stringstream is(val);
    return split(is, delim);
}

struct WCResult 
{
    std::string Name;
    std::size_t LineCount;
    std::size_t WordCount;
    std::size_t ByteCount;
    
    WCResult(const std::string& name = "", std::size_t lc = 0, std::size_t wc = 0, std::size_t bc = 0): Name(name), LineCount(lc), WordCount(wc), ByteCount(bc) 
    {}
    void output(std::ostream& os) 
    { 
        os << this->LineCount << " " << this->WordCount << " " << this->ByteCount << " " << this->Name << std::endl; 
    }
};


WCResult getWordCount(std::istream& input, std::string name){
	
	WCResult wcr;
	std::string line;
	std::string word;
	wcr.Name = name;
	
	while(!input.eof()){
		
		if(getline(input, line, ' ')){
			wcr.WordCount++;
		}
		
	}
	
	while(getline(input, line)){
		wcr.LineCount++;
	}
	
	while(input.get() == ' '){
		wcr.WordCount++;
	}
	

	
	std::cout << wcr.LineCount << " " << wcr.WordCount << " " << wcr.ByteCount << " " << wcr.Name << std::endl;
	
	return wcr;
}



int main(int argc, char** argv)
{
    std::vector<std::string> args(argv, argv + argc);
   
	std::ifstream file(argv[1]);
	std::string name = argv[1];
	WCResult wcresult;
	getWordCount(file,name);

	
	
    return 0;
}

