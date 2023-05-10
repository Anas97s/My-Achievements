/*
Compile: make random_sort
Run: ./random_sort
make random_sort && ./random_sort
*/

#include "base.h"
#include <math.h>

//template code

char* brands[] = {"VW", "BMW", "Mercedes", "Fiat", "Ford", "Dacia", "Audi", "Citroen"};
int brand_count = sizeof(brands) / sizeof(char*);
int min_year = 1950;
int max_year = 2017;
int max_km = 250000;


struct Car{
	char* brand;
	int year;
	int km;
	double price;
};

typedef struct Car Car;

//print car information
void print_car(Car* car){
	printf("Caryear: %4d\tbrand: %8s\tKM: %6dkm\tPrice: %8.2lfEuro\n", car->year, car->brand, car->km, car->price);	
}

//print carArray
void print_car_array(Car* car_park, int car_count){
	for(int i = 0; i < car_count; i++){
		print_car(car_park + i);
	}
}


// Creates an array with random cars. Returns a pointer to the array.
Car* create_car_park(int car_count){
	//next statement is part of the following lectures. Don't worry.
	Car* car = (Car*) xmalloc(sizeof(Car) * car_count);
	
	//fill the array with random cars
	for(int i = 0; i < car_count; i++){
		int random_brand_index = i_rnd(brand_count); // car brand index in interval: [0,7]

		car[i].brand = brands[random_brand_index];
		int random_year = i_rnd(max_year - min_year) + min_year; //years between min and max year
		car[i].year = random_year;
		
		int random_km = 0;
		// On MinGW: max random number is 32767. To overcome this two 16 bit random numbers are glued together.
		if(RAND_MAX == 32767){
			random_km = (i_rnd(max_km >> 16) << 16 ) | i_rnd(max_km); //dirty hack
		}else{
			random_km = i_rnd(max_km); 
		}
		car[i].km = random_km; //max 250.000km
		
		car[i].price = 30000.00 * pow(0.85, (random_year - min_year)) + 20000.00 * pow(0.75, random_km / 10000.0)  ; // car price max 50.000 Euro
	}
	return car;
}

// Deletes a car array. Call the function, when you don't need the array anymore.
void delete_car_park(Car* cars){
	free(cars);
}

//end of template code

//now it is your turn ... 


int compare (Car car1, Car car2) {
	if (car1.year > car2.year) {
		return 1;
	} else if (car1.year < car2.year) {
		return -1;
	} else if (car1.year == car2.year) {
	    int cmp = strcmp(car1.brand, car2.brand);
		if (cmp < 0) {
			return -1;
		} else if (cmp > 0) {
			Car x = car1;
			car1 = car2;
			car2 = x;
			return 1;
		} else if (cmp == 0) {
			return 0;
		}
	}
	return 0;
}



void compare_test(void) {
	Car* car = (Car*) xmalloc(sizeof(Car) * 6);
	car[0].year = 1976;
	car[0].brand = "Ford";
	car[1].year = 2002;
	car[1].brand = "Audi";
	car[2].year = 2002;
	car[2].brand = "VW";
	car[3].year = 2002;
	car[3].brand = "Ford";
	car[4].year = 2010;
	car[4].brand = "Dacia";
	car[5].year = 2010;
	car[5].brand = "Dacia";
	
	test_equal_i(compare(car[0], car[1]), -1);
	test_equal_i(compare(car[1], car[2]), -1);
	test_equal_i(compare(car[4], car[5]), 0);
	test_equal_i(compare(car[2], car[3]), 1);
	test_equal_i(compare(car[4], car[3]), 1);
}



bool sorted(Car* a, int length) {
	for (int i = 0; i < length - 1; i++) {
		if(compare(a[i], a[i + 1]) > 0){
			return false;
		}
	}
	return true;
}



int random_sort(Car* a, int length) {
	int i;
	int j; 
	int swaps = 0;
	while(sorted(a, length) == false){
		i = i_rnd(length);
		j = i_rnd(length);
		Car d = a[i];
		a[i] = a[j];
		a[j] = d;
		swaps++;
	}
	return swaps++;
}



int main(void) {
	
	compare_test();
	printsln(" ");
	
	int number_of_random_cars = 10;
	Car* car_park = create_car_park(number_of_random_cars);
	print_car_array(car_park, number_of_random_cars);
	
	printf("Sorting...\n");
	

	random_sort(car_park, 10); // das habe ich neu geschrieben
	print_car_array(car_park, number_of_random_cars);
	delete_car_park(car_park);
	
	
	
	
	for(int size = 3; size < 10; size++){ // Arraygrößen im Bereich von 3 – 10.
		int all_swaps = 0;
		for (int i = 0; i < 100; i++){ //große zufällige Arrays jeweils 100-mal
			Car* car_park = create_car_park(size);
			int swaps = random_sort(car_park, size);
			all_swaps += swaps;
		}
		double average_value_of_swaps = (double) all_swaps / 100.0;
		double numbers_of_made_calls = (double) ((size) * ( all_swaps )) / 100.0;
		printf("Sorting 100 arrays of cars with length %d it's take %.2f swaps and %.2f the numbers of made calls in average \n", size, average_value_of_swaps, numbers_of_made_calls);
	}

    return 0;
}

