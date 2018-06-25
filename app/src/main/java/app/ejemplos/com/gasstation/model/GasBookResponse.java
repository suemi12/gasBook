package app.ejemplos.com.gasstation.model;


import java.util.ArrayList;

public class GasBookResponse {

    private ArrayList<GasBook> results;

    public ArrayList<GasBook> getResults() {
        return results;
    }

    public void setResults(ArrayList<GasBook> results) {
        this.results = results;
    }
}
