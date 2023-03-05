package fr.deuspheara.rickandmorty.presentation.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import fr.deuspheara.rickandmorty.databinding.FragmentCharactersBinding
import fr.deuspheara.rickandmorty.utils.NetworkUtils
import fr.deuspheara.rickandmorty.utils.PaginationScrollListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var viewModel: CharactersViewModel
    private lateinit var adapter: CharactersAdapter

    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[CharactersViewModel::class.java]
        adapter = CharactersAdapter(arrayListOf())

        //use getCharacters Flow<PagingData<ResultCharacter>> from CharactersViewModel
        //launch coroutineScope to collect data from Flow<PagingData<ResultCharacter>>
        //and submit data to adapter
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.getCharacters().collect {
                adapter.submitData(it)
            }
        }
        binding.charactersRecyclerView.adapter = adapter
        binding.charactersRecyclerView.layoutManager =  LinearLayoutManager(requireContext())


        binding.charactersRecyclerView.addOnScrollListener(object : PaginationScrollListener(binding.charactersRecyclerView.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                viewLifecycleOwner.lifecycle.coroutineScope.launch {
                    viewModel.getCharacters().collect {
                        adapter.submitData(it)
                    }
                }
            }

        })

        return binding.root
    }


}