/*
Compile: make wolf_goat_cabbage
Run: ./wolf_goat_cabbage
make wolf_goat_cabbage && ./wolf_goat_cabbage
*/

#include "base.h"

// Represents a single list node. The value is dynamically allocated. The node 
// is the owner of the value and has to free it when itself is released.
struct Node {
    String value; // dynamically allocated string, release memory!
    struct Node* next; // self-reference
};
typedef struct Node Node;

// Create a list node. Copies the value (into dynamically allocated storage).
Node* new_node(String value, Node* next) {
    Node* node = xcalloc(1, sizeof(Node));
    node->value = s_copy(value); // s_copy performs dynamic allocation
    node->next = next;
    return node;
}

// Prints the components of the given list.
void print_list(Node* list) {
    if (list == NULL) {
        printf("[]");
    } else {
        printf("[%s", list->value);
        for (Node* n = list->next; n != NULL; n = n->next) {
            printf(" %s", n->value);
        }
        printf("]");
    }
}

// Print list followed by a newline.
void println_list(Node* list) {
    print_list(list);
    printsln("");
}

// Free all nodes of the list, including the values it contains.
void free_list(Node* list) {
	if (list != NULL){
		free_list(list->next);
		free(list->value);
		free(list);
	}
}


bool test_equal_lists(int line, Node* list1, Node* list2);
/*
Example calls for test_equal_lists.
Expected output (line numbers may differ):
Line 63: The lists are equal.
Line 66: The lists are equal.
Line 70: The lists are equal.
Line 74: The values at node 0 differ: hello <-> you.
Line 78: The lists are equal.
Line 82: The values at node 1 differ: second <-> hello.
Line 86: list1 is shorter than list2.
Line 90: list1 is longer than list2.
*/
void test_equal_lists_test(void) {
    Node* list1 = NULL;
    Node* list2 = NULL;
    test_equal_lists(__LINE__, list1, list2); // true
    list1 = new_node("hello", NULL); // hello -> NULL 	
    list2 = list1; // hello -> NULL     
    test_equal_lists(__LINE__, list1, list2); // true
    free_list(list1);
    list1 = new_node("hello", NULL); // hello -> NULL  
    list2 = list1; // hello -> NULL   
    test_equal_lists(__LINE__, list1, list2); // true
    free_list(list1);
    list1 = new_node("hello", NULL); // hello -> NULL       
    list2 = new_node("you", NULL); // you -> NULL 
    test_equal_lists(__LINE__, list1, list2); // false
    free_list(list1); free_list(list2);
    list1 = new_node("first", new_node("second", NULL)); // first -> second -> NULL                 
    list2 = list1; // first -> second -> NULL                   
    test_equal_lists(__LINE__, list1, list2); // true
    free_list(list1);
    list1 = new_node("first", new_node("second", NULL)); // first -> second -> NULL  
    list2 = new_node("first", new_node("hello", NULL)); // first -> hello -> NULL    	
    test_equal_lists(__LINE__, list1, list2); // false
    free_list(list1); free_list(list2);
    list1 = new_node("first", new_node("second", NULL)); // first -> second -> NULL                                           
    list2 = new_node("first", new_node("second", new_node("third", NULL))); // first -> second -> third -> NULL  	
    test_equal_lists(__LINE__, list1, list2); // false
    free_list(list1); free_list(list2);
    list1 = new_node("first", new_node("second", new_node("third", NULL))); // first -> second -> third -> NULL  
    list2 = new_node("first", new_node("second", NULL)); // first -> second -> NULL    
    test_equal_lists(__LINE__, list1, list2); // false
    free_list(list1); free_list(list2);

}

// Checking whether two string lists are equal.
bool test_equal_lists(int line, Node* list1, Node* list2) {
	
	int length_list(Node* list);
	int index = 0;
	if(list1 == NULL && list2 == NULL){// wenn die beide listen gleich NULL,dann sind die equal.
		printf("Line %d: The lists are equal.\n",line);
		return true;
	}else if(list1 == list2){ //wenn die list1 gleich list2 dann sind die equal.
		printf("Line %d: The lists are equal.\n",line);
	}else if(length_list(list1) > length_list(list2)){ // wenn die Länge der list1 ist großer als die Länge der list2, dann ist die list1 großer als list2 
		printf("Line %d: list1 is longer than list2.\n",line);
	}else if(length_list(list1) < length_list(list2)){// wenn die Länge der list1 ist kleiner als die Länge der list2, dann ist die list1 kleiner als list2 
		printf("Line %d: list1 is shorter than list2.\n",line);
	}
	
	while(list1 != NULL && list2 != NULL){
		if(s_equals(list1->value,list2->value) == false){//wenn die Values von beiden listen ungleich sind
			printf("Line %d: The values at node %d differ: %s <-> %s.\n",line,index, list1->value, list2->value);
		}
		//wenn die Values gleich sind, dann addieren wir 1 zu index und machen neue liste mit anfang der Value von next.
		index++;
		list1 = list1->next;
		list2 = list2->next;
	}
	
    return false;
}

