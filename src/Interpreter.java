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

public class Interpreter extends Evaluator {
    boolean debug = false;
    static Scanner scan;
    static HashMap<String, Integer> variables = new HashMap<>();

    boolean interpret(String s) {
        // <S> -> <expr> | <define>
        List<String> tokens = Tokenizer.tokenize(s);
        return (
                define(tokens, 0, tokens.size() - 1) ||
                        evaluate(tokens, 0, tokens.size() - 1)
        );
    }

    boolean define(List<String> tokens, int start, int end) {
        // "(", "define", "key", "value", ")"
        if (tokens.get(start).equals("(")
                && tokens.get(start + 1).equals("define")
                && isValidKey(tokens.get(start + 2))
                && isId(tokens.get(start + 3))
                && tokens.get(start + 4).equals(")")
                && end - start == 4) {
            variables.put(tokens.get(start + 2), Integer.parseInt(tokens.get(start + 3)));
            return true;
        }
        return false;
    }

    boolean isValidKey(String s) {
        return !(s.equals("(") || s.equals(")") || isId(s));
    }

    boolean evaluate(List<String> tokens, int start, int end) {
        if (isIdentifier(tokens.get(start))) {
            System.out.println(evalVar(tokens, start, end));
            return true;
        }
        Number r = evalExpr(tokens, 0, tokens.size() - 1);
        if (r == null) return false;
        System.out.println(r);
        return true;
    }

    @Override
    List<Number> getOperands(List<String> tokens, int start, int end) {
        List<Number> operands = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            String token = tokens.get(i);
            if (token.equals("(")) {
                List<String> subList = tokens.subList(i, tokens.size() - 1);
                int iOfEnding = subList.indexOf(")") + i;
                operands.add(evalExpr(tokens, i, iOfEnding));
                i = iOfEnding;
            } else if (isIdentifier(token)) {
                operands.add(evalVar(tokens, i, i));
            } else if (SimpleParser.isInteger(tokens, i, i)) {
                operands.add(Integer.parseInt(token));
            } else if (SimpleParser.isFloat(tokens, i, i)) {
                operands.add(Float.parseFloat(token));
            }
        }

        return operands;
    }

    Integer evalVar(List<String> tokens, int start, int end) {
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

    boolean isIdentifier(String s) {
        return variables.containsKey(s);
    }

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
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
            else if (!interpreter.interpret(userInput))
                System.out.println("Syntax Error !!");
        }
        scan.close();
    }
}