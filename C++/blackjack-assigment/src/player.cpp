/**
 * @file player.cpp
 */

#include "player.h"

Player::Player(Deck* d) : deck(d)
{}

size_t Player::getHandValue() const
{
   
	size_t sum = 0;
	for(unsigned int i = 0; i < this->cards.size(); i++){
		sum += this->cards[i];
	}
    return sum;
}

bool Player::isBust() const 
{
    
    return this->getHandValue() > 21;
}

bool Player::hasBlackjack() const
{
    
	
	return this->cards.size() == 2 && this->getHandValue() == 21;
}

bool Player::devaluateAce()
{
   
    for(unsigned int i = 0; i < this->cards.size(); i++){
		if(this->cards[i] == 11 && this->getHandValue() >= 11){
			return true;
		}
	}
	
	return false;
}

void Player::takeHit()
{
 
	size_t size = this->cards.size();
	size_t cardFromDeck = this->cards[size - 1];	
	this->cards.pop_back();
	this->cards.push_back(cardFromDeck);
	size_t handValue = this->getHandValue();
	while(this->isBust()){
		if(this->devaluateAce()){
			handValue -= 10;
		}
	}
}

void Player::playTurn()
{
    while(!this->isBust() && this->evaluateHand())
        this->takeHit();
}

void Player::resetHand()
{
    this->cards.clear();
}

std::ostream& operator<<(std::ostream& os, const Player& player)
{
    os << player.getHandValue();
    os << " (";
    for(size_t card : player.cards)
        os << " " << card;
    os << " " << ")";
    return os;
}

std::unique_ptr<Player> Player::create(PlayerType type, Deck* deck)
{
 
    return nullptr;
}


bool AutomatedPlayer::evaluateHand(){
	
	return Player::getHandValue() <= 16;
}

bool ManualPlayer::evaluateHand(){
	std::cout << "This is sum of your hand: " << Player::getHandValue() << std::endl;
	std::cout << "Do you want to pull another card? (y / n)" << std::endl;
	std::string input;
	if(input == "y"){
		return true;
	}
	
	return false;
}
