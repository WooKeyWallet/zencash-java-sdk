package wookey.wallet.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Base64;

/**
 * AuthInterceptor
 */
public class AuthInterceptor implements Interceptor {

    private String username;
    private String password;

    public AuthInterceptor() {
    }

    public AuthInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final String auth = Base64.getEncoder()
                .encodeToString(new StringBuilder(this.username)
                        .append(":")
                        .append(this.password)
                        .toString().getBytes());

        Request original=chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", "Basic " + auth)
                .method(original.method(), original.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
