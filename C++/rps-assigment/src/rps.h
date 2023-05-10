#ifndef RPS_H_
#define RPS_H_
#include <vector>
#include <string>
#include <tuple>
#include <iostream>

enum class Move {
    Rock,
    Paper,
    Scissors,
    Error
};

enum class Result {
    Player1Wins,
    Player2Wins,
    Tie,
    Invalid
};

/**
 * @brief turns a string into an move
 * @param input input move as string
 * @return input move as a Move
 * */
Move parseInput(const std::string& input);

/**
 * @brief evaluates the inputs and determines the result
 * @param p1 input of player 1
 * @param p2 input of player 2
 * @return result which can be the winner, a tie or an error
 */
Result rockPaperScissors(const std::string& p1, const std::string& p2);

#endif /* RPS_H_*/

