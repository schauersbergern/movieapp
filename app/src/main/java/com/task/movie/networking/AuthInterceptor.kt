import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()

        //get your api key here
        //https://www.themoviedb.org/settings/api
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        val url = req.url.newBuilder().addQueryParameter("api_key", "").build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }

}