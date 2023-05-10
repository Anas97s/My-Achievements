/*
Compile: make people
Run: ./people
*/

#include "base.h"
#include "string.h"

struct Statistics {
	int myear;
	int males; 
	int females; 
	double heightm; 
	double heightf;
};
typedef struct Statistics Statistics;

Statistics make_statistics() {
	Statistics s = { 0, 0, 0, 0.0, 0.0};
	return s;
}

void print_statistics(Statistics s) {
	printf("mean year: %d\n", s.myear);
	printf("males: %d\n", s.males);
	printf("females: %d\n", s.females);
	printf("males height: %.2f\n", s.heightm);
	printf("females height: %.2f\n", s.heightf);
}
	
Statistics compute_statistics(String s){
	Statistics ss = make_statistics();
	for (int i = 16; i < s_length(s); i = i + 12){
		if(s_get(s, i + 5) == 'm') {
			ss.males++;
			String heightm = s_sub(s, i + 7, i + 11);
			double heightm_d = d_of_s(heightm);
			ss.heightm += heightm_d;
		}
		if(s_get(s, i + 5) == 'f') {
			ss.females++;
			String heightf = s_sub(s, i + 7, i + 11);
			double heightf_d = d_of_s(heightf);
			ss.heightf += heightf_d;
		}
		String int_year = s_sub(s, i, i + 4);
		int year_int = i_of_s(int_year);
		ss.myear += year_int;
	}
	ss.heightm = ss.heightm / ss.males;
	ss.heightf = ss.heightf / ss.females;
	double people = ss.males + ss.females;
	ss.myear = (ss.myear / people );
	
	return ss;
}




int main(void) {
	String table = s_read_file("people.txt");
	printsln(table);
	Statistics status =compute_statistics(table);
	print_statistics(status);
	return 0;
}

