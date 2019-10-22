package com.example.dnail1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dnail1.navigations.NavigationHost;


public class RegisterFragment extends Fragment {

    Button btn_complete;
    ImageView image_registerFragment_back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        addControls(root);
        addEvents();
        return root;
    }

    private void addEvents() {
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new BookMapFragment(), false); // Navigate to the next Fragment
            }
        });
        image_registerFragment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new OTPFragment(), false); // Navigate to the next Fragment
            }
        });
    }

    private void addControls(View root) {
        btn_complete = root.findViewById(R.id.btn_next_register);
        image_registerFragment_back = root.findViewById(R.id.image_registerFragment_back);
    }
}
