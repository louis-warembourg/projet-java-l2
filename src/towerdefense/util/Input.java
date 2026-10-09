package towerdefense.util;


import java.util.Scanner;

/**
 * A utility class for entering strings or integers on standard input
 */

public class Input {
	
	/**a scanning instance of the standard input */
	private Scanner scanner; 
    
	/**
	 * builds a new input
	 */
	public Input() {
	   this.scanner = new Scanner(System.in);
	}
	
	/**
	 * Allows entry of a string on standard input
	 * @return the string entered
	 */
	public static String readString() {
      return new Input().localReadString();
	}
	
	/**
	 * allowas to insert enters a string on the standard input 
	 * @return the string entered
	 */
	private String localReadString() {
		return this.scanner.next();
	}

	
	/**
	 * Allows entry of an int on standard input
	 * @return the int entered
	 * @exception java.io.IOException if the entry is invalid
	 */
	public static int readInt() throws java.io.IOException {
	   return new Input().localReadInt();
	}
	
	/**
	 * provides a integer entered on the standard input 
	 * @return the integer entered
	 * @exception java.io.IOException if the entry is invalid
	 */
	private int localReadInt() throws java.io.IOException {
		try {
			return this.scanner.nextInt();
		} catch (Exception e) {
			this.scanner.skip(".*");
			throw new java.io.IOException();
		}
	}	
	
} // Input

//
//
//import java.util.Scanner;
//
///**
// * Une classe utilitaire pour la saisie de cha�nes ou d'entiers sur l'entr�e
// * standard.
// */
//
//public class Input {
//	private static Scanner scanner = new Scanner(System.in);
//
//	/**
//	 * permet la saisie d'une chaîne sur l'entrée standard
//	 * 
//	 * @return la chaîne saisie
//	 */
//	public static String readString() {
//		return Input.scanner.next();
//	}
//
//	/**
//	 * permet la saisie d'un entier sur l'entrée standard
//	 * 
//	 * @return l'entier saisi
//	 */
//	public static int readInt() throws java.io.IOException {
//		try {
//			return Input.scanner.nextInt();
//		} catch (Exception e) {
//			Input.scanner.skip(".*");
//			throw new java.io.IOException();
//		}
//	}	
//	
//	// pour le test
//	public static void main(String[] args) {
//		try {
//			System.out.print(" chaine : ? ");
//			String chaineLue = Input.readString();
//			System.out.println("lue  => " + chaineLue);
//			System.out.print(" int : ? ");
//			int intLu = Input.readInt();
//			System.out.println("lue  => " + intLu);
//		} catch (java.io.IOException e) {
//		}
//	}
//} // Input
