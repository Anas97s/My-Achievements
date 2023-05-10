/*
    make wish_tree
    ./wish_tree
    make wish_tree && ./wish_tree
*/

#include "base.h"


typedef struct Element Element;

void free_element(Element* element);


typedef struct Node{
    struct Node* next;
    char* value;
}Node;

/*
    Returns a pointer to a dynamically allocated node.
*/
Node* new_node(char* value, Node* next){
    Node* node = xmalloc(sizeof(Node));
    node->next = next;
    node->value = s_copy(value);
    return node;
}

/* 
    Releases the memory used by node. 
*/
Node* free_node(Node* node){
    Node* next = node->next;
    free(node->value);
    free(node);
    return next;
}

/*
    Returns the length of a list of nodes.
*/
int length(Node* node){
    int count = 0;
    while(node != NULL){
        count++;
        node = node->next;
    }
    return count;
}

/*
    Returns true if the list of nodes contains find.
*/
bool contains(Node* node, char* find){
    while(node != NULL){
        if(strcmp(node->value, find) == 0){
            return true;
        }
        node = node->next;
    }
    return false;
}


/* 
    A TreeNode has a left and right successor of type TreeNode. A successor TreeNode is a TreeNode or NULL
        
            TreeNode root
           /          \
          /            \
    TreeNode left      TreeNode right
    /       \       /           \
   ...      ...    ...          ...
*/

typedef struct TreeNode{
    struct TreeNode* left;
    struct TreeNode* right;
    struct Element* element;
}TreeNode;

/*
    Returns a pointer to a dynamically allocated tree_node.
*/
TreeNode* new_tree_node(Element* element){
    TreeNode* bnode = xmalloc(sizeof(TreeNode));
    bnode->left = NULL;
    bnode->right = NULL;
    bnode->element = element;
    return bnode;
}

/*  todo: g)
    Releases the memory used by a tree_node and also frees the included element.
*/
void free_tree_node(TreeNode* tree){
	if (tree != NULL){
		free_element(tree->element);
		free_tree_node(tree->left);
		free_tree_node(tree->right);
		free(tree);
	}else{
		printf("tree is empty!");
		exit(-1);
	}
}

/*free_all ist eine Hilfsfunktion*/
void free_all(TreeNode* tree){
	if(tree != NULL){ // wenn tree leer ist.
		free_all(tree->left); //freen linkste knoten
		free_all(tree->right); // dann freen rechte knoten
		free_tree_node(tree); // dann freen den ganzen Baum
		exit(0);
	}else{
		exit(-1);
	}
}


//todo: b)
struct Element{
	String wish; // wish ist ein String
	Node* children; // Children sind Node* (liste)
	int count; // count ist ein int
};

//todo: b)
Element* new_element(char* wish, char* child){
	Element * wishlist = xmalloc(sizeof(Element));
	wishlist->wish = s_copy(wish); // da wish ein String ist. s_copy kopiert den gegebenen string.
	wishlist->children = new_node(child,NULL); //da children eine pointerNode ist, erstellen wir eine new_node.
	wishlist->count = 1; //fangen wir ab 1.
    return wishlist;
}

//todo: g)
void free_element(Element* element){
	/*wir könnte hier while-schleife benutzen aber mit for-schleife finde ich es besser :D*/
	free(element->wish); //freeen die string zuerst
	for(Node* i = element->children; i != NULL; i = i->next){ //wenn die Bedingung erfüllt ist,freen next node von element->children.
		Node* j = i;
		if(j != NULL){
			free_node(j);
		}
	}
	free(element); // freen element am Ende.
	
}

//todo: c)
TreeNode* add_wish(TreeNode* tree, char* wish, char* child){
   if(tree == NULL){//wenn tree leer ist, erstellen wir ein neues.
	   Element* newtree = new_element(wish, child);
	   tree = new_tree_node(newtree);
   }else if(tree != NULL){
	   if(strcmp(tree->element->wish,wish) == 0){//wenn string von wish ist gleich wish
		   while(contains(tree->element->children,child) == false){//es muss falsch sein, sondern bekommt ein Kind das Spiele und die andere Kinder können nicht das gleiche Spiel haben.
			   tree->element->count++;
			   tree->element->children = new_node(child, tree->element->children);
			   break;
		   }
	   }else if(strcmp(wish, tree->element->wish) < 0){// wenn wish kommt zu erst dann setzen wir den wunsch auf der linken Steite des Baumes
		   tree->left = add_wish(tree->left,wish,child);
	   }else if(strcmp(wish, tree->element->wish) > 0){// wenn wish nachher kommt dann setzen wir das Kind auf der rechten Steite des Baumes
		   tree->right = add_wish(tree->right, wish, child);
	   }
   }
    return tree;
}

typedef struct ElementNode{
    struct ElementNode* next;
    Element* element;
}ElementNode;

ElementNode* new_element_node(Element* element, ElementNode* next){
    ElementNode* en = xmalloc(sizeof(ElementNode));
    en->next = next;
    en->element = element;
    return en;
}
//todo: g)
void free_element_list(ElementNode* en){
	/*auch hier könnten wir mit if oder while-schleife machen, aber mit for-schleife finde ich es schöner :D*/
	for(ElementNode* i = en; i != NULL; i = i->next){
		free(i);
	}
	
}

