package com.example.dnail1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dnail1.Products.Model;
import com.example.dnail1.Products.ModelAdapter;
import com.example.dnail1.Times.Time;
import com.example.dnail1.Times.TimeAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    SearchView searchView;


    TextView text_linearTimeLocation_enterLocation;
    TextView text_linearTimeLocation_enterTime;

    TextView txtSearchingWorker;

    MaterialButton btnOK;
    MaterialButton btnSearchWoker;
    MaterialButton button_linearSearchLocation_ok;

    TextView txtMoney;

//    LinearLayout lnNhapViTri;
    LinearLayout linear_chooseTime;
    LinearLayout linear_time_locaion;

    LinearLayout linear_selectModel;

    LinearLayout linear_way_to_pay;
    LinearLayout linear_promotion;
    LinearLayout linear_note;
    LinearLayout app_bar_bottom;
    LinearLayout linear_bookSuccessful;

//    bottom bar
    LinearLayout linear_price;
    TextView text_promotion;
    TextView text_note;

    CountDownTimer w;

    RecyclerView recyclerView;
    RecyclerView recyclerViewPhu;
    private ModelAdapter adapter;
    private ModelAdapter adapterPhu;
    private List<Model> modelList;
    private List<Model> modelListPhu;

    ImageView imageView_three_dots, imageView_esc_in_way_to_pay, imageView_esc_in_promotion, imageView_esc_in_note;

    Button button_linearModel_bookWorker;

    TextView text_linearBookSuccessful_selectedModel, text_linearBookSuccessful_selectedModelPrice,
            text_linearBookSuccessful_time, text_linearBookSuccessful_location;
    ImageView image_linearBookSuccessful_selectedModel;

    TextView vote;
    TextView vote1;
