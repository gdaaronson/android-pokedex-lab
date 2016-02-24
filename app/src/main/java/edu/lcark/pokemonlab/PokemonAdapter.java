package edu.lcark.pokemonlab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;

/**
 * Created by Greg on 2/18/2016.
 */
public class PokemonAdapter extends RecyclerView.Adapter<PokemonRecycleViewHolder> {

    private Pokedex pokedex;

    private final OnPokemonRowClickListener mListener;

    public interface OnPokemonRowClickListener {
        void onPokemonRowClick(Pokemon pokemon);
    }


    public PokemonAdapter(Pokedex pokedex, OnPokemonRowClickListener listener) {
        this.pokedex = pokedex;
        mListener = listener;
    }

    @Override
    public PokemonRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.row_pokemon, parent, false);
        return new PokemonRecycleViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PokemonRecycleViewHolder holder, final int position) {
        final Pokemon pokemon = pokedex.getPokemons().get(position);
        holder.name.setText(pokemon.getName());
        holder.id.setText(pokemon.getId());


        Context context = holder.name.getContext();
        String weight = context.getString(R.string.weight_label) + pokemon.getWeight();
        holder.weight.setText(weight);
        String height = context.getString(R.string.height_label) + pokemon.getHeight();
        holder.height.setText(height);

        Picasso.with(context).load(pokemon.getImageUrl()).fit().centerInside().into(holder.picture);
        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPokemonRowClick(pokedex.getPokemons().get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pokedex.getPokemons().size();
    }


}
