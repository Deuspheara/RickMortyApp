package fr.deuspheara.rickandmorty.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.deuspheara.rickandmorty.R
import fr.deuspheara.rickandmorty.data.models.ResultCharacter
import fr.deuspheara.rickandmorty.databinding.CharacterItemBinding


class CharactersAdapter(private var resultsCharacter: ArrayList<ResultCharacter>) : PagingDataAdapter<ResultCharacter, CharactersAdapter.CharactersViewHolder>(COMPARATOR) {


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ResultCharacter>() {
            override fun areItemsTheSame(oldItem: ResultCharacter, newItem: ResultCharacter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultCharacter, newItem: ResultCharacter): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }


    fun updateCharacters(newCharacters: List<ResultCharacter>) {
        resultsCharacter.clear()
        resultsCharacter.addAll(newCharacters)
        notifyDataSetChanged()
    }

    inner class CharactersViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CharacterItemBinding.bind(itemView)

        fun bind(resultCharacter: ResultCharacter) {
            binding.characterName.text = resultCharacter.name
            binding.characterStatus.text = resultCharacter.status
            when(resultCharacter.status) {
                "Alive" -> binding.characterStatusColor.setCardBackgroundColor(itemView.context.getColor(R.color.green))
                "Dead" -> binding.characterStatusColor.setCardBackgroundColor(itemView.context.getColor(R.color.red))
                "unknown" -> binding.characterStatusColor.setCardBackgroundColor(itemView.context.getColor(R.color.gray))
            }
            Glide.with(itemView.context)
                .load(resultCharacter.image)
                .into(binding.characterImage)
        }
    }
}