//    choose time
    private RecyclerView recyclerViewMorning;
    private RecyclerView recyclerViewAfternoon;
    private RecyclerView recyclerViewNight;
    private TimeAdapter adapterMorning;
    private TimeAdapter adapterAfternoon;
    private TimeAdapter adapterNight;
    private List<Time> timeListMorning;
    private List<Time> timeListAfternoon;
    private List<Time> timeListNight;

    private TextView text_chooseTime_morning, text_chooseTime_afternoon, text_chooseTime_night;

    private TextView text_chooseTime_day1, text_chooseTime_day2, text_chooseTime_day3;

    ImageView imageView_linearBookSuccessful_threeDots;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        addControls(root);
        addEvents(root);

        onMapSearch(root);

        setRecyclerView(root);
        setRecyclerViewPhu(root);

        setMap();

        return root;
    }

    // Add id
    private void addControls(View root) {

        text_linearTimeLocation_enterLocation = root.findViewById(R.id.text_linearTimeLocation_enterLocation);
        text_linearTimeLocation_enterTime = root.findViewById(R.id.text_linearTimeLocation_enterTime);

        txtSearchingWorker = root.findViewById(R.id.txtSearchingWorker);

        btnOK = root.findViewById(R.id.btnOK);
        btnSearchWoker = root.findViewById(R.id.btnSearchWoker);
        button_linearSearchLocation_ok = root.findViewById(R.id.button_linearSearchLocation_ok);

        linear_chooseTime = root.findViewById(R.id.linear_chooseTime);
        linear_time_locaion = root.findViewById(R.id.linear_time_locaion);

        linear_way_to_pay = root.findViewById(R.id.linear_way_to_pay);
        linear_promotion = root.findViewById(R.id.linear_promotion);
        linear_note = root.findViewById(R.id.linear_note);

        linear_selectModel = root.findViewById(R.id.linear_selectModel);

        searchView = root.findViewById(R.id.sv_location);

        imageView_three_dots = root.findViewById(R.id.imageView_three_dots);
        imageView_esc_in_way_to_pay = root.findViewById(R.id.imageView_esc_in_way_to_pay);
        imageView_esc_in_promotion = root.findViewById(R.id.imageView_esc_in_promotion);
        imageView_esc_in_note = root.findViewById(R.id.imageView_esc_in_note);

        linear_price = root.findViewById(R.id.linear_price);
        text_promotion = root.findViewById(R.id.text_promotion);
        text_note = root.findViewById(R.id.text_note);
        app_bar_bottom = root.findViewById(R.id.app_bar_bottom);

        button_linearModel_bookWorker = root.findViewById(R.id.button_linearModel_bookWorker);
        linear_bookSuccessful = root.findViewById(R.id.linear_bookSuccessful);

        text_linearBookSuccessful_selectedModel = root.findViewById(R.id.text_linearBookSuccessful_selectedModel);
        text_linearBookSuccessful_selectedModelPrice = root.findViewById(R.id.text_linearBookSuccessful_selectedModelPrice);
        text_linearBookSuccessful_location = root.findViewById(R.id.text_linearBookSuccessful_location);
        text_linearBookSuccessful_time = root.findViewById(R.id.text_linearBookSuccessful_time);
        image_linearBookSuccessful_selectedModel = root.findViewById(R.id.image_linearBookSuccessful_selectedModel);

        vote = root.findViewById(R.id.text_linearSelectModel_vote);
        vote1 = root.findViewById(R.id.text_linearSelectModel_vote1);

        recyclerView = root.findViewById(R.id.rv_model);
        recyclerViewPhu = root.findViewById(R.id.rv_model1);

        imageView_linearBookSuccessful_threeDots = root.findViewById(R.id.imageView_linearBookSuccessful_threeDots);

    }

    private void addEvents(View view) {

        text_linearTimeLocation_enterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_chooseTime.setVisibility(linear_chooseTime.GONE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);
                linear_selectModel.setVisibility(linear_selectModel.GONE);
                searchView.setVisibility(searchView.VISIBLE);
                button_linearSearchLocation_ok.setVisibility(button_linearSearchLocation_ok.VISIBLE);
                linear_time_locaion.setVisibility(linear_time_locaion.GONE);

                button_linearSearchLocation_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text_linearTimeLocation_enterLocation.setText(searchView.getQuery());
                        linear_time_locaion.setVisibility(linear_time_locaion.VISIBLE);
                        searchView.setVisibility(searchView.GONE);
                        btnSearchWoker.setVisibility(btnSearchWoker.VISIBLE);
                        button_linearSearchLocation_ok.setVisibility(button_linearSearchLocation_ok.GONE);
                    }
                });
            }
        });

        text_linearTimeLocation_enterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_chooseTime.setVisibility(linear_chooseTime.VISIBLE);

                btnOK.setVisibility(btnOK.VISIBLE);

                btnSearchWoker.setVisibility(btnSearchWoker.GONE);

                linear_selectModel.setVisibility(linear_selectModel.GONE);

                button_linearSearchLocation_ok.setVisibility(button_linearSearchLocation_ok.GONE);
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_chooseTime.setVisibility(linear_chooseTime.GONE);
                btnSearchWoker.setVisibility(btnSearchWoker.VISIBLE);
                btnOK.setVisibility(btnOK.GONE);
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

        imageView_three_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenuInSelectModel(v);
            }
        });

        imageView_linearBookSuccessful_threeDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenuInBookSuccessful(v);
            }
        });

//        bottom bar
        setBottomBar();

