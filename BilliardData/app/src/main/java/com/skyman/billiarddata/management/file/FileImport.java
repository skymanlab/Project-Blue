package com.skyman.billiarddata.management.file;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class FileImport {

    // constant
    private static final String CLASS_NAME_LOG = FileExport.class.getSimpleName();


    // instance variable
    private Activity activity;
    private UserDbManager userDbManager;
    private BilliardDbManager billiardDbManager;
    private PlayerDbManager playerDbManager;
    private FriendDbManager friendDbManager;

    // constructor
    public FileImport(Activity activity, UserDbManager userDbManager, BilliardDbManager billiardDbManager, PlayerDbManager playerDbManager, FriendDbManager friendDbManager) {

        this.activity = activity;
        // DB manager
        this.userDbManager = userDbManager;
        this.billiardDbManager = billiardDbManager;
        this.playerDbManager = playerDbManager;
        this.friendDbManager = friendDbManager;

    }

    // init
    public void init() {
        openFile();
    }

    /**
     * 파일 열기
     */
    private void openFile() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(FileConstants.FILE_TYPE);

        activity.startActivityForResult(intent, FileConstants.REQUEST_CODE_OPEN_FILE);
    }

    public void saveDatabase(Uri uri) {

        DeveloperManager.displayLog(
                CLASS_NAME_LOG,
                "====----------------------------------->>>>>>>>>>>>>>>>>>>"
        );

        // 파일 내용 읽어오기
        String data = readDataFromFile(uri);
        DeveloperManager.displayLog(
                CLASS_NAME_LOG,
                "file data : " + data
        );

        // JsonParser
        JsonParser jsonParser = new JsonParser(data);
        jsonParser.parseContent();

        // data 를 parsing 실패 했을 때
        if (!jsonParser.isCompletedParsing()) {

            Toast.makeText(
                    activity,
                    "형식에 맞지 않은 파일입니다.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }
        jsonParser.print();

        // 파싱한 데이터를 Database 에 저장하기
        saveUserData(jsonParser.getUserDataArrayList());
        saveFriendData(jsonParser.getFriendDataArrayList());
        saveBilliardData(jsonParser.getBilliardDataArrayList());
        savePlayerData(jsonParser.getPlayerDataArrayList());

        // <사용자 알림>
        Toast.makeText(
                activity,
                "데이터베이스에 저장되었습니다.",
                Toast.LENGTH_SHORT
        ).show();


    }

    private String readDataFromFile(Uri uri) {

        // data 담기
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStream = activity.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }


    private void saveUserData(ArrayList<UserData> userDataArrayList) {

        for (int index = 0; index < userDataArrayList.size(); index++) {
            this.userDbManager.saveContent(userDataArrayList.get(index));
        }
    }

    private void saveBilliardData(ArrayList<BilliardData> billiardDataArrayList) {

        this.billiardDbManager.saveContentByImport(billiardDataArrayList);

    }

    private void savePlayerData(ArrayList<PlayerData> playerDataArrayList) {

        this.playerDbManager.saveContentByImport(playerDataArrayList);
    }

    private void saveFriendData(ArrayList<FriendData> friendDataArrayList) {

        this.friendDbManager.saveContentByImport(friendDataArrayList);
    }

    public boolean checkFile(Uri uri) {
        if (uri.toString().contains(FileConstants.FILE_NAME)) {

            DeveloperManager.displayLog(
                    CLASS_NAME_LOG,
                    "checkFile : true"
            );
            return true;
        } else {

            DeveloperManager.displayLog(
                    CLASS_NAME_LOG,
                    "checkFile : false"
            );
            return false;
        }
    }
}
