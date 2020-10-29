package com.skyman.billiarddata.management.projectblue.data;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

public class ChangedDataChecker {

    // constant
    private static final String CLASS_NAME_LOG = "[C]_ChangedDataChecker";

    // instance variable
    private BilliardData billiardData;
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;
    private ArrayList<PlayerData> playerDataArrayList;

    // instance variable
    private long initWinnerId;
    private String initWinnerName;
    private int initWinnerPlayerIndex;
    private int initPlayTime;
    private int initScore[];
    private int initCost;

    // instance variable
    private boolean isCompleteSetting;
    private boolean isRangeError;
    private boolean isEqualError;
    private boolean isChanged;

    // constructor
    public ChangedDataChecker(int playerCount) {

        this.billiardData = new BilliardData();
        this.userData = new UserData();
        this.friendDataArrayList = new ArrayList<>();
        this.playerDataArrayList = new ArrayList<>();
        this.initWinnerId = -100;
        this.initWinnerName = new String();
        this.initPlayTime = -100;
        this.initScore = new int[playerCount];
        for (int index = 0; index < playerCount; index++) {
            this.initScore[index] = -100;
        }
        this.initCost = -100;

        this.isCompleteSetting = false;
        this.isRangeError = false;
        this.isEqualError = false;
        this.isChanged = false;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    /**
     * [method] [set] init variable 설정하기
     *
     * <p>
     * 1. winnerId
     * 2. winnerName
     * 3. playTime
     * 4. score
     * 5. cost
     * </p>
     */
    public void setInitData() {

        final String METHOD_NAME = "[setInitData] ";

        // [iv/l]initWinnerId : billiardData 의 처음 winnerId 값 저장
        this.initWinnerId = this.billiardData.getWinnerId();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winnerId 의 값 = " + this.initWinnerId);

        // [iv/C]String : initWinnerName / billiardData 의 처음 winnerName 값 저장
        this.initWinnerName = this.billiardData.getWinnerName();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winnerName 의 값 = " + this.initWinnerName);

        // [iv/i]initPlayTime : billiardData 의 처음 winnerName 값 저장
        this.initPlayTime = this.billiardData.getPlayTime();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 playTime 의 값 = " + this.initPlayTime);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "playerDataArrayList 로 구한 초기 score 들을 확입합니다.");
        // [cycle 1] : player 의 수 만큼
        for (int playerIndex = 0; playerIndex < this.playerDataArrayList.size(); playerIndex++) {


            this.initScore[playerIndex] = this.playerDataArrayList.get(playerIndex).getScore();
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + playerIndex + "번째 player 의 score = " + this.initScore[playerIndex]);

            // [check 1] : 승리한 사람인가?
            if ((this.billiardData.getWinnerId() == this.playerDataArrayList.get(playerIndex).getPlayerId()) && (this.billiardData.getWinnerName().equals(this.playerDataArrayList.get(playerIndex).getPlayerName()))) {

                // [iv/i]initWinnerPlayerIndex : 승리한 player 의 index 를 저장
                this.initWinnerPlayerIndex = playerIndex;
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winner 는 playerDataArrayList 에서 index = " + this.initWinnerPlayerIndex);

            } // [check 1]

        } // [cycle 1]

        // [iv/i]initCost : billiardData 의 처음 winnerName 값 저장
        this.initCost = this.billiardData.getCost();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 cost 의 값 = " + this.initCost);

    } // End of method [setInitData]


