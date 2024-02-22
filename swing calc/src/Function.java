import java.util.Stack;

public class Function {
    static double evaluateInfix(String infix) {
        Stack<Character> operators = new Stack<>();
        Stack<Double> operands = new Stack<>();
        boolean flag=false;
        String fun = null;
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            // Print debug information
            System.out.println("Current Char: " + c);
            System.out.println("Operators Stack: " + operators);
            System.out.println("Operands Stack: " + operands);

            if (Character.isDigit(c)) {
                StringBuilder number = new StringBuilder();
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    number.append(infix.charAt(i));
                    i++;
                }
                operands.push(Double.parseDouble(number.toString()));
                i--; // Adjust the index since it was incremented after the loop// Convert char to double

            } else if (Character.isLetter(c)) {
                // Handle functions (exp, sqrt, sin, cos, tan)
                String function = readFunction(infix, i);
                //System.out.println("HIII="+function);
                if (function.equals("sin")==true||function.equals("cos")==true||function.equals("tan")==true)
                {
                    //operands.push((double) (9));
                    flag=true;
                    fun=function;
                    i += function.length() - 1;
                    continue;
                }
                double argument = operands.pop();
                double result = evaluateFunction(function, argument);
                operands.push(result);

                // Update the index to skip the function name in the next iteration
                i += function.length() - 1;

            } else if (c == '(') {
                operators.push(c);

            } else if (c == ')') {
                if (flag==true)
                {
                    double argument = operands.pop();
                    double result = evaluateFunction(fun, argument);
                    operands.push(result);
                }
                while (!operators.isEmpty() && operators.peek() != '(') {
                    processOperation(operators, operands);

                }
                operators.pop();

            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    processOperation(operators, operands);
                }
                operators.push(c);
            }

        }

        while (!operators.isEmpty()) {
            processOperation(operators, operands);
        }

        return operands.pop();
    }

    static void processOperation(Stack<Character> operators, Stack<Double> operands) {
        System.out.println("Processing Operation");
        System.out.println("Operators Stack: " + operators);
        System.out.println("Operands Stack: " + operands);

        char operator = operators.pop();
        if (operator == '(') {
            return;
        }

        if (operands.isEmpty()) {
            throw new IllegalArgumentException("Not enough operands for operator: " + operator);
        }

        double operand2 = operands.pop();
        if (operands.isEmpty()) {
            throw new IllegalArgumentException("Not enough operands for operator: " + operator);
        }

        double operand1 = operands.pop();
        double result = performOperation(operator, operand1, operand2);
        operands.push(result);

        System.out.println("After Processing Operation");
        System.out.println("Operators Stack: " + operators);
        System.out.println("Operands Stack: " + operands);
    }

    static double performOperation(char operator, double operand1, double operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            case '^':
                return Math.pow(operand1, operand2);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    static double evaluateFunction(String function, double argument) {
        switch (function.toLowerCase()) {
            case "exp":
                return Math.exp(argument);
            case "sqrt":
                return Math.sqrt(argument);
            case "sin":
                return Math.sin(toRadians(argument));
            case "cos":
                return Math.cos(toRadians(argument));
            case "tan":
                return Math.tan(toRadians(argument));
            // You can add more functions here as needed
            default:
                throw new IllegalArgumentException("Invalid function: " + function);
        }
    }

    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    static String readFunction(String infix, int startIndex) {
        StringBuilder function = new StringBuilder();
        int index = startIndex;
        while (index < infix.length() && Character.isLetter(infix.charAt(index))) {
            function.append(infix.charAt(index));
            index++;
        }
        return function.toString();
    }

    static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }

    public static double getResult(String infixExpression) {
        double result = evaluateInfix(infixExpression);

        System.out.println("Infix Expression: " + infixExpression);
        System.out.println("Result after evaluation: " + result);
        return result;
    }
}
