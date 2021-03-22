package com.skyman.billiarddata.management.file;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager2;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager2;
import com.skyman.billiarddata.management.projectblue.database.AppDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager2;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileExport {

    // constant
    private static final String CLASS_NAME = FileExport.class.getSimpleName();

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


    /**
     * 내보내기를 위한 초기 설정작업 실시
     */
    public void init() {

        // 데이터 가져오기
        getDbAllData();

        if (userData == null) {

            Toast.makeText(
                    activity,
                    "저장할 데이터가 없습니다.",
                    Toast.LENGTH_SHORT)
                    .show();
            return;

        }

        // 위에서 가져온 데이터로 Json object 생성하기
        jsonData = createJsonObject();
        DeveloperManager.displayLog(
                CLASS_NAME,
                jsonData.toString()
        );

        // 파일 저장할 장소 찾기 / 파일 만들기
        makeNewFile();

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
    private JSONObject createJsonObject() {

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


    /**
     * 파일의 uri 에 jsonData 를 쓴다.
     *
     * @param uri
     */
    public void writeJsonObject(Uri uri) {

        if (jsonData == null) {

            new RuntimeException("exportData() method 를 먼저 수행해주세요.");
            return;
        }

        try {

            ParcelFileDescriptor fileDescriptor = activity.getContentResolver().openFileDescriptor(uri, "w");

            // 해당 파일의 stream 열기
            FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptor.getFileDescriptor());

            // 파일에 내용 쓰기
            fileOutputStream.write(jsonData.toString().getBytes());

            // close
            fileOutputStream.close();
            fileDescriptor.close();

            // <사용자 알림>

            Toast.makeText(
                    activity,
                    "내보내기가 성공하였습니다.",
                    Toast.LENGTH_SHORT)
                    .show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *
     */
    public void print() {

        DeveloperManager.displayLog(CLASS_NAME, "========================================= user =========================================");
        DeveloperManager.displayLog(
                CLASS_NAME,
                "==================> 데이터 확인"
        );
        // user
        DeveloperManager.displayToUserData(CLASS_NAME, userData);

        // billiard
        DeveloperManager.displayToBilliardData(CLASS_NAME, billiardDataArrayList);

        // player
        DeveloperManager.displayToPlayerData(CLASS_NAME, playerDataArrayList);

        // friend
        DeveloperManager.displayToFriendData(CLASS_NAME, friendDataArrayList);

    }

}