int length_list(Node* list);

// Example calls for length_list (below).
void length_list_test(void) {
	Node* list = NULL;
	test_equal_i(length_list(list),0);
	free_list(list);
	
	list = new_node("hello",new_node("wolrd",new_node("wide",NULL)));
	test_equal_i(length_list(list),3);
	free_list(list);
	
	list = new_node("first",new_node("second",new_node("third",new_node("end",NULL))));
	test_equal_i(length_list(list),4);
	free_list(list);
	
	list = new_node("BMW",new_node("GMC",new_node("PS4",new_node("PC",new_node("PS5",NULL)))));
	test_equal_i(length_list(list),5);
	free_list(list);
	
}

// Number of elements of the list.
int length_list(Node* list) {
    int n = 0;
    for (Node* node = list; node != NULL; node = node->next) n++;
    return n;
}

int index_list(Node* list, String s);

// Example calls for index_list (below).
void index_list_test(void) {
	Node* list = NULL;
	test_equal_i(index_list(list, "abend"),-1);
	free_list(list);
	
	list = new_node("hello",new_node("world",NULL));
	test_equal_i(index_list(list, "hello"),0);
	free_list(list);
	
	list = new_node("hello",new_node("world",NULL));
	test_equal_i(index_list(list, "he"),-1);
	free_list(list);
	
	list = new_node("take",new_node("your",new_node("time",NULL)));
	test_equal_i(index_list(list, "your"),1);
	free_list(list);
	
	list = new_node("BMW",new_node("Ferrari",new_node("Porsche",new_node("Golf",new_node("GMC",NULL)))));
	test_equal_i(index_list(list, "GMC"),4);
	free_list(list);

}

// Return index of s in list, or -1 if s is not in list.
int index_list(Node* list, String s) {
	require("vaild string", s != NULL);
	int index = 0;
	while(list != NULL){
		if(s_equals(list->value,s) == true){// wenn string s ist in liste bzw gleich als list value dann soll der index zurückgegeben wird.
			return index;
		}
		index++;
		list = list->next;
		
	}
	
    return -1;
}

// Check whether list contains s.
bool contains_list(Node* list, String s) {
    return index_list(list, s) >= 0;
}

Node* remove_list(Node* list, int index);

// Example calls for remove_list (below).
void remove_list_test(void) {
	Node* actual;
	Node* expected;
	
	actual = new_node("PS4",new_node("PC",NULL));
	actual = remove_list(actual,0);
	expected = new_node("PC",NULL);
	test_equal_lists(__LINE__,actual,expected);
	free_list(actual); 
	free_list(expected);
	
	actual = new_node("Hannover",new_node("Hamburg",new_node("Berlin",new_node("Frankfurt",NULL))));
	actual = remove_list(actual,3);
	expected = new_node("Hannover",new_node("Hamburg",new_node("Berlin",NULL)));
	test_equal_lists(__LINE__,actual,expected);
	free_list(actual); 
	free_list(expected);
	
	actual = new_node("hello",new_node("friend",new_node("how",new_node("you",new_node("doing",NULL)))));
	actual = remove_list(actual, 4);
	expected = new_node("hello",new_node("friend",new_node("how",new_node("you",NULL))));
	test_equal_lists(__LINE__,actual,expected);
	free_list(actual); 
	free_list(expected);
}

// Remove element at position index from list. The element at index has to be deleted.
Node* remove_list(Node* list, int index) {
    // todo: implement (Aufgabe 1)
	require("vaild list", list != NULL);
	Node* removed;
	Node* nextnode;
	int i = 1;
	removed = list;
	while(i < index - 1){
		removed = removed->next;
		i++;
	}
	nextnode = removed->next;
	removed->next = nextnode->next;
	free(nextnode->value);
	free(nextnode);


    return list;
}


