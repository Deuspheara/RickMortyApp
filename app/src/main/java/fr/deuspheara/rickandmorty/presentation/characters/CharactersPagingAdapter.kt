package fr.deuspheara.rickandmorty.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.deuspheara.rickandmorty.R
import fr.deuspheara.rickandmorty.data.models.ResultCharacter
import fr.deuspheara.rickandmorty.databinding.CharacterItemBinding


class CharactersPagingAdapter(private var resultsCharacter: ArrayList<ResultCharacter>) : PagingDataAdapter<ResultCharacter, CharactersPagingAdapter.CharactersViewHolder>(COMPARATOR) {


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
            binding.characterItem.setOnClickListener {
                val bundle = bundleOf(
                    "characterId" to resultCharacter.id,
                    "characterName" to resultCharacter.name,
                    "characterStatus" to resultCharacter.status,
                    "characterSpecies" to resultCharacter.species,
                    "characterOrigin" to resultCharacter.origin?.name,
                    "characterLocation" to resultCharacter.location?.name,
                    "characterImage" to resultCharacter.image
                )
                val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment()
                itemView.findNavController().navigate(action.actionId, bundle)
            }
        }
    }
}