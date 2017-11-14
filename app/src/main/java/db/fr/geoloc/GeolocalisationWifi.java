package db.fr.geoloc;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GeolocalisationWifi extends AppCompatActivity implements View.OnClickListener {

    // Attributs pour les widgets
    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private Button buttonDemarrerGeolocalisationWIFI;
    private TextView textViewMessage;

    private LocationManager gestionnaire;
    private LocationListener ecouteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocalisation_wifi);
        initInterface();
        initEvents();

    } /// onCreate

    private void initInterface() {
        // Liaison widget <--> Attribut
        textViewLongitude = (TextView) findViewById(R.id.textViewLongitude);
        textViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        buttonDemarrerGeolocalisationWIFI = (Button) findViewById(R.id.buttonDemarrerGeolocalisationWIFI);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);
    } /// initInterface

    private void initEvents() {
        // Liaison widget <--> Events
        buttonDemarrerGeolocalisationWIFI.setOnClickListener(this);
    } /// initEvents

    @Override
    public void onClick(View v) {

        if (v == buttonDemarrerGeolocalisationWIFI) {

            StringBuilder lsbWifi = new StringBuilder();

            // Le manager
            gestionnaire = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Liste des capteurs presents sur le terminal
            List<String> listeFournisseurs = gestionnaire.getAllProviders();

            if (listeFournisseurs.contains("network")) {
                lsbWifi.append("Capteur WIFI présent\n");
                boolean lbWifiActif = gestionnaire.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (lbWifiActif) {
                    lsbWifi.append("Wi-Fi actif\n");
                    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    if (wifiManager.isWifiEnabled()) {
                        lsbWifi.append("Wi-Fi disponible\n");
                        ecouteur = new EcouteurPosition();
                        // public void requestLocationUpdates (String provider, long minTime, float minDistance, LocationListener listener)
                        // 1000 * 60 = 1 minute
                        // 1 metre
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        gestionnaire.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 1, ecouteur);
                    } else {
                        lsbWifi.append("Wi-Fi indisponible\n");
                    }
                } else {
                    lsbWifi.append("Wi-Fi désactivé\n");
                }
            } else {
                lsbWifi.append("Capteur WIFI absent\n");
            }
            textViewMessage.setText(lsbWifi.toString());
        } /// id demarrer

    } /// onClick

    /**
     * NESTED CLASS qui implemente les services de geolocalisation
     */
    public class EcouteurPosition implements LocationListener {

        public EcouteurPosition() {
            textViewMessage.setText("Constructeur EcouteurPosition");
        } /// Constructeur

        public void onProviderDisabled(String provider) {
            textViewMessage.setText("onProviderDisabled");
        } /// onProviderDisabled

        public void onProviderEnabled(String provider) {
            textViewMessage.setText("onProviderEnabled");
        } /// onProviderEnabled

        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {

            textViewMessage.setText("onStatusChanged");
            String newStatus = "";
            switch (status) {
                case LocationProvider.OUT_OF_SERVICE:
                    newStatus = "OUT_OF_SERVICE";
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    newStatus = "TEMPORARILY_UNAVAILABLE";
                    break;
                case LocationProvider.AVAILABLE:
                    newStatus = "AVAILABLE";
                    break;
            }
            textViewMessage.setText(newStatus);
        } /// onStatusChanged

        public void onLocationChanged(Location location) {
            textViewMessage.setText("onLocationChanged");
            // Affichage des valeurs lat et long
            textViewLongitude.setText(Double.toString(location.getLatitude()));
            textViewLatitude.setText(Double.toString(location.getLongitude()));
        } /// onLocationChanged

    } /// NESTED CLASS (public ou private)

}
