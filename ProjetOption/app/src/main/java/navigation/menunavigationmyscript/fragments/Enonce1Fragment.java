package navigation.menunavigationmyscript.fragments;

import android.content.DialogInterface;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.myscript.atk.math.widget.MathWidgetApi;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import connexion_serveur.ApiCall;
import connexion_serveur.RequestBuilder;
import exercice_textview.Exercice;
import navigation.menunavigationmyscript.activity.MainActivity;
import navigation.menunavigationmyscript.architecture.AbstractFragment;
import navigation.menunavigationmyscript.BuildConfig;
import navigation.menunavigationmyscript.MyCertificate;
import navigation.menunavigationmyscript.R;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.myscript.atk.math.widget.MathWidgetApi.RecognitionBeautification.BeautifyFontify;

/**
 * Created by Ali.
 */

@EFragment(R.layout.enonce1)
public class Enonce1Fragment extends AbstractFragment implements
        MathWidgetApi.OnConfigureListener,
        MathWidgetApi.OnRecognitionListener {

    // les éléments de l'interface visuelle

    //  @ViewById(R.id.enonce) est équivalent à (TextView) findViewById(R.id.enonce);
    @ViewById(R.id.enonce)
    protected TextView enonce;

    // Données MyScript
    protected static final String TAG = "MathDemo";
    private MathWidgetApi widget;

    // données
    private int numVisit;
    private int idExo;
    private ArrayList<Exercice> exercices ;
    private String reponseAenvoyer ="" ;

    //Client qui va permettre d effectuer les requetes GET et POST
    protected OkHttpClient client;

    // n° de fragment
    private static final String ARG_SECTION_NUMBER = "section_number";

    //Creation des exercices
    public void rempliExercices(){
        exercices = new ArrayList<Exercice>();
        Exercice e1 = new Exercice("Exercice 1 \n \n" + "Michaël a 20 euros dans son porte-monnaie. Il achète une voiture miniature qui coûte 4 euros et une sucette à 1 euro.\n \n" +
                "Combien a-t-il dépensé en tout ?","4+1=5",3,0);
        Exercice e2 = new Exercice("Exercice 2 \n \n" + "Pascal possède 7 autocollants, Julien en a 14 et Sonia a 11 autocollants.\n \n" +
                "Combien d'autocollants ont-ils à tous les trois ?","7+14+11=32",3,1);
        Exercice e3 = new Exercice("Exercice 3 \n \n" + "Jones est allé en récréation avec 8 billes. À la fin de la récréation, il a gagné 3 billes.\n \n" +
                "Combien en a-t-il maintenant ?","8+3=11",3,2);
        Exercice e4 = new Exercice("Exercice 4 \n \n" + "Coralie a 10 euros dans sa poche.\n" +
                "son père lui donne 5 euros.\n \n" +
                "Combien d'euros a-t-elle maintenant ?.","10+5=15",3,3);
        Exercice e5 = new Exercice("Exercice 5 \n \n" + "Aurélie a 12 ans. Sa sœur, Sophie, a 5 ans de moins qu'elle.\n \n" +
                "Quel âge a Sophie ?","12-5=7",3,4);

        exercices.add(e1);
        exercices.add(e2);
        exercices.add(e3);
        exercices.add(e4);
        exercices.add(e5);
    }

    public void creation_widget(){
        // Equivalent du OnCreate pour le widget
        client = new OkHttpClient();
        widget = (MathWidgetApi) getActivity().findViewById(R.id.math_widget);
        if (!widget.registerCertificate(MyCertificate.getBytes()))
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
            dlgAlert.setMessage("Please use a valid certificate.");
            dlgAlert.setTitle("Invalid certificate");
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    //dismiss the dialog
                }
            });
            dlgAlert.create().show();
            return;
        }
        // Listen to widget events (see onConfigureEnd and onRecognitionEnd APIs)
        widget.setOnConfigureListener(this);
        widget.setOnRecognitionListener(this);

        widget.clearSearchPath();
        // references assets directly from the APK to avoid extraction in application
        // file system
        widget.addSearchDir("zip://" + getActivity().getPackageCodePath() + "!/assets/conf");
        widget.configure("math", "standard");
        widget.setBeautificationOption(BeautifyFontify);
    }

    // Méthode qui s'exécute après que les @ViewById ce soient exécutés
    @AfterViews
    protected void afterViews() {
        // mémoire
        afterViewsDone = true;
        // log
        if (isDebugEnabled) {
            Log.d("BienvenueFragment", String.format("afterViews %s - %s", getParentInfos(), getLocalInfos()));
        }

        getActivity().setTitle("Exercices");
        // On crée le widget
        creation_widget();
        // On remplit les exercices
        rempliExercices();

        //ecriture de l enonce
        enonce.setTextSize(40);
        enonce.setTextColor(-1);
        idExo = (int) (Math.random()*(exercices.size()));
        String exercice = exercices.get(idExo).getEnonce();
        enonce.setText(exercice);
    }

    // Configuration du bouton clear : on gère le clic
    @Click(R.id.action_clear)
    protected void clearWidget() {
        widget.clear(true);
        //Toast.makeText(getActivity(), "Contenu effacé", Toast.LENGTH_SHORT).show();
    }

    // Configuration du bouton clear : on gère le clic
    @Click(R.id.action_change)
    protected void changeExercice() {
        int indexExo = (int) (Math.random()*(exercices.size()));
        idExo = indexExo;
        enonce.setText(exercices.get(indexExo).getEnonce());
        widget.clear(true);
        //Toast.makeText(getActivity(), "Exercice Changé", Toast.LENGTH_SHORT).show();
    }

    // Configuration du bouton envoyer : on gère le clic
    @Click(R.id.action_send)
    protected void envoieFichier() {

        //recuperation de la reponse
        String reponse = widget.getResultAsText();

        //Comparaison avec la bonne reponse
        String reponseAEnvoyer = "";
        if(reponse.equals(exercices.get(idExo).getReponse())){
            reponseAEnvoyer = "vrai";
        } else {
            reponseAEnvoyer = "faux";
        }

        //Recuperation de la date
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        String currentDate = sdf.format(new Date());

        //Creation du JSON a envoyer
        RequestBody body = RequestBuilder.ReponseBody(getID(),Integer.toString(exercices.get(idExo).getId()),reponseAEnvoyer,currentDate);

        //Envoi du JSON
        try {
            String reponsePOST = ApiCall.POST(client,RequestBuilder.buildURL(),body);
            Log.e("reponse serveur", reponsePOST);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(reponseAEnvoyer == "vrai"){
            enonce.setText("Bravo, tu as eu la bonne réponse!\n" + "Continue à t'entrainer avec un autre exercice.");
        } else{
            enonce.setText("Tu n'as pas trouvé la bonne réponse.\n" + "Continue à t'entrainer avec un autre exercice.");
        }
    }

    @Override
    protected void updateFragment() {
        if (isDebugEnabled) {
            Log.d("Enonce1Fragment", String.format("update %s - %s - %s", getArguments().getInt(ARG_SECTION_NUMBER), getParentInfos(), getLocalInfos()));
        }
        // incrément n° de visite
        numVisit = session.getNumVisit();
        numVisit++;
        session.setNumVisit(numVisit);
        // on affiche le n° de la visite
        //Toast.makeText(getActivity(), String.format("Visite n° %s", numVisit), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigureBegin(MathWidgetApi mathWidgetApi) {

    }

    @Override
    public void onConfigureEnd(MathWidgetApi mathWidgetApi, boolean success) {
        if(!success)
        {
            //Toast.makeText(getActivity().getApplicationContext(), widget.getErrorString(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Unable to configure the Math Widget: " + widget.getErrorString());
            return;
        }
        //Toast.makeText(getActivity().getApplicationContext(), "Math Widget Configured", Toast.LENGTH_SHORT).show();
        if(BuildConfig.DEBUG)
            Log.d(TAG, "Math Widget configured!");
    }

    @Override
    public void onRecognitionBegin(MathWidgetApi mathWidgetApi) {

    }

    @Override
    public void onRecognitionEnd(MathWidgetApi mathWidgetApi) {
        //Toast.makeText(getActivity().getApplicationContext(), "Recognition update", Toast.LENGTH_SHORT).show();
        if(BuildConfig.DEBUG)
        {
            Log.d(TAG, "Math Widget recognition: " + widget.getResultAsText());
        }
    }

    @Override
    public void onDestroy()
    {
        widget.setOnRecognitionListener(null);
        widget.setOnConfigureListener(null);

        // release widget's resources
        widget.release();
        super.onDestroy();
    }

    // infos locales pour logs
    protected String getLocalInfos() {
        return String.format("numVisit=%s", numVisit);
    }

    public String getID(){
        TelephonyManager teleManager = (TelephonyManager) getActivity().getSystemService(TELEPHONY_SERVICE);
        String tmSerial = teleManager.getSimSerialNumber();
        String tmDeviceId = teleManager.getDeviceId();
        String androidId = android.provider.Settings.Secure.getString(getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (tmSerial  == null) tmSerial   = "1";
        if (tmDeviceId== null) tmDeviceId = "1";
        if (androidId == null) androidId  = "1";
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDeviceId.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

}
