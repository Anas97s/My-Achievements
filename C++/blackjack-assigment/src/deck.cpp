/**
 * @file deck.cpp
 */

#include "deck.h"
#include <iostream>

std::mt19937 Deck::re(std::random_device{}());

void Deck::fill(size_t packs)
{
    this->cards.reserve(52 * packs);
    for (size_t i = 0; i < 4 * packs; ++i)
    {
        // Add numbered cards and aces:
        for (size_t i = 2; i < 12; ++i)
            this->cards.push_back(i);
        // Add face cards except aces:
        for (size_t i = 0; i < 3; ++i)
            this->cards.push_back(10);
    }
}

InfiniteDeck::InfiniteDeck()
{
    this->fill(1);
    this->dist = std::uniform_int_distribution<size_t> (0, this->cards.size() - 1);
}

size_t InfiniteDeck::getRandomCard()
{    
    auto idx = this->dist(this->re);
    return this->cards[idx];
}


LimitedDeck::LimitedDeck(size_t d)
{
	Deck::fill(d);
	std::shuffle(Deck::cards.begin(), Deck::cards.end(), re);
}

size_t LimitedDeck::getRandomCard()
{
    
	
  	size_t size = Deck::cards.size();
	size_t lastCard = Deck::cards[size - 1]; //last card that has been drawn
	Deck::cards.pop_back(); //remove it from deck cards
	this->discarded.push_back(lastCard); //save it in discarded deck for reset later.
	
	return lastCard;
}

void LimitedDeck::resetCards()
{
 
	for(unsigned int i = 0; i < discarded.size(); i++){
		Deck::cards.push_back(discarded[i]);
	}
	
	std::shuffle(Deck::cards.begin(), Deck::cards.end(), re);
}



