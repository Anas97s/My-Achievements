/*
Compile: make student_list
Run: ./student_list
make student_list && ./student_list
*/

#include "pointer_list.h"

// Data definition for a student
typedef struct {
	String name;
	String sex;
	int mat_nr;
	double debts;
} Student;



// String, String, int, double -> City*
// Create a student (constructor).
Student* new_student(String name, String sex, int mat_nr, double debts) {
    Student* s = xcalloc(1, sizeof(Student));
    s->name = s_copy(name);
    s->sex = s_copy(sex);
	s->mat_nr = mat_nr;
    s->debts = debts;
	return s;
}

// Student* -> Student*
// Create a copy of student.
void* copy_student(void* x) {
	Student* st = (Student*)x;
    return new_student(st->name, st->sex, st->mat_nr, st->debts);
}

// Student* -> String
// String representation of student in the form:
// s is a hard working student who is in debt for debts €. The matriculations number is mat_nr and the students sex is sex
String student_to_string(void* x) {
    Student* s = (Student*) x;
    int n = s_length(s->name) + s_length(s->sex) + 150;
    String text = xmalloc(n);
    snprintf(text, n, "%s is a hard working student who is in debt for %.2f Euro. The matriculation number is %d and the students sex is %s", s->name, s->debts, s->mat_nr, s->sex);
    return text;
}

// Student* -> void
// Releases memory of a student.
void free_student(void* x) {
	/* int und double sollen nicht freen, wie in der Vorlesung gesagt wurde.*/
	
	Student* st = (Student*)x; // setzen wir st als student Pointer
	free(st->name); // free name
	free(st->sex); // free sex
	free(st); //free stud
}

// Student* -> bool
// Returns true if the amount of debts is above 20000.
bool poor_student(void* element, int i, void* x) {
	Student* st_p = (Student*)element;
	if(st_p->debts > 20000) return true;
	return false;
}

// Student* -> bool
// Returns true if the matriculation number is shorter than *x.
bool mat_nr_less_digits_than(void* element, int i, void* x) {
    Student* s = (Student*)element; // setzen wir s  pointer zu Student als element, da element in parameter war void* und ist es unbekannt auf was zeichnet der Element.
    int* a = (int*)x; // in parameter haben wir void pointer, ist unbekannt. die Funktion handelt sich über nummern, deswegen haben wir void* x als int pointer gesetzt. / *x ist "hier" max_digits und digit aus Main funktion gemeint
	int counter = 0; // counter ist ein int nummer, um die kurzer Matrikelnnummer zu finden. mit 0 ist gemeint, dass wir vom Anfang starten.
	for(int i = s->mat_nr; i > 0; i = i / 10 )//for-schleife, damit wir durch jede Matrikelnnummer durchlaufen können, und wenn die Matrikelnnummer ist großer als 0 dann erhöhen wir counter um 1 und teilen wir 
	// der Matrikelnnummer durch 10.
		counter++; // counter um eins erhöhen.
    return (counter < (*a)); // die Funktion soll nur die Matrikelnnummer, die kurzer als *a zurückgeben.
}

// Student* -> String
// Maps a student to its name.
void* student_name(void* element, int i, void* x) {
	Student* n = (Student*)element;
    return n->name;
}

/* 
	Aufgabe 1)e)
	
   Der Vorteil von Zeigerlisten ist, dass man auf mehrere Daten und verschiedene Typ-Werte zugreifen kann,
   ohne viele Funktionen zu erstellen. Dies kann einfach durch Zeigerlisten passieren. Deswegen ist es hilfreich und sinnvoll Zeigerlisten zu benutzen.
   
   */

// Student*, int, double* -> Student*
// Creates a new student with less debts. x is in range of [0, 1].
void* pay_debts(void* element, int i, void* x){
	Student* st = (Student*)element;
	double* d = (double*)x;
	st->debts *= 1 - *d;
	return new_student(st->name, st->sex, st->mat_nr, st->debts);
}

// double*, Student*, int -> void
// Adds students debts to state.
void add_debts(void* state, void* element, int index) {
    double* a = (double*)state; // setzen wir a als ein double pointer, in dieser stelle wir a auf total_debts in main funktion bezeichnet. 
    Student* s = (Student*)element; // setzen wir s als pointer zu Student.
    *a = *a + s->debts; // addieren wir die angegebenen total_debts in main funktion zu s->debts die in Student angegeben ist.
}

typedef struct {
	int n;
	double debts;
}Student_stats;

// Student_stats**, Student*, int -> void
// Computes the average debts for students.
void student_stats(void* state, void* element, int index) {
    Student_stats** s = (Student_stats**) state; // Der zweite Zeiger wird als s verwendet, um die Adresse des ersten Zeiger "Student_stats" zu speichern und zugreifen.
    Student* e = (Student*) element; // e wird als zeiger verwendet, um die Adresse von Student zu speichern und zugreifen.
    if(*s == NULL){ // wenn der erste Zeiger des zweiten Zeiger von Student_stats leer ist bzw. gleich NULL ist.
		*s = xmalloc(sizeof(Student_stats)); // speichern wir einen speicherplatz von der Größe von Student_stats.
		(*s)->n = 0; // zeigen wir dass den ersten Zeiger von Student_stats "int" gleich 0 ist.
		(*s)->debts = 0.0; // zeigen wir dass den ersten Zeiger von Student_stats "double" gleich 0.0 ist.
	}
	(*s)->n++; // erhöhen wir dne Int-wert um 1.
	(*s)->debts += e->debts; // addieren wir debts von studen_stats.
}

