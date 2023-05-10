enum Operator {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

//Should not be instanciated with right_==0 and op_==DIVIDE
class Expression {
    double left_, right_;
    Operator op_;

    Expression(double left, double right, Operator op){
        this.left_  = left;
        this.right_ = right;
        this.op_    = op;
    }

    double evaluate() {
        switch (this.op_) {
            case ADD: //1) Operator. not needed in switch-case
                return this.left_ + this.right_;
            case SUBTRACT://2) Operator. not needed in switch-case
                return this.left_ - this.right_;
            case MULTIPLY: ////3) Operator. not needed in switch-case
                return this.left_ * this.right_;
            case DIVIDE: ////4) Operator. not needed in switch-case
                return this.left_ / this.right_;
            default:
                return 0.0;
        }
    }
}

class Debug {

    public static void main(String[] args) {
        Operator[] ops = new Operator[4]; //5) size of array it 4 not 5!
        ops[0] = Operator.SUBTRACT; //6) because our for-loop in next step is starting with a 0, so we cant have DIVIDE in 1st step
        ops[1] = Operator.DIVIDE;
        ops[2] = Operator.MULTIPLY;
        ops[3] = Operator.ADD;

        Expression[] exp = new Expression[ops.length];
        for (int i = 0; i < ops.length; ++i) {
            exp[i] = new Expression(i + 1, i, ops[i]);
        }

        for (int i = 0; i < ops.length; ++i) {
            System.out.println(exp[i].evaluate());
        }
    }
}