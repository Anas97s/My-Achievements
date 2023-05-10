/*
    make bleep_censor
    ./bleep_censor
    make bleep_censor && ./bleep_censor

*/

#include "base.h"



typedef struct Token{
    char* start;
    char* end;
}Token;

typedef struct TreeNode{
    struct TreeNode* left;
    struct TreeNode* right;
    Token* token;
}TreeNode;

Token* new_token(char* start, char* end){
    Token* t = xmalloc(sizeof(Token));
    t->start = start;
    t->end = end;
    return t;
}

void print_token(Token* t, bool censored) {
    //todo
	
	int i = 1;
	printf("%c", *(t->start));
	while(i < (t->end - t->start)){
		if(censored){
			printf("*");
		}else{
			printf("%c", *(t->start + i));
		}
		i++;
	}
	printf(" ");
	

}

void print_tokenln(Token* t, bool censored) {
    print_token(t, censored);
    printc('\n');
}

TreeNode* new_tree_node(TreeNode* left, Token* token, TreeNode* right){
    TreeNode* tree_node = xmalloc(sizeof(TreeNode));
    tree_node->token = token;
    tree_node->left = left;
    tree_node->right = right;
    return tree_node;
}

void free_tree(TreeNode* tree){
    //todo
	if(tree != NULL){
		free_tree(tree->left);
		free_tree(tree->right);
		free(tree->token);
		free(tree);
	}

}

TreeNode* new_leaf(Token* token){
    return new_tree_node(NULL, token, NULL);
}


/*

Hilfsfunktion für alle Buchstabe zu klein schreibung

*/
int change_to_small(int x, int y){
	if(x < y){
		return x;
	}else if(x > y){
		return y;
	}
	return x;
}

void print_tree(TreeNode* tree){
    if(tree == NULL){
        return;
    }
    print_tree(tree->left);
    printc(' ');
    print_token(tree->token, false);
    printc(' ');
    print_tree(tree->right);
}


int compare_token(Token* t1, Token* t2){
    //todo
	
	int small = change_to_small((t1->end - t1->start),(t2->end - t2->start)); // t->end - t->start is die länge des Wortes
	
	
	/*
	hier ist wenn die Schreibung ist nicht alle klein, dann machen wir vergleich in while-schleife
	*/
	int i = 0;
	while(i < small){
		char lowert1 = tolower(*(t1->start + i));
		char lowert2 = tolower(*(t2->start + i));
		if(lowert1 < lowert2){
			return -1;
		}else if(lowert1 > lowert2){
			return 1;
		}
		i++;
	}
	
	/*hier vergleich ist für beide gleich screibung*/
	if((t1->end - t1->start) < (t2->end - t2->start)){
		return -1;
	}else if((t1->end - t1->start) > (t2->end - t2->start)){
		return 1;
	}
	
	
    return 0;
	
}


void test_token_compare(){
    char* s = "hallo huhu hall HALLO";
    Token hallo = {s + 0, s + 5};
    //print_tokenln(&hallo);
    Token huhu = {s + 6, s + 10};
    //print_tokenln(&huhu);
    Token hall = {s + 11, s + 15};
    //print_tokenln(&hall);
    Token hallo_2 = {s + 16, s + 21};
    //print_tokenln(&hallo_2);
    
    test_equal_i(compare_token(&hallo, &hallo), 0);
    test_equal_i(compare_token(&huhu, &huhu), 0);
    test_equal_i(compare_token(&huhu, &hallo), 1);
    test_equal_i(compare_token(&hallo, &huhu), -1);
    test_equal_i(compare_token(&hall, &hallo), -1);
    test_equal_i(compare_token(&hallo, &hall), 1);
    test_equal_i(compare_token(&hallo, &hallo_2), 0);
    test_equal_i(compare_token(&hallo_2, &hallo), 0);
    
}

bool contains(TreeNode* tree, Token* token){
    if(tree == NULL){
        return false;
    }
    int cmp_result = compare_token(token, tree->token);
    if(cmp_result == 0){
        return true;
    }
    else if(cmp_result < 0){
        return contains(tree->left, token);

    }else{
        return contains(tree->right, token);
    }  
}



