package src;

import java.util.*;

/*
 * Only +, * operators with integers
 * So all values are still integers
 */
public class Evaluator {

    Number evalS(String expression) {
        // return true if s is a valid expression. expression could be nested
        List<String> tokens = Tokenizer.tokenize(expression);
        return evalExpr(tokens, 0, tokens.size() - 1);
    }

    Number evalExpr(List<String> tokens, int start, int end) {
        // <expr> -> ( + <operands> ) | ( * <operands> ) | <id>
        if (tokens.get(start).equals("(") && tokens.get(end).equals(")")) {
            String operator = tokens.get(start + 1);
            List<Number> operands = getOperands(tokens, start + 2, end - 1);
            switch (operator) {
                case "+":
                    return sum(operands);
                case "*":
                    return mult(operands);
                case "/":
                    return div(operands);
                default:
                    return null;
            }
        } else if (start == end) {
            return evalId(tokens.get(start));
        }
        return null;
    }

    List<Number> getOperands(List<String> tokens, int start, int end) {
        //<operands> -> <expr> <operands> | <id>
        List<Number> operands = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            String token = tokens.get(i);
            if (token.equals("(")) {
                List<String> subList = tokens.subList(i, tokens.size() - 1);
                int iOfEnding = subList.indexOf(")") + i;
                operands.add(evalExpr(tokens, i, iOfEnding));
                i = iOfEnding;
            } else if (isId(token)) {
                operands.add(evalId(token));
            }
        }

        return operands;
    }

    protected boolean isId(String s) {
        return evalId(s) != null;
    }

    Number evalId(String token) {
        Integer i = evalInteger(token);
        if (i != null) {
            return i;
        }
        Float f = evalFloat(token);
        if (f != null) {
            return f;
        }
        Rational r = evalRational(token);
        if (r != null) {
            return r;
        }
        return null;
    }

    Float evalFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    Integer evalInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    Rational evalRational(String s) {
        try {
            return new Rational(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // helper functions
    public Number sum(List<Number> list) {
        float sum = 0;
        boolean floatCheck = false;
        boolean rationalCheck = false;
        Rational rationalSum = new Rational();
        for (Number n : list) {
            if (rationalCheck && !floatCheck) {
                if (n instanceof Integer) {
                    rationalSum = rationalSum.plus(new Rational(n.intValue(), 1));
                } else if (n instanceof Float) {
                    sum = rationalSum.floatValue();
                    sum += n.floatValue();
                    floatCheck = true;
                } else if (n instanceof Rational) {
                    rationalSum = rationalSum.plus((Rational) n);
                }
            } else {
                if (n instanceof Integer)
                    sum += n.intValue();
                else if (n instanceof Float) {
                    sum += n.floatValue();
                    floatCheck = true;
                } else if (n instanceof Rational) {
                    if (floatCheck) {
                        sum += n.floatValue();
                    } else {
                        rationalSum = new Rational(sum);
                        rationalSum = rationalSum.plus((Rational) n);
                        rationalCheck = true;
                    }
                }
            }

        }
        if (floatCheck) return sum;
        else if (rationalCheck) return rationalSum;
        else return (int) sum;
    }

    public Number mult(List<Number> list) {
        float total = 1;
        Rational rationalTotal = new Rational(1, 1);
        boolean floatCheck = false;
        boolean rationalCheck = false;
        for (Number i : list) {
            if (rationalCheck && !floatCheck) {
                if (i instanceof Integer) {
                    rationalTotal.times(new Rational(i.intValue(), 1));
                } else if (i instanceof Float) {
                    total = rationalTotal.floatValue();
                    total *= i.floatValue();
                    floatCheck = true;
                } else if (i instanceof Rational) {
                    rationalTotal.times((Rational) i);
                }
            } else {
                if (i instanceof Integer)
                    total *= i.intValue();
                else if (i instanceof Float) {
                    total *= i.floatValue();
                    floatCheck = true;
                } else if (i instanceof Rational) {
                    if (floatCheck) {
                        total *= i.floatValue();
                    } else {
                        rationalTotal = new Rational((int) total, 1);
                        rationalTotal.times((Rational) i);
                        rationalCheck = true;
                    }
                }
            }
        }
        if (floatCheck) return total;
        else if (rationalCheck) return rationalTotal;
        else return (int) total;
    }

    public Number div(List<Number> list) {
        float denom = 1;
        float total;
        Number numerator = list.get(0);
        boolean floatCheck = false;
        for (int i = 1; i <= list.size() - 1; i++) {
            if (list.get(i) instanceof Integer)
                denom *= list.get(i).intValue();
            else if (list.get(i) instanceof Float) {
                denom *= list.get(i).floatValue();
                floatCheck = true;
            }
        }
        if (numerator instanceof Integer) {
            total = numerator.intValue() / denom;
            if (floatCheck) return total;
            else return (int) total;
        } else if (numerator instanceof Float) {
            total = numerator.floatValue() / denom;
            return total;
        } else return null;
    }

    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();

//        System.out.println(evaluator.evalS("234")); // 234
//        System.out.println(evaluator.evalS("2.34")); // 2.34
//        System.out.println(evaluator.evalS("2/34")); // 2/34
//        System.out.println(evaluator.evalS("(+ 1 2/3)"));
        System.out.println(evaluator.evalS("(* 2 2.5 3/2)"));
//        System.out.println(evaluator.evalS("(+ 2 3)")); // 5
//        System.out.println(evaluator.evalS("(+ 2.5 3)")); // 5.5
//        System.out.println(evaluator.evalS("(* 2 3)")); // 6
//        System.out.println(evaluator.evalS("(* 2.5 3)")); // 7.5
//        System.out.println(evaluator.evalS("(+ 20)")); // 20
//        System.out.println(evaluator.evalS("(+ 1 234)")); // 235
//        System.out.println(evaluator.evalS("(+ 2 10 200)")); // 212
//        System.out.println(evaluator.evalS("(* (+ 1 2) (+ 1 3))")); // 12
//        System.out.println(evaluator.evalS("(* (+ 1 2) (+ 1 3) (* 2 3))")); // 72
//
//        System.out.println(evaluator.evalS("(+ 20")); // null
    }
}