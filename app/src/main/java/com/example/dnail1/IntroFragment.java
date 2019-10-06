package com.example.dnail1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dnail1.navigations.NavigationHost;


public class IntroFragment extends Fragment {
    String EXTRA_PHONE = "PHONE";
    EditText edt_phone;
    Button btn_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View root = inflater.inflate(R.layout.fragment_intro, container, false);

        addControls(root);
//        addEvent(root);
//        sendPhone();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPhoneValid(edt_phone.getText())) {
                    edt_phone.setError(getString(R.string.text_sdt_khong_dung));
                } else {
                    edt_phone.setError(null); // Clear the error
                    ((NavigationHost) getActivity()).navigateTo(new OTPFragment(), false); // Navigate to the next Fragment
                }
            }
        });
        return root;
    }


    //    public void addEvent(View root) {
//        Intent intent = new Intent(getActivity(), OTPFragment.class);
//        startActivity(intent);
//    }
//
//
//    private void sendPhone() {
//        Intent intentResult = new Intent();
//
//        if (edt_phone.getText().toString() != null){
//            intentResult.putExtra(EXTRA_PHONE, edt_phone.getText().toString());
//            getActivity().setResult(Activity.RESULT_OK, intentResult);
//        }
//        else{
//            Toast.makeText(getContext(), R.string.text_sdt_trong, Toast.LENGTH_LONG).show();
//        }
//    }
//
    private void addControls(View root) {
        edt_phone = root.findViewById(R.id.edt_phone);
        btn_next = root.findViewById(R.id.btn_next_intro);
    }

    private boolean isPhoneValid(@Nullable Editable text) {
        return text != null && text.length() >= 1;
    }

}