// remove_list_for_puzzle ist Hilfsfunktion für Puzzle
Node* remove_list_for_puzzle(Node* list, int index) {
	require("vaild list", list != NULL); // nichts kann gelöscht werden, wenn die Liste gleich NULL ist!
	int i = 0;
	Node* start = list; // start of node list
	if(index == 0){
		Node* remove_first = list;
		list = list->next;
		free(remove_first->value);
		free(remove_first);
		start = list;
	} 
	
	while(list != NULL){
		if(i == index - 1){
			Node* nextnode = list->next;
			list->next = list->next->next;
			free(nextnode->value);
			free(nextnode);
			break;
		}else{
			i++;
			list = list->next;
		}
	}
	
    return start;
}



///////////////////////////////////////////////////////////////////////////

// The boat may either be at the left or right river bank. 
// We don't care for the transition (boat crossing the river).
enum Position {
    LEFT, RIGHT
};

// The data that represents the state of the puzzle.
typedef struct {
    // List of objects on the left river bank.
    Node* left;

    // List of objects on the right river bank.
    Node* right;

    // List of objects in the boat. The boat has a capacity of one object only.
    Node* boat;
	

    // Current boat position.
    enum Position position;
} Puzzle;

// Initialize the state of the puzzle.
Puzzle make_puzzle(void) {
    Puzzle p = { new_node("Wolf", new_node("Ziege", new_node("Kohl", NULL))), NULL, NULL, LEFT};
    return p;
}


// Print the current state of the puzzle.
void print_puzzle(Puzzle* p) {
	// Linke Seite dann Boot (kann rechts/links bewegen) dann rechte Seite
	print_list(p->left); 
	if(p->position == LEFT){
		print_list(p->boat);
		printf("        ");
	}else if(p->position == RIGHT){
		printf("        ");
		print_list(p->boat);
	}
	print_list(p->right);
	printf("\n");
	
}

// Release memory and quit.
void finish_puzzle(Puzzle* p) {
	if (p != NULL){
		free_list(p->left);
		free_list(p->right);
		free_list(p->boat);
		exit(0);
	}
	else {
		printf("puzzle is empty!");
		exit(-1);
	}
	
}

void evaluate_puzzle(Puzzle* p) {
	/*Wir besprechen 4 mögliche Situationen*/
   bool situation_1 = contains_list(p->left,"Kohl") && contains_list(p->left,"Ziege") && p->position == RIGHT; 
   bool situation_2 = contains_list(p->right,"Kohl") && contains_list(p->right,"Ziege") && p->position == LEFT;
   bool situation_3 = contains_list(p->left,"Wolf") && contains_list(p->left,"Ziege") && p->position == RIGHT;
   bool situation_4 = contains_list(p->right,"Wolf") && contains_list(p->right,"Ziege") && p->position == LEFT;
   
   if(p->left != NULL && p->boat == NULL && p->right == NULL){ //Anfangszustand, alle am linken Ufer
	   printf("Please Choose one to get in boat before moving the boat!\n");
   }else if(situation_1 || situation_2){ //Kohl und Ziege sind allein am linken/rechten Ufer, nict erfolgreicher Beispielablauf
		printf("Wolf is in Boat!\n");
	   printf("Goat is alone with Cabbage and eat it!\n");
	   printf("Game Over, Try Again!\n");
	   finish_puzzle(p);
   }else if(situation_3 || situation_4){//Wolf und Ziege sind allein am linken/rechten Ufer, nict erfolgreicher Beispielablauf
	   printf("Cabbage is in Boat!\n");
	   printf("Wolf is alone with Goat and eat it\n");
	   printf("Game Over, Try Again!\n");
	   finish_puzzle(p);
   }else if(p->left == NULL && p->boat == NULL){ // Alle sind in sicher, Spiel endet!
	   printf("Well Done!, All passed the River!\n");
	   finish_puzzle(p);
   }
}

