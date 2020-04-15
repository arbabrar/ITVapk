package policiadnfr.gob.itvremolques.Data.Api;


public class Api {
    private static final String base_url="http://192.168.1.104/ApiAndroid/public/api/";
    public static ApiRoutes getApi(){
        return RetrofitClient.getClient(base_url).create(ApiRoutes.class);
    }

}
