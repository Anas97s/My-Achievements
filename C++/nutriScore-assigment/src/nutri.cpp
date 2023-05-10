/**
 * @file nutri.cpp
 */
#include "nutri.h"


using Meal = std::tuple<std::string, char, size_t>;


const Meal& getUnhealthier(const Meal& mealA, const Meal& mealB) {

	int scoreA = 0;
	int scoreB = 0;
	
	
	if(std::get<1>(mealA) == 'E'){
		scoreA = static_cast<int>(std::get<1>(mealA)) *static_cast<int>(std::get<2>(mealA)) * 2;
	}else if(std::get<1>(mealA) == 'D'){
		scoreA = static_cast<int>(std::get<1>(mealA)) *static_cast<int>(std::get<2>(mealA));
	}
	
	
	if(std::get<1>(mealB) == 'E'){
		scoreB = static_cast<int>(std::get<1>(mealB)) *static_cast<int>(std::get<2>(mealB)) * 2;
	}else if(std::get<1>(mealB) == 'D'){
		scoreB = static_cast<int>(std::get<1>(mealB)) *static_cast<int>(std::get<2>(mealB));
	}
	
	
	if(scoreA > scoreB){
		return  mealA;
	}
	
    return mealB; 
}

void analyzeMeals(std::ostream& os, const std::vector<Meal>& meals){
	
	int healthyfood = 0;
	int numberOfFood = 0;
	int unhealthyFoodCounter = 0;
	std::string unhealthyFoodName = "";
	int consumed = 0;
	
	
	unsigned int i = 0;
	unsigned int indexBefore = 0;
	while(i < meals.size()){
		
		numberOfFood  += static_cast<int>(std::get<2>(meals.at(i)));
		
		if(std::get<1>(meals.at(i)) != 'D' && std::get<1>(meals.at(i)) != 'E'){
			healthyfood += static_cast<int>(std::get<2>(meals.at(i)));
		}else{
			unhealthyFoodCounter++;
		}
		
		if(i >= 1){
			Meal m = getUnhealthier(meals.at(i), meals.at(indexBefore));
			unhealthyFoodName = static_cast<std::string>(get<0>(m));
			consumed = static_cast<int>(std::get<2>(m));
			indexBefore++;
		}
		
		i++;
	}
	
	
	
	if(healthyfood != 0 && unhealthyFoodCounter != 0){
		os 
			<< "Von " << numberOfFood << " Lebensmitteln waren " << healthyfood 
			<<" gesund.\nEs sollte lieber auf das Lebensmittel *" 
			<< unhealthyFoodName << "* verzichtet werden, es wurde " << consumed << " mal verzehrt.\n"; 
	}else{
		os
			<< "Von " << numberOfFood << " Lebensmitteln waren " << healthyfood << " gesund." 
			<< " Super, es waren keine ungesunden Lebensmittel dabei!\n";	
	}
	
}

