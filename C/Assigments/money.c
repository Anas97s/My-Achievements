/*
Compile: make money
Run: ./money
make money && ./money
*/

#include "base.h"


const double BITCOIN_USD = 4273.38; // 1 Bitcoin = 4273.38 USD
const double EURO_USD = 1.14; 		// 1 EURO = 1.14 USD
const double DKK_USD = 0.15; 		// 1 Dansk Krone (DKK) = 0.15 USD

enum Currency { BITCOIN, DOLLAR, EURO, DANSK_KRONE }; 
typedef enum Currency Currency;

struct Money { double amount; Currency currency; }; 
typedef struct Money Money;

// double, Currency -> Money
// Initialize a Money structure.
Money make_money(double amount, Currency currency) {
    Money m = { amount, currency };
    return m;
}

// int Money, Money, double -> bool
// Test wheather the actual value of the money is within +/- tolerance of the expected value of the money.
bool test_value(int line, Money a, Money e, double tolerance) {
    bool x = base_test_within_d(__FILE__, line, a.amount, e.amount, tolerance);
    bool u = base_test_equal_i(__FILE__, line, a.currency, e.currency);
    return x && u;
}

void print_money(Money m);

void print_money_test() {
    print_money(make_money(1234, DOLLAR));
    print_money(make_money(1.234, BITCOIN));
    print_money(make_money(1.34, EURO));
    print_money(make_money(27.50, DANSK_KRONE));
}

// Money -> void
// Print the ammount of money.
void print_money(Money m) {
	if (m.currency == BITCOIN) {
		printd(m.amount);
		printsln (" Bitcoin");
	} else if (m.currency == EURO) {
		printd(m.amount);
		printsln (" Euro");
	} else if (m.currency == DANSK_KRONE) {
		printd(m.amount);
		printsln (" DKK"); 
	} else if (m.currency == DOLLAR) {
		printd(m.amount);
		printsln (" $"); 
	}		
}

Money to_currency(Money m, Currency currency);

void to_currency_test(void) {
    test_value(__LINE__, to_currency(make_money(1, BITCOIN), DOLLAR), make_money(4273.38, DOLLAR), 1e-3);
	test_value(__LINE__, to_currency(make_money(1, EURO), DOLLAR), make_money(1.14, DOLLAR), 1e-3);
	test_value(__LINE__, to_currency(make_money(1, DANSK_KRONE), DOLLAR), make_money(0.15, DOLLAR), 1e-3);
	test_value(__LINE__, to_currency(make_money(4273.38, DOLLAR), BITCOIN), make_money(1, BITCOIN), 1e-3);
	test_value(__LINE__, to_currency(make_money(1.14, DOLLAR), EURO), make_money(1, EURO), 1e-3);
	test_value(__LINE__, to_currency(make_money(0.15, DOLLAR), DANSK_KRONE), make_money(1, DANSK_KRONE), 1e-3);
    // todo: add tests (at least 5)
	
    
}

// Money, Currency -> Money
// Convert an amount of money to the given target currency.
Money to_currency(Money m, Currency target_currency) {
	switch (m.currency) {
		case DOLLAR: 
		        m.currency = target_currency;
				if (target_currency == BITCOIN) {m.amount = m.amount / BITCOIN_USD;} 
				if (target_currency == EURO) {m.amount = m.amount / EURO_USD;}
				if (target_currency == DANSK_KRONE) {m.amount = m.amount /DKK_USD;}
				break;
		case BITCOIN:
		        m.currency = target_currency;
				if (target_currency == DOLLAR) {m.amount = m.amount * BITCOIN_USD;}
				if (target_currency == EURO) {m.amount = (m.amount / BITCOIN_USD) * EURO_USD;}
                if (target_currency == DANSK_KRONE) {m.amount = (m.amount / BITCOIN_USD) * DKK_USD;}
                break;
        case EURO:
                m.currency = target_currency;
                if (target_currency == DOLLAR) {m.amount = m.amount * EURO_USD;}
				if (target_currency == BITCOIN) {m.amount = (m.amount / EURO_USD) * BITCOIN_USD;}
                if (target_currency == DANSK_KRONE) {m.amount = (m.amount / EURO_USD) * DKK_USD;}
                break;	
        case DANSK_KRONE:	
		        m.currency = target_currency;
                if (target_currency == DOLLAR) {m.amount = m.amount * DKK_USD;}
				if (target_currency == EURO) {m.amount = (m.amount / DKK_USD) * EURO_USD;}
                if (target_currency == BITCOIN) {m.amount = (m.amount / DKK_USD) * BITCOIN_USD;}
                break;		
	}
    return m;
}

int compare(Money m, Money v);

void compare_test(void) {
	test_equal_i(compare(make_money(1000, DOLLAR), make_money(877.19, EURO)), 0);
    test_equal_i(compare(make_money(4273.38, DOLLAR), make_money(1, BITCOIN)), 0);
	test_equal_i(compare(make_money(433, EURO), make_money(0.22, BITCOIN)), -1);
    test_equal_i(compare(make_money(433, EURO), make_money(495, DOLLAR)), -1);
	test_equal_i(compare(make_money(1000, DOLLAR), make_money(6500, DANSK_KRONE)), 1);
}
    
// Money, Money -> int
// Compares two amounts of money. Returns 0 if the two amount of money are equal, 
// -1 if w is smaller than v and +1 otherwise.
int compare(Money m, Money v) {
	m = to_currency(m, DOLLAR);
	v = to_currency(v, DOLLAR);
	int x = m.amount + 1e-2;
	int y = v.amount + 1e-2;
    if (x == y) {
		return 0;
	} else if (x < y) {
		return -1;
	} else if (x > y) {
		return 1;
	}
	return 0;
}

int main(void) {
	printf("%.2f\n", 22.3);
    print_money_test();
    to_currency_test();
    compare_test();
    return 0;
}

