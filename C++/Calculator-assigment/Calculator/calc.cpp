#include <string>
#include <iostream>

int add(int a, int b);
int sub(int a, int b);
int main(){
    int numberA;
    int numberB;
    std::string operation;    
	std::cout << "Number 1 : ";
	std::cin >> numberA;
	std::cout << "Number 2 : ";
	std::cin >> numberB;
	std::cout << "Operation add/sub: ";
	std::cin >> operation;
	if(operation == "add"){
		std::cout << "Result is: " << add(numberA, numberB) << "\n"; 
	}else if(operation == "sub"){
		std::cout << "Result is: " << sub(numberA, numberB) << "\n";
	}

    return 0;
}

int add(int a, int b){
	return a + b;
}

int sub(int a, int b){
	return a - b;
}


