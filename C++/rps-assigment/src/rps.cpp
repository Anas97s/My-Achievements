/**
 * @file rps.cpp
 */
#include "rps.h"
#include <sstream>

Move parseInput(const std::string& input) {

	Move result = Move::Error;
	
	if(input.compare("paper") == 0){
		result = Move::Paper;
	}else if(input.compare("rock") == 0){
		result = Move::Rock;
	}else if(input.compare("scissors") == 0){
		result = Move::Scissors;
	}
	
	return result;
}

Result rockPaperScissors(const std::string& p1, const std::string& p2) {

	Result result = Result::Invalid;
	if((p1 == "rock" && p2 == "rock") || (p1 == "paper" && p2 == "paper") || (p1 == "scissors" && p2 == "scissors")){
		result = Result::Tie;
	}else if((p1 == "rock" && p2 == "scissors") || (p1 == "paper" && p2 == "rock") || (p1 == "scissors" && p2 == "paper")){
		result = Result::Player1Wins;
	}else if((p2 == "rock" && p1 == "scissors") || (p2 == "paper" && p1 == "rock") || (p2 == "scissors" && p1 == "paper")){
		result = Result::Player2Wins;
	}
	
	return result;

}

int main(int argc, char** argv) {
    std::vector<std::string> args(argv, argv + argc);
	std::string playerOne = args[1];
	std::string playerTwo = args[2];
	
	if(parseInput(playerOne) != Move::Error){
		if(parseInput(playerTwo) != Move::Error){
			std:: cout << "Player1: " << playerOne << ", Player2: " << playerTwo << "\n";
		
			if(rockPaperScissors(playerOne, playerTwo) == Result::Player1Wins){
				std::cout << "Winner: Player 1\n";
			}else if(rockPaperScissors(playerOne, playerTwo) == Result::Player2Wins){
				std::cout << "Winner: Player 2\n";
			}else if(rockPaperScissors(playerOne, playerTwo) == Result::Tie){
				std::cout << "Tied, no one wins!\n";
			}else if(rockPaperScissors(playerOne, playerTwo) == Result::Invalid){
				std::cout << "Invalid input!\n";
			}
		}else{
			std::cout << "Player 2 did write a invalid input!\n";
		}
	}else if (parseInput(playerOne) == Move::Error && parseInput(playerTwo) == Move::Error){
		
		std::cout << "Both players did write a invalid input!\n";
		
	}else{
		std::cout << "Player 1 did write a invalid input!\n";
	}
	
    return 0;
}





