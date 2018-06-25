package app.ejemplos.com.gasstation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.ejemplos.com.gasstation.R;
import app.ejemplos.com.gasstation.model.GasBook;
import app.ejemplos.com.gasstation.model.GasBookResponse;
import app.ejemplos.com.gasstation.model.remote.APIService;
import app.ejemplos.com.gasstation.model.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView maps;
    private GoogleMap gMap;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    final public static String ID_LUGAR = "id-Lugar";
    private String id;
    private APIService mAPIService;
    private TextView street;
    private TextView regular;
    private TextView colonia;
    private TextView premium;
    private TextView bussinesName;
    private TextView cp;
    private TextView diesel;
    private GasBook idGasElement;
    private FrameLayout progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        street = findViewById(R.id.street_gas);
        regular = findViewById(R.id.magna_gas);
        colonia = findViewById(R.id.colonia_gas);
        premium = findViewById(R.id.premium_gas);
        bussinesName = findViewById(R.id.title_gas);
        cp = findViewById(R.id.cp_gas);
        diesel = findViewById(R.id.diesel_gas);
        progressView = findViewById(R.id.progress_view);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString(ID_LUGAR);

        //Inicializa instancia de la interfaz
        mAPIService = ApiUtils.getAPIService();
        showProgress(true);
        //Consume servicio
        mAPIService.getGasStationById(id).enqueue(new Callback<GasBookResponse>() {
            @Override
            public void onResponse(Call<GasBookResponse> call, Response<GasBookResponse> response) {
                if (response.isSuccessful()) {
                    GasBookResponse gasBookResponse = response.body();
                    fillInfo(gasBookResponse);
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<GasBookResponse> call, Throwable t) {
                showProgress(false);

            }
        });
    }

    private void showProgress(Boolean show) {
        if (show) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressView.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressView.setVisibility(View.GONE);
        }
    }

    private void fillInfo(GasBookResponse gasBookResponse) {
        idGasElement = gasBookResponse.getResults().get(0);
        bussinesName.setText(idGasElement.getBusinessName());
        street.setText(idGasElement.getStreet());
        colonia.setText(idGasElement.getColony());
        cp.setText(idGasElement.getCp());
        regular.setText(idGasElement.getRegular());
        premium.setText(idGasElement.getPremium());
        diesel.setText(idGasElement.getDieasel());
        double longitude = Double.parseDouble(idGasElement.getLongitude());
        double latitude = Double.parseDouble(idGasElement.getLatitude());
        //crea coordenada
        LatLng coordinateLatLong = new LatLng(latitude,longitude);
        addMarkerView(coordinateLatLong);

    }


    private void addMarkerView(LatLng coordinate) {

        //configura posicion de la camara
        CameraPosition.Builder camBuilder = CameraPosition.builder();
        camBuilder.bearing(45);
        camBuilder.tilt(30);
        camBuilder.target(coordinate);
        camBuilder.zoom(15);
        CameraPosition cp = camBuilder.build();

        //crea marcador
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(coordinate);
        gMap.addMarker(markerOptions);

        //zoom a la camara del mapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cp);
        gMap.moveCamera(cameraUpdate);


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(12);
        gMap.setIndoorEnabled(true);
        UiSettings uiSettings = gMap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);


    }

}
