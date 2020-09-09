package Developer;

import Enums.PrintType;

public class DeveloperManager {
	
	/*
	 * ====================================================================================
	 * 개발자가 정검해야할 내용들을 보기 위한 constant와 method들이 있는 클래스이다.
	 * ====================================================================================
	 */
	// constant
	public static final PrintType PRINT_TYPE = PrintType.ON;

	// method : developer message print
	public static void printDeveloperMessage(String message) {
		if (DeveloperManager.PRINT_TYPE == PrintType.ON) {
			System.out.println(message);
		} else {

		}
	}

}
