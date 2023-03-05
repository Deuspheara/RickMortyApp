package fr.deuspheara.rickandmorty.presentation.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import fr.deuspheara.rickandmorty.databinding.FragmentCharacterDetailsBinding


class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

       Glide.with(this)
            .load(arguments?.getString("characterImage"))
            .into(binding.imageView)
        binding.nameCharacterDetail.text = arguments?.getString("characterName")
        binding.characterOrigin.text = arguments?.getString("characterOrigin")
        binding.lastKnownLocation.text = arguments?.getString("characterLocation")
        binding.statusCharacterDetail.text = "${arguments?.getString("characterStatus")} - ${arguments?.getString("characterSpecies")}"
        when(arguments?.getString("characterStatus")){
            "Alive" -> binding.characterStatusColorDetail.setCardBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark))
            "Dead" -> binding.characterStatusColorDetail.setCardBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))
            "unknown" -> binding.characterStatusColorDetail.setCardBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
        }
        binding.backButton.setOnClickListener {
            it.findNavController().navigateUp()
        }


        return binding.root
    }
}