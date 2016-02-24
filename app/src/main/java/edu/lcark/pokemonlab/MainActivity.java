package edu.lcark.pokemonlab;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonRowClickListener{

    public RecyclerView recyclerView;
    public static final int CODE_POKEMON = 0;
    public Pokedex pokedex;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokedex = new Pokedex();
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.activity_main_pokemon_recycleview);
        recyclerView.setAdapter(new PokemonAdapter(pokedex, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onPokemonRowClick(Pokemon pokemon) {
        Intent intent = new Intent(this, PokemonDetailActivity.class);
        intent.putExtra(PokemonDetailActivity.ARG_POKEMON, pokemon);
        startActivityForResult(intent, CODE_POKEMON);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        } else if(pokemon.getBXP() == null){
            Toast.makeText(this, getString(R.string.valid_connection) + pokemon.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_POKEMON && resultCode == RESULT_OK) {
            Pokemon pokemon = data.getParcelableExtra(PokemonDetailActivity.ARG_POKEMON);
            pokedex.getPokemons().set(pokemon.getIDNumber() - 1, pokemon);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
