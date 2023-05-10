#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#define NOBONUS

void compareFilesLineByLine(const std::vector<std::string>&,
                            const std::vector<std::string>&);

int main(int argc, char** argv)
{
    std::vector<std::string> arguments(argv, argv + argc);
	std::vector<std::string> file1(0);
	std::vector<std::string> file2(0);
	
	std::string line;
	
	std::ifstream fileOne(argv[1]);
	std::ifstream fileTwo(argv[2]);
	
	bool notEmpty = true;
	
	while(notEmpty){
		if(getline(fileOne, line)){
			file1.push_back(line);
		}else{
			notEmpty = false;
			fileOne.close();
		}
	}
	
	notEmpty = true;
	while(notEmpty){
		if(getline(fileTwo, line)){
			file2.push_back(line);
		}else{
			notEmpty = false;
			fileTwo.close();
		}
	}
	
	compareFilesLineByLine(file1, file2);
	
    return 0;
}

#if defined(NOBONUS)
void compareFilesLineByLine(const std::vector<std::string>& lines1,
                            const std::vector<std::string>& lines2)
{
	if(lines1.size() > lines2.size()){
		for(unsigned int i = 0; i < lines1.size(); i++){
			if(i < lines2.size()){
				if((lines1.at(i).length() > lines2.at(i).length()) || (lines1.at(i).compare(lines2.at(i)) != 0)){
					std::cout << "<<<" << i + 1 << "<<< " << lines1.at(i) << "\n" <<
					">>>" << i + 1 << ">>> " << lines2.at(i) << std::endl;
				}else if((lines1.at(i).length() < lines2.at(i).length()) || (lines1.at(i).compare(lines2.at(i)) != 0)){
					std::cout << "<<<" << i + 1 << "<<< " << lines1.at(i) << "\n" <<
					">>>" << i + 1 << ">>> " << lines2.at(i) << std::endl;
				}
			}else{
				std::cout << ">>>" << i + 1 << ">>> " << lines1.at(i) << std::endl;
			}
		}
	}else{
		for (unsigned int i = 0; i < lines2.size(); i++){
			if(i < lines1.size()){
				if((lines1.at(i).length() > lines2.at(i).length()) || (lines1.at(i).compare(lines2.at(i)) != 0)){
					std::cout << "<<<" << i + 1 << "<<< " << lines1.at(i) << "\n" <<
					">>>" << i + 1 << ">>> " << lines2.at(i) << std::endl;
				}else if((lines1.at(i).length() < lines2.at(i).length()) || (lines1.at(i).compare(lines2.at(i)) != 0)){
					std::cout << "<<<" << i + 1 << "<<< " << lines1.at(i) << "\n" <<
					">>>" << i + 1 << ">>> " << lines2.at(i) << std::endl;
				}
			}else{
				std::cout << ">>>" << i + 1 << ">>> " << lines2.at(i) << std::endl;
			}
		}
	}
}          
#else
   
#endif


