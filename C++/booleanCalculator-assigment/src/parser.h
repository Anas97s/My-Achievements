#ifndef PARSER_H
#define PARSER_H

#include <memory>
#include <string>

#include "calculator.h"

std::unique_ptr<Calculator> parseExpression(const std::string&);

#endif // PARSER_H

