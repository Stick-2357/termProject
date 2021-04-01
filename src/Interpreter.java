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
	static HashMap<String,Integer> variables = new HashMap<String, Integer>();
	static boolean interpret(String s) {
		// <S> -> <expr> | <define>
		ArrayList<String> tokens = Tokenizer.tokenize(s);	
		return (
				define(tokens, 0, tokens.size()-1) ||
				evaluate(tokens, 0, tokens.size()-1) 
		);
	}
	static boolean define(ArrayList<String> tokens, int start, int end) {
		// add your code
	}
	static boolean evaluate(ArrayList<String> tokens, int start, int end) {
		Integer r = evalExpr(tokens, 0, tokens.size()-1);	
		if (r == null) return false;
		System.out.println(r);
		return true;
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
	static Integer evalVar(ArrayList<String> tokens, int start, int end) {
		if(debug) System.out.println("evalVar("+start+","+end+")");
		if(start!=end) return null;
		String s = tokens.get(start);
		if (isIdentifier(s)) {
			Integer r = variables.get(s);
			if(r == null)
				System.out.println("Unknown symbol: "+s);
			else return r;
		}
		return null;
	}
	static boolean isIdentifier(String s) {
		// add your code
	}
	public static void main(String[] args) {
		System.out.println("Andy Petite Chez Scheme 0.9. Nothing much supported.");
		scan = new Scanner(System.in);
		String userInput;
		while(true) {
			System.out.print("> ");
			userInput = scan.nextLine().trim();
			if(userInput.equals("")) 
				continue;
			else if (userInput.equals("(quit)"))
				break;
			else if (userInput.equals("(local)"))
				System.out.println(variables);
			else if (interpret(userInput) == false)
				System.out.println("Syntax Error !!");
		}
		scan.close();
	}
}