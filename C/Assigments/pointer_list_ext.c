/*
Compile: make pointer_list_ext
Run: ./pointer_list_ext
make pointer_list_ext && ./pointer_list_ext
*/

#include "pointer_list.h"

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

// Create a list consisting of the first n nodes of list.
Node* take_list(Node* list, int n) {
	Node* new_take = NULL; //erstellen wir neue Liste und setzen wir die gleich NULL, damit die Elemente nicht dupliziert werden.
	int i = 1; 
	while(i <= n){ // wenn i kleiner oder gleich int n 
		new_take = append_list(new_take,list->value); // append_list Fügt am Ende der Liste ein Element hinzu. Ändert die new_take " neue lsite".
		list = list->next; //springen wir zum nächsten wert der liste
		i++; 
	}

	return new_take; //die neue liste zurückgeben.
}

// Create a list consisting of nodes of list, except the first n.
Node* drop_list(Node* list, int n) {
	Node* new_drop = list; //erstellen wir neue Liste und ist gleich list.
	int i = 1;
	while(i <= n){//wenn i kleiner gleich n 
		new_drop = new_drop->next;// springen wir immer zum nächsten wert in der Liste
		i++;
	}
    return new_drop; //die neue liste zurückgeben.
}


// Take alternatingly from list1 and list2 until all data is collected in the result.
Node* interleave(Node* list1, Node* list2) {
	Node*  new_interleave = NULL; //neue liste erstellen
	int i = 1;
	int x = length_list(list1) + length_list(list2); //addition der länge von list1 + list2
	while (i <= x) { //wenn i kleiner oder gleich x ist 
	    if (list1 != NULL && length_list(new_interleave) < x) {
		    new_interleave = append_list(new_interleave, list1->value); //liste1 an die neue liste einfügen
		    if (list1->next != NULL) list1 = list1->next; // zum nächsten wert in liste1 springen
		}
		if (list2 != NULL && length_list(new_interleave) < x) {
		    new_interleave = append_list(new_interleave, list2->value); //liste 2 an die neue liste einfügen
		    if (list2->next != NULL) list2 = list2->next; // zum nächsten wert in liste2 springen
		}
		i++;
	}
    return  new_interleave; //die neue liste zurückgeben.

} 

// typedef bool (*EqualFun)(void* element1, void* element2);

bool group_by_length(void* element1, void* element2) {
    String s1 = element1;
    String s2 = element2;
    return s_length(s1) == s_length(s2);
}

// Group elements in list. Equivalent elements (for which equivalent is true) are put
// in the same group. The result is a list of groups. Each group is itself a list.
// Each group contains items that are equivalend.
Node* group_list(Node* list, EqualFun equivalent) {
	Node* new_group = new_node(NULL,NULL); //erstellen wir eine neue liste
	new_group->value = new_node(NULL,NULL); // indem jede Value besteht aus maximal zwei elemente./ es kann sein new_node(value,NULL)
	Node* node = list; // neue node zeichnet auf list.
	while(node != NULL){ // wenn die liste nicht leer ist.
		Node* e1 = new_group; 
		Node* e2 = e1->value; 
		if(e2->value == NULL){ //wenn e2 gleich null ist 
			e2->value = node->value; // setzen wir den Wert von e2 zu dem Wert von liste.
		}else if(e2->value != NULL){
			for(Node* x = e1; x->next != NULL; x = x->next){
				if((equivalent(e2->value, node->value)) == false){//wenn den Wert von e2 ist ungleich den wert von liste ist
					e1 = e1->next;  //zum nächsten wert in liste springen
					e2 = e1->value; // und setzen wir e2 ist gleich den Wert vom e1
				}
			}
			if(equivalent(e2->value, node->value) == true){//wenn den Wert von e2 ist gleich den wert von liste ist
				for(Node* y = e2; y->next != NULL; y = y->next){
					if(y->value != NULL){//sichern wir dass den wert von e2 ungleich NULL ist
						y = y->next;//wenn ja, dann zum nächsten wert springen
					}
				}
				if(e2->value != NULL){ // wenn den Wert von e2 ungleich null ("nicht leer") ist
					e2->next = new_node(NULL,NULL); // setzen wir  next von e2 ist eine neue node mit maximal 2 werte
					e2 = e2->next;// dann zum nächsten wert springen
				}
				e2->value = node->value;
			}else{//damit wir noch weiter gruppen erstellen und nicht nur eine, wenn wir ohne das hier den Code beenden dann wird nur eine Gruppe erstellt und keine weitere.
				e1->next = new_node(NULL,NULL);//wie oben, eine next node erstellen mit maximal 2 elementen
				e1 = e1->next; //zum nächsten wert springen
				e1->value = new_node(NULL,NULL);//auch hier, ein Wert besteht aus maximal zwei elemente.
				e2 = e1->value;
				e2->value = node->value;
			}
		}
		node = node->next;//solange die list nicht leer ist,zum nächsten Wert der Liste springen.
	}
	return new_group;
}

void free_group(void* x) {
    Node* list = x;
    free_list(list, NULL);
}

int main(void) {
    report_memory_leaks(true);
	
    printf("\n");
    Node* list = new_node("a", new_node("bb", new_node("ccc", new_node("dd", new_node("e", NULL)))));
    println_list(list, string_to_string);
	
	printf("\n");
    prints("take_list: ");
    Node* list2 = take_list(list, 3);
    println_list(list2, string_to_string);
    
	printf("\n");
    prints("drop_list: ");
    Node* list3 = drop_list(list, 3);
    println_list(list3, string_to_string);
    
	printf("\n");
    prints("interleave: ");
    Node* list4 = interleave(list2, list3);
    println_list(list4, string_to_string);
    
	printf("\n");
    Node* groups = group_list(list, group_by_length);
    printf("%d groups:\n", length_list(groups));
    for (Node* n = groups; n != NULL; n = n->next) {
        if (n->value != NULL) {
        println_list(n->value, string_to_string);
        }
    }
    
    free_list(list, NULL);
    free_list(list2, NULL);
    free_list(list4, NULL);
    free_list(groups, free_group);
    
    return 0;
}
