package termProject;
import java.util.*;

/* 
 * Only +, * operators with integers
 * So all values are still integers 
 */
 public class Evaluator {

//	static final boolean debug = true;
	static final boolean debug = false;
	static Integer evalS(String expression) {
		// return true if s is a valid expression. expression could be nested
		ArrayList<String> tokens = Tokenizer.tokenize(expression);
		if(debug) tokens.forEach(System.out::println);
		return evalExpr(tokens, 0, tokens.size()-1);	
	}
	static Integer evalExpr(ArrayList<String> tokens, int start, int end) {
		// add your code
	}
	static ArrayList<Integer> getOperands(ArrayList<String> tokens, int start, int end) {
		// add your code
	}
	static Integer evalId(ArrayList<String> tokens, int start, int end) {
		// add your code
	}
	static Integer evalInteger(ArrayList<String> tokens, int start, int end) {
		// add your code
	}
	public static void main(String[] args) {
		System.out.println(evalS("234")); // 234
		System.out.println(evalS("(+ 20)")); // 20
		System.out.println(evalS("(+ 1 234)")); // 235
		System.out.println(evalS("(+ 2 10 200)")); // 212
		System.out.println(evalS("(* (+ 1 2) (+ 1 3))")); // 12 
		System.out.println(evalS("(* (+ 1 2) (+ 1 3) (* 2 3))")); // 72 

		System.out.println(evalS("(+ 20")); // null
	}
}