package db.fr.geoloc;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class TestsReseaux extends AppCompatActivity {

    private TextView textViewFournisseurs;
    private TextView textViewGPS;
    private TextView textViewPassive;
    private TextView textViewWIFI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tests_reseaux);

        textViewFournisseurs = (TextView) findViewById(R.id.textViewFournisseurs);
        textViewGPS = (TextView) findViewById(R.id.textViewGPS);
        textViewWIFI = (TextView) findViewById(R.id.textViewWIFI);
        textViewPassive = (TextView) findViewById(R.id.textViewPassive);

        textViewFournisseurs.setText("");
        textViewGPS.setText("");
        textViewWIFI.setText("");
        textViewPassive.setText("");

        StringBuilder lsbListeFournisseurs = new StringBuilder();
        StringBuilder lsbGPS = new StringBuilder();
        StringBuilder lsbWIFI = new StringBuilder();
        StringBuilder lsbPassive = new StringBuilder();

        // Le manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Liste des capteurs presents sur le terminal
        List<String> listeFournisseurs = locationManager.getAllProviders();

        for (int i = 0; i < listeFournisseurs.size(); i++) {
            lsbListeFournisseurs.append(listeFournisseurs.get(i));
            lsbListeFournisseurs.append("\n");
        }

        /*
        GPS
         */
        if (listeFournisseurs.contains("gps")) {
            lsbGPS.append("GPS présent sur le terminal\n");
            // Test pour savoir si le service de geolocalisation via le GPS est disponible
            boolean lbGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (lbGPS) {
                lsbGPS.append("GPS actif sur le terminal\n");

                int permissionCheckAction = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

                if (permissionCheckAction == PackageManager.PERMISSION_GRANTED) {
                    // Faire quelque chose
                    lsbGPS.append("Le GPS peut accéder à une localisation\n");
                } else if (permissionCheckAction == PackageManager.PERMISSION_DENIED) {
                    // Faire quelque chose
                    lsbGPS.append("Le GPS ne peut pas accéder à une localisation\n");
                }
            } else {
                lsbGPS.append("GPS désactivé sur le terminal\n");
            }
        } else {
            lsbGPS.append("GPS absent sur le terminal\n");
        }

        /*
        WIFI
         */
        if (listeFournisseurs.contains("network")) {
            lsbWIFI.append("Network présent sur le terminal\n");
            // Test pour savoir si le service de geolocalisation via le WIFI est disponible
            boolean lbWifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (lbWifi) {
                lsbWIFI.append("WIFI actif sur le terminal\n");

                int permissionCheckAction = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheckAction == PackageManager.PERMISSION_GRANTED) {
                    // Faire quelque chose
                    lsbWIFI.append("Le WIFI peut accéder à une localisation\n");
                } else if (permissionCheckAction == PackageManager.PERMISSION_DENIED) {
                    // Faire quelque chose
                    lsbWIFI.append("Le WIFI ne peut pas accéder à une localisation\n");
                }
            } else {
                lsbWIFI.append("WIFI désactivé sur le terminal\n");
            }
        } else {
            lsbWIFI.append("WIFI absent sur le terminal\n");
        }

        textViewFournisseurs.setText(lsbListeFournisseurs);
        textViewGPS.setText(lsbGPS);
        textViewWIFI.setText(lsbWIFI);

    } /// onCreate
}
