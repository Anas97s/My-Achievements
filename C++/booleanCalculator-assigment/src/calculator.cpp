#include "calculator.h"

/**
 * Print Calculator to a stream.
 */
std::ostream& operator<<(std::ostream& os, const Calculator& calc)
{
    calc.print(os);
    return os;
}


CalculatorPrinter::CalculatorPrinter(const Calculator& calc, Calculator::TraversalType t) :
    calculator(&calc), type(t)
{}

std::ostream& operator<<(std::ostream& os, const CalculatorPrinter& cp)
{
    cp.calculator->print(os, cp.type);
    return os;
}


LeafCalculator::LeafCalculator(size_t i) : idx(i) {}

bool LeafCalculator::calculate(const std::vector<bool>& input) const
{
    return input[this->idx];
}

void LeafCalculator::print(std::ostream& os, TraversalType) const
{
    os << this->idx;
}


UnaryCalculator::UnaryCalculator(std::unique_ptr<Calculator> in, std::string opSymbol) :
    inner(std::move(in)), op(std::move(opSymbol))
{}

bool UnaryCalculator::calculate(const std::vector<bool>& input) const
{
    return this->operate(this->inner->calculate(input));
}

void UnaryCalculator::print(std::ostream& os, TraversalType type) const
{
  
	if(type == TraversalType::Prefix){
		os << op  << *inner;
	}else if(type == TraversalType::Postfix){
		os << *inner << op;
	}else if(type == TraersalType::Infix){
		os << "(" << op << "(" << *inner << "))";
	}
	
	
}

NotCalculator::NotCalculator(std::unique_ptr<Calculator> inner) :
    UnaryCalculator(std::move(inner), "~")
{}


bool NotCalculator::operate(bool x) const{
	return !x;
}

void NotCalculator::print(std::ostream &os, TraversalType traversalType) const{
	

	if(traversalType == TraversalType::Prefix){
		os << "~" << *inner;
	}else if(traversalType == TraversalType::Postfix){
		os << *inner << "~";
	}else if(traversalType == TraversalType::Infix){
		os << "(" << "~" << "(" << *inner << "))";
	}
	
}

Calculators::Calculators(std::unique_ptr<Calculator> left, std::unique_ptr<Calculator> right) : left(std::move(left)), right(std::move(right)){}

bool Calculators::calculate(const std::vector<bool>& input) const {
	return this->operate(this->left->calculate(input), this->right->calculate(input));
}

OrCalc::OrCalc(std::unique_ptr<Calculator> left, std::unique_ptr<Calculator> right) : Calculators(std::move(left), std::move(right)){}

bool OrCalc::operate(bool left, bool right) const{
	
	if(left || right){
		return true;
	}
	
	return false;
}

void OrCalc::print(std::ostream &os, TraversalType traversalType) const{
	if(traversalType == TraversalType::Prefix){
		os << "|" << *left << *right;
	}else if(traversalType == TraversalType::Postfix){
		os << *left << *right << "|";
	}else if(traversalType == TraversalType::Infix){
		os << "((" << *left << ")" << "|" << "(" << *right << "))";
	}
}

AndCalc::AndCalc(std::unique_ptr<Calculator> left, std::unique_ptr<Calculator> right) : Calculators(std::move(left), std::move(right)){}

bool AndCalc::operate(bool left, bool right) const{
	if(left && right){
		return true;
	}
	
	return false;
}

void AndCalc::print(std::ostream &os, TraversalType traversalType) const{
	
	
	if(traversalType == TraversalType::Prefix){
		os << "&" << *left << *right;
	}else if(traversalType == TraversalType::Postfix){
		os << *left << *right << "&";
	}else if(traversalType == TraversalType::Infix){
		os <<  "((" << *left << ")" << "&" << "(" << *right << "))";
	}
}

