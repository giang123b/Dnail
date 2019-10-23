package com.example.dnail1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.dnail1.navigations.NavigationHost;

public class MainActivity extends AppCompatActivity implements NavigationHost {
//  , IntroFragment.SendData
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new IntroFragment())
                    .commit();
        }
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);
            transaction.addToBackStack(null);

        transaction.commit();
    }

//    @Override
//    public void onMessageSend(String message) {
//        OTPFragment otpFragment = new OTPFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("phoneNumber", message);
//        otpFragment.setArguments(bundle);
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().
//        replace(R.id.container, otpFragment, null);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }

//    @Override
//    public void sendData(String message) {
//        String tag = "android:switcher:" + R.id.OTP_fragment + ":" + 1;
//        OTPFragment f = (OTPFragment) getSupportFragmentManager().findFragmentByTag(tag);
//        f.displayReceivedData(message);
//    }

//    @Override
//    public void sendText(String phoneNumber) {
//        OTPFragment otpFragment = new OTPFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("phoneNumber", phoneNumber);
//        otpFragment.setArguments(bundle);
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().
//                replace(R.id.container, otpFragment, null);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
}
