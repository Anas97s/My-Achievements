/*
Compile: make mensa
Run: ./mensa
Compile & run: make mensa && ./mensa
*/

#include "base.h"

/**
Represents a single list node. The string value is dynamically allocated. 
The node is the owner of the value and has to free it when released.
*/
struct Node {
    String value; // dynamically allocated string, release memory!
    struct Node* next; // self-reference
};
typedef struct Node Node;

/**
Create a list node. Copies the value (into dynamically allocated storage).
*/
Node* new_node(String value, Node* next) {
    Node* node = xcalloc(1, sizeof(Node));
    node->value = s_copy(value); // s_copy performs dynamic allocation
    node->next = next;
    return node;
}

/**
Prints the components of the given list.
*/
void print_list(Node* list) {
    if (list == NULL) {
        printf("[]");
    } else {
        printf("[%s", list->value);
        for (Node* n = list->next; n != NULL; n = n->next) {
            printf(", %s", n->value);
        }
        printf("]");
    }
}

/**
Print list followed by a newline.
*/
void println_list(Node* list) {
    print_list(list);
    printsln("");
}

/**
Return the number of elements in the list.
*/
int length_list(Node* list) {
    // todo: implement (a)
	if (list == NULL){
		return 0;
	} else {
		return 1 + length_list(list->next);
	}
	
    return 0;
}

/**
Return the element at position.
*/
String get_list(Node* list, int position) {
    // todo: implement (b)
	if (position < 0) return NULL;
	
	
	int get = 0;
	for(Node* node = list; node != NULL; node = node->next){
		if (get == position) {
			return node->value;
			get++;
		}
	}
	
    return NULL;
}

/**
Free all nodes of the list, including the values it contains.
*/
void free_list(Node* list) {
    // todo: implement (c)
	if (list != NULL){
		free_list(list->next);
		free(list->value);
		free(list);
	}
}

/**
Adds an element to the end of the list. Modifies the existing list.
*/
Node* append_list(Node* list, String value) {
    // todo: implement (d)
	if ( list == NULL ) return new_node(value, NULL);
	
	Node* neu = new_node(value, NULL);
	for(Node* i = list; i != NULL; i = i->next){
		if (i->next == NULL){
			i->next = neu;
			break;
		}
	}
    return list;
}

// Remove element at position index from list. The element at index has to be deleted.
Node* remove_list(Node* list, int index) {
    if (list == NULL || index < 0) return list;
    if (index == 0) { // remove first element of non-empty list
        Node* n = list->next;
        free(list->value);
        free(list);
        return n;
    }
    // return second or later element of non-empty list
    int i = 0;
    for (Node* node = list; node != NULL; node = node->next) {
        if (i + 1 == index) {
            Node* to_remove = node->next;
            if (to_remove == NULL) return list;
            Node* n = to_remove->next;
            free(to_remove->value);
            free(to_remove);
            node->next = n;
            return list;
        }
        i++;
    }
    return list;
}

/**
Today's menu.
*/
const String menu[] = { "Currywurst", "Spaghetti", "Vegi", "Eintopf", "Salat" };

/**
The number of items on the menu.
*/
const int menu_count = sizeof(menu) / sizeof(char*);

/**
The list of completed food from the kitchen, waiting to be served. Each time a food item is served, the kitchen prepares a new food item randomly from the menu.
*/
Node* food; // List(String)

/**
The list of students standing in line and waiting for food. Each entry contains a string for the food that this student wants.
*/
Node* students; // List(String)

/**
The reputation of the mensa. The player is a staff member of the mensa. The goal of the player is to maximize the reputation of the mensa. Actually serving a dish that was asked for increases the reputation of the mensa by 1. Serving the wrong dish decreases the reputation by 1. If a student asks for a dish that is on the menu but not ready to be served, the reputation of the mensa decreases by 2.
*/
int reputation = 0;

/**
Example output:
fertige Essen: [Spaghetti, Vegi, Salat, Salat, Vegi]
nächster Essenswunsch: Currywurst (3 hungrige Studierende warten)
Reputation der Mensa: 0
> 
*/
void print_situation(void) {
    // todo: implement (e)
	printf("Fertige Essen: ");
	println_list(food);
	printf("naechster Essenswunsch: %s (%d hungrige Studierende warten)\n", students->value, length_list(students));
	printf("Reputation der Mensa: %d\n", reputation);
}

