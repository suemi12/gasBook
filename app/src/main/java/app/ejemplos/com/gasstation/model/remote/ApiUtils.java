package app.ejemplos.com.gasstation.model.remote;


public class ApiUtils {

    private ApiUtils() {
    }

    public static final String BASE_URL = "https://api.datos.gob.mx/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}

