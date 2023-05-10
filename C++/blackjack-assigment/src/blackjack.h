/**
 * @file blackjack.h
 */

#ifndef BLACKJACK_H_
#define BLACKJACK_H_

#include <iostream>
#include <memory>
#include <cassert>
#include "player.h"
#include "deck.h"

/**
 * Instances of class Blackjack represent a Black Jack table
 * and are used to simulate or interactively play rounds of Black Jack
 * with different types of players.
 */
class Blackjack
{
private:
    /** Vector containing the Player instances participating in the game. */
    std::vector<std::unique_ptr<Player>> players;
    /** Deck of cards used in the game. */
    std::shared_ptr<Deck> deck;
    /** Number of round being played at the table. */
    size_t round;

    /**
     * Evaluates the outcome of a round for every player
     * and prints it onto the console.
     */
    void evaluateRound();

    /**
     * Represents the Dealer preparing the next round by collecting
     * played cards, putting them back into the Deck and shuffling.
     */
    void prepareRound();
public:
    /**
     * Constructor used for setting up an interactive game
     * against an automated Dealer.
     */
    Blackjack();

    /**
     * Constructor used for setting up a simulation of a game comprising an
     * arbitrary number of players using an arbitrary number of packs of cards.
     *
     * @param players The number of players supposed to play at the table
     * @param decks The number of packs of cards meant to compose the deck
     */
    Blackjack(size_t players, size_t decks);

    /**
     * Controlls the procedure involved in playing a round of Black Jack
     * as it is described on Wikipedia.
     */
    void playRound();
};

#endif /* BLACKJACK_H_ */

