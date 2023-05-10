/*
Compile: make matrix
Run: ./matrix
make matrix && ./matrix
*/

#include "base.h" 

struct Matrix {
    int rows; // number of rows
    int cols; // number of columns
    double** data; // a pointer to an array of n_rows pointers to rows; a row is an array of n_cols doubles 
};
typedef struct Matrix Matrix;

/**
Creates a zero-initialized matrix of rows and columns matrix.
@param[in] n_rows number of rows
@param[in] n_cols number of columns
@return a pointer to an array of n_rows pointers to rows; a row is an array of n_cols doubles 
*/
Matrix* new_matrix(int n_rows, int n_cols) {
    // todo: implement
	require("vaild matrix", n_cols > 0 && n_rows >0);
	Matrix* m = xmalloc(sizeof(Matrix));
	m->rows = n_rows;
	m->cols = n_cols;
	m->data = xcalloc(n_rows, sizeof(double*));
	for(int x = 0; x < n_rows; x++){
		m->data[x] = xcalloc(n_cols, sizeof(double)); 
	}
    return m;
}

/**
Creates a zero-initialized matrix of rows and columns matrix.
@param[in] data an array of doubles, ordered row-wise
@param[in] n_rows number of rows
@param[in] n_cols number of columns
@return a pointer to an array of n_rows pointers to rows; a row is an array of n_cols doubles 
*/
Matrix* copy_matrix(double* data, int n_rows, int n_cols) {
    // todo: implement
	require("vaild matrix", n_cols > 0 && n_rows > 0);
	Matrix* m = new_matrix(n_rows, n_cols);
		for(int x = 0; x < n_rows; x++){
			for (int y = 0; y < n_cols; y++){
				m->data[x][y] = data[n_cols * x + y];
			}
		}
	return m;
}

/**
Print a matrix.
@param[in] m the matrix to print
*/
void print_matrix(Matrix* m) {
    // todo: implement
	require("vaild matrix", m >= 0);
	for(int x = 0; x < m->rows; x++){
		for(int y = 0; y < m->cols; y++){
			if (y != m->cols - 1){
				printf("%.2f\t", m->data[x][y]);
			} else {
				printf("%.2f\t", m->data[x][y]);
				printf("\n");
			}			
		}
	}
}

/**
Transpose a matrix.
@param[in] a the first operand
@return a new matrix whose elements are transposed
*/
Matrix* transpose_matrix(/*in*/ Matrix* a){
    // todo: implement
	require_not_null(a);
	Matrix* m = new_matrix(a->cols, a->rows);
	for(int y = 0; y < m->cols; y++){
		for (int x = 0; x < m->rows; x++){
			m->data[x][y] = a->data[y][x];
		}
	}
	ensure("matrix are able to be transposed", m->rows == a->cols);
    return m;
}

/**
Multiplies two matrices.
@param[in] a the first operand
@param[in] b the second operand
@return a new matrix that is the result of the multiplication of a * b
*/
Matrix* mul_matrices(/*in*/ Matrix* a, /*in*/ Matrix* b) {
    // todo: implement
	require("vaild multiplication", a->rows == b->cols);
	Matrix* m = new_matrix(a->rows, b->cols);
	double summe;
	for (int x = 0; x < m->rows; x++){
		for ( int y = 0; y < m->cols; y++) {
			summe = 0;
			for ( int z = 0; z < a->cols; z++) {
				summe += a->data[x][z] * b->data[z][y];
			}
			m->data[x][y] = summe;
		}
	}
	ensure("m is not Null", m != NULL);
    return m;
}

/**
Free a matrix.
@param[in] m the matrix to free
*/
void free_matrix(Matrix* m) {
    // todo: implement
	require_not_null(m);
	for ( int x = 0; x < m->rows; x++){
		free(m->data[x]);
	}
	free(m->data);
	free(m);
}

void matrix_test(void) {
    printf("Create empty matrix: \n");
    
    Matrix* m0 = new_matrix(7, 1);
    print_matrix(m0);
    
    printf("Copy matrix data from double[]\n");
    
    double a[] = { 
        1, 2, 3, 
        4, 5, 6, 
        7, 8, 9 };
    Matrix* m1 = copy_matrix(a, 3, 3);
    printf("m1:\n");
    print_matrix(m1);
    

    
    double a2[] = { 
        1, 2, 3, 3.5,
        4, 5, 6, 7};
    Matrix* m2 = copy_matrix(a2, 2, 4);
    printf("m2:\n");
    print_matrix(m2);
    
    
    
    printf("Transpose: m2\n");
    Matrix* m2t = transpose_matrix(m2);
    print_matrix(m2t);


    double a3[] = { 
        1, 2, 
        3, 4, 
        5, 6,
        7, 8};
    Matrix* m3 = copy_matrix(a3, 4, 2);
    printf("m3:\n");
    print_matrix(m3);

    printf("Multiplie m3 * m3t\n");
    Matrix* m3t = transpose_matrix(m3);
    printf("m3t:\n");
    print_matrix(m3t);
    
    Matrix* m4 = mul_matrices(m3, m3t);
    printf("m4:\n");
    print_matrix(m4);
    
    free_matrix(m0);
    free_matrix(m1);
    free_matrix(m2);
    free_matrix(m2t);
    free_matrix(m3);
    free_matrix(m3t);
    free_matrix(m4);
    
}

int main(void) {
    report_memory_leaks(true);
    matrix_test();
    return 0;
}
