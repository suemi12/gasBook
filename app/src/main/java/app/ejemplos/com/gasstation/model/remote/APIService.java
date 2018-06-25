package app.ejemplos.com.gasstation.model.remote;


import app.ejemplos.com.gasstation.model.GasBook;
import app.ejemplos.com.gasstation.model.GasBookResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIService {

    @GET("v1/precio.gasolina.publico")
    Call<GasBookResponse> getHistoryGasStation();

    @GET("v1/precio.gasolina.publico")
    Call<GasBookResponse> getGasStationById(@Query("_id") String id);
}
