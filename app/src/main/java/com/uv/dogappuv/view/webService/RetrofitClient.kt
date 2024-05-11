import com.uv.dogappuv.view.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.uv.dogappuv.view.utils.Constants.BASE_URL
object RetrofitClient {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

