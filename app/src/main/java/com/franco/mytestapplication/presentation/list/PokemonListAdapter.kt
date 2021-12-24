package com.franco.mytestapplication.presentation.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.franco.mytestapplication.R
import com.franco.mytestapplication.databinding.ItemPokemonBinding
import com.franco.mytestapplication.domain.model.local.Pokemon
import com.franco.mytestapplication.utils.loadUrlImage

/**
 * Adapter to show a pokemon list.
 */
class PokemonListAdapter(
    private val onPokemonClickListener: (String) -> Unit
): RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private val pokemons: MutableList<Pokemon> by lazy {
        ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PokemonViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_pokemon,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        pokemons[position].let { pokemon ->
            holder.bind(pokemon)
        }
    }

    override fun getItemCount() = pokemons.size

    @SuppressLint("NotifyDataSetChanged")
    fun submit(pokemonList: List<Pokemon>) {
        pokemons.apply {
            clear()
            addAll(pokemonList)
        }
        notifyDataSetChanged()
    }


    /**
     * Viewholder class to map model pokemon into view.
     */
    inner class PokemonViewHolder (
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val viewBinding = ItemPokemonBinding.bind(itemView)

        init {
            viewBinding.root.setOnClickListener(this)
        }

        fun bind(pokemon: Pokemon) {
            viewBinding.apply {
                tvPokemonName.text = pokemon.name
                ivPokemonPreview.loadUrlImage(pokemon.preview)
            }
        }

        override fun onClick(v: View?) {
            onPokemonClickListener.invoke(pokemons[adapterPosition].name.orEmpty())
        }
    }
}