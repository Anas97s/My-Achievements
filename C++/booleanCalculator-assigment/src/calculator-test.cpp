#include "calculator-test.h"

#include <cassert>
#include <fstream>
#include <sstream>
#include <string>

#include "calculator.h"
#include "parser.h"

/**
 * Tests the function expression parser and the function expression print.
 */
void testExpression(const std::string& expr)
{
    std::unique_ptr<Calculator> cal = parseExpression(expr);
    std::cout << "Parsed: " << expr << std::endl;
    std::cout << "Printed:" << *cal << std::endl;
    std::stringstream s;
    s << *cal;
    assert(expr == s.str());
}

/**
 * Function for testing the Calculator classes.
 * Can be modified for any needs.
 */
void testCalculator()
{
    // Basic expression parse test:
    {
        testExpression("~0");
        testExpression("&01");
        testExpression("|01");
        testExpression("&|01|~0~1");
        testExpression("|&01|&12&02");
        testExpression("|&~2|&~01&~10&2~|&~01&~10");
    }

    // Calculation test:
    {
        auto cal = parseExpression("|&~2|&~01&~10&2~|&~01&~10");
        assert(cal->calculate({ false, false, false }) == false);
        assert(cal->calculate({ false, false, true }) == true);
        assert(cal->calculate({ false, true, false }) == true);
        assert(cal->calculate({ false, true, true }) == false);
        assert(cal->calculate({ true, true, true }) == true);
    }

    // Calculation test:
    {
        auto cal = parseExpression("|&~01&~10");
        assert(cal->calculate({ false, false }) == false);
        assert(cal->calculate({ true,  true  }) == false);
        assert(cal->calculate({ false, true  }) == true);
        assert(cal->calculate({ true,  false }) == true);

    }

    // Infix and Postfix test:
    {
        auto cal = parseExpression("|&~2|&~01&~10&2~|&~01&~10");

        std::stringstream infix;
        cal->print(infix, Calculator::TraversalType::Infix);
        std::cout << "Infix: " << infix.str() << std::endl;
        assert(infix.str() == "((~(2))&(((~(0))&(1))|((~(1))&(0))))|((2)&(~(((~(0))&(1))|((~(1))&(0)))))");

        std::stringstream postfix;
        cal->print(postfix, Calculator::TraversalType::Postfix);
        std::cout << "Postfix: " << postfix.str() << std::endl;
        assert(postfix.str() == "2~0~1&1~0&|&20~1&1~0&|~&|");
    }

    // CalculatorPrinter test:
    {
        auto cal = parseExpression("|&~2|&~01&~10&2~|&~01&~10");

        std::stringstream prefix;
        prefix << CalculatorPrinter(*cal, Calculator::TraversalType::Prefix);
        assert(prefix.str() == "|&~2|&~01&~10&2~|&~01&~10");

        std::stringstream infix;
        infix << CalculatorPrinter(*cal, Calculator::TraversalType::Infix);
        assert(infix.str() == "((~(2))&(((~(0))&(1))|((~(1))&(0))))|((2)&(~(((~(0))&(1))|((~(1))&(0)))))");
        
        std::stringstream postfix;
        postfix << CalculatorPrinter(*cal, Calculator::TraversalType::Postfix);
        assert(postfix.str() == "2~0~1&1~0&|&20~1&1~0&|~&|");
        std::cout << "BonusInfix: " << CalculatorPrinter(*cal, Calculator::TraversalType::BonusInfix) << "\n";
    }
}

