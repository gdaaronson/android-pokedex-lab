package edu.lcark.pokemonlab;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Greg on 2/20/2016.
 */
public class PokemonASync extends AsyncTask<String, Integer, JSONObject> {

    public static final String TAG = PokemonASync.class.getSimpleName();

    private PokemonDetailActivity pokemonDetailActivity;

    private Pokemon pokemon;

    public PokemonASync (Pokemon pokemon, PokemonDetailActivity pokemonDetailActivity){
        this.pokemon = pokemon;
        this.pokemonDetailActivity = pokemonDetailActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Started AsyncTask for " + pokemon.getName());
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        StringBuilder responseBuilder = new StringBuilder();
        JSONObject jsonObject = null;
        if (params.length == 0) {
            return null;
        }

        try {
            URL url = new URL(pokemon.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            if (isCancelled()) {
                return null;
            }
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);

                if (isCancelled()) {
                    return null;
                }
            }

            jsonObject = new JSONObject(responseBuilder.toString());

            if (isCancelled()) {
                return null;
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return jsonObject;
    }

    @Override
    protected void onCancelled(JSONObject jsonObject) {
        super.onCancelled(jsonObject);
        Log.d(TAG, "AsyncTask cancelled");
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (jsonObject == null) {
            Log.e(TAG, "Resulting JSON is null");
        } else {
            pokemon.setStats(jsonObject);
            pokemonDetailActivity.waiting();
            Log.d(TAG, "The stats should be set");
        }
    }

}
