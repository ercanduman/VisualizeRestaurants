package ercanduman.visualizerestaurant.ui.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.visualizerestaurant.R
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import ercanduman.visualizerestaurant.databinding.ActivityMainBinding
import ercanduman.visualizerestaurant.databinding.ActivityMainSearchBarBinding
import ercanduman.visualizerestaurant.databinding.ActivityMainToolbarBinding
import ercanduman.visualizerestaurant.ui.utils.hide
import ercanduman.visualizerestaurant.ui.utils.show
import kotlinx.coroutines.launch
import java.util.*

/**
 * The main UI which all user interactions will be handled and also items list will be displayed.
 *
 * Annotation on an Activity with @AndroidEntryPoint enables member injection into activity class.
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 *
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AppAdapter.ItemClickListener {
    private val viewModel: AppViewModel by viewModels()

    private lateinit var appAdapter: AppAdapter
    private lateinit var mRestaurant: List<Restaurant>

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbarBinding: ActivityMainToolbarBinding
    private lateinit var searchBinding: ActivityMainSearchBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbarBinding = binding.activityMainToolbar
        searchBinding = binding.activityMainSearchBar

        initToolbarActions()
        appAdapter = AppAdapter(this)
        setupRecyclerView()
        initSpinner()

        loadData()
    }

    override fun onItemClicked(position: Int) {
        val currentItem = appAdapter.getCurrentItem(position)
        val favorite = currentItem.isFavorite

        currentItem.isFavorite = favorite.not()
        appAdapter.notifyItemChanged(position)
        viewModel.update(currentItem)
    }

    override fun onBackPressed() {
        val searchText = searchBinding.mainSearchBarTextField.text.toString()
        if (searchText.isEmpty().not()) resetSearch()
        else super.onBackPressed()
    }

    private fun setupRecyclerView() {
        binding.mainRecyclerViewRestaurants.apply {
            adapter = appAdapter
        }
    }

    private fun initToolbarActions() {
        toolbarBinding.mainToolbarSearchIcon.setOnClickListener { applySearch() }
        searchBinding.mainSearchBarCancel.setOnClickListener { resetSearch() }
    }

    private fun initSpinner() {
        binding.mainSpinnerFilter.apply {
            setSelection(viewModel.sortType.ordinal)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(aV: AdapterView<*>?, v: View?, position: Int, i: Long) {
                    viewModel.sortType = SortType.values()[position]
                    loadData()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun loadData() = lifecycleScope.launch {
        viewModel.getRestaurants().observe(this@MainActivity, {
            mRestaurant = it
            appAdapter.submitList(mRestaurant)
            appAdapter.setSortingOption(viewModel.sortType)
        })
    }

    private fun applySearch() {
        toolbarBinding.mainToolbar.hide()
        searchBinding.mainSearchBarParent.show()
        searchBinding.mainSearchBarTextField.apply {
            addTextChangedListener {
                val searchText = text.toString().toLowerCase(Locale.getDefault())

                val filteredList = getFilteredRestaurants(searchText)
                if (filteredList.isEmpty()) {
                    val message: String = getString(R.string.format_item_not_found, searchText)
                    showContent(false, message)
                } else {
                    showContent(true, null)
                    appAdapter.submitList(filteredList)
                    appAdapter.setSortingOption(viewModel.sortType)
                }
            }
        }
    }

    private fun resetSearch() {
        toolbarBinding.mainToolbar.show()
        searchBinding.mainSearchBarTextField.setText("")
        searchBinding.mainSearchBarParent.hide()
    }

    private fun getFilteredRestaurants(searchText: String): List<Restaurant> {
        return if (searchText.isEmpty()) mRestaurant
        else mRestaurant.filter { it.name.toLowerCase(Locale.getDefault()).contains(searchText) }
    }

    private fun showContent(show: Boolean, message: String?) {
        if (show) {
            binding.mainRecyclerViewRestaurants.show()
            binding.mainNotFound.hide()
        } else {
            binding.mainRecyclerViewRestaurants.hide()
            binding.mainNotFound.apply {
                show()
                text = message
            }
        }
    }
}