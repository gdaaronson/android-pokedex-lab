package edu.lcark.pokemonlab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class PokemonDetailActivity extends AppCompatActivity implements Serializable{

    public static final String ARG_POKEMON = "ArgPokemon";

    private Pokemon mPokemon;

    private PokemonASync pokemonASync;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);

        setContentView(R.layout.pokemon_detailed);
        mPokemon = getIntent().getParcelableExtra(ARG_POKEMON);

        //TextView nameTextView = (TextView) findViewById(R.id.activity_pokemon_detail_name_textview);
        TextView heightTextView = (TextView) findViewById(R.id.activity_pokemon_detail_height_textview);
        TextView weightTextView = (TextView) findViewById(R.id.activity_pokemon_detail_weight_textview);
        TextView idTextView = (TextView) findViewById(R.id.activity_pokemon_detail_id_textview);
        ImageView pokemonImageView = (ImageView) findViewById(R.id.activity_pokemon_detail_imageview);

        Picasso.with(this).load(mPokemon.getImageUrl()).fit().centerInside().into(pokemonImageView);

        //nameTextView.setText(mPokemon.getName());
        heightTextView.setText(getString(R.string.height_short) + mPokemon.getHeight());
        weightTextView.setText(getString(R.string.weight_short) + mPokemon.getWeight());
        idTextView.setText(getString(R.string.number) + mPokemon.getId());

        setTitle(mPokemon.getName());

        if (mPokemon.getBXP() == null) {
            progressBar = (ProgressBar) findViewById(R.id.activity_pokemon_detail_progressbar);
            progressBar.setMax(7);
            progressBar.setVisibility(View.VISIBLE);
            Log.d(PokemonDetailActivity.class.getSimpleName(), "This should be after the stats");
            pokemonASync = new PokemonASync(mPokemon, this);
            pokemonASync.execute(mPokemon.getId());
        } else {
            progressBar = (ProgressBar) findViewById(R.id.activity_pokemon_detail_progressbar);
            Log.d(PokemonDetailActivity.class.getSimpleName(), "It should work");
            waitingMod();
        }
    }

    private TextView baseXPTextView;

    private TextView hpTextView;

    private TextView attackTextView;

    private TextView defenseTextView;

    private TextView sAttackTextView;

    private TextView sDefenseTextView;

    private TextView speedTextView;

    public void waiting(){
        baseXPTextView = (TextView) findViewById(R.id.activity_pokemon_detail_base_ex_textview);
        baseXPTextView.setText(getString(R.string.b_xp) + mPokemon.getBXP());
        progressBar.setProgress(1);

        hpTextView = (TextView) findViewById(R.id.activity_pokemon_detail_HP_textview);
        hpTextView.setText(getString(R.string.HP) + mPokemon.getHP());
        progressBar.setProgress(2);

        attackTextView = (TextView) findViewById(R.id.activity_pokemon_detail_attack_textview);
        attackTextView.setText(getString(R.string.attack) + mPokemon.getAttack());
        progressBar.setProgress(3);

        defenseTextView = (TextView) findViewById(R.id.activity_pokemon_detail_defense_textview);
        defenseTextView.setText(getString(R.string.defense) + mPokemon.getDefence());
        progressBar.setProgress(4);

        sAttackTextView = (TextView) findViewById(R.id.activity_pokemon_detail_sattack_textview);
        sAttackTextView.setText(getString(R.string.s_attack) + mPokemon.getSAttack());
        progressBar.setProgress(5);

        sDefenseTextView = (TextView) findViewById(R.id.activity_pokemon_detail_sdefense_textview);
        sDefenseTextView.setText(getString(R.string.s_defense) + mPokemon.getSDefence());
        progressBar.setProgress(6);

        speedTextView = (TextView) findViewById(R.id.activity_pokemon_detail_speed_textview);
        speedTextView.setText(getString(R.string.speed) + mPokemon.getSpeed());
        progressBar.setProgress(7);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void waitingMod(){
        progressBar.setVisibility(View.GONE);
        baseXPTextView = (TextView) findViewById(R.id.activity_pokemon_detail_base_ex_textview);
        baseXPTextView.setText(getString(R.string.b_xp) + mPokemon.getBXP());

        hpTextView = (TextView) findViewById(R.id.activity_pokemon_detail_HP_textview);
        hpTextView.setText(getString(R.string.HP) + mPokemon.getHP());

        attackTextView = (TextView) findViewById(R.id.activity_pokemon_detail_attack_textview);
        attackTextView.setText(getString(R.string.attack) + mPokemon.getAttack());

        defenseTextView = (TextView) findViewById(R.id.activity_pokemon_detail_defense_textview);
        defenseTextView.setText(getString(R.string.defense) + mPokemon.getDefence());

        sAttackTextView = (TextView) findViewById(R.id.activity_pokemon_detail_sattack_textview);
        sAttackTextView.setText(getString(R.string.s_attack) + mPokemon.getSAttack());

        sDefenseTextView = (TextView) findViewById(R.id.activity_pokemon_detail_sdefense_textview);
        sDefenseTextView.setText(getString(R.string.s_defense) + mPokemon.getSDefence());

        speedTextView = (TextView) findViewById(R.id.activity_pokemon_detail_speed_textview);
        speedTextView.setText(getString(R.string.speed) + mPokemon.getSpeed());
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ARG_POKEMON, mPokemon);
        setResult(RESULT_OK, intent);
        finish();
        onStop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        if (pokemonASync != null && !pokemonASync.isCancelled()) {
            pokemonASync.cancel(true);
            Log.d(PokemonDetailActivity.class.getSimpleName(), "Async was stopped");
        }
    }
}
