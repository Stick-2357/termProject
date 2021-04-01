package termProject;

import java.util.*;
public class Tokenizer {
	enum TokenStatus {START, WORD};
	public static ArrayList<String> tokenize(String s){
		ArrayList<String> tokens = new ArrayList<String>();
		String token="";
		TokenStatus status = TokenStatus.START;
		char ch;
		for(int i = 0; i<s.length(); i++) {
			ch = s.charAt(i);
			if (status == TokenStatus.START) { 	
				// add your code
			} 
			else { // TokenStatus.WORD
				// add your code
			}
		}
		if (token != "") tokens.add(token);
		return tokens;
	}
	public static void main(String[] args) {
		tokenize("(+ 1 2)").forEach(System.out::println);
		tokenize("[=(+ 1 2) 3]").forEach(System.out::println);
		tokenize("(+ 2 3 1.5)").forEach(System.out::println);
		tokenize("(<= 1 2)").forEach(System.out::println);
		tokenize("(*(- 2 1)(+ 3 1.5))").forEach(System.out::println);
		tokenize("(* (- x 1) (- y 2))").forEach(System.out::println);
		tokenize("(/ 1.2e3 10)").forEach(System.out::println);
		tokenize("(*(* 1.2e-2 10) 10)").forEach(System.out::println);
		tokenize("(cons 12'(2 3))").forEach(System.out::println);
		tokenize("(equal? 1'abc)").forEach(System.out::println);
		tokenize("(define d^2 (+ (square (- x2 x1)) (square (- y2 y1)) ))").forEach(System.out::println);
		tokenize("(define 12a (+ 12 a_b)").forEach(System.out::println);
		tokenize("(define larger? (lambda (a b) (if (> a b) #t #f)))").forEach(System.out::println);
		tokenize("(cond [(< _t 12) 'morning] [(< _t 18) 'afternoon] [else 'evening])").forEach(System.out::println);
	}
}