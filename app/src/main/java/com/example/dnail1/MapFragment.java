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

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

    TextView txtViTri1;
    TextView txtViTri2;

//    LinearLayout lnNhapViTri;
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

        onMapSearch(root);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

//    private  void chuyenSangMap(){
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.container, new map())
//                .commit();
//    }

    private void addEvents() {

        thingNeedGONE();

        txtNhapViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnChonThoiGian.setVisibility(lnChonThoiGian.GONE);
//                lnNhapViTri.setVisibility(lnNhapViTri.VISIBLE);
                btnSearchWoker.setVisibility(btnSearchWoker.GONE);
                lnSellectModel.setVisibility(lnSellectModel.GONE);
                searchView.setVisibility(searchView.VISIBLE);
//                chuyenSangMap();


//                txtViTri1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        txtNhapViTri.setText(txtViTri1.getText());
//                    }
//                });
//                txtViTri2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        txtNhapViTri.setText(txtViTri2.getText());
//                    }
//                });
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

//    Nhung cai phai an luc dau
    private void thingNeedGONE() {
//        lnNhapViTri.setVisibility(lnNhapViTri.GONE);
        lnChonThoiGian.setVisibility(lnChonThoiGian.GONE);
        btnSearchWoker.setVisibility(btnSearchWoker.GONE);
        txtSearchingWorker.setVisibility(txtSearchingWorker.GONE);
        lnSellectModel.setVisibility(lnSellectModel.GONE);
    }

    private void addControls(View root) {
        txtChonNgay = root.findViewById(R.id.txtChonNgay);
        txtChonGio = root.findViewById(R.id.txtChonGio);

//        txtViTri1 = root.findViewById(R.id.txtViTri1);
//        txtViTri2 = root.findViewById(R.id.txtViTri2);

        txtNhapViTri = root.findViewById(R.id.txtNhapViTri);
        txtNhapThoiGian = root.findViewById(R.id.txtNhapThoiGian);

        txtSearchingWorker = root.findViewById(R.id.txtSearchingWorker);

        btnOK = root.findViewById(R.id.btnOK);
        btnSearchWoker = root.findViewById(R.id.btnSearchWoker);
        btnOKInSearchLocation = root.findViewById(R.id.btnOKInSearchLocation);

//        lnNhapViTri = root.findViewById(R.id.lnNhapViTri);

        lnChonThoiGian = root.findViewById(R.id.lnChonThoiGian);
        lnNhapThoiGianViTri = root.findViewById(R.id.lnNhapThoiGianViTri);

        lnSellectModel = root.findViewById(R.id.lnSellectModel);

        searchView = root.findViewById(R.id.sv_location);
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
}
