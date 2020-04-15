package policiadnfr.gob.itvremolques.Data.Api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static Retrofit retrofit;
    public static Retrofit getClient(String base_url){
        if(retrofit==null){

           // OkHttpClient client = new OkHttpClient();

           /* Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();*/
            retrofit= new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    static private OkHttpClient getClient(){
        OkHttpClient client= new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request origen=chain.request();
                    HttpUrl origenHttpUrl=origen.url();
                    HttpUrl url=origenHttpUrl.newBuilder()
                            .addQueryParameter("api_key","QnJpc2VpZGFGYXZpbzA1MTBtaXRvZG8=")
                            .build();
                    Request.Builder requestBuilder=origen.newBuilder().url(url);
                    Request request=requestBuilder.build();
                    return  chain.proceed(request);

                }
            })
            .build();
        return client;
    }

}
