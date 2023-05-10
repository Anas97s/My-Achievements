/**
 * @file deck.h
 */

#ifndef DECK_H_
#define DECK_H_

#include <algorithm>
#include <random>
#include <vector>


/**
 * An instance of class Deck represents a Deck of cards as required
 * in order to play Black Jack. Decks used in Black Jack may vary in size
 * but most commonly consist of six packs of 52 cards.
 */
class Deck
{
protected:
    /** mersenne-twister 19937 random engine */
    static std::mt19937 re;
    /** Vector of cards making up the deck. */
    std::vector<size_t> cards;

    /**
     * Fills this Deck with cards. Appends as many
     * packs of 52 cards as are supposed to be used.
     * @param packs Number of packs of cards to be added to this deck
     */
    void fill(size_t packs);
public:
    /**
     * Draws a random card from this Deck and returns its value.
     * @return Value of the card drawn
     */
	
    virtual size_t getRandomCard() = 0;
    virtual void resetCards() = 0;
    virtual ~Deck() = default;
};

class InfiniteDeck: public Deck
{
private:
    std::uniform_int_distribution<size_t> dist;
public:
    InfiniteDeck();
    size_t getRandomCard() override;
    void resetCards() override {};
};

class LimitedDeck: public Deck
{
private:
    /** Number of packs of cards composing this deck. */
    size_t packets;
    /** Vector containing the cards drawn from this deck. */
    std::vector<size_t> discarded;
public:
    LimitedDeck(size_t packs);
    size_t getRandomCard() override;

    /**
     * Shuffles the discarded cards back into this Deck.
     */
    void resetCards() override;
	
};

#endif /* DECK_H_ */

