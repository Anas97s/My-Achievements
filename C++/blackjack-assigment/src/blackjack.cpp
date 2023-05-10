/**
 * @file blackjack.cpp
 */

#include "blackjack.h"

Blackjack::Blackjack() : deck(std::make_unique<InfiniteDeck>()), round(1)
{
    this->players.push_back(Player::create(PlayerType::automated, deck.get()));
    this->players.push_back(Player::create(PlayerType::manual,    deck.get()));
}

Blackjack::Blackjack(size_t players, size_t decks) : deck(std::make_unique<LimitedDeck>(decks)), round(1)
{
    this->players.push_back(Player::create(PlayerType::automated, deck.get()));
    for(size_t i = 0; i < players; ++i)
        this->players.push_back(Player::create(PlayerType::automated, deck.get()));
}

void Blackjack::playRound()
{
    if (this->players[0].get() == nullptr)
        return;
        
    std::cout << "Round " << round << ":\n";

    Player* dealer = this->players[0].get();
    
    for (size_t i = 1; i < players.size(); ++i)
        this->players[i]->takeHit();
    dealer->takeHit();

    std::cout << "Dealer's first card: " << dealer->getHandValue() << "\n";

    for (size_t i = 1; i < players.size(); ++i)
        this->players[i]->takeHit();
    dealer->takeHit();

    for (size_t i = 1; i < players.size(); ++i)
        this->players[i]->playTurn();
    dealer->playTurn();

    this->evaluateRound();
    this->prepareRound();

    ++round;
}

void Blackjack::evaluateRound()
{
    assert(this->players[0].get() != nullptr);
    Player& dealer = *this->players[0];    
    std::cout << "Dealer: " << dealer << "\n";
    if (dealer.hasBlackjack())
        std::cout << "Dealer has blackjack!" << "\n";
    else if (dealer.isBust())
        std::cout << "Dealer is bust!" << "\n";
    
    for (size_t i = 1; i < this->players.size(); ++i)
    {
        Player& player = *this->players[i];
        std::cout << "Player " << i << ": " << player << "\n";
        std::cout << "Player " << i;
        if(player.isBust()
           || (dealer.hasBlackjack() && !player.hasBlackjack())
           || (!dealer.isBust() && (player.getHandValue() < dealer.getHandValue())))
            std::cout << " has lost!";
        else if(player.hasBlackjack() && !dealer.hasBlackjack())
            std::cout <<" has won with a blackjack!";
        else if(dealer.isBust() 
                || (player.getHandValue() > dealer.getHandValue()))
            std::cout << " has won!";
        else
            std::cout << " has played for a draw!";
        std::cout << "\n";
    }
    std::cout << "\n";
}

void Blackjack::prepareRound()
{
    for(auto& player: this->players)
        player->resetHand();
    this->deck->resetCards();
}