/**
Print final message, release all dynamically allocated memory, and exit the program.
Fertig für heute. Die Mensa schließt.
Finale Reputation der Mensa: 3
*/
void finish(void) {
    // todo: implement (f)
	printf("Fertig fuer heute. Die Mensa schliesst.\n");
	printf("Finale Reputation der Mensa: %d\n", reputation);
	free_list(students);
	free_list(food);
    exit(0);
}

/**
Run the mensa simulation.

The mensa person then enters the (0-based) index from the list of food. -1 stands for "not available". -2 ends the program. 

Here is an example dialog:

fertige Essen: [Salat, Vegi, Vegi, Spaghetti, Eintopf]
nächster Essenswunsch: Spaghetti (3 hungrige Studierende warten)
Reputation der Mensa: 0
> 3
Vielen Dank! Ich liebe die Mensa!
fertige Essen: [Salat, Vegi, Vegi, Eintopf, Eintopf]
nächster Essenswunsch: Spaghetti (3 hungrige Studierende warten)
Reputation der Mensa: 1
> -1
Spaghetti ist nicht da? Schade.
fertige Essen: [Salat, Vegi, Vegi, Eintopf, Eintopf]
nächster Essenswunsch: Salat (2 hungrige Studierende warten)
Reputation der Mensa: -1
> 0
Vielen Dank! Ich liebe die Mensa!
fertige Essen: [Vegi, Vegi, Eintopf, Eintopf, Eintopf]
nächster Essenswunsch: Eintopf (2 hungrige Studierende warten)
Reputation der Mensa: 0
> 1
Vegi möchte ich nicht! Ich möchte Eintopf! Dann hole ich mir halt einen Döner!
fertige Essen: [Vegi, Vegi, Eintopf, Eintopf, Eintopf]
nächster Essenswunsch: Currywurst (1 hungrige Studierende warten)
Reputation der Mensa: -1
> 1
Vegi möchte ich nicht! Ich möchte Currywurst! Dann hole ich mir halt einen Döner!
Fertig für heute. Die Mensa schließt.
Reputation der Mensa: -2
*/

 //random_food und random_wishes sind als Hilfsfunktion.
 
void random_food(){
	// to creat random food
	int random = i_rnd(menu_count);
	String r_food = menu[random];
	food = append_list(food, r_food);
	
}

void random_wishes(){
	// to creat random food wishes
	int random = i_rnd(menu_count);
	String r_wishes = menu[random];
	students = append_list(students, r_wishes);
	
}
void run_mensa(void) {
    // todo: implement
	
    // create 5 random food items from the menu
    for (int f = 0; f < 5; f++){
		random_food();
	}
    // create 3 random food wishes from the menu (each wish from one student)
    for(int w = 0; w < 3; w++){
		random_wishes();
	}
    
    print_situation();
    
    int i;
    while ((i = i_input()) >= -1) {
        // process input
        // distinguish different cases, update lists
        // use get_list, remove_list, append_list, length_list
        // use i_rnd to select random food items and random food wishes
        // use s_equals or strcmp to compare strings
		
		
		if (i == -1){ // if the wish food is not available
		
			printf("%s, ist nicht da? Schade\n", get_list(students, 0));
			
			students = remove_list(students, 0); // remove the student from the current list
			
			reputation -= 2; // reputation go down 2 times because of wished food not found !
			
		} else if ( i < length_list(food)){
			
			String choosed = get_list(food, i); // put the choosed food on the list
			
			String wanted = get_list(students, 0); // put the wanted food on the list
			
			if(s_equals(choosed, wanted)){ // the wishe menu passed with choosed menu
				printf("Vielen Dank ich liebe Mensa!\n");
				reputation += 1; // // reputation go up 1 time because of wished food is found !
				
				food = remove_list(food, i ); // remove current food from the list.
				students = remove_list(students, 0); // remove current student from the list
				random_food(); // add the next food to the list
				random_wishes(); // add the next wish from student to the list
			} else {
				printf("%s moechte ich nicht! ich moechte %s!\n", choosed, wanted);
				reputation -= 1; // reputation go down 1 time because of wanted food not found !
				
				food = remove_list(food, i); // remove current food from the list 
				students = remove_list(students, 0); // remove the current student from the list
				random_food(); // add the next food on the list
			}
		}
		if(length_list(students) == 0) break; // if all students are done, end the situation.
        print_situation();
    }
    finish();
}

int main(void) {
    report_memory_leaks(true);
    run_mensa();
    return 0;
}
