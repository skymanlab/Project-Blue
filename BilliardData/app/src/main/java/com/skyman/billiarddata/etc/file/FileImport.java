package com.skyman.billiarddata.etc.file;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.table.friend.data.FriendData;
import com.skyman.billiarddata.table.friend.database.FriendDbManager2;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.user.data.UserData;
import com.skyman.billiarddata.table.user.database.UserDbManager2;


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
    private AppDbManager appDbManager;

    // constructor
    public FileImport(Activity activity, AppDbManager appDbManager) {

        this.activity = activity;
        this.appDbManager = appDbManager;

    }

    /**
     * 파일 선택 화면을 연다.
     */
    public void init() {

        openFile();

    }


    /**
     * 해당 메소드를 이용하여 onActivityResult() 메소드로 넘어온
     * 선택된 file 을 JsonParser 로 파싱하여 파싱이 완료되면
     * SQLite Database 에 userData, friendDataArrayList, billiardDataArrayList, friendDataArrayList 를
     * 각 테이블에 저장한다.
     *
     * @param uri 선택된 file 의 uri
     */
    public void saveDatabase(Uri uri, OnSuccessListener onSuccessListener) throws Exception {

        // <과정>
        // 1. 선택된 파일의 내용을 읽어오기
        // 2. 파일에서 읽어온 내용을 JsonParser 를 사용하여 파싱하기
        // 3. 파싱에 실패하였으면 종료와 함계 사용자에게 알림
        // 4. 파싱이 성공했으면 데이터베이스에 저장하기

        // <1>
        // 파일 내용 읽어오기
        String jsonData = readDataFromFile(uri);
        DeveloperManager.displayLog(
                CLASS_NAME_LOG,
                "파일의 내용 : " + jsonData
        );

        // <2>
        // JsonParser 를 사용하여 파일 내용 파싱하기
        JsonParser jsonParser = new JsonParser(jsonData);
        jsonParser.parseContent();

        // <4>
        // 파싱한 데이터를 Database 에 저장하기
        saveUserData(jsonParser.getUserData());
        saveFriendData(jsonParser.getFriendDataArrayList());
        saveBilliardData(jsonParser.getBilliardDataArrayList());
        savePlayerData(jsonParser.getPlayerDataArrayList());

        onSuccessListener.onSuccess();
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


    /**
     * openFile() 메소드로 선택한 file 의 uri 로
     * 해당 file 의 내용을 읽어온다.
     *
     * @param uri 선택된 file 의 uri
     * @return 선택된 file 의 내용
     */
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


    /**
     * SQLite Database: save userData
     *
     * @param userData
     */
    private void saveUserData(UserData userData) {

        appDbManager.requestUserQuery(
                new AppDbManager.UserQueryRequestListener() {
                    @Override
                    public void requestQuery(UserDbManager2 userDbManager2) {

                        // primary key 를 포함하여
                        // user 테이블에 userData 를 저장한다.
                        // 그러면 primary key 가 autoincrement 되는 것이 아니라
                        // 해당 primary key 로 저장된다.
                        userDbManager2.saveContent(
                                userData
                        );

                    }
                }
        );
    }


    /**
     * SQLite Database: save friendDataArrayList
     *
     * @param friendDataArrayList
     */
    private void saveFriendData(ArrayList<FriendData> friendDataArrayList) {

        appDbManager.requestFriendQuery(
                new AppDbManager.FriendQueryRequestListener() {
                    @Override
                    public void requestQuery(FriendDbManager2 friendDbManager2) {

                        for (int index = 0; index < friendDataArrayList.size(); index++) {

                            // primary key 를 포함하여
                            // friend 테이블에 friendData 를 저장한다.
                            // 그러면 primary key 가 autoincrement 되는 것이 아니라
                            // 해당 primary key 로 저장된다.
                            friendDbManager2.saveContent(friendDataArrayList.get(index));

                        }

                    }
                }
        );

    }


    /**
     * SQLite Database: save billiardDataArrayList
     *
     * @param billiardDataArrayList
     */
    private void saveBilliardData(ArrayList<BilliardData> billiardDataArrayList) {

        appDbManager.requestBilliardQuery(
                new AppDbManager.BilliardQueryRequestListener() {
                    @Override
                    public void requestQuery(BilliardDbManager2 billiardDbManager2) {

                        for (int index = 0; index < billiardDataArrayList.size(); index++) {

                            // primary key 를 포함하여
                            // billiard 테이블에 billiardData 를 저장한다.
                            // 그러면 primary key 가 autoincrement 되는 것이 아니라
                            // 해당 primary key 로 저장된다.
                            billiardDbManager2.saveContent(billiardDataArrayList.get(index));

                        }
                    }
                }
        );


    }


    /**
     * SQLite Database: save playerDataArrayList
     *
     * @param playerDataArrayList
     */
    private void savePlayerData(ArrayList<PlayerData> playerDataArrayList) {

        appDbManager.requestPlayerQuery(
                new AppDbManager.PlayerQueryRequestListener() {
                    @Override
                    public void requestQuery(PlayerDbManager2 playerDbManager2) {

                        for (int index = 0; index < playerDataArrayList.size(); index++) {

                            playerDbManager2.saveContent(playerDataArrayList.get(index));
                        }
                    }
                }
        );

    }


    public interface OnSuccessListener {
        void onSuccess();
    }

}
