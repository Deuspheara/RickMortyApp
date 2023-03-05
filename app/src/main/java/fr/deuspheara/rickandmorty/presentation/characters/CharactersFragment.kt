package fr.deuspheara.rickandmorty.presentation.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fr.deuspheara.rickandmorty.databinding.FragmentCharactersBinding
import fr.deuspheara.rickandmorty.utils.NetworkUtils

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var viewModel: CharactersViewModel
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CharactersViewModel::class.java]
        adapter = CharactersAdapter(arrayListOf())

        viewModel.charactersListLiveData.observe(viewLifecycleOwner) { characters ->
            characters?.let {
                binding.charactersRecyclerView.visibility = View.VISIBLE
                adapter.updateCharacters(characters.results)
            }
        }

        binding.charactersRecyclerView.adapter = adapter
        binding.charactersRecyclerView.layoutManager =  LinearLayoutManager(requireContext())

        if (NetworkUtils.provideIsNetworkAvailable(requireActivity())) {
            viewModel.getCharacters(1)
        }


    }


}