void play_puzzle(Puzzle* p) {
	while(p){
		print_puzzle(p);
		evaluate_puzzle(p);
		Node** multi_positions; // damit wir alle Listseiten zugriff können.
		String entered_from_user = s_input(100); // was der Spieler schreibt !
		String insert_user = entered_from_user;
		if(strcmp(entered_from_user,"q") == 0){ // Spiel beenden.
			free(entered_from_user);
			finish_puzzle(p);
		}else if(strcmp(entered_from_user,"r") == 0){ // das Boot zu Rechts bewegen
			p->position = RIGHT;
		}else if(strcmp(entered_from_user,"l") == 0){ // das Boot zu Links bewegen
			p->position = LEFT;
		}else if(strcmp(entered_from_user,"Wolf") == 0 || strcmp(entered_from_user,"Ziege") == 0 || strcmp(entered_from_user,"Kohl") == 0){
			 // nur die Wörte, die ( Wolf Ziege Kohl ) oben in if-anweisung stehen, sollen als insert_user bezeichnet, damit wir sie bewegen können und löschen bzw einfügen in andere Liste.
			 
			/* Die Verwendung dieser Funktion hilft, weil wir Dinge verschieben und gleichzeitig von der Liste entfernen möchten.
			 Node** new ist als Hilfsfunktion benutzt!!*/
			if(p->position == LEFT){
				multi_positions = &p->left; //die Adresse von dem Pointer der linksten List.
			}else if(p->position == RIGHT){
				multi_positions = &p->right; // die Adresse von dem Pointer der rechten liste.
			}
			
			/*wir haben eine Hilfsfunktion remove_list_for_puzzle erstellt, Siehe Oben.*/
			// Die maximale Länge der Bootliste ist 1.
			if(p->boat == NULL){ // wenn das Boot leer ist
				int pos = index_list(*multi_positions,insert_user); //um die Position bzw Index von dem angegebenen Wort zu wissen.
				if(pos != -1){ // wenn Pos bzw Index -1 ist, dann existiert das Wort in liste nicht! 
					*multi_positions = remove_list_for_puzzle(*multi_positions,pos);
					p->boat = new_node(insert_user,NULL); // da das Boot leer ist, wird die erste Node vom Boot das Wort, das von dem Spieler angegeben ist.
				}
			}else{ // Die maximale länge für die linken/rechten List ist 3 index, die erste index ist 0->1->2->NULL, Wolf->Ziege->Kohl.
				if(contains_list(p->boat,insert_user)){ // wenn das Boot nihct leer ist, und ein Wort inthalt.
					p->boat = remove_list_for_puzzle(p->boat,0); // löschen wir die erste Node, da das Boot nur ein Wort inhalten kann, löschen wir index 0 von der boatliste.
					if(length_list(*multi_positions) == 0){ // wenn das Boot auf der rechten/linken Seite, wird überprüft, wie größ ist die rechte/linken liste, wenn die länge der Liste gleich 0, 
						*multi_positions = new_node(insert_user,NULL); //fügen wir das Wort auf dem ersten (0) Platz/Index
					}else if(length_list(*multi_positions) == 1){// wenn das Boot auf der rechten/linken Seite, wird überprüft, wie größ ist die rechte/linken liste, wenn die länge der Liste gleich 1,
						(*multi_positions)->next = new_node(insert_user,NULL);//fügen wir das Wort auf dem zweit (1) Platz/Index.
					}else if(length_list(*multi_positions) == 2){// wenn das Boot auf der rechten/linken Seite, wird überprüft, wie größ ist die rechte/linken liste, wenn die länge der Liste gleich 2,
						(*multi_positions)->next->next = new_node(insert_user,NULL);//fügen wir das Wort auf dem dritten (2) Platz/Index.
					}
				}
			}
		}else{
			printf("\n**You have enterd a Invaild Input, please check what did you write!\n");
			printf("You have to write only Vaild word/letter**\n");
			printf("\n");
		}
		free(entered_from_user);
    }
}

///////////////////////////////////////////////////////////////////////////

int main(void) {
    report_memory_leaks(true);

    test_equal_lists_test();
    length_list_test();
    index_list_test();
    remove_list_test();
   
   	printf("\n*** PLEASE READ THIS FIRST BEFORE YOU PLAY ***\n");
	printf("\nIn This Game You need to pick one from left side of river to the right side");
	printf(" but you cant leave Wolf with Goat alone or Cabbage with Goat");
	printf(" if you do that, you will lose the Game!.");
	printf("\n");
	printf(" You have to write the name of thing you picking same as on screen written!!\n");
	printf(" TO MOVE THE BOAT RIGHT SIDE PRESS r\n");
	printf(" TO MOVE THE BOAT LEFT SIDE PRESS l\n");
	printf(" TO QUIT THE GAME PRESS q\n");
	printf("\tCreated by: Anas Salameh, Katrin Kindereich\t\n");
	printf("\tMatrikeln.: 10040389    , 10037573\t");
	printf("\n");
	printf("\n");
	printf("Game Started, Have Fun! :)\n");
	printf("\n");
    
    Puzzle p = make_puzzle();
    play_puzzle(&p);
	
    return 0;
}
