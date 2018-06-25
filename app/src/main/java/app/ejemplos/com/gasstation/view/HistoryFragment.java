package app.ejemplos.com.gasstation.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import app.ejemplos.com.gasstation.R;
import app.ejemplos.com.gasstation.adapters.HistoryAdapter;
import app.ejemplos.com.gasstation.model.GasBook;
import app.ejemplos.com.gasstation.model.GasBookResponse;
import app.ejemplos.com.gasstation.model.remote.APIService;
import app.ejemplos.com.gasstation.model.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class HistoryFragment extends Fragment implements HistoryAdapter.OnItemSelected{
    private RecyclerView recyclerHistory;
    private APIService mAPIService;


    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerHistory = view.findViewById(R.id.recycler_history);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAPIService = ApiUtils.getAPIService();
        mAPIService.getHistoryGasStation().enqueue(new Callback<GasBookResponse>() {
            @Override
            public void onResponse(Call<GasBookResponse> call, Response<GasBookResponse> response) {
                if (response.isSuccessful()) {
                    GasBookResponse gasBookResponse = response.body();
                    createAdapterHistory(gasBookResponse);
                }
            }

            @Override
            public void onFailure(Call<GasBookResponse> call, Throwable t) {

            }
        });


    }

    private void createAdapterHistory(GasBookResponse gasBookResponse) {
        HistoryAdapter adapter = new HistoryAdapter(gasBookResponse.getResults(), this);
        recyclerHistory.setAdapter(adapter);
    }


    @Override
    public void onItemOnClick(GasBook gasBook) {
        System.out.println("onclick: " + gasBook);
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra(MapsActivity.ID_LUGAR, gasBook.getId());
        startActivity(intent);



    }
}