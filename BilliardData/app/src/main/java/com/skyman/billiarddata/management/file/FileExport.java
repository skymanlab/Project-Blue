package com.skyman.billiarddata.management.file;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileExport {

    // constant
    private static final String CLASS_NAME_LOG = FileExport.class.getSimpleName();

    // instance variable
    private Activity activity;
    private UserDbManager userDbManager;
    private BilliardDbManager billiardDbManager;
    private PlayerDbManager playerDbManager;
    private FriendDbManager friendDbManager;

    // instance variable
    private ArrayList<UserData> userDataArrayList;
    private ArrayList<BilliardData> billiardDataArrayList;
    private ArrayList<PlayerData> playerDataArrayList;
    private ArrayList<FriendData> friendDataArrayList;

    // instance variable
    private JSONObject jsonData = null;

    // constructor
    public FileExport(Activity activity, UserDbManager userDbManager, BilliardDbManager billiardDbManager, PlayerDbManager playerDbManager, FriendDbManager friendDbManager) {

        this.activity = activity;
        // DB manager
        this.userDbManager = userDbManager;
        this.billiardDbManager = billiardDbManager;
        this.playerDbManager = playerDbManager;
        this.friendDbManager = friendDbManager;

        // 생성
        userDataArrayList = new ArrayList<>();
        billiardDataArrayList = new ArrayList<>();
        playerDataArrayList = new ArrayList<>();
        friendDataArrayList = new ArrayList<>();

    }


    /**
     * 내보내기를 위한 초기 설정작업 실시
     */
    public void init() {

        // 데이터 가져오기
        getDbAllData();

        if (userDataArrayList.isEmpty()) {

            Toast.makeText(
                    activity,
                    "저장할 데이터가 없습니다.",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // 위에서 가져온 데이터로 Json object 생성하기
        jsonData = createJsonObject();

        // 파일 저장할 장소 찾기 / 파일 만들기
        makeNewFile();

    }


    /**
     * 각각의 Db manager 를 이용하여
     * userDataArrayList, billiardDataArrayList, playerDataArrayList, friendDataArrayList 가져온다.
     */
    private void getDbAllData() {

        // 가져오기
        userDataArrayList = this.userDbManager.loadAllContent();
        billiardDataArrayList = this.billiardDbManager.loadAllContent();
        playerDataArrayList = this.playerDbManager.loadAllContent();
        friendDataArrayList = this.friendDbManager.loadAllContent();

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
                .setUserDataArrayList(userDataArrayList)
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
     * @param uri
     */
    public void writeJsonObject(Uri uri) {

        if (jsonData == null) {

            new RuntimeException("exportData() method 를 먼저 수행해주세요.");
            return;
        }

        try {

            ParcelFileDescriptor fileDescriptor = activity.getContentResolver().openFileDescriptor(uri, "wr");

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

        DeveloperManager.displayLog(CLASS_NAME_LOG, "========================================= user =========================================");
        // user
        for (int index = 0; index < userDataArrayList.size(); index++) {
            DeveloperManager.displayToUserData(CLASS_NAME_LOG, userDataArrayList.get(index));
        }

        // billiard
        DeveloperManager.displayLog(CLASS_NAME_LOG, "========================================= billiard =========================================");
        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, billiardDataArrayList);

        // player
        DeveloperManager.displayLog(CLASS_NAME_LOG, "========================================= player =========================================");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);

        // friend
        DeveloperManager.displayLog(CLASS_NAME_LOG, "========================================= friend =========================================");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendDataArrayList);

    }

}