//        set linear choose time
        setLinearChooseTime(view);

    }

    //        set linear choose time
    public String day ="";

    private void setLinearChooseTime(View view) {

        text_chooseTime_day1 = view.findViewById(R.id.text_chooseTime_day1);
        text_chooseTime_day2 = view.findViewById(R.id.text_chooseTime_day2);
        text_chooseTime_day3 = view.findViewById(R.id.text_chooseTime_day3);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("dd");
        SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MM");
        String month = simpleDateFormatMonth.format(calendar.getTime());

        String day1 = String.valueOf(Integer.parseInt(simpleDateFormatDay.format(calendar.getTime())));
        String day2 = String.valueOf(Integer.parseInt(simpleDateFormatDay.format(calendar.getTime())) + 1);
        String day3 = String.valueOf(Integer.parseInt(simpleDateFormatDay.format(calendar.getTime())) + 2);

        text_chooseTime_day1.setText(day1 + "/" + month);
        text_chooseTime_day2.setText(day2 + "/" + month);
        text_chooseTime_day3.setText(day3 + "/" + month);

        day = (String) text_chooseTime_day1.getText();

        text_chooseTime_day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_chooseTime_day1.setBackgroundResource(R.drawable.shape_round_full_pink);
                text_chooseTime_day1.setTextColor(getResources().getColor(R.color.white));
                text_chooseTime_day2.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_day2.setTextColor(getResources().getColor(R.color.pink_theme));
                text_chooseTime_day3.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_day3.setTextColor(getResources().getColor(R.color.pink_theme));

                day = (String) text_chooseTime_day1.getText();
                text_linearTimeLocation_enterTime.setText(day);
            }
        });

        text_chooseTime_day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_chooseTime_day2.setBackgroundResource(R.drawable.shape_round_full_pink);
                text_chooseTime_day2.setTextColor(getResources().getColor(R.color.white));
                text_chooseTime_day1.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_day1.setTextColor(getResources().getColor(R.color.pink_theme));
                text_chooseTime_day3.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_day3.setTextColor(getResources().getColor(R.color.pink_theme));

                day = (String) text_chooseTime_day2.getText();
                text_linearTimeLocation_enterTime.setText(day);
            }
        });

        text_chooseTime_day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_chooseTime_day3.setBackgroundResource(R.drawable.shape_round_full_pink);
                text_chooseTime_day3.setTextColor(getResources().getColor(R.color.white));
                text_chooseTime_day1.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_day1.setTextColor(getResources().getColor(R.color.pink_theme));
                text_chooseTime_day2.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_day2.setTextColor(getResources().getColor(R.color.pink_theme));

                day = (String) text_chooseTime_day3.getText();
                text_linearTimeLocation_enterTime.setText(day);
            }
        });

        text_chooseTime_morning = view.findViewById(R.id.text_chooseTime_morning);
        text_chooseTime_afternoon = view.findViewById(R.id.text_chooseTime_afternoon);
        text_chooseTime_night = view.findViewById(R.id.text_chooseTime_night);

        text_chooseTime_morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_chooseTime_morning.setBackgroundResource(R.drawable.shape_round_full_pink);
                text_chooseTime_morning.setTextColor(getResources().getColor(R.color.white));
                text_chooseTime_afternoon.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_afternoon.setTextColor(getResources().getColor(R.color.pink_theme));
                text_chooseTime_night.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_night.setTextColor(getResources().getColor(R.color.pink_theme));

                recyclerViewMorning.setVisibility(recyclerViewMorning.VISIBLE);
                recyclerViewAfternoon.setVisibility(recyclerViewAfternoon.GONE);
                recyclerViewNight.setVisibility(recyclerViewNight.GONE);
            }
        });

        text_chooseTime_afternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_chooseTime_afternoon.setBackgroundResource(R.drawable.shape_round_full_pink);
                text_chooseTime_afternoon.setTextColor(getResources().getColor(R.color.white));
                text_chooseTime_morning.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_morning.setTextColor(getResources().getColor(R.color.pink_theme));
                text_chooseTime_night.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_night.setTextColor(getResources().getColor(R.color.pink_theme));

                recyclerViewMorning.setVisibility(recyclerViewMorning.GONE);
                recyclerViewAfternoon.setVisibility(recyclerViewAfternoon.VISIBLE);
                recyclerViewNight.setVisibility(recyclerViewNight.GONE);
            }
        });

        text_chooseTime_night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_chooseTime_night.setBackgroundResource(R.drawable.shape_round_full_pink);
                text_chooseTime_night.setTextColor(getResources().getColor(R.color.white));
                text_chooseTime_afternoon.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_afternoon.setTextColor(getResources().getColor(R.color.pink_theme));
                text_chooseTime_morning.setBackgroundResource(R.drawable.shape_round_not_full_pink);
                text_chooseTime_morning.setTextColor(getResources().getColor(R.color.pink_theme));

                recyclerViewMorning.setVisibility(recyclerViewMorning.GONE);
                recyclerViewAfternoon.setVisibility(recyclerViewAfternoon.GONE);
                recyclerViewNight.setVisibility(recyclerViewNight.VISIBLE);
            }
        });

        rcMorning(view);
        recAfternoon(view);
        rcNight(view);
    }

