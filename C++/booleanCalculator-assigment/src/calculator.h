#ifndef CALCULATOR_H_
#define CALCULATOR_H_

#include <iostream>
#include <memory>
#include <string>
#include <vector>


/**
 * A Calculator instance can evaluate a boolean function.
 */
class Calculator
{
public:
    enum class TraversalType
    {
        Prefix, Infix, Postfix, BonusInfix
    };

    virtual bool calculate(const std::vector<bool>&) const = 0;
    virtual void print(std::ostream&, TraversalType = TraversalType::Prefix) const = 0;
    virtual ~Calculator() = default;
};



/**
 * The following function overloads the "<<" operator.
 * This allows performing a stream out operation.
 * It prints the given Calculator's function expression to os and returns the os.
 */
std::ostream& operator<<(std::ostream&, const Calculator&);

class CalculatorPrinter
{
public:
    CalculatorPrinter(const Calculator&, Calculator::TraversalType);
    friend std::ostream& operator<<(std::ostream&, const CalculatorPrinter&);
private:
    const Calculator* calculator;
    Calculator::TraversalType type;
};

/**
 * Calculator for a given boolean value.
 * The value is taken out of the given input vector of boolean values.
 */
class LeafCalculator: public Calculator
{
public:
    LeafCalculator(size_t);
private:
    size_t idx;

    bool calculate(const std::vector<bool>&) const override;
    void print(std::ostream&, TraversalType) const override;
};

/**
 * Base class of a Calculator that evaluates a unary operation.
 */
class UnaryCalculator: public Calculator
{
public:
    UnaryCalculator(std::unique_ptr<Calculator>, std::string);
	
protected:
	 std::unique_ptr<Calculator> inner;
private:
	std::string op;
    bool calculate(const std::vector<bool>&) const override;
    void print(std::ostream&, TraversalType) const override;

    virtual bool operate(bool) const = 0;
};

/**
 * Calculator for the unary NOT operation.
 */
class NotCalculator: public UnaryCalculator
{
public:
    NotCalculator(std::unique_ptr<Calculator>);

private:
    // TODO: Add missing overrides here
	void print(std::ostream&, TraversalType) const override;
	bool operate(bool) const override;
	
};

// TODO: Define classes for handling AND and OR

class Calculators : public Calculator{
	public:
		Calculators(std::unique_ptr<Calculator>, std::unique_ptr<Calculator>);
		
	protected:
		std::unique_ptr<Calculator> left;
		std::unique_ptr<Calculator> right;
		
	private:
		bool calculate(const std::vector<bool>&) const override;
		void print(std::ostream&, TraversalType) const = 0;
		virtual bool operate(bool, bool) const = 0;
};

class OrCalc : public Calculators{
	public:
		OrCalc(std::unique_ptr<Calculator>, std::unique_ptr<Calculator>);
	
	private:
	
		void print(std::ostream&, TraversalType) const override;
		bool operate(bool, bool) const override;
};

class AndCalc : public Calculators{
	public:
		AndCalc(std::unique_ptr<Calculator>, std::unique_ptr<Calculator>);
		
	private:
		void print(std::ostream&, TraversalType) const override;
		bool operate(bool, bool) const override;
};

#endif /* CALCULATOR_H_ */

