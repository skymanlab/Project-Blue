package com.skyman.billiarddata.etc.file;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.table.friend.data.FriendData;
import com.skyman.billiarddata.table.friend.database.FriendDbManager2;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.user.data.UserData;
import com.skyman.billiarddata.table.user.database.UserDbManager2;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileExport {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "FileExport";

    // instance variable
    private Activity activity;
    private AppDbManager appDbManager;
    private UserData userData;

    // instance variable
    private ArrayList<FriendData> friendDataArrayList;
    private ArrayList<BilliardData> billiardDataArrayList;
    private ArrayList<PlayerData> playerDataArrayList;

    // instance variable
    private JSONObject jsonData = null;

    // constructor
    public FileExport(Activity activity, AppDbManager appDbManager, UserData userData) {

        this.activity = activity;
        this.appDbManager = appDbManager;
        this.userData = userData;

        this.friendDataArrayList = new ArrayList<>();
        this.billiardDataArrayList = new ArrayList<>();
        this.playerDataArrayList = new ArrayList<>();

    }


    // ============================================== 사용자 사용 메소드 ==============================================

    /**
     * 내보내기 초기 설정작업 실시
     *
     * @throws Exception 오류 발생 시
     */
    public void init() throws Exception {

        // 데이터 가져오기
        getDbAllData();

        // 위에서 가져온 데이터로 Json object 생성하기
        jsonData = createJsonObject();
        printLog(jsonData);

        // 파일 저장할 장소 찾기 / 파일 만들기
        makeNewFile();

    }


    /**
     * uri 에 해당하는 파일에 jsonData 를 쓴다. 그리고 쓰기가 완료 되면 onSuccessListener 를 통해서 다음 과정을 진행한다.
     *
     * @param uri               파일 uri
     * @param onSuccessListener 성공했을 때 다음 과정을 진행
     * @throws IOException 파일의 스트림을 얻어오는 과정에서 오류가 발생했을 때
     */
    public void writeJsonObject(Uri uri, OnSuccessListener onSuccessListener) throws IOException {

        if (jsonData == null) {
            new RuntimeException("exportData() method 를 먼저 수행해주세요.");
            return;
        }

        ParcelFileDescriptor fileDescriptor = activity.getContentResolver().openFileDescriptor(uri, "w");

        // 해당 파일의 stream 열기
        FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptor.getFileDescriptor());

        // 파일에 내용 쓰기
        fileOutputStream.write(jsonData.toString().getBytes());

        // close
        fileOutputStream.close();
        fileDescriptor.close();

        // 성공했을 때
        onSuccessListener.onSuccess();
    }

    /**
     * 각각의 Db manager 를 이용하여
     * userDataArrayList, billiardDataArrayList, playerDataArrayList, friendDataArrayList 가져온다.
     */
    private void getDbAllData() {

        appDbManager.requestQuery(
                new AppDbManager.QueryRequestListener() {
                    @Override
                    public void requestUserQuery(UserDbManager2 userDbManager2) {

                    }

                    @Override
                    public void requestFriendQuery(FriendDbManager2 friendDbManager2) {
                        friendDataArrayList = friendDbManager2.loadAllContent();
                    }

                    @Override
                    public void requestBilliardQuery(BilliardDbManager2 billiardDbManager2) {
                        billiardDataArrayList = billiardDbManager2.loadAllContent();
                    }

                    @Override
                    public void requestPlayerQuery(PlayerDbManager2 playerDbManager2) {
                        playerDataArrayList = playerDbManager2.loadAllContent();
                    }
                }
        );

    }


    /**
     * userDataArrayList, billiardDataArrayList, playerDataArrayList, friendDataArrayList 의 데이터를
     * Json object 로 만들어서 반환한다.
     *
     * @return
     */
    private JSONObject createJsonObject() throws Exception {

        // SQLite 에서 가져온 데이터베이스의 모든 내용을 JSONObject 로 만들기
        JsonGenerator jsonGenerator = new JsonGenerator.Builder()
                .setUserData(userData)
                .setBilliardDataArrayList(billiardDataArrayList)
                .setPlayerDataArrayList(playerDataArrayList)
                .setFriendDataArrayList(friendDataArrayList)
                .create();
        jsonGenerator.createJsonObject();

        return jsonGenerator.getJsonObject();
    }


    /**
     * 어플이 삭제되어도 저장할 장소를 찾고 먼저 파일을 만들기
     */
    private void makeNewFile() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        StringBuilder fileName = new StringBuilder()
                .append(dateFormat.format(new Date()))
                .append("_")
                .append(FileConstants.FILE_NAME)
                .append(".txt");

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(FileConstants.FILE_TYPE);
        intent.putExtra(Intent.EXTRA_TITLE, fileName.toString());

        activity.startActivityForResult(intent, FileConstants.REQUEST_CODE_CREATE_FILE);

    }


    public interface OnSuccessListener {
        void onSuccess();
    }

    public void printLog(JSONObject jsonData) {
        if (DeveloperLog.PROJECT_LOG_SWITCH == LogSwitch.ON)
            if (CLASS_LOG_SWITCH == LogSwitch.ON) {

                Log.d(CLASS_NAME, "[jsonData 내용 확인]");
                Log.d(CLASS_NAME, jsonData.toString());

            }
    }

    public void printLog(UserData userData, ArrayList<FriendData> friendDataArrayList, ArrayList<BilliardData> billiardDataArrayList, ArrayList<PlayerData> playerDataArrayList) {

        DeveloperLog.printLogUserData(CLASS_LOG_SWITCH, CLASS_NAME, userData);
        DeveloperLog.printLogFriendData(CLASS_LOG_SWITCH, CLASS_NAME, friendDataArrayList);
        DeveloperLog.printLogBilliardData(CLASS_LOG_SWITCH, CLASS_NAME, billiardDataArrayList);
        DeveloperLog.printLogPlayerData(CLASS_LOG_SWITCH, CLASS_NAME, playerDataArrayList);

    }

}