    /**
     * [method] [check] 위의 set method 로 필드 변수에 데이터를 모두 설정하였는지 검사하여 true 또는 false 를 반환한다.
     */
    public void setCompleteSetting() {

        final String METHOD_NAME = "[setCompleteSetting] ";

        // [lv/b]isCompletedSetting : 모든 데이터를 다 입력 받았을 때
        boolean isCompletedSetting = false;

        // [check 1] : billiardData 입력 완료
        if (this.billiardData != null) {

            // [check 2] : userData 입력 완료
            if (this.userData != null) {

                // [check 3] : friendDataArrayList 에 데이터 입력 완료
                if (this.friendDataArrayList.size() != 0) {

                    // [check 4] : playerDataArrayList 에 데이터 입력 완료
                    if (this.playerDataArrayList.size() != 0) {

                        // [check 5] : initWinnerId 초기값 입력 완료
                        if (this.initWinnerId > 0) {

                            // [check 6] : initWinnerName 초기값 입력 완료
                            if (!this.initWinnerName.equals("")) {

                                // [check 7] : initPlayTime 초기값 입력 완료
                                if (this.initPlayTime > 0) {

                                    // [check 8] : initCost 초기값 입력 완료
                                    if (this.initCost >= 0) {

                                        // [cycle 1] : score 의 길이 만큼
                                        for (int index = 0; index < this.initScore.length; index++) {

                                            // [check 9] : index 번째 score 초기값 입력 완료
                                            if (this.initScore[index] >= 0) {
                                                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.initScore 중 " + index + " 번째는 통과했어!");
                                                isCompletedSetting = true;
                                            } else {
                                                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.initScore 중 " + index + " 번째의 초기값이 설정되지 않았어!");
                                                isCompletedSetting = false;
                                                break;
                                            } // [check 9]

                                        } // [cycle 1]

                                    } else {
                                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.initCost 초기값이 설정되지 않았어!");
                                    } // [check 8]

                                } else {
                                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.initCost 초기값이 설정되지 않았어!");
                                } // [check 7]

                            } else {
                                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.initWinnerName 초기값이 설정되지 않았어!");
                            } // [check 6]

                        } else {
                            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.initWinnerId 초기값이 설정되지 않았어!");
                        } // [check 5]

                    } else {
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.playerDataArrayList 입력 해줘!");
                    } // [check 4]

                } else {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.friendDataArrayList 입력 해줘!");
                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.userData 입력 해줘!");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "this.billiardData 입력 해줘!");
        } // [check 1]

        this.isCompleteSetting = isCompletedSetting;

    } // End of method [setCompleteSetting]


    public boolean isRangeError() {
        return isRangeError;
    }


    /**
     * [method] playerDataArrayList 의 score 값들이 범위 안의 값인지 검사하여, 다른 범위의 값이 있으면 error 가 발생한 것이므로 true 를 반환한다.
     *
     * <p>
     * true : 에러 발생 / 다음 과정 진행 못함
     * false : 에
     * </p>
     */
    public void setRangeError() {

        final String METHOD_NAME = "[setRangeError] ";

        // [lv/b]isRangeError : 범위 에러가 발생하였는가?
        boolean isRangeError = false;

        // [cycle 1] : playerDataArrayList 의 size 만큼
        for (int index = 0; index < this.playerDataArrayList.size(); index++) {

            int targetScore = this.playerDataArrayList.get(index).getTargetScore();
            int score = this.playerDataArrayList.get(index).getScore();

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "check_1. <player " + index + "> 의 <score> 값의 범위를 확인 중입니다.");
            // [check 1] : 0 <= score <= targetScore 에서 벗어난 값
            if ((0 <= score) && (score <= targetScore)) {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "check_1. <player " + index + "> 의 통과");
            } else {
                // 오류 발생

                isRangeError = true;
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "check_1.<player " + index + "> 의 <score> 가 범위 밖의 값이여!");
            } // [check 1]

        } // [cycle 1]

