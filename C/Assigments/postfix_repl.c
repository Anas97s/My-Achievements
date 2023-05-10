/*
	make postfix_repl && ./postfix_repl
*/

#include "base.h"

/*
    Macro for testing if a condition is valid for all i's in the range of [0, length)].
    e.g.
    int length = 10;
    int x[length];
    
    ensure("Array initialized with 0's", forall_i(length, x[i] == 0));

*/
#define forall_i(length, condition) ({\
   bool result = true;\
   for (int i = 0; i < length; i++) { if (!(condition)) { result = false; break; } }\
   result;\
})


#define STACK_SIZE  10

typedef enum {
	OPERATION, VALUE, EMPTY
} Tag;



typedef struct{
	Tag tag;
	
	union{
		int value;
		char operation;
	};
} StackElement;

typedef struct{
	StackElement elements[STACK_SIZE];
	int stack_pointer;
    int stack_height;
} Stack;

//checks if char c belongs to an operation
bool is_operation(char c){
	return c == '+' || c == '/' || c == '-' || c == '*';
}

//checks wheter c is a digit
bool is_digit(char c){
	return c >= '0' && c <= '9';
}

//todo:
StackElement make_stack_element(Tag tag){
	StackElement ele;
	ele.tag = tag;
	return ele;
} 
//todo:
StackElement make_value(int value){
	StackElement ele;
	ele.tag = VALUE;
	ele.value = value;
	return ele;
}
//todo:
StackElement make_operation(char operation){
	StackElement ele; 
	ele.tag = OPERATION;
	ele.operation = operation;
	return ele;
}


bool is_empty(Stack* stack){
	return stack->stack_pointer < 0;
}

//todo:
void clear_stack(Stack* stack){
	stack->stack_pointer = -1;
	for(int i = 0; i < STACK_SIZE; i++){
		stack->elements[i].tag = EMPTY;
	}

	
	
}

void push(StackElement element, Stack* stack){
    require_not_null(stack);
	require("the stack is not full", stack->stack_pointer < ( stack->stack_height - 1 ));
	stack->stack_pointer++;
	stack->elements[stack->stack_pointer] = element;
	
}

StackElement pop(Stack* stack){
    require_not_null(stack);
    require("non empty stack", stack->stack_pointer >= 0);
    StackElement ele = stack->elements[stack->stack_pointer ];
    stack->elements[stack->stack_pointer] = make_stack_element(EMPTY);
	stack->stack_pointer--;
	return ele;
}

//todo:
void print_stack_element(StackElement ele){
	if(ele.tag == VALUE){
		printf("[%d]", ele.value);
	} else if ( ele.tag == OPERATION){
		printf("[%c]", ele.operation);
	} else if ( ele.tag == EMPTY){
		printf("[ ]");
	}
}

//todo:
void print_stack(Stack* stack, int n){
	require_not_null(stack);
	require("n is in range", n <= stack->stack_height);
	for (int i = n - 1; i >= 0; i--){
		print_stack_element(stack->elements[i]);
		if(i == 0 || i == stack->stack_pointer){
			printf(" <- ");
		} 
		if (i == 0){
			printf("unterstes Element");
		} 
		if (i == stack->stack_pointer){
			printf("auf dieses Element zeigt der Stackpointer");
		} 
		if (i == 0 && i == stack->stack_pointer){
			printf("  ");
		}
		printf("\n");
	}
	
}

void compute(Stack* stack){
	require_not_null(stack);
	require("not empty", stack->stack_pointer >= 0);
	require("values are enough", stack->stack_pointer >= 2);
	require("operators are vaild", stack->elements[stack->stack_pointer - 2].tag == VALUE && stack->elements[stack->stack_pointer - 1].tag == VALUE);
	if(stack->elements[stack->stack_pointer].operation == '*'){
		stack->elements[stack->stack_pointer - 2].value *= stack->elements[stack->stack_pointer - 1].value;
		pop(stack);
	} else if(stack->elements[stack->stack_pointer].operation == '/'){
		stack->elements[stack->stack_pointer - 2].value /= stack->elements[stack->stack_pointer - 1].value;
		pop(stack);
	} else if (stack->elements[stack->stack_pointer].operation == '+'){
		stack->elements[stack->stack_pointer - 2].value += stack->elements[stack->stack_pointer - 1].value;
		pop(stack);
	} else if (stack->elements[stack->stack_pointer].operation == '-'){
		stack->elements[stack->stack_pointer - 2].value -= stack->elements[stack->stack_pointer - 1].value;
		pop(stack);
	}
	pop(stack);
}

int main (void){
	printsln("Please enter a number or operator. If you want to exit the program plese enter 'q'.");
	Stack stack;
    stack.stack_height = STACK_SIZE;
	clear_stack(&stack);
	int input_size = 128;	
	char input[input_size];
	for(int i = 0; i < input_size; i++){
		input[i] = '\0';
	}
	int input_index = 0;
	int c;
	while((c = getchar()) != 'q'){
		if(c == '\n'){
			int nummer = 0;
			for(int i = 0; i< input_index; i++){
                char c = input[i];
				if(is_operation(c)){
					push(make_operation(c), &stack);
					print_stack(&stack, stack.stack_pointer + 1);
					printf("\n");
					compute(&stack);
					print_stack(&stack, stack.stack_pointer + 1);
					printf("\n");
				}
				if (is_digit(c)) {
					nummer *= 10;
					nummer += c - '0';
				if(i == input_index - 1 || !is_digit(input[i + 1])){
					push(make_value(nummer), &stack);
					print_stack(&stack, stack.stack_pointer + 1);
					nummer = 0;
				}
				}
			}
			for(int i = 0; i < input_size; i++){
				input[i] = '\0';
			}
			input_index = 0;
		} else {
			input[input_index] = c;
			input_index++;
		}
	}
	return 0;
}
/* e) pop und push schützen die Funktionen vor falschen Eingaben und Fehlern im Programm. Sowohl pop als auch push enthalten pointers als Parameter. Eingabeparameter von pop ist stack und Ausgabeparameter ele. 
      Eingabeparameter von push ist element & stack und Ausgabeparameter void. 
	  Um ungültige Zustände des Stacks zu verhindern, sollten lieber Preconditions genutzt werden und keine if/else Verzweigungen. Perconditions machen die Funktionen übersichtlicher. 
	  if/else verzweigungen würden eine sehr lange Funktion bedeuten, die schwer lesbar wäre. Unter Umständen verzettelt man sich dann auch noch und schlussendlich gibt es daraufhin Fehler in der Funktion. 
*/

