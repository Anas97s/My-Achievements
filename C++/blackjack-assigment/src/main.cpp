/**
 * Main file. Should not be modified.
 * @file main.cpp
 */

#include <iostream>
#include "blackjack.h"
#include "deck.h"
#include "player.h"

/**
 * Main function.
 */
int main(int argv, char** argc)
{
    std::vector<std::string> arguments(argc, argc + argv);

    if(arguments.size() == 1)
    {
        Blackjack game = Blackjack();
        
        std::string playRound = "y";
        while(playRound == "y")
        {
            game.playRound();
            std::cout << "Play another round? (y/n) " << std::flush;
            std::cin  >> playRound;
        }
    }
    else
    {
        Blackjack game = Blackjack(7, 6);
        size_t n = static_cast<size_t>(std::stoi(arguments[1]));
        for(size_t i = 0; i < n; ++i)
            game.playRound();
    }

    return 0;
}