void insert_in_tree(TreeNode** tree, Token* token){
    if(*tree == NULL){
        *tree = new_leaf(token);
    }else {
		int result = compare_token(token,(*tree)->token);
		if(result < 0){
            insert_in_tree(&((*tree)->left), token);
        }else if(result > 0){
            insert_in_tree(&((*tree)->right), token);
        }
    }
}

void test_insert_token(){
    char* s = "hallo huhu hall HALLO";
    
    Token hallo = {s + 0, s + 5};
    //print_tokenln(&hallo);
    Token huhu = {s + 6, s + 10};
    //print_tokenln(&huhu);
    Token hall = {s + 11, s + 15};
    //print_tokenln(&hall);
    
    TreeNode* tree = NULL;
    
    insert_in_tree(&tree, &hallo);
    
    test_equal_i(compare_token(tree->token, &hallo), 0);
    
    insert_in_tree(&tree, &hallo);
    test_equal_i(compare_token(tree->token, &hallo), 0);
    
    insert_in_tree(&tree, &hall);
    test_equal_i(compare_token(tree->left->token, &hall), 0);
    
    insert_in_tree(&tree, &huhu);
    test_equal_i(compare_token(tree->right->token, &huhu), 0);
    
    free(tree->left);
    free(tree->right);
    free(tree);
    
}


TreeNode* create_bleep_tree(char* bleep_words){
    //todo
	
	/*es ist wie unter buffer aber mit pointer von Token*/
	TreeNode* tree = NULL;
	int first = 0;
	int i = 0;
	for(i = 0; i < strlen(bleep_words); i++){  
		if(bleep_words[i] == ' ' || bleep_words[i] == '\0'){ //wenn i gleich end Wort oder leerzeichnen ist,
			Token* t = new_token(bleep_words + first, bleep_words + i); //erstellen wir ein neuen Token.
			insert_in_tree(&tree, t);// fügen wir den neuen Token in tree
			first = i + 1; // fangen wir mit einem neuem Wort, oder einem neuen Anfang.
		}
	}
	
	return tree;
	
	
}

void test_create_bleep_tree(){
    char* s = "hallo huhu hall HALLO";
    
    Token hallo = {s + 0, s + 5};
    //print_tokenln(&hallo);
    Token huhu = {s + 6, s + 10};
    //print_tokenln(&huhu);
    Token hall = {s + 11, s + 15};
    //print_tokenln(&hall);
    
    TreeNode* tree = create_bleep_tree(s);
   
    test_equal_i(compare_token(tree->token, &hallo), 0);  
    test_equal_i(compare_token(tree->left->token, &hall), 0); 
    test_equal_i(compare_token(tree->right->token, &huhu), 0);
    
    free_tree(tree);
}

int main(void){    
    report_memory_leaks(true);
    test_token_compare();
    test_insert_token(); // uncomment when implemented
    test_create_bleep_tree(); //<--- uncomment when implemented
    
    char* bleep_words = "schwein affe dumm bloed bloedmann idiot ";    
    TreeNode* tree = create_bleep_tree(bleep_words);
    
    print_tree(tree);
    printc('\n');
    
    char buffer[256];
    get_line(buffer, 256);
    printf("Original String: %s\n", buffer);
    printf("Bleeped String: ", buffer);
    //todo
	int first = 0;
	int i = 0;
	do{
		if(buffer[i] == ' ' || buffer[i] == '\0'){// wenn i gleich end Wort oder leerzeichnen ist, 
			Token t = {buffer + first, buffer + i}; // word bedeutet buffer start den ersten Buchstab von eingabe folgt mit länge des wortes
			first = i + 1;
			bool word_delete = contains(tree, &t); // kontrolliert wenn das Wort censored ist durch compare_token funktion, die in contains funktion enthalten ist.
			print_token(&t, word_delete); // wenn die Antwort von bool true ist, printen wir den ganzen token mit word delete.
		}
		i++;
	}while(i <= strlen(buffer));// i muss keliner als die länge des wortes ( buffer ) sein.
    printf("\n");
	
    free_tree(tree);
    return 0;
}
