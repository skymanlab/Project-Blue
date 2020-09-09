package Data;

import Enums.PrintType;

public class HangleData {

	/*
	 * character set encoding type = UTF-8
	 * UTF-8 타입의 한글데이터 입니다.
	 */
	public static String HttpConnector_HEADER_NOTHING_MSSEAGE = "Header에 대한 요청값이 없습니다. ";
	public static String HttpConnector_NOTING_DATATYPE_MESSAGE = "응답 받는 데이터의 타입의 범주에 속하지 않습니다.";
	public static String HttpManager_STEP_1 = "서버에 보낸 요청이 거부되었습니다.";
	public static String HttpManager_STEP_2 = "서버에서 InputStream을 가져오지 못 했습니다.";
	public static String HttpManager_STEP_3 = "서버에서 보낸 응답 메시지를 읽어오지 못 했습니다.";
	private final static PrintType PRINT_TYPE = PrintType.OFF;
	
	private static void printDeveloperMessage(String message) {
		if (PRINT_TYPE == PrintType.ON)
			System.out.println(message);
	}

}
