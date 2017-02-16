package connexion_serveur;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;


/**
 * Created by aurelienzakowic on 16/02/2017.
 */

public class RequestBuilder {

    //Create a body to send to the server
    public static RequestBody ReponseBody(String idclient, String idexo, String reponse,String date) {
        return new FormBody.Builder()
                .add("idclient", idclient)
                .add("idexo", idexo)
                .add("reponse", reponse)
                .add("dateexo",date)
                .build();
    }

    //Create the url used for the server
    public static HttpUrl buildURL() {
        return new HttpUrl.Builder()
                .scheme("https") //http
                .host("calm-beyond-73982.herokuapp.com")
                .addPathSegment("userdata")
                .build();
        /**
         * The return URL:
         *  https://calm-beyond-73982.herokuapp.com/userdata
         */
    }

}
