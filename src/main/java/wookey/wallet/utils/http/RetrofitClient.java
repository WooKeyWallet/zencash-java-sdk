package wookey.wallet.utils.http;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import wookey.wallet.config.Config;
import wookey.wallet.exception.RPCException;
import wookey.wallet.interceptor.AuthInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * retorfitClient Request tool class
 */
public class RetrofitClient {

    private Retrofit.Builder builder = new Retrofit.Builder();
    private OkHttpClient.Builder httpClientBulider = new OkHttpClient.Builder();
    private long timeout = Config.getInstance().getIntProperty("connectTimeout");

    /**
     * Create a request interface implementation class (proxy) (timeout is configurable)
     *
     * @param serviceClass Interface type
     * @param url          server address
     * @param <T>
     * @return Return value of interface definition
     */
    public <T> T createService(Class<T> serviceClass, String url, String username, String password) {
        httpClientBulider.interceptors().clear();
        httpClientBulider.addInterceptor(new AuthInterceptor(username, password));
        //Set log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor((message) -> System.out.println("【retrofit】:" + message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBulider.addInterceptor(loggingInterceptor);
        //Set connection timeout
        httpClientBulider.connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS);
        //create Retrofit
        builder.client(httpClientBulider.build())
                .addConverterFactory(JacksonConverterFactory.create());
        return builder.baseUrl(url).build().create(serviceClass);
    }

    public <T> T createService(Class<T> serviceClass, String url) {
        httpClientBulider.interceptors().clear();
        //Set connection timeout
        httpClientBulider.connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS);
        builder.client(httpClientBulider.build())
                .addConverterFactory(JacksonConverterFactory.create());
        return builder.baseUrl(url).build().create(serviceClass);
    }


    /**
     * Synchronous request
     *
     * @param call
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T execute(Call<T> call) throws Exception {
        String url = call.request().url().toString();
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                MediaType mediaType = response.errorBody().contentType();
                if ("text".equals(mediaType.type())) {
                    throw new RPCException("502 Bad Gateway,current url:&" + url + "%");
                }
                throw new RPCException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("SocketTimeoutException,current url:&" + url + "%", e);
        }
    }


    /**
     * Asynchronous request
     *
     * @param call
     * @param <T>
     * @return
     */
    public <T> T enqueue(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return null;
    }

}
