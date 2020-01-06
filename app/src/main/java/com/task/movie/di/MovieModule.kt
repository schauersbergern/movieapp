import com.task.movie.core.StringResolver
import com.task.movie.core.StringResolverImpl
import com.task.movie.data.MovieRepositoryImpl
import com.task.movie.domain.GetMoviesUseCase
import com.task.movie.domain.MovieRepository
import com.task.movie.domain.SearchMovieUseCase
import com.task.movie.networking.MovieInterface
import com.task.movie.utilis.Constants
import com.task.movie.viewmodel.*
import com.task.movie.viewmodel.MovieListViewModelImpl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val movieModule = module {

    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideMovieApi(get()) }
    single { provideRetrofit(get()) }

    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single<StringResolver> {StringResolverImpl(androidContext())}
    viewModel<MovieListViewModel> { MovieListViewModelImpl(get(), get(), get()) }
    viewModel<MovieDetailViewModel> { MovieDetailViewModelImpl() }
    factory { GetMoviesUseCase(get()) }
    factory { SearchMovieUseCase(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(Constants.TAG_API_BASEURL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideMovieApi(retrofit: Retrofit): MovieInterface = retrofit.create(MovieInterface::class.java)
