#include "parser.h"

#include <string_view>

/**
 * Helper function of parseExpression.
 * Does the parsing of a function expression in prefix notation.
 */
std::unique_ptr<Calculator> parseExpressionRecursive(std::string_view& expr)
{
    char op = expr.at(0);
    expr.remove_prefix(1);
	

	
    switch (op)
    {
  
    case '~':
    {
		
        auto right = parseExpressionRecursive(expr);
        return std::make_unique<NotCalculator>(std::move(right));
		
    }
	case '&':
	{
		auto left = parseExpressionRecursive(expr);
		auto right = parseExpressionRecursive(expr);
		
		
		
		return std::make_unique<AndCalc>(std::move(left), std::move(right));
		
	}
	case '|':
	{

		auto left = parseExpressionRecursive(expr);
		auto right = parseExpressionRecursive(expr);
	
		return std::make_unique<OrCalc>(std::move(left), std::move(right));
	}
    default:
        return std::make_unique<LeafCalculator>(static_cast<size_t>(std::stoul(std::string(1, op))));
    }
}

/**
 * Parses a boolean function expression in prefix notation.
 * Returns a unique_ptr to a new Calculator for the function expression.
 */
std::unique_ptr<Calculator> parseExpression(const std::string& expr)
{
    std::string_view exprView(expr);
    return parseExpressionRecursive(exprView);
}

