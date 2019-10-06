package com.example.dnail1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dnail1.navigations.NavigationHost;


public class OTPFragment extends Fragment {

    CountDownTimer w;
    TextView second;
    Button btn_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_otp, container, false);

        timeCountDown();
        addControls(root);
        addEvents();

        return root;
    }

    private void addEvents() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new RegisterFragment(), false); // Navigate to the next Fragment
            }
        });
    }

    private void timeCountDown() {
        w=new CountDownTimer(30000, 1000) {
            public void onTick(long mil) {
                second.setText("Gửi lại mã OTP sau: " + mil/1000 + " giây");
            }
            public void onFinish() {
                second.setText("Gửi lại mã OTP sau: 0 giây");
            }
        }.start();
    }

    private void addControls(View root) {
        second = root.findViewById(R.id.second_countdown);
        btn_next = root.findViewById(R.id.btn_next_otp);

    }
}
