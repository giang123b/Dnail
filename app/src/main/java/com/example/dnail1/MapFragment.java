package com.example.dnail1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;


public class MapFragment extends Fragment{

    TextView txtNhapViTri;
    TextView txtNhapThoiGian;

    TextView txtChonNgay;
    TextView txtChonGio;

    TextView txtSearchingWorker;

    MaterialButton btnOK;
    MaterialButton btnSearchWoker;

    TextView txtViTri1;
    TextView txtViTri2;

    LinearLayout lnNhapViTri;
    LinearLayout lnChonThoiGian;
    LinearLayout lnNhapThoiGianViTri;

    LinearLayout lnSellectModel;

    CountDownTimer w;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        addControls(root);
        addEvents();

//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.container, new map())
//                .commit();

        return root;
    }


    private void addEvents() {

        thingNeedGONE();

        txtNhapViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnChonThoiGian.setVisibility(lnChonThoiGian.GONE);
                lnNhapViTri.setVisibility(lnNhapViTri.VISIBLE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);


                txtViTri1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtNhapViTri.setText(txtViTri1.getText());
                    }
                });
                txtViTri2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtNhapViTri.setText(txtViTri2.getText());
                    }
                });
            }
        });

        lnChonThoiGian.setVisibility(lnChonThoiGian.GONE);
        txtNhapThoiGian.setOnClickListener(new View.OnClickListener() {
            String ngay = "";
            String gio = "";
            @Override
            public void onClick(View view) {
                lnNhapViTri.setVisibility(lnNhapViTri.GONE);
                lnChonThoiGian.setVisibility(lnChonThoiGian.VISIBLE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);

                txtChonNgay.setOnClickListener(new View.OnClickListener() {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                calendar.set(i, i1, i2);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                txtChonNgay.setText(simpleDateFormat.format(calendar.getTime()));
                                ngay = simpleDateFormat.format(calendar.getTime());

                                txtNhapThoiGian.setText(ngay + " - " + gio);
                            }
                        }, year, month, day);
                        datePicker.show();
                    }
                });

                txtChonGio.setOnClickListener(new View.OnClickListener() {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                calendar.set(0, 0, 0, i, i1);
                                txtChonGio.setText(simpleDateFormat.format(calendar.getTime()));
                                gio = simpleDateFormat.format(calendar.getTime());

                                txtNhapThoiGian.setText(ngay + " - " + gio);
                            }
                        }, hour, minute, true);
                        timePicker.show();
                    }
                });

            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnChonThoiGian.setVisibility(lnChonThoiGian.GONE);
                btnSearchWoker.setVisibility(btnSearchWoker.VISIBLE);
            }
        });

        btnSearchWoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSearchingWorker.setVisibility(txtSearchingWorker.VISIBLE);
                btnSearchWoker.setText(R.string.text_huy);
                timeCountDown();
            }
        });

    }

    private void thingNeedGONE() {
        lnNhapViTri.setVisibility(lnNhapViTri.GONE);
        lnChonThoiGian.setVisibility(lnChonThoiGian.GONE);
        btnSearchWoker.setVisibility(btnSearchWoker.GONE);
        txtSearchingWorker.setVisibility(txtSearchingWorker.GONE);
        lnSellectModel.setVisibility(lnSellectModel.GONE);
    }

    private void addControls(View root) {
        txtChonNgay = root.findViewById(R.id.txtChonNgay);
        txtChonGio = root.findViewById(R.id.txtChonGio);

        txtViTri1 = root.findViewById(R.id.txtViTri1);
        txtViTri2 = root.findViewById(R.id.txtViTri2);

        txtNhapViTri = root.findViewById(R.id.txtNhapViTri);
        txtNhapThoiGian = root.findViewById(R.id.txtNhapThoiGian);

        txtSearchingWorker = root.findViewById(R.id.txtSearchingWorker);

        btnOK = root.findViewById(R.id.btnOK);

        btnSearchWoker = root.findViewById(R.id.btnSearchWoker);

        lnNhapViTri = root.findViewById(R.id.lnNhapViTri);

        lnChonThoiGian = root.findViewById(R.id.lnChonThoiGian);
        lnNhapThoiGianViTri = root.findViewById(R.id.lnNhapThoiGianViTri);

        lnSellectModel = root.findViewById(R.id.lnSellectModel);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void timeCountDown() {
        w=new CountDownTimer(3000, 1000) {
            public void onTick(long mil) {

            }
            public void onFinish() {
                txtSearchingWorker.setVisibility(txtSearchingWorker.GONE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);
                lnSellectModel.setVisibility(lnSellectModel.VISIBLE);
            }
        }.start();
    }
}
