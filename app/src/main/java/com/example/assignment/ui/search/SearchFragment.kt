package com.example.assignment.ui.search

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.assignment.R
import com.example.assignment.adapter.CountryAdapter
import com.example.assignment.api.response.country.CountryResponseItem
import com.example.assignment.databinding.FragmentSearchBinding
import com.example.assignment.di.MyPreference
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()
    private var adapter: CountryAdapter? = null

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var pref: MyPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)
        adapter = CountryAdapter(requireContext())
        _binding?.recyclerView?.adapter = adapter
        binding.progressBar.visibility = View.VISIBLE

        viewModel.callCountriesApi()
        viewModel.getCountries.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            Log.d("response", it.toString())
            val list: MutableList<CountryResponseItem> = mutableListOf()
            it?.let {
                list.addAll(it)
            }
            adapter?.setData(list)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val filterItem = menu.findItem(R.id.action_filter)
        val searchView = searchItem.actionView as SearchView

        filterItem.setOnMenuItemClickListener {
            val colors = arrayOf("<1M", "<5M", "<10M", "Reset")

            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Population")
            builder.setItems(colors, DialogInterface.OnClickListener { dialog, which ->
                // the user clicked on colors[which]
                val list: List<CountryResponseItem>? = adapter?.getData()

                when(which){
                    0 -> {
                        adapter?.setData(list?.filter { it.population < 1 * 100000 })
                    }
                    1 -> {

                    adapter?.setData(list?.filter { it.population < 5 * 100000 })
                    }
                    2 -> {

                    adapter?.setData(list?.filter { it.population < 10 * 100000 })
                    }
                    3 -> {

                    adapter?.setData(viewModel.getCountries.value)
                    }
                    else -> {

                    adapter?.setData(viewModel.getCountries.value)
                    }
                }

            })
            builder.show()

            true
        }

        val closeButton: ImageView = searchView.findViewById(R.id.search_close_btn) as ImageView

        closeButton.setOnClickListener {
            adapter?.setData(viewModel.getCountries.value)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.length >= 3) {
                    binding.recyclerView.scrollToPosition(0)
                    val list: List<CountryResponseItem>? = adapter?.getData()
                    adapter?.setData(list?.filter { it.name.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) })
                    searchView.clearFocus()
                } else
                    Toast.makeText(
                            activity,
                            "Type at least 3 characters to search",
                            Toast.LENGTH_SHORT
                    ).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}