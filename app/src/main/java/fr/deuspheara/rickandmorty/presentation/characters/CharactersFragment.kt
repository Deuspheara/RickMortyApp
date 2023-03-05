package fr.deuspheara.rickandmorty.presentation.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import fr.deuspheara.rickandmorty.databinding.FragmentCharactersBinding
import fr.deuspheara.rickandmorty.utils.NetworkUtils
import fr.deuspheara.rickandmorty.utils.PaginationScrollListener
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var viewModel: CharactersViewModel
    private lateinit var pagingAdapter: CharactersPagingAdapter
    private lateinit var adapter: CharacterAdapter

    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[CharactersViewModel::class.java]
        pagingAdapter = CharactersPagingAdapter(arrayListOf())
        adapter = CharacterAdapter(arrayListOf())

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.getCharacters().collect {
                pagingAdapter.submitData(it)
            }
        }
        binding.charactersRecyclerView.adapter = pagingAdapter
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
                binding.progressBar.isVisible = true
                viewLifecycleOwner.lifecycle.coroutineScope.launch {
                    viewModel.getCharacters().collect {
                        binding.progressBar.isVisible = false
                        pagingAdapter.submitData(it)
                    }
                }

            }

        })

        binding.searchCharacter.setOnCloseListener {
            binding.charactersRecyclerView.adapter = pagingAdapter
            binding.searchCharacter.clearFocus()

            false
        }

        binding.searchCharacter.setOnClickListener {
            binding.searchCharacter.onActionViewExpanded()
        }

        binding.searchCharacter.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (NetworkUtils.provideIsNetworkAvailable(requireContext())) {
                    viewLifecycleOwner.lifecycle.coroutineScope.launch {
                        viewModel.searchCharacters(query!!).observe(viewLifecycleOwner) {
                            viewLifecycleOwner.lifecycle.coroutineScope.launch {
                                pagingAdapter.submitData(PagingData.from(it.results))
                            }

                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText == "") {
                    binding.charactersRecyclerView.adapter = pagingAdapter
                    return true
                }else{
                    binding.charactersRecyclerView.adapter = adapter
                    if (NetworkUtils.provideIsNetworkAvailable(requireContext()) && newText != null) {
                        viewLifecycleOwner.lifecycle.coroutineScope.launch {
                            viewModel.searchCharacters(newText).observe(viewLifecycleOwner) {
                                adapter.updateCharacters(it.results)
                            }
                        }
                    }
                }

                return true
            }

        })

        return binding.root
    }


}