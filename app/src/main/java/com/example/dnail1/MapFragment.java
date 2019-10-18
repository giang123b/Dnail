package com.example.dnail1;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
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

import com.example.dnail1.Product.Model;
import com.example.dnail1.Product.ModelAdapter;
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


    TextView txtNhapViTri;
    TextView txtNhapThoiGian;

    TextView txtChonNgay;
    TextView txtChonGio;

    TextView txtSearchingWorker;



    MaterialButton btnOK;
    MaterialButton btnSearchWoker;
    MaterialButton btnOKInSearchLocation;

    TextView txtMoney;

//    LinearLayout lnNhapViTri;
    LinearLayout lnChonThoiGian;
    LinearLayout lnNhapThoiGianViTri;

    LinearLayout lnSellectModel;

    LinearLayout linear_way_to_pay;
    LinearLayout linear_promotion;
    LinearLayout linear_note;
    LinearLayout app_bar_bottom;

//    bottom bar
    LinearLayout linear_price;
    TextView text_promotion;
    TextView text_note;

    CountDownTimer w;

    RecyclerView recyclerView;
    private ModelAdapter adapter;
    private List<Model> modelList;

    ImageView imageView_three_dots;
    ImageView imageView_esc_in_way_to_pay;
    ImageView imageView_esc_in_promotion;
    ImageView imageView_esc_in_note;

    Button button_linearModel_bookWorker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        addControls(root);
        addEvents();

        onMapSearch(root);

        setRecyclerView(root);


        setMap();

        return root;
    }

    private void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rv_model);
        txtMoney = root.findViewById(R.id.txtMoney);

        modelList = new ArrayList<>();
        adapter = new ModelAdapter(getContext(), modelList, txtMoney);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        prepareAlbums();
    }

    private void addEvents() {

        txtNhapViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnChonThoiGian.setVisibility(lnChonThoiGian.GONE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);
                lnSellectModel.setVisibility(lnSellectModel.GONE);
                searchView.setVisibility(searchView.VISIBLE);
                btnOKInSearchLocation.setVisibility(btnOKInSearchLocation.VISIBLE);
                lnNhapThoiGianViTri.setVisibility(lnNhapThoiGianViTri.GONE);

                btnOKInSearchLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtNhapViTri.setText(searchView.getQuery());
                        lnNhapThoiGianViTri.setVisibility(lnNhapThoiGianViTri.VISIBLE);
                        searchView.setVisibility(searchView.GONE);
                        btnOKInSearchLocation.setVisibility(btnOKInSearchLocation.INVISIBLE);
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
//                lnNhapViTri.setVisibility(lnNhapViTri.GONE);
                lnChonThoiGian.setVisibility(lnChonThoiGian.VISIBLE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);
                lnSellectModel.setVisibility(lnSellectModel.GONE);

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

                btnSearchWoker.setText(R.string.text_tim_tho_mong);

                btnOKInSearchLocation.setVisibility(btnOKInSearchLocation.GONE);
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
                showPopupMenu(v);
            }
        });

//        bottom bar
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

    }

    private void addControls(View root) {
        txtChonNgay = root.findViewById(R.id.txtChonNgay);
        txtChonGio = root.findViewById(R.id.txtChonGio);


        txtNhapViTri = root.findViewById(R.id.txtNhapViTri);
        txtNhapThoiGian = root.findViewById(R.id.txtNhapThoiGian);

        txtSearchingWorker = root.findViewById(R.id.txtSearchingWorker);

        btnOK = root.findViewById(R.id.btnOK);
        btnSearchWoker = root.findViewById(R.id.btnSearchWoker);
        btnOKInSearchLocation = root.findViewById(R.id.btnOKInSearchLocation);

//        lnNhapViTri = root.findViewById(R.id.lnNhapViTri);

        lnChonThoiGian = root.findViewById(R.id.lnChonThoiGian);
        lnNhapThoiGianViTri = root.findViewById(R.id.lnNhapThoiGianViTri);

        linear_way_to_pay = root.findViewById(R.id.linear_way_to_pay);
        linear_promotion = root.findViewById(R.id.linear_promotion);
        linear_note = root.findViewById(R.id.linear_note);

        lnSellectModel = root.findViewById(R.id.lnSellectModel);

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
        w=new CountDownTimer(1000, 1000) {
            public void onTick(long mil) {

            }
            public void onFinish() {
                txtSearchingWorker.setVisibility(txtSearchingWorker.GONE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);
                lnSellectModel.setVisibility(lnSellectModel.VISIBLE);
            }
        }.start();
    }


//    MAP
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
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.md1,
                R.drawable.md2,
                R.drawable.md4,
                R.drawable.md5,
                R.drawable.md6,
                R.drawable.md2,
                R.drawable.md2,
                R.drawable.md2,
                R.drawable.md6,
                R.drawable.md6,
                R.drawable.md6,
                R.drawable.md6,
                R.drawable.md6,
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
    private void showPopupMenu(View view) {
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
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.refresh_worker:
                    lnSellectModel.setVisibility(lnSellectModel.GONE);
                    btnSearchWoker.setVisibility(btnSearchWoker.VISIBLE);
                    btnSearchWoker.setText(R.string.text_tim_tho_mong);
                    return true;
                default:
            }
            return false;
        }
    }
}
