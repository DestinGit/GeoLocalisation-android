package db.fr.geoloc;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class GeocodageInverse extends AppCompatActivity {

    private TextView textViewMessage;
    private Geocoder geocoder;
    private double idLat;
    private double idLng;
    private String lsPays;
    private String lsCodePays;
    private String lsVille;
    private String lsCodePostal;
    private String lsVoie;
    private String lsNumeroVoie;
    private StringBuilder lsb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geocodage_inverse);

        textViewMessage = (TextView) findViewById(R.id.textViewMessage);

        // Paris 48.8494248, 2.3907379
        idLat = 48.8494248;
        idLng = 2.3907379;
        // Sarlat : 44.889 - Longitude : 1.2166
        idLat = 44.889;
        idLng = 1.2166;

        lsb = new StringBuilder();

        /*
        Comment peut-il être absent ??? Cela dépend du terminal ou de l'émulateur
         */
        boolean lbGeocoder = Geocoder.isPresent(); // Methode statique

        if (lbGeocoder) {
            lsb.append("Geocoder is Present\n\n");
            //geocoder = new Geocoder(this, Locale.FRANCE);
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                // getFromLocation(lat, lng, n adresses max)
                List<Address> adresses = geocoder.getFromLocation(idLat, idLng, 1);

                if (adresses.size() > 0) {

                    Address adresse = adresses.get(0);

                    lsPays = adresse.getCountryName();
                    lsCodePays = adresse.getCountryCode();
                    lsVille = adresse.getLocality();
                    lsCodePostal = adresse.getPostalCode();
                    lsVoie = adresse.getThoroughfare();
                    lsNumeroVoie = adresse.getSubThoroughfare();


                    lsb.append("Pays : ");
                    lsb.append(lsPays);
                    lsb.append("\n");

                    lsb.append("Pays : ");
                    lsb.append(lsCodePays);
                    lsb.append("\n");

                    lsb.append("Ville : ");
                    lsb.append(lsVille);
                    lsb.append("\n");

                    lsb.append("Code postal : ");
                    lsb.append(lsCodePostal);
                    lsb.append("\n");

                    lsb.append("Voie : ");
                    lsb.append(lsVoie);
                    lsb.append("\n");

                    lsb.append("Numéro : ");
                    lsb.append(lsNumeroVoie);
                    lsb.append("\n");

//                    lsb.append("\n");
//                    lsb.append(adresse.toString());
                    lsb.append("\ngetAdminArea\n");
                    lsb.append(adresse.getAdminArea());
//                    lsb.append(adresse.getPhone());
//                    lsb.append("\n");
                    lsb.append("\ngetSubAdminArea\n");
                    lsb.append(adresse.getSubAdminArea());
                    lsb.append("\ngetSubLocality\n");
                    lsb.append(adresse.getSubLocality());
                    lsb.append("\n");
                } else {
                    lsb.append("Aucune adresse !!!");
                }
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
                lsb.append(e.getMessage());
            }
        } else {
            lsb.append("Geocoder is Absent\n");
        }

        textViewMessage.setText(lsb);

    } /// onCreate
}
