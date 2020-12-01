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
import ercanduman.visualizerestaurant.ui.utils.hide
import ercanduman.visualizerestaurant.ui.utils.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_toolbar.*
import kotlinx.android.synthetic.main.activity_main_toolbar_search.*
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AppAdapter.ItemClickListener {
    private val viewModel: AppViewModel by viewModels()

    private lateinit var appAdapter: AppAdapter
    private lateinit var mRestaurant: List<Restaurant>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        val searchText = main_toolbar_search_text_field.text.toString()
        if (searchText.isEmpty().not()) resetSearch()
        else super.onBackPressed()
    }

    private fun setupRecyclerView() {
        main_recycler_view_restaurants.apply {
            adapter = appAdapter
        }
    }

    private fun initToolbarActions() {
        main_toolbar_search_icon.setOnClickListener { applySearch() }
        main_toolbar_search_cancel.setOnClickListener { resetSearch() }
    }

    private fun initSpinner() {
        main_spinner_filter.apply {
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
        main_toolbar.hide()
        main_toolbar_search.show()
        main_toolbar_search_text_field.apply {
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
        main_toolbar.show()
        main_toolbar_search_text_field.setText("")
        main_toolbar_search.hide()
    }

    private fun getFilteredRestaurants(searchText: String): List<Restaurant> {
        return if (searchText.isEmpty()) mRestaurant
        else mRestaurant.filter { it.name.toLowerCase(Locale.getDefault()).contains(searchText) }
    }

    private fun showContent(show: Boolean, message: String?) {
        if (show) {
            main_recycler_view_restaurants.show()
            main_not_found.hide()
        } else {
            main_recycler_view_restaurants.hide()
            main_not_found.apply {
                show()
                text = message
            }
        }
    }
}