/**
 * @file player.h
 */

#ifndef PLAYER_H_
#define PLAYER_H_

#include <iostream>
#include <memory>
#include <stdexcept>
#include "deck.h"


enum class PlayerType
{
    automated,
    manual
};

/**
 * An instance of class Player represents a Player taking part in
 * a game of Black Jack. Players may display different behaviors
 * in accordance to their implementation.
 */
class Player
{
private:
    /** Pointer to the Deck used in the game. */
    Deck* deck;
protected:
    /** Vector of cards representing the hand of this Player */
    std::vector<size_t> cards;

    /**
     * Represents the decision making process by a Player on
     * whether to draw another card or "stay".
     * @return True if this Player wants another card
     */
    virtual bool evaluateHand() = 0;
public:
    Player(Deck* deck);
	
    /**
     * Calculates the worth of this Player's hand by adding up
     * the values of all the individual cards held.
     * @return The current hand value of this Player
     */
    size_t getHandValue() const;

    /** Checks if this Player is bust. */
    bool isBust()   const;
    /** Checks if this Player has a Black Jack. */
    bool hasBlackjack() const;

    /**
     * Reduces the value of an Ace held by this Player from 11 to 1.
     * @return True if an Ace was found and devaluated
     */
    bool devaluateAce();

    /**
     * Gets a card form the Deck and checks if this Player is bust.
     * If that is the case possible Aces are devaluated in order to
     * save this Player from loosing the current round.
     * @see bool devaluateAce();
     */
    void takeHit();

    /**
     * Plays an entire round by continuously asking for another card
     * until this Player is either bust or satisfied.
     */
    void playTurn();

    /**
     * Represents the Player returning his cards to the dealer
     * at the end of a round. Clears the Player's hand.
     */
    void resetHand();

    virtual ~Player() = default;
    friend std::ostream& operator<<(std::ostream& os, const Player& player);
    
    /**
     * Creates a new Player instance of the given type and returns
     * a pointer to it.
     * @param type The type of Player to be created
     * @param deck A pointer to the Deck instance used in the current game
     * @see enum class PlayerType
     * @return The unique pointer to the newly created Player instance
     */
    static std::unique_ptr<Player> create(PlayerType type, Deck* deck);
};

// TODO: Insert the following two concrete classes inheriting from Player:
//        1.    AutomatedPlayer
class AutomatedPlayer : public Player{
	public:
		bool evaluateHand() override;
};

//        2.    ManualPlayer
class ManualPlayer : public Player{
	public:
		bool evaluateHand() override;
};

#endif /* PLAYER_H_ */

