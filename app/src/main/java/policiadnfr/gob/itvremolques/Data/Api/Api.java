package policiadnfr.gob.itvremolques.Data.Api;


public class Api {
    //casa
    //private static final String base_url="http://192.168.0.104/ApiAndroid/public/api/";
    //trabajo
    private static final String base_url="http://192.168.1.104/ApiAndroid/public/api/";
    //produccion
    //private static final String base_url="http://android.policiadnfr.gob.bo/api/";
    public static ApiRoutes getApi(){
        return RetrofitClient.getClient(base_url).create(ApiRoutes.class);
    }

}