//    Choose time
    private void rcNight(View view) {
        recyclerViewNight =  view.findViewById(R.id.rc_timeNight);

        timeListNight = new ArrayList<>();
        adapterNight = new TimeAdapter(getContext(), timeListNight, text_linearTimeLocation_enterTime,
                (String) text_linearTimeLocation_enterTime.getText());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewNight.setLayoutManager(mLayoutManager);
        recyclerViewNight.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerViewNight.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNight.setAdapter(adapterNight);

        prepareAlbumsNight();
    }

    private void recAfternoon(View view) {
        recyclerViewAfternoon =  view.findViewById(R.id.rc_timeAfternoon);

        timeListAfternoon = new ArrayList<>();
//        chooseDay(view);
        adapterAfternoon = new TimeAdapter(getContext(), timeListAfternoon, text_linearTimeLocation_enterTime,
                (String) text_linearTimeLocation_enterTime.getText());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewAfternoon.setLayoutManager(mLayoutManager);
        recyclerViewAfternoon.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerViewAfternoon.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAfternoon.setAdapter(adapterAfternoon);

//        hour = adapterAfternoon.getHour();
//        text_linearTimeLocation_enterTime.setText(hour + " - " + day);
        prepareAlbumsAfternoon();

    }

    private void rcMorning(View view) {

        recyclerViewMorning =  view.findViewById(R.id.rc_time);

        timeListMorning = new ArrayList<>();
//        chooseDay(view);
        adapterMorning = new TimeAdapter(getContext(), timeListMorning, text_linearTimeLocation_enterTime,
                (String) text_linearTimeLocation_enterTime.getText());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewMorning.setLayoutManager(mLayoutManager);
        recyclerViewMorning.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerViewMorning.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMorning.setAdapter(adapterMorning);

//        hour = adapterMorning.getHour();
//        text_linearTimeLocation_enterTime.setText(hour + " - " + day);
        prepareAlbumsMoring();
    }

    private void prepareAlbumsMoring() {

        Time a = new Time( "8:00");
        timeListMorning.add(a);

        a = new Time("8:30");
        timeListMorning.add(a);

        a = new Time("9:00");
        timeListMorning.add(a);

        a = new Time("9:30");
        timeListMorning.add(a);

        a = new Time("10:00");
        timeListMorning.add(a);

        a = new Time("10:30");
        timeListMorning.add(a);

        a = new Time("11:00");
        timeListMorning.add(a);

        a = new Time("11:30");
        timeListMorning.add(a);

        adapterMorning.notifyDataSetChanged();
    }

    private void prepareAlbumsAfternoon() {

        Time a = new Time( "12:00");
        timeListAfternoon.add(a);

        a = new Time("12:30");
        timeListAfternoon.add(a);

        a = new Time("13:00");
        timeListAfternoon.add(a);

        a = new Time("13:30");
        timeListAfternoon.add(a);

        a = new Time("14:00");
        timeListAfternoon.add(a);

        a = new Time("14:30");
        timeListAfternoon.add(a);

        a = new Time("15:00");
        timeListAfternoon.add(a);

        a = new Time("15:30");
        timeListAfternoon.add(a);

        a = new Time("16:00");
        timeListAfternoon.add(a);

        a = new Time("16:30");
        timeListAfternoon.add(a);

        a = new Time("17:00");
        timeListAfternoon.add(a);

        a = new Time("17:30");
        timeListAfternoon.add(a);


        adapterAfternoon.notifyDataSetChanged();
    }

    private void prepareAlbumsNight() {

        Time a = new Time( "18:00");
        timeListNight.add(a);

        a = new Time("18:30");
        timeListNight.add(a);

        a = new Time("19:00");
        timeListNight.add(a);

        a = new Time("19:30");
        timeListNight.add(a);

        a = new Time("20:00");
        timeListNight.add(a);

        a = new Time("20:30");
        timeListNight.add(a);

        a = new Time("21:00");
        timeListNight.add(a);

        a = new Time("21:30");
        timeListNight.add(a);

        adapterNight.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //        bottom bar
    private void setBottomBar() {
        linear_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_way_to_pay.setVisibility(linear_way_to_pay.VISIBLE);
                linear_promotion.setVisibility(linear_promotion.GONE);
                linear_note.setVisibility(linear_note.GONE);
                app_bar_bottom.setVisibility(app_bar_bottom.GONE);
                button_linearModel_bookWorker.setVisibility(button_linearModel_bookWorker.GONE);
            }
        });

        imageView_esc_in_way_to_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_way_to_pay.setVisibility(linear_way_to_pay.GONE);
                app_bar_bottom.setVisibility(app_bar_bottom.VISIBLE);
                button_linearModel_bookWorker.setVisibility(button_linearModel_bookWorker.VISIBLE);
            }
        });

        text_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_promotion.setVisibility(linear_promotion.VISIBLE);
                linear_way_to_pay.setVisibility(linear_way_to_pay.GONE);
                linear_note.setVisibility(linear_note.GONE);
                app_bar_bottom.setVisibility(app_bar_bottom.GONE);
                button_linearModel_bookWorker.setVisibility(button_linearModel_bookWorker.GONE);
            }
        });

        imageView_esc_in_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_promotion.setVisibility(linear_promotion.GONE);
                app_bar_bottom.setVisibility(app_bar_bottom.VISIBLE);
                button_linearModel_bookWorker.setVisibility(button_linearModel_bookWorker.VISIBLE);
            }
        });

        text_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_note.setVisibility(linear_note.VISIBLE);
                linear_way_to_pay.setVisibility(linear_way_to_pay.GONE);
                linear_promotion.setVisibility(linear_promotion.GONE);
                app_bar_bottom.setVisibility(app_bar_bottom.GONE);
                button_linearModel_bookWorker.setVisibility(button_linearModel_bookWorker.GONE);
            }
        });

        imageView_esc_in_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_note.setVisibility(linear_note.GONE);
                app_bar_bottom.setVisibility(app_bar_bottom.VISIBLE);
                button_linearModel_bookWorker.setVisibility(button_linearModel_bookWorker.VISIBLE);
            }
        });

        button_linearModel_bookWorker.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                linear_bookSuccessful.setVisibility(linear_bookSuccessful.VISIBLE);
                linear_time_locaion.setVisibility(linear_time_locaion.GONE);
                linear_selectModel.setVisibility(linear_selectModel.GONE);
                button_linearModel_bookWorker.setVisibility(button_linearModel_bookWorker.GONE);

                text_linearBookSuccessful_time.setText(getString(R.string.text_thoi_gian)+ text_linearTimeLocation_enterTime.getText());
                text_linearBookSuccessful_location.setText(getString(R.string.text_dia_diem) + text_linearTimeLocation_enterLocation.getText());

            }
        });
    }

    private void timeCountDown() {
        w=new CountDownTimer(1000, 1000) {
            public void onTick(long mil) {

            }
            public void onFinish() {
                txtSearchingWorker.setVisibility(txtSearchingWorker.GONE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);
                linear_selectModel.setVisibility(linear_selectModel.VISIBLE);
                btnSearchWoker.setText(R.string.text_tim_tho_mong);
                button_linearModel_bookWorker.setVisibility(button_linearModel_bookWorker.VISIBLE);
            }
        }.start();
    }