// Student*, Student* -> { <0, 0, >0 }
// Returns =0 if x and y are equal, <0 if x smaller than y, >0 otherwise.
int cmp_student_mat_nr(void* x, void* y) {
	Student* a = (Student*)x; // setzen wir a als pointer an student und mit x definiert.
	Student* b = (Student*)y; // setzen wir b als pointer an student und mit y definiert.
	if(a->mat_nr < b->mat_nr) return -1; // wenn die Matrikelnummer von a ist kleiner als b, dann soll -1 zurückgeben.
	if(a->mat_nr > b->mat_nr) return 1; // wenn die Matrikelnummer von a ist großer als b, dann soll 1 zurückgeben.
    return 0; // wenn die beiden Matrikelnnummern gleich groß sind, soll 0 zurückgeben.
}

// String -> String
// Copies a string for printing (and later release).
String string_to_string(void* x) {
    return s_copy(x);
}

// String -> void
// Releases a string.
void free_string(void* x) {
    free(x);
}

int main(void) {
    report_memory_leaks(true);
	
	Student* s1 = new_student("Mina", "not binary", 2090, 15000.00);
    Student* s2 = new_student("Mike", "male", 208080, 23607.56);
	Student* s3 = new_student("Anna", "female", 42, 406.26);
	Student* s4 = new_student("Jimmy", "male", 133, 8000.90);
	
	printf("\n");
    Node* list = new_node(s1, new_node(s2, new_node(s3, new_node(s4, NULL))));
    println_list(list, student_to_string);
    
	printf("\n");
    // find first poor student, use find_list
    // print result, if any
    printsln("first poor student:");
    Student* found_student = find_list(list, poor_student, NULL);
	if(found_student != NULL){
		String st = student_to_string(found_student);
		printf("[ ");
		prints(found_student->name);
		printf(" ]");
		printf("\n");
		free(st);
	}
    
	printf("\n");
	
    
    // find the first student with a matriculation number with a length less than max_digits, use find_list
    // print result, if any
    printsln("first student with mat_nr digit count less than max_digits:");
	int max_digits = 8;
	Student* sofmax = find_list(list, mat_nr_less_digits_than,&max_digits); // student mit der kurzen Matrikelnummer als max digit, short of max digit.
	if(sofmax != NULL){
		printf("[ ");
		prints(sofmax->name); 
		printf(" ]");
		printf("\n");
	}
    printf("\nMax digits: %d", max_digits);
	printf("\n");
    

    // map the list of students to a list of student names
    printsln("\nstudent names:");
    Node* names = map_list(list, student_name, NULL);
    println_list(names, string_to_string);
    free_list(names, NULL);
	printf("\n");
    // reduce the amount of debts for all students by 15%
    // new list, do not modify original list elements, use map_list
    printsln("reduce debts:");
    double factor = 0.15;
	Node* de = copy_list(list,copy_student); // erstellen wir eine copy liste wie in der Aufgabe gewünscht ist 
    Node* list2 = map_list(de, pay_debts, &factor); // setzen wir die kopierte liste in map_list in list2
    println_list(list2, student_to_string); 
    free_list(list2, free_student);// free list2 und studenten
	free_list(de,free_student); // free die kopierte liste und die studenten.
	printf("\n");
    // find all students with matriculation numbers with less than 4 digits, use filter_list
    printsln("short matriculation numbers:");
	int digit = 4;
	Node* selected_students = filter_list(list, mat_nr_less_digits_than, &digit); //Erstellt eine Liste mit Studenten, deren Matrikelnummer kleiner als 4 ist
	println_list(selected_students, student_to_string); // Gibt die erstellte Liste aus
	free_list(selected_students, NULL); // Gibt den Speicherplatz der erstellten Liste frei
    //Node* selected_students = filter_list(list, ...);
    printf("\n");
    // produce a list of the names students with short matricultion numbers, use filter_map_list
    printsln("names of students with short matriculation numbers:");
	Node* n = filter_map_list(list, mat_nr_less_digits_than, student_name, &digit); // Es wird eine filter_map_list erstellt
    println_list(n, string_to_string); // Wird ausgegeben
	free_list(n, NULL); //Speicher wird freigegeben
    //names = filter_map_list(list, ...);

    printf("\n");
    // compute the sum of the debts of all students, use reduce_list
    printsln("total debts:");
    double total_debts = 0;
    reduce_list(list, add_debts, &total_debts);
    printdln(total_debts);

	printf("\n");
    // calculate the average debts, use reduce_list
    Student_stats* stats = NULL;
    reduce_list(list, student_stats, &stats);
    if (stats != NULL) {
        printf("The average debts are: %.2f\n", stats->debts/stats->n);
        free(stats);
    }
    printf("\n");
    // sort the students by their matriculation number, in increasing order
    // use insert_ordered, do not modify the original list, do not copy the students
    Node* sorted_list = NULL;
    for (Node* n = list; n != NULL; n = n->next) {
        sorted_list = insert_ordered(sorted_list, n->value, cmp_student_mat_nr);
    }
    printsln("sorted students:");
    if (sorted_list != NULL) {
        println_list(sorted_list, student_to_string);
        free_list(sorted_list, NULL);
    }

    // free all memory
    free_list(list, free_student);
    
    return 0;
}

