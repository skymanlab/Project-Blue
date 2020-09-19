package com.skyman.billiarddata.factivity.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbHelper;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInput#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInput extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // value : activity 의 widget 객체 선언
    EditText userName;
    EditText targetScore;
    RadioGroup speciality;
    Button input;

    // value : SQLite DB Helper Manager 객체 선언
    UserDbManager userDbManager = null;

    public UserInput() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInputData.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInput newInstance(String param1, String param2) {
        UserInput fragment = new UserInput();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_input, container, false);

        // 데이터 있는지 확인
        userDbManager = new UserDbManager(view.getContext());
        userDbManager.init_tables();

        ArrayList<UserData> userDataArrayList = userDbManager.load_contents();

        // text view : user name setting
        userName = (EditText) view.findViewById(R.id.f_userinput_user_name);

        // text view : target score setting
        targetScore = (EditText) view.findViewById(R.id.f_userinput_target_score);

        // radio group : speciality setting
        speciality = (RadioGroup) view.findViewById(R.id.f_userInput_speciality);
        // button : input setting
        input = (Button) view.findViewById(R.id.f_userinput_input);

        if( userDataArrayList.size() == 0) {


            input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton selectedRadioButton = (RadioButton) view.findViewById(speciality.getCheckedRadioButtonId());

                    Toast.makeText(view.getContext(), "내용 : " + selectedRadioButton.getText().toString(), Toast.LENGTH_SHORT).show();

                    if (!targetScore.getText().toString().equals("")) {

                        userDbManager.save_content(userName.getText().toString(),
                                Integer.parseInt(targetScore.getText().toString()),
                                selectedRadioButton.getText().toString(),
                                0,
                                0,
                                0,
                                0);
                    } else {
                        DeveloperManager.displayLog("UserInput", "일단 수지를 입력해주세요.");
                    }
                }
            });
        } else {
            userName.setEnabled(false);
            targetScore.setEnabled(false);
            speciality.setEnabled(false);
            input.setEnabled(false);
        }




        // Inflate the layout for this fragment
        return view;
    }
}