void print_element_list(ElementNode* list, int n){
    printf("\n");
	printf("\n");
    printf("%55s\t%6s\n", "Wunsch", "Anzahl");
    while(n > 0 && list != NULL){
        printf("%55s\t%6d\n", list->element->wish, list->element->count); 
        n--;
        list = list->next;
    }
}

/*das hier ist sortierte liste wie in der Aufgabe stehet.!
Erstellen Sie als erstes eine Liste, in der die Wünsche absteigend nach Ihrer Häufigkeit sortiert sind.
*/
ElementNode* sorted(ElementNode* list, Element* wish){

    if(list == NULL){// list ist leer!,dann erstellen wir eine neue element node.
		return new_element_node(wish,NULL);
	}
    while(list != NULL) {
        if(wish->count > list->element->count){
            return new_element_node(wish, list);
        }else{
			ElementNode* i = list;// list ersetzt durch i
			ElementNode* j = list->next;// list->next ersetzt durch j
			do{
				if(wish->count > j->element->count){
					i->next = new_element_node(wish, j);
					return list;
				}
				i = i->next;
				j= i->next;
			}while(j != NULL);
			i->next = new_element_node(wish, NULL);
			return list;
		}
    }
    return NULL;
}
//todo: e)
ElementNode* insert_ordered_by_count(ElementNode* result, TreeNode* tree){
	if(tree == NULL){
		return result;
	}
	while(tree != NULL) {
		result = sorted(result,tree->element);
		result = insert_ordered_by_count(result, tree->left);
		result = insert_ordered_by_count(result, tree->right);
		
		return result;
	}
	return NULL;
}

//todo: d)
void print_tree_as_list(TreeNode* tree){// die den Baum auf der Konsole im Listenformat lexikographisch sortiert nach dem Wunschtext.

	if(tree == NULL){
		return;
	}else{
		print_tree_as_list(tree->left);
		printf("%55s\t%6d\t",tree->element->wish, tree->element->count);
		for(Node* i = tree->element->children; i != NULL; i = i->next){
			if(i->next == NULL){
				printf("%s\n",i->value);
			}else if(i->next != NULL){
				printf("%s, ",i->value);
			}
		}
		print_tree_as_list(tree->right);
	}
}

// Skips the rest of the current line.
char* skip_line(char* s) {
    while (*s != '\n' && *s != '\0') s++;
    if (*s == '\n') s++;
    return s;
}

// Skips the current token.
char* skip_token(char* s) {
    while (*s != ',' && *s != '\n' && *s != '\0') s++;
    return s;
}

// Skips spaces.
char* skip_spaces_and_commas(char* s) {
    while (*s == ' ' || *s == ',') s++;
    return s;
}


// Create a new token from s (inclusive) to t (exclusive).
char* new_token(char* s, char* t) {
    char* token = xmalloc(t - s + 1);
    int i = 0;
    while (s < t) {
        token[i] = *s;
        i++;
        s++;
    }
    token[i]= '\0';
    return token;
}

// Returns a new token by first skipping spaces and commas and then reading the token. 
// Sets *s to the first character after the token.
char* next_token(/*INOUT*/char** s) {
    *s = skip_spaces_and_commas(*s);
    char* t = skip_token(*s);
    char* token = new_token(*s, t);
    *s = t; // set *s to the character after the token
    return token;
}


/* 
    This function reads in one line of the wish text file and adds the wishes of a child to tree.
*/
TreeNode* read_wish_list(char** s, TreeNode* tree){
    char* child = next_token(s);
    
    while(**s != '\0' && **s != '\n'){
        char* wish = next_token(s);
        *s = skip_spaces_and_commas(*s);
        tree = add_wish(tree, wish, child); 
        free(wish);
    }
    
    free(child);
    return tree;
}

/*  
    This function reads the whole file and uses the read_wish_list function to parse a single line.
*/
TreeNode* read_wishes(char* filename, TreeNode* tree){
    char* file = s_read_file(filename);
    char* s = file;
    s = skip_line(s);
    while(*s != '\0'){
        tree = read_wish_list(&s, tree);
        s = skip_line(s);
        
    }
    free(file);
    return tree;
}


int main(int argc, char** argv){
    report_memory_leaks(true);
    TreeNode* tree = read_wishes("wishes.txt", NULL);
    printf("%55s\t%6s\tKinder\n", "Wunsch", "Anzahl"); // um Wunsch Anzahl Kinder am anfang zu schreiben.
    print_tree_as_list(tree);
    ElementNode* sorted_by_count = insert_ordered_by_count(NULL, tree);
    print_element_list(sorted_by_count, 10);
    printf("\n\tHerzlichen Glueckwunsch! Alle Kinder haben ihre Geschenke bekommen!");
	printf("\n");
	

	free_element_list(sorted_by_count);
	free_all(tree); //free_all ist eine Hilfsfunktion siehe oben.
    return 0;
}
