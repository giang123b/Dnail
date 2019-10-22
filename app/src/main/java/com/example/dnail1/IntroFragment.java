package com.example.dnail1;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dnail1.navigations.NavigationHost;


public class IntroFragment extends Fragment {
    String EXTRA_PHONE = "PHONE";
    EditText editText_introFragment_phoneNumber;
    Button btn_next;
//    TextView text_otpFragment_phoneNumber;

    // send phone number
    SendData sendData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_intro, container, false);

        addControls(root);
        addEvent();
        return root;
    }

    private void addEvent() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPhoneValid(editText_introFragment_phoneNumber.getText())) {
                    editText_introFragment_phoneNumber.setError(getString(R.string.text_sdt_khong_dung));
//                    Toast.makeText(getActivity(), getString(R.string.text_sdt_khong_dung), Toast.LENGTH_SHORT).show();
                } else {
                    editText_introFragment_phoneNumber.setError(null); // Clear the error
                    ((NavigationHost) getActivity()).navigateTo(new OTPFragment(), false); // Navigate to the next Fragment
//
//                    Editable phone = editText_introFragment_phoneNumber.getText();
//                    text_otpFragment_phoneNumber.setText( "+8333333334");

                    String phoneNumber = editText_introFragment_phoneNumber.getText().toString();
                    sendData.sendText(phoneNumber);
                }
            }
        });
        
    }


    private void addControls(View root) {
        editText_introFragment_phoneNumber = root.findViewById(R.id.editText_introFragment_phoneNumber);
        btn_next = root.findViewById(R.id.btn_next_intro);
//        text_otpFragment_phoneNumber = root.findViewById(R.id.text_otpFragment_phoneNumber);
    }

    private boolean isPhoneValid(@Nullable Editable text) {
        return text != null && text.length() >= 0;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            sendData = (SendData) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }
}