        this.isRangeError = isRangeError;
    } // End of method [setRangeError]


    public boolean isEqualError() {
        return isEqualError;
    }


    /**
     * [method] 승리자의 점수의 값이 목표점수와 같은지 비교하여, 다르면 true 를 반환하여 error 가 발생한 것을 알린다.
     */
    public void setEqualError() {

        final String METHOD_NAME = "[setEqualError] ";

        // [lv/b]isEqualError : 범위 에러가 발생하였는가? / 아직 발생안함.
        boolean isEqualError = false;

        // [lv/l]winnerId : 최종 변경된 승리자의 아이디
        long winnerId = this.billiardData.getWinnerId();

        // [lv/c]String : 최종 변경된 승리자의 이름
        String winnerName = this.billiardData.getWinnerName();

        // [cycle 1] : playerDataArrayList 의 size 만큼
        for (int index = 0; index < this.playerDataArrayList.size(); index++) {

            long playerId = this.playerDataArrayList.get(index).getPlayerId();
            String playerName = this.playerDataArrayList.get(index).getPlayerName();

            // [check 2] : winner 를 찾아서
            if ((winnerId == playerId) && (winnerName.equals(playerName))) {

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "check_2. <승리자>의 targetScore 와 score 값이 같은지 비교합니다.");

                int targetScore = this.playerDataArrayList.get(index).getTargetScore();
                int score = this.playerDataArrayList.get(index).getScore();

                // [check 3] : 승리자의 score 가 targetScore 가 같나요?
                if (score != targetScore) {

                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "check_2. <승리자> 의 targetScore != score 에러 발생했어!");
                    isEqualError = true;

                } else {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "check_2. <승리자> 통과");
                } // [check 3]

            } // [check 2]

        } // [cycle 1]

        this.isEqualError = isEqualError;
    } // End of method [setEqualError]


    public BilliardData getBilliardData() {
        return billiardData;
    }


    /**
     * [method] [set] billiardData 를 복사하기
     *
     * @param billiardData 값 복사를 위한 원본 billiardData
     */
    public void setBilliardData(BilliardData billiardData) {

        final String METHOD_NAME = "[setBilliardData] ";

        // [iv/C]BilliardData : 매개변수의 값들을 복사하여 새로운 객체를 만든다.
        this.billiardData.setCount(billiardData.getCount());                // 0. count
        this.billiardData.setDate(billiardData.getDate());                  // 1. date
        this.billiardData.setGameMode(billiardData.getGameMode());          // 2. game mode
        this.billiardData.setPlayerCount(billiardData.getPlayerCount());    // 3. player count
        this.billiardData.setWinnerId(billiardData.getWinnerId());          // 4. winner id
        this.billiardData.setWinnerName(billiardData.getWinnerName());      // 5. winner name
        this.billiardData.setPlayTime(billiardData.getPlayTime());          // 6. play time
        this.billiardData.setScore(billiardData.getScore());                // 7. score
        this.billiardData.setCost(billiardData.getCost());                  // 8. cost

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "************ 복사한 this.billiardData 확인! ************");
        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, this.billiardData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "********************************************************");

    } // End of method [setBilliardData]

    public UserData getUserData() {
        return userData;
    }

    /**
     * [method] [set] userData 를 복사하기
     *
     * @param userData 값 복사를 위한 원본 userData
     */
    public void setUserData(UserData userData) {

        final String METHOD_NAME = "[setUserData] ";

        // [iv/C]UserData : 매개변수의 값들을 복사하여 새로운 객체를 만든다.
        this.userData.setId(userData.getId());                                              // 0. id
        this.userData.setName(userData.getName());                                          // 1. name
        this.userData.setTargetScore(userData.getTargetScore());                            // 2. target score
        this.userData.setSpeciality(userData.getSpeciality());                              // 3. speciality
        this.userData.setGameRecordWin(userData.getGameRecordWin());                        // 4. game record win
        this.userData.setGameRecordLoss(userData.getGameRecordLoss());                      // 5. game record loss
        this.userData.setRecentGameBilliardCount(userData.getRecentGameBilliardCount());    // 6. recent game billiard count
        this.userData.setTotalPlayTime(userData.getTotalPlayTime());                        // 7. total play time
        this.userData.setTotalCost(userData.getTotalCost());                                // 8. total cost

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "************ 복사한 this.userData 확인! ************");
        DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "****************************************************");

    } // End of method [setUserData]


    public ArrayList<FriendData> getFriendDataArrayList() {
        return friendDataArrayList;
    }


    /**
     * [method] [set] 모든 fiendData 를 복사한다.
     *
     * @param friendDataArrayList 값 복사를 위한 원본 friendDataArrayList
     */
    public void setFriendDataArrayList(ArrayList<FriendData> friendDataArrayList) {

        final String METHOD_NAME = "[setFriendDataArrayList] ";

        // [iv/C]ArrayList<FriendData> : 기존 데이터를 모두 지우기
        this.friendDataArrayList.clear();

        // [cycle 1] : friendDataArrayList 의 size 만큼
        for (int index = 0; index < friendDataArrayList.size(); index++) {

            // [lv/C]FriendData : friendDataArrayList 에서 하나의 FriendData 데이터를 저장한다.
            FriendData friendData = new FriendData();
            friendData.setId(friendDataArrayList.get(index).getId());                                               // 0. id
            friendData.setUserId(friendDataArrayList.get(index).getUserId());                                       // 1. user id
            friendData.setName(friendDataArrayList.get(index).getName());                                           // 2. name
            friendData.setGameRecordWin(friendDataArrayList.get(index).getGameRecordWin());                         // 3. game record win
            friendData.setGameRecordLoss(friendDataArrayList.get(index).getGameRecordLoss());                       // 4. game record loss
            friendData.setRecentGameBilliardCount(friendDataArrayList.get(index).getRecentGameBilliardCount());     // 6. recent game billiard count
            friendData.setTotalPlayTime(friendDataArrayList.get(index).getTotalPlayTime());                         // 7. total play time
            friendData.setTotalCost(friendDataArrayList.get(index).getTotalCost());                                 // 8. total cost

            // [iv/C]ArrayList<FriendData> : 위의 friendData 를 추가하기
            this.friendDataArrayList.add(friendData);

        } // [cycle 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "************ 복사한 this.friendDataArrayList 확인! ************");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, this.friendDataArrayList);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "***************************************************************");

    } // End of method [setFriendDataArrayList]

    public ArrayList<PlayerData> getPlayerDataArrayList() {
        return playerDataArrayList;
    }

    /**
     * [method] [set] 이 게임에 참가한 모든 player 의 정보가 담긴 playerDataArrayList 를 복사한다.
     */
    public void setPlayerDataArrayList(ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[setPlayerDataArrayList] ";

        // [iv/C]ArrayList<PlayerData> : 기존 데이터를 모두 지우기
        this.playerDataArrayList.clear();

        // [cycle 1] : playerDataArrayList 의 size 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]PlayerData : playerDataArrayList 에서 하나의 PlayerData 데이터를 저장한다.
            PlayerData playerData = new PlayerData();
            playerData.setCount(playerDataArrayList.get(index).getCount());                     // 0. count
            playerData.setBilliardCount(playerDataArrayList.get(index).getBilliardCount());     // 1. billiard count
            playerData.setPlayerId(playerDataArrayList.get(index).getPlayerId());               // 2. player id
            playerData.setPlayerName(playerDataArrayList.get(index).getPlayerName());            // 3. player name
            playerData.setTargetScore(playerDataArrayList.get(index).getTargetScore());         // 4. target score
            playerData.setScore(playerDataArrayList.get(index).getScore());                     // 5. score

            // [iv/C]ArrayList<PlayerData> : 위의 playerData 를 추가하기
            this.playerDataArrayList.add(playerData);

        } // [cycle 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "************ 복사한 this.playerDataArrayList 확인! ************");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, this.playerDataArrayList);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "***************************************************************");

    } // End of method [setPlayerDataArrayList]


    public boolean isCompleteSetting() {
        return isCompleteSetting;
    }


    public boolean isChanged() {
        return isChanged;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    /**
     * [method] [Change] winnerName spinner 의 값이 변경되었으면 해당 데이터와 관련된 것들을 변경한다.
     *
     * <p>
     * 1. billiardData
     * - winnerId
     * - winnerName
     * 2. userData / player 0
     * - gameRecordWin
     * - gameRecordLoss
     * 3. friendData / player 1, 2, 3
     * - gameRecordWin
     * - gameRecordLoss
     * </p>
     *
     * @param changedWinnerId          변경된 승리자의 아이디
     * @param changedWinnerName        변경된 승리자의 이름
     * @param changedWinnerPlayerIndex 변경된 승리자의 playerDataArrayList 에서의 위치
     */
    public void changeWinner(long changedWinnerId, String changedWinnerName, int changedWinnerPlayerIndex) {

        final String METHOD_NAME = "[changeWinner] ";

        // [check 1] : initWinnerId 와 initWinnerName 이 변경되지 않았다.
        if ((changedWinnerId == this.initWinnerId) && (changedWinnerName.equals(this.initWinnerName))) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<승리자>가 변경되지 않았어!");

        } else {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<승리자>가 변경되었습니다. 변경된 <승리자> 를 확인하겠습니다.");

            // [iv/C]BilliardData : 기존 this.billiardData 의 winnerId 와 winnerName 을 변경한다. / this.billiardData
            this.billiardData.setWinnerId(changedWinnerId);
            this.billiardData.setWinnerName(changedWinnerName);

            // [method] : 변경된 승리자를 <패배자> -> <승리자>로 변경하기 / this.userData, this.friendDataArrayList
            changeToWinnerFromChangedWinner(changedWinnerPlayerIndex);

            // [method] : 기존 승리자를 <승리자> -> <패배자>로 변경하기 / this.userData, this.friendDataArrayList
            changeToLooserFromInitialWinner();

            // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
            this.isChanged = true;

        } // [check 1]

    } // End of method [changeWinner]


    /**
     * [method] [Change] 변경된 승리자가 누구인지를 판별해서 그 player 를 패배자에서 승리자로 변경한다. 즉 기존의 gameRecordWin 과 gameRecordLoss 를 바꾼다.
     *
     * <p>
     * gameRecordWin += 1
     * gameRecordLoss -= 1
     * </p>
     *
     * @param changedWinnerPlayerIndex 변경된 승리자의 playerDataArrayList 에서의 위치
     */
    private void changeToWinnerFromChangedWinner(int changedWinnerPlayerIndex) {

        final String METHOD_NAME = "[changeToWinnerFromChangedWinner] ";

        // [check 2] : winner 가 변경되었을 때, 변경된 Looser(패배자) 가 playerDataArrayList 에서 몇 번째 player 인가?\
        switch (changedWinnerPlayerIndex) {
            case 0:
                // player 0 이 <패배자> -> <승리자>로 변경됨 / userData

                // [iv/C]UserData : 기존값이 패배자여서 추가된 값이 없다. 그러므로 gameRecordWin 를  +1 증가한다. / 기존값이 패배자여서 +1된 값이었다. 그러므로 gameRecordLoss 을 -1 하여서 원상태로 복구한다.
                this.userData.setGameRecordWin(this.userData.getGameRecordWin() + 1);
                this.userData.setGameRecordLoss(this.userData.getGameRecordLoss() - 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 0 이 <패배자> -> <승리자>로 변경되었습니다. / userData");
                break;
            case 1:
                // player 1 이 <패배자> -> <승리자>로 변경됨 / friendDataArrayList.get(0)

                // [iv/C]ArrayList<FriendData> : 기존값이 패배자여서 추가된 값이 없다. 그러므로 gameRecordWin 를  +1 증가한다. / 기존값이 패배자여서 +1된 값이었다. 그러므로 gameRecordLoss 을 -1 하여서 원상태로 복구한다.
                this.friendDataArrayList.get(0).setGameRecordWin(this.friendDataArrayList.get(0).getGameRecordWin() + 1);
                this.friendDataArrayList.get(0).setGameRecordLoss(this.friendDataArrayList.get(0).getGameRecordLoss() - 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1 이 <패배자> -> <승리자>로 변경되었습니다. / friendDataArrayList.get(0)");
                break;

            case 2:
                // player 2 가 <패배자> -> <승리자>로 변경됨 / friendDataArrayList.get(1)

                // [iv/C]ArrayList<FriendData> : 기존값이 패배자여서 추가된 값이 없다. 그러므로 gameRecordWin 를  +1 증가한다. / 기존값이 패배자여서 +1된 값이었다. 그러므로 gameRecordLoss 을 -1 하여서 원상태로 복구한다.
                this.friendDataArrayList.get(1).setGameRecordWin(this.friendDataArrayList.get(1).getGameRecordWin() + 1);
                this.friendDataArrayList.get(1).setGameRecordLoss(this.friendDataArrayList.get(1).getGameRecordLoss() - 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 2 이 <패배자> -> <승리자>로 변경되었습니다. / friendDataArrayList.get(1)");
                break;

            case 3:
                // player 3 이 승리자로 변경됨 / friendDataArrayList.get(2)

                // [iv/C]ArrayList<FriendData> :기존값이 패배자여서 추가된 값이 없다. 그러므로 gameRecordWin 를  +1 증가한다. / 기존값이 패배자여서 +1된 값이었다. 그러므로 gameRecordLoss 을 -1 하여서 원상태로 복구한다.
                this.friendDataArrayList.get(2).setGameRecordWin(this.friendDataArrayList.get(2).getGameRecordWin() + 1);
                this.friendDataArrayList.get(2).setGameRecordLoss(this.friendDataArrayList.get(2).getGameRecordLoss() - 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 3 이 <패배자> -> <승리자>로 변경되었습니다. / friendDataArrayList.get(2)");
                break;

            default:
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "이 게임에 참가한 player 가 아니예요!");
                break;

        } // [check 1]

    } // End of method [changeToWinnerFromChangedWinner]


    /**
     * [method] [Change] 기존 승리자가 누구인지를 판별해서 그 player 를 승리자에서 패배자로 변경한다. 즉 기존의 gameRecordWin 과 gameRecordLoss 를 바꾼다.
     *
     * <p>
     * gameRecordWin -= 1
     * gameRecordLoss += 1
     * </p>
     */
    private void changeToLooserFromInitialWinner() {

        final String METHOD_NAME = "[changeToLooserFromInitialWinner] ";

        // [check 2] : winner 가 변경되었을 때, 변경된 Looser(패배자) 가 playerDataArrayList 에서 몇 번째 player 인가?\
        switch (this.initWinnerPlayerIndex) {
            case 0:
                // player 0 이 패배자로 변경됨 / userData

                // [lv/C]UserData : 기존 승리자인 player 0 을 패배자로 변경 / 기존값이 승리자여서 +1된 값이었다. 그러므로 gameRecordWin 을 -1 하여서 원상태로 복구한다. / 기존값이 승리자여서 추가된 값이 없다. 그러므로 gameRecordLoss 를  +1 증가한다.
                this.userData.setGameRecordWin(this.userData.getGameRecordWin() - 1);
                this.userData.setGameRecordLoss(this.userData.getGameRecordLoss() + 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 0 이 <승리자> -> <패배자>로 변경되었습니다. / userData");
                break;

            case 1:
                // player 1 이 패배자로 변경됨 / friendDataArrayList.get(0)

                // [lv/C]ArrayList<FriendData> : 기존값이 승리자여서 +1된 값이었다. 그러므로 gameRecordWin 을 -1 하여서 원상태로 복구한다. / 기존값이 승리자여서 추가된 값이 없다. 그러므로 gameRecordLoss 를  +1 증가한다.
                this.friendDataArrayList.get(0).setGameRecordWin(this.friendDataArrayList.get(0).getGameRecordWin() - 1);
                this.friendDataArrayList.get(0).setGameRecordLoss(this.friendDataArrayList.get(0).getGameRecordLoss() + 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1 이 <승리자> -> <패배자>로 변경되었습니다. / friendDataArrayList.get(0)");
                break;

            case 2:
                // player 2 가 패배자로 변경됨 / friendDataArrayList.get(1)

                // [lv/C]ArrayList<FriendData> : 기존값이 승리자여서 +1된 값이었다. 그러므로 gameRecordWin 을 -1 하여서 원상태로 복구한다. / 기존값이 승리자여서 추가된 값이 없다. 그러므로 gameRecordLoss 를  +1 증가한다.
                this.friendDataArrayList.get(1).setGameRecordWin(this.friendDataArrayList.get(1).getGameRecordWin() - 1);
                this.friendDataArrayList.get(1).setGameRecordLoss(this.friendDataArrayList.get(1).getGameRecordLoss() + 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 2 이 <승리자> -> <패배자>로 변경되었습니다. / friendDataArrayList.get(1)");
                break;

            case 3:
                // player 3 이 패배자로 변경됨 / friendDataArrayList.get(2)

                // [lv/C]ArrayList<FriendData> : 기존값이 승리자여서 +1된 값이었다. 그러므로 gameRecordWin 을 -1 하여서 원상태로 복구한다. / 기존값이 승리자여서 추가된 값이 없다. 그러므로 gameRecordLoss 를  +1 증가한다.
                this.friendDataArrayList.get(2).setGameRecordWin(this.friendDataArrayList.get(2).getGameRecordWin() - 1);
                this.friendDataArrayList.get(2).setGameRecordLoss(this.friendDataArrayList.get(2).getGameRecordLoss() + 1);
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 3 이 <승리자> -> <패배자>로 변경되었습니다. / friendDataArrayList.get(2)");
                break;

            default:
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "이 게임에 참가한 player 가 아니예요!");
                break;

        } // [check 1]

    } // End of method [changeToLooserFromInitialWinner]


    /**
     * [method] 기존 gameMode 가 변경되었는지 판별하여 gameMode 과 관련된 부분을 변경한다.
     *
     * <p>
     * 1.billiardData
     * -gameMode
     * </p>
     *
     * @param changedGameMode 변경된 게임시간
     */
    public void changeGameMode(String changedGameMode) {

        final String METHOD_NAME = "[changeGameMode] ";

        // [check 1] : 기존 gameMode 가 변경되었다.
        if (!changedGameMode.equals(this.billiardData.getGameMode())) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<gameMode> 가 변경되었습니다.");

            // [iv/C]BilliardData : 변경된 changedGameMode 로 변경하기
            this.billiardData.setGameMode(changedGameMode);

            // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
            this.isChanged = true;

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<gameMode> 가 변경되지 않았어!");
        } // [check 1]

    } // End of method [changeGameMode]


    /**
     * [method] [Change] 기존 playTime 가 변경되었는지 판별하여 playTime 과 관련된 부분을 변경한다.
     *
     * <p>
     * 1. billiardData
     * - playTime
     * 2. userData
     * - totalPlayTime
     * 3. FriendData
     * - totalPlayTime
     * </p>
     *
     * @param changedPlayTime 변경된 게임시간
     */
    public void changePlayTime(int changedPlayTime) {

        final String METHOD_NAME = "[changePlayTime] ";

        // [check 1] : 기존 playTime 이 변경되었다.
        if (changedPlayTime != this.initPlayTime) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<playTime> 이 변경되었습니다. / 기존 initPlayTime = " + this.initPlayTime + " / 변경된 playTime = " + changedPlayTime);

            // [iv/C]BilliardData : 이 게임의 기존 playTime(initPlayTime) 을 변경된 playTime(changePlayTime) 으로 변경한다. / this.billiardData
            this.billiardData.setPlayTime(changedPlayTime);

            // [iv/C]UserData : 기존 TotalPlayTime 에서 초기값(initPlayTime)을 빼고 변경된 playTime(changePlayTime) 을 더한다. / this.userData
            this.userData.setTotalPlayTime(this.userData.getTotalPlayTime() - this.initPlayTime + changedPlayTime);

            // [cycle 1] : player 중 friend 의 수 만큼
            for (int friendIndex = 0; friendIndex < this.friendDataArrayList.size(); friendIndex++) {

                // [iv/C]ArrayList<FriendData> : 모든 player 의 기존 totalPlayTime 에서 초기값(initPlayTime)을 빼고 변경된 playTime(changePlayTime) 을 더한다. / this.friendDataArrayList
                this.friendDataArrayList.get(friendIndex).setTotalPlayTime(this.friendDataArrayList.get(friendIndex).getTotalPlayTime() - this.initPlayTime + changedPlayTime);

            } // [cycle 1]

            // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
            this.isChanged = true;

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<playTime> 가 변경되지 않았어!");
        } // [check 1]

    } // End of method [changePlayTime]


    /**
     * [method] [Change] 기존 cost 가 변경되었는지 판별하여 cost 부분과 관련된 부분을 수정한다.
     *
     * <p>
     * 1. BilliardData
     * - cost
     * 2. UserData
     * - totalCost
     * 3. FriendData
     * - totalCost
     * </p>
     *
     * @param changedCost 변경된 비용
     */
    public void changeCost(int changedCost) {

        final String METHOD_NAME = "[changeCost] ";

        // [check 1] : 기존 cost 가 변경되었다.
        if (changedCost != this.initCost) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<cost> 가 변경되었습니다. / 기존 initCost = " + this.initCost + " / 변경된 cost = " + changedCost);

            // [lv/C]BilliardData : 이 게임의 기존 cost(initCost) 를 변경된 cost(changeCost) 로 변경한다. / this.billiardData
            this.billiardData.setCost(changedCost);

            // [lv/C]UserData : 기존 totalCost 에서 초기값(initCost)을 빼고 변경된 cost(changeCost)로 변경한다. / this.userData
            this.userData.setTotalCost(this.userData.getTotalCost() - this.initCost + changedCost);

            // [cycle 1] : player 중 friend 의 수 만큼
            for (int friendIndex = 0; friendIndex < this.friendDataArrayList.size(); friendIndex++) {

                // [lv/C]ArrayList<FriendData> : 모든 player 의 기존 totalPlayTime 에서 초기값(initPlayTime)을 빼고 변경된 playTime(changePlayTime) 을 더한다. / this.friendDataArrayList
                this.friendDataArrayList.get(friendIndex).setTotalCost(this.friendDataArrayList.get(friendIndex).getTotalCost() - this.initCost + changedCost);

            } // [cycle 1]

            // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
            this.isChanged = true;

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<cost> 가 변경되지 않았어!");
        } // [check 1]

    } // End of method [changeCost]


    /**
     * [method] [Change] 기존 targetScore 가 변경되었느지 판별하여 targetScore 와 관련된 부분을 수정한다.
     *
     * <p>
     * PlayerData
     * - targetScore
     * </p>
     *
     * @param changedTargetScores 변경된 수지(목표점수)
     */
    public void changeTargetScores(int[] changedTargetScores) {

        final String METHOD_NAME = "[changeTargetScores] ";

        // [cycle 1] : player 의 수 만큼
        for (int index = 0; index < this.playerDataArrayList.size(); index++) {

            // [check 1] : targetScore 값이 변경되었다.
            if (changedTargetScores[index] != this.playerDataArrayList.get(index).getTargetScore()) {

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<targetScore> <" + index + ">번째 값은 변경었습니다.");

                // [lv/C]ArrayList<PlayerData> : 기존 targetScore 값을 changeTargetScore 값으로 변경한다.
                this.playerDataArrayList.get(index).setTargetScore(changedTargetScores[index]);

                // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
                this.isChanged = true;

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<targetScore> <" + index + ">번째 값은 변경되지 않았어! ");
            } // [check 1]

        } // [cycle 1]

    } // End of method [changeTargetScores]


    /**
     * [method] [Change] 기존 targetScore 가 변경되었느지 판별하여 targetScore 와 관련된 부분을 수정한다.
     *
     * <p>
     * PlayerData
     * - targetScore
     * </p>
     *
     * @param changedTargetScore 변경된 수지(목표점수)
     * @param index              변경된 수지와 비교할 playerDataArrayList 의 위치
     */
    public void changeTargetScore(int changedTargetScore, int index) {

        final String METHOD_NAME = "[changeTargetScore] ";

        // [check 1] : targetScore 값이 변경되었다.
        if (changedTargetScore != this.playerDataArrayList.get(index).getTargetScore()) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<targetScore> <" + index + ">번째 값은 변경었습니다.");

            // [lv/C]ArrayList<PlayerData> : 기존 targetScore 값을 changeTargetScore 값으로 변경한다.
            this.playerDataArrayList.get(index).setTargetScore(changedTargetScore);

            // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
            this.isChanged = true;

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<targetScore> <" + index + ">번째 값은 변경되지 않았어! ");
        } // [check 1]

    } // End of method [changeTargetScore]


    /**
     * [method] [Change] 기존 score 가 변경되었는지 판별하여 score 와 관련된 부분을 수정한다.
     *
     * <p>
     * 1. Billiard
     * - score
     * 2. PlayerData
     * - score
     * </p>
     *
     * @param changedScores 변경된 모든 score 값
     */
    public void changeScores(int[] changedScores) {

        final String METHOD_NAME = "[changeScores] ";

        // [lv/C]ArrayList<String> : 모든 player 의 score 값을 저장한다. / 스코어의 형태 문자열을 만들기 위해
        ArrayList<String> changedScoreList = new ArrayList<>();

        // [cycle 1] : player 수 만큼
        for (int index = 0; index < this.playerDataArrayList.size(); index++) {

            // [check 1] : 기존 score 값이 변경되었다.
            if (changedScores[index] != this.initScore[index]) {

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player " + index + "> 의 <score> 값이 변경되었습니다.");

                // [lv/C]ArrayList<PlayerData> : 해당 player 의 score 값을 changeScore 값으로 변경
                playerDataArrayList.get(index).setScore(changedScores[index]);

                // [lv/C]ArrayList<String> : changeScoreList 에 changeScore 값을 추가
                changedScoreList.add(changedScores[index] + "");

                // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
                this.isChanged = true;

            } else {

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player " + index + "> 의 <score> 값은 변경되지 않았어!");

                // [lv/C]ArrayList<String> : changeScoreList 에 기존 score(this.initScore) 값을 추가
                changedScoreList.add(this.initScore[index] + "");

            } // [check 1]

        } // [cycle 1]

        // [lv/C]String : 위의 changedScoreList 로 특정 문자열로 만들기
        String changedScoreFormat = ProjectBlueDataFormatter.getFormatOfScore(changedScoreList);

        // [check 2] : billiardData 의 score 가 변경되었다.
        if (!changedScoreFormat.equals(this.billiardData.getScore())) {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiard> 의 <score> 가 변경되었습니다. / 변경 전 = " + this.billiardData.getScore() + " /  변경 후 = " + changedScoreFormat);

            // [lv/C]BilliardCount : 위에서 다시 설정한 player 들의 score 값으로 billiard 의 score 값으로 변경하기
            this.billiardData.setScore(changedScoreFormat);

            // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
            this.isChanged = true;

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiard> 의 <score> 가 변경되지 않았어!");
        } // [check 2]

    } // End of method [changeScores]


    /**
     * [method] [Change] 기존 score 가 변경되었는지 판별하여 score 와 관련된 부분을 수정한다.
     *
     * <p>
     * 1. PlayerData
     * - score
     * </p>
     *
     * @param changedScore 변경된 score 값
     * @param index        몇 번째 player 인가요?
     * @return 변경 검사 후 이 player 의 최종 score 값
     */
    public int changeScoreOfOnePlayerInPlayerDataArrayList(int changedScore, int index) {

        final String METHOD_NAME = "[changeScoreOfOnePlayerInPlayerDataArrayList] ";

        int score;

        // [check 1] : 기존 score 값이 변경되었다.
        if (changedScore != this.initScore[index]) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player " + index + "> 의 <score> 값이 변경되었습니다.");
            // [lv/C]ArrayList<PlayerData> : 해당 player 의 score 값을 changeScore 값으로 변경
            playerDataArrayList.get(index).setScore(changedScore);

            // [lv/i]score : index 번째에 변경된 changedScore 를 추가한다.
            score = changedScore;

            // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
            this.isChanged = true;

        } else {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player " + index + "> 의 <score> 값은 변경되지 않았어!");

            // [lv/i]scores : index 번째에 기존의 score(this.initScore) 를 추가한다.
            score = this.initScore[index];

        } // [check 1]

        return score;
    } // End of method [changeScoreOfOnePlayerInPlayerDataArrayList]


    /**
     * [method] [Change] 기존 score 가 변경되었는지 판별하여 score 와 관련된 부분을 수정한다.
     *
     * <p>
     * 1. Billiard
     * - score
     * 2. PlayerData
     * - score
     * </p>
     *
     * @param finalChangedScore 변경검사가 끝난 최종 scores
     */
    public void changeScoreOfBilliardData(int[] finalChangedScore) {

        final String METHOD_NAME = "[changeScoreOfBilliardData] ";

        // [lv/C]String : 위의 finalChangedScore 로 특정 문자열로 만들기
        String finalChangedScoreFormat = ProjectBlueDataFormatter.getFormatOfScore(finalChangedScore);


        // [check 2] : billiardData 의 score 가 변경되었다.
        if (!finalChangedScoreFormat.equals(this.billiardData.getScore())) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiard> 의 <score> 가 변경되었습니다.");
            // [lv/C]BilliardCount : 위에서 만든 finalChangedScoreFormat 으로 변경하기
            this.billiardData.setScore(finalChangedScoreFormat);

            // [iv/b]isChanged : 변경된 것이 있으므로 true 로 변경
            this.isChanged = true;

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiard> 의 <score> 가 변경되지 않았어!");
        } // [check 2]

    } // End of method [changeScoreOfBilliardData]


    /**
     * [method] [check] score 값이 0 <= score <= targetScore 안의 값이면 true 아니면 false 를 반환한다.
     */
    private boolean checkWhetherInputWithinRangeOfScore(int score, int targetScore) {

        final String METHOD_NAME = "[checkWhetherInputWithinRangeOfScore] ";

        // [lv/b]isWithinRange : 범위 내의 값인가요?
        boolean isWithinRange;

        // [check 1] : 0 <= changeScore <= targetScore
        if ((0 <= score) && (score <= targetScore)) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "변경된 <score> 값이 범위 안의 값이다.");
            // [lv/b]isWithinRange : 범위 안의 값이다.
            isWithinRange = true;

        } else {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "변경된 <score> 값이 잘못된 범위의 값이여!");
            // [lv/b]isWithinRange : 범위 안의 값이 아니여유!
            isWithinRange = false;

        } // [check 1]

        return isWithinRange;
    } // End of method [checkWhetherInputWithinRangeOfScore]


    /**
     * [method] [Check] 변경된 승리자 player 의 targetScore 와 score 가 같은지 판별한다.
     */
    private boolean checkWhetherEqualOfTargetScoreAndScore(long changeWinnerId, String changeWinnerName, int changeWinnerPlayerIndex) {

        final String METHOD_NAME = "[checkWhetherEqualOfTargetScoreAndScore] ";

        // [lv/b]isEqual : 승리자의 targetScore 와 score 값이 같은지 / 왜? 승리자는 목표점수와 같아야 하므로
        boolean isEqual = false;

        // [check 1] : player 의 id 및 name 이 winnerId 와 winnerName 이 같다.
        if ((changeWinnerId == this.playerDataArrayList.get(changeWinnerPlayerIndex).getPlayerId()) && (changeWinnerName.equals(this.playerDataArrayList.get(changeWinnerPlayerIndex).getPlayerName()))) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player " + changeWinnerPlayerIndex + "> 는 winner 입니다.");

            // [check 2] : winner 의 targetScore 와 score 가 같다.
            if (playerDataArrayList.get(changeWinnerPlayerIndex).getTargetScore() == playerDataArrayList.get(changeWinnerPlayerIndex).getScore()) {

                // [lv/b]isEqual : 이 winner 는 진짜 승리자이다.
                isEqual = true;

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player " + changeWinnerPlayerIndex + "> 은 targetScore 와 score 값이 달라요!");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player " + changeWinnerPlayerIndex + "> 는  패배자입니다요!");
        } // [check 1]

        return isEqual;
    } // End of method [checkWhetherEqualOfTargetScoreAndScore]
}