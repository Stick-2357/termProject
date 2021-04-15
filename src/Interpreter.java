package src;

import java.util.*;

/*
A Grammar for scheme application with integers and arithmetic operators +, *
<s> -> <expr> | <define>
<define> -> ( define <id> <expr> )
<expr> -> ( + <operands> ) | ( * <operands> ) | <id>
<operands> -> <expr> <operands> | <id>
<id> -> <integer>  | <var>
 */

public class Interpreter {
    static boolean debug = false;
    static Scanner scan;
    static HashMap<String, Integer> variables = new HashMap<>();

    static boolean interpret(String s) {
        // <S> -> <expr> | <define>
        ArrayList<String> tokens = Tokenizer.tokenize(s);
        return (
                define(tokens, 0, tokens.size() - 1) ||
                        evaluate(tokens, 0, tokens.size() - 1)
        );
    }

    static boolean define(ArrayList<String> tokens, int start, int end) {
        // add your code
        return false;
    }

    static boolean evaluate(ArrayList<String> tokens, int start, int end) {
        Integer r = evalExpr(tokens, 0, tokens.size() - 1);
        if (r == null) return false;
        System.out.println(r);
        return true;
    }

    static Integer evalExpr(ArrayList<String> tokens, int start, int end) {
        // add your code
        return Evaluator.evalExpr(tokens, start, end);
    }

    static ArrayList<Integer> getOperands(ArrayList<String> tokens, int start, int end) {
        // add your code
        return new ArrayList<>(List.of(0));
    }

    static Integer evalId(ArrayList<String> tokens, int start, int end) {
        // add your code
        return null;
    }

    static Integer evalInteger(ArrayList<String> tokens, int start, int end) {
        // add your code
        return null;
    }

    static Integer evalVar(ArrayList<String> tokens, int start, int end) {
        if (debug) System.out.println("evalVar(" + start + "," + end + ")");
        if (start != end) return null;
        String s = tokens.get(start);
        if (isIdentifier(s)) {
            Integer r = variables.get(s);
            if (r == null)
                System.out.println("Unknown symbol: " + s);
            else return r;
        }
        return null;
    }

    static boolean isIdentifier(String s) {
        return variables.containsKey(s);
    }

    public static void main(String[] args) {
        System.out.println("Eli and Alex Chez Scheme 0.9. Nothing much supported.");
        scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String userInput = scan.nextLine().trim();
            if (userInput.equals(""))
                continue;
            else if (userInput.equals("(quit)"))
                break;
            else if (userInput.equals("(local)"))
                System.out.println(variables);
            else if (!interpret(userInput))
                System.out.println("Syntax Error !!");
        }
        scan.close();
    }
}