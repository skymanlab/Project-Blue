package com.skyman.billiarddata.management.file;

public class FileConstants {

    /**
     * 데이터베이스의 모든 내용을 파일로 저장할 때 필요한 파일 이름
     */
    public static final String FILE_NAME = "billiardData_databaseData";


    /**
     * 내보내기 할 파일을 생성하고 그 결과(파일의 uri)를 가져오기 위한 request code
     */
    public static final int REQUEST_CODE_CREATE_FILE = 10001;

    /**
     * 가져오기 할 파일을 선택하고 그 결과(파일의 uri)를 가져오기 위한 request code
     */
    public static final int REQUEST_CODE_OPEN_FILE = 10002;

    /**
     * 인텐트 type : 보고 싶은 파일을 정한다.
     * 1. 오디오 : audio/(파일형식)
     * 2. 이미지 : image/(파일형식)
     * 3. 모든 파일 :  아래의 형식
     */
    public static final String FILE_TYPE = "*/*";

}
