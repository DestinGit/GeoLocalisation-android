package db.fr.geoloc;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geocodage extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewLatitude;
    private TextView textViewLongitude;
    private TextView textViewAdresse;
    private TextView textViewMessage;

    private Button buttonGeocodage;
    private Button buttonGeocodageInverse;
    private Button buttonListeAdresses;

    private EditText editTextAdresse;
    private EditText editTextLatitude;
    private EditText editTextLongitude;

    private Geocoder igeocodeur;
    private boolean ibGeocodeur;
    private double[] tLatLng = {48.8494248, 2.3907379}; // Paris

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geocodage);

        initInterface();

        // Test la presence du geocodage via une methode statique
        ibGeocodeur = Geocoder.isPresent();

        if (ibGeocodeur) {
            //Instancie un Geocoder en parametrant la Locale pour geocoder
            igeocodeur = new Geocoder(this, Locale.FRANCE);

            textViewMessage.setText("Géocodage possible");
        } else {
            textViewMessage.setText("Géocodage impossible");

            buttonGeocodage.setBackgroundColor(Color.RED);
            buttonGeocodageInverse.setBackgroundColor(Color.RED);
            buttonListeAdresses.setBackgroundColor(Color.RED);

            buttonGeocodage.setEnabled(false);
            buttonGeocodageInverse.setEnabled(false);
            buttonListeAdresses.setEnabled(false);
        }

    } // / onCreate


    /**
     * getLatLongFromAdresse : les coordonnees d'une adresse, géocodage
     *
     * @param adresse
     * @return
     */
    private double[] getLatLongFromAdresse(Geocoder geocodeur, String adresse) {
        // Fonction perso pour le géocodage
        double tLatLng[] = {0.0, 0.0};

        try {
            List<Address> adresses = geocodeur.getFromLocationName(adresse, 1);
            if (adresses.size() > 0) {
                tLatLng[0] = adresses.get(0).getLatitude();
                tLatLng[1] = adresses.get(0).getLongitude();
            }
        } catch (IOException e) {
        }

        return tLatLng;

    } // / getLatLongFromAdresse


    /**
     * @param asAdresse
     * @return
     */
    private String getListeAdresses(Geocoder geocodeur, String asAdresse) {
        // Adresse -> adresses
        // Fonction perso qui renvoie une liste d'adresses en fonction d'une adresse, c'est du géocodage
        StringBuilder lsb = new StringBuilder();
        try {
//                List<Address> liste = geocodeur.getFromLocationName("France,Paris,Place de la Nation", 10);
            List<Address> liste = geocodeur.getFromLocationName(asAdresse, 10);
            for (int i = 0; i < liste.size(); i++) {
                lsb.append(liste.get(i));
                lsb.append("\n\n");
            }
        } catch (Exception e) {
            lsb.append(e.getMessage());
        }
        return lsb.toString();
    } /// getListeAdresses


    /**
     * getAdresseFromLatLong : une adresse a partir de lat, lng (Geocodage inverse)
     *
     * @param latitude
     * @param longitude
     * @return : String : l'adresse
     */
    private String getAdresseFromLatLong(Geocoder geocodeur, double latitude, double longitude) {
        // Fonction perso qui renvoie la première adresse d'une liste d'adresse en fonction de coordonnées, géocodage inverse
        String lsAdresse = "Non trouvée";

        try {
            //geocoder.getFromLocation(lat, lng, nombre d'adresses max);
            List<Address> adresses = geocodeur.getFromLocation(latitude, longitude, 1);
            if (adresses.size() > 0) {
                Address adresse = adresses.get(0);
                lsAdresse = adresse.getAddressLine(0);
            }
        } catch (IOException e) {
            lsAdresse = e.getMessage();
        }

        return lsAdresse;
    } // / getAdresseFromLatLong


    @Override
    public void onClick(View v) {

        // Geocodage
        if (v == buttonGeocodage) {
            // Adresse -> coordonnees
            /*
            Sarlat, France - Latitude : 44.889 - Longitude : 1.2166
             */
            tLatLng = getLatLongFromAdresse(igeocodeur, this.editTextAdresse.getText().toString());
            textViewLatitude.setText(Double.toString(tLatLng[0]));
            textViewLongitude.setText(Double.toString(tLatLng[1]));
        }

        // Geocodage inverse
        if (v == buttonGeocodageInverse) {
            // Coordonnees -> Adresse
            double latitude;
            double longitude;

            // Coordonnees -> Adresse
            latitude = Double.valueOf(editTextLatitude.getText().toString());
            longitude = Double.valueOf(editTextLongitude.getText().toString());
            textViewAdresse.setText(getAdresseFromLatLong(igeocodeur, latitude, longitude));
        }

        // Liste d'adresses !!!
        if (v == buttonListeAdresses) {
            // Adresse -> adresses
            String lsAdresses = getListeAdresses(igeocodeur, editTextAdresse.getText().toString());
            textViewMessage.setText(lsAdresses);
        }
    } /// onClick

    // -----------------------
    private void initInterface() {
        /*
         * Liens Widgets-Variables
        */
        textViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        textViewLongitude = (TextView) findViewById(R.id.textViewLongitude);
        textViewAdresse = (TextView) findViewById(R.id.textViewAdresse);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);

        buttonGeocodage = (Button) findViewById(R.id.buttonGeocodage);
        buttonGeocodageInverse = (Button) findViewById(R.id.buttonGeocodageInverse);
        buttonListeAdresses = (Button) findViewById(R.id.buttonListeAdresses);

        editTextLatitude = (EditText) findViewById(R.id.editTextLatitude);
        editTextLongitude = (EditText) findViewById(R.id.editTextLongitude);
        editTextAdresse = (EditText) findViewById(R.id.editTextAdresse);

        /*
         * Widgets et evenements
        */
        buttonGeocodage.setOnClickListener(this);
        buttonGeocodageInverse.setOnClickListener(this);
        buttonListeAdresses.setOnClickListener(this);

    } // / initInterface
}
