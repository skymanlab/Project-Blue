package com.skyman.billiarddata.management;

import android.app.Activity;

import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.user.database.UserDbManager;

public class SectionManager {

    // instance variable

    // instance variable

    // constructor
    public SectionManager() {

    }


    public interface Initializable {
        void initAppDbManager();

        void connectWidget();

        void initWidget();
    }
}