//    MAP
    private void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    
    public void onMapSearch(View view) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList =geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }
    
    @Override
    public void onConnectionSuspended(int i) {
    }
    
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getContext(), "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

//    RecyclerView mau mong
    private void setRecyclerView(View root) {
        txtMoney = root.findViewById(R.id.txtMoney);
    
        modelList = new ArrayList<>();
        adapter = new ModelAdapter(getContext(), modelList, txtMoney, text_linearBookSuccessful_selectedModel,
                text_linearBookSuccessful_selectedModelPrice, image_linearBookSuccessful_selectedModel);
    
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    
        prepareAlbums();
    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.md1,
                R.drawable.md2,
                R.drawable.md4,
                R.drawable.md5,
                R.drawable.md6,
                R.drawable.md3,
                R.drawable.md7,
                R.drawable.md8,
                R.drawable.md9,
                R.drawable.md10,
                R.drawable.md11,
                R.drawable.md12,
                R.drawable.md13,
        };

        Model a = new Model("Mẫu 1", 120, covers[0]);
        modelList.add(a);

        a = new Model("Mẫu 2", 220, covers[1]);
        modelList.add(a);

        a = new Model("Mẫu 3", 125, covers[2]);
        modelList.add(a);

        a = new Model("Mẫu 4", 240, covers[3]);
        modelList.add(a);

        a = new Model("Mẫu 5", 200, covers[4]);
        modelList.add(a);

        a = new Model("Mẫu 6", 150, covers[5]);
        modelList.add(a);

        a = new Model("Mẫu 7", 400, covers[6]);
        modelList.add(a);

        a = new Model("Mẫu 8", 110, covers[7]);
        modelList.add(a);

        a = new Model("Mẫu 9", 90, covers[8]);
        modelList.add(a);

        a = new Model("Mẫu 10", 100, covers[9]);
        modelList.add(a);

        adapter.notifyDataSetChanged();
    }

    //    Tao su kien cho doi mau mong
    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenuInSelectModel(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_refresh_woker, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    int refesh = 1;
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.refresh_worker:
                    if (refesh > 0){
                        refesh = -1;
                        linear_selectModel.setVisibility(linear_selectModel.GONE);
                        recyclerView.setVisibility(recyclerView.GONE);

                        txtSearchingWorker.setVisibility(txtSearchingWorker.VISIBLE);
                        recyclerViewPhu.setVisibility(recyclerViewPhu.VISIBLE);

                        vote.setVisibility(vote.GONE);
                        vote1.setVisibility(vote1.VISIBLE);

                        timeCountDown();
                    }
                    else{
                        refesh = 1;
                        linear_selectModel.setVisibility(linear_selectModel.GONE);
                        recyclerViewPhu.setVisibility(recyclerViewPhu.GONE);

                        txtSearchingWorker.setVisibility(txtSearchingWorker.VISIBLE);
                        recyclerView.setVisibility(recyclerView.VISIBLE);

                        vote1.setVisibility(vote1.GONE);
                        vote.setVisibility(vote.VISIBLE);

                        timeCountDown();
                    }
                    return true;
                default:
            }
            return false;
        }
    }

    private void showPopupMenuInBookSuccessful(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_complete_book, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListenerInBookSuccessful());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListenerInBookSuccessful implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListenerInBookSuccessful() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.refresh_worker:
                    linear_bookSuccessful.setVisibility(linear_bookSuccessful.GONE);
                    linear_time_locaion.setVisibility(linear_time_locaion.VISIBLE);
                    btnSearchWoker.setVisibility(btnSearchWoker.VISIBLE);
                    return true;
                default:
            }
            return false;
        }
    }

    // recyclerview Phu
    private void setRecyclerViewPhu(View root) {
        txtMoney = root.findViewById(R.id.txtMoney);

        modelListPhu = new ArrayList<>();
        adapterPhu = new ModelAdapter(getContext(), modelListPhu, txtMoney, text_linearBookSuccessful_selectedModel,
                text_linearBookSuccessful_selectedModelPrice, image_linearBookSuccessful_selectedModel);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPhu.setLayoutManager(mLayoutManager);
        recyclerViewPhu.setAdapter(adapterPhu);

        prepareAlbumsPhu();
    }

    private void prepareAlbumsPhu() {
        int[] covers = new int[]{
                R.drawable.md10,
                R.drawable.md11,
                R.drawable.md12,
                R.drawable.md13,
                R.drawable.md6,
                R.drawable.md3,
                R.drawable.md7,
                R.drawable.md8,
                R.drawable.md9,
                R.drawable.md1,
                R.drawable.md2,
                R.drawable.md4,
                R.drawable.md5,
        };

        Model a = new Model("Mẫu 1", 250, covers[0]);
        modelListPhu.add(a);

        a = new Model("Mẫu 2", 100, covers[1]);
        modelListPhu.add(a);

        a = new Model("Mẫu 3", 90, covers[2]);
        modelListPhu.add(a);

        a = new Model("Mẫu 4", 400, covers[3]);
        modelListPhu.add(a);

        a = new Model("Mẫu 5", 75, covers[4]);
        modelListPhu.add(a);

        a = new Model("Mẫu 6", 200, covers[5]);
        modelListPhu.add(a);

        a = new Model("Mẫu 7", 120, covers[6]);
        modelListPhu.add(a);

        a = new Model("Mẫu 8", 110, covers[7]);
        modelListPhu.add(a);

        a = new Model("Mẫu 9", 90, covers[8]);
        modelListPhu.add(a);

        a = new Model("Mẫu 10", 100, covers[9]);
        modelListPhu.add(a);

        adapterPhu.notifyDataSetChanged();
    }
}
