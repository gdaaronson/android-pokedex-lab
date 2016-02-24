package edu.lcark.pokemonlab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Greg on 2/18/2016.
 */

public class PokemonRecycleViewHolder extends RecyclerView.ViewHolder {

    TextView name, id, weight, height;
    ImageView picture;
    View fullView;

    public PokemonRecycleViewHolder(View textView){
        super(textView);
        fullView = itemView;
        name = (TextView) itemView.findViewById(R.id.row_pokemon_name_textview);
        id = (TextView) itemView.findViewById(R.id.row_pokemon_id_textview);
        weight = (TextView) itemView.findViewById(R.id.row_pokemon_weight_textview);
        height = (TextView) itemView.findViewById(R.id.row_pokemon_height_textview);
        picture = (ImageView) itemView.findViewById(R.id.row_pokemon_imageview);
    }

}
