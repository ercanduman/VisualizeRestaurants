package ercanduman.visualizerestaurant.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ercanduman.visualizerestaurant.R
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import ercanduman.visualizerestaurant.databinding.ListItemRestaurantBinding

/**
 * Responsible to provide views that represents items in list.
 *
 * @author ERCAN DUMAN
 * @since  27.11.2020
 */
class AppAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<AppAdapter.ItemViewHolder>() {
    private var sortingOption: SortType = SortType.bestMatch

    /**
     * DiffUtil is a utility class that calculates the differences between two lists.
     * It can be used to calculate updates for RecyclerView Adapter. ListAdapter or AsyncListDiffer
     * can be implemented which can simplify the usage of DiffUtil on a background thread.
     */
    private val diffCallback = object : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            /**
             * hashCodes are calculated hash values for the object.
             * If two objects are equal according to the equals() method,
             * then calling the hashCode() method on each of the two objects must produce the same integer result.
             */
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    /**
     * A list differ is a tool that takes two lists and calculates the differences.
     * (Best practice to use for RecyclerViews)
     *
     * With list differ there no need to update all items, only changed items will be updated.
     */
    private val listDiffer = AsyncListDiffer(this, diffCallback)

    /**
     * Apply parameter list to listDiffer.
     *
     * @param list List<Restaurant>
     */
    fun submitList(list: List<Restaurant>) = listDiffer.submitList(list)

    fun setSortingOption(sortType: SortType) {
        sortingOption = sortType
        notifyDataSetChanged()
    }

    /**
     * Gets item from list based on position
     *
     * @param position Int item position
     * @return Restaurant
     */
    fun getCurrentItem(position: Int): Restaurant = listDiffer.currentList[position]

    inner class ItemViewHolder(private val binding: ListItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.apply {
                val context = itemView.context
                itemName.text = restaurant.name
                val openingStateText =
                    context.getString(R.string.format_opening_state, restaurant.status)
                itemOpeningState.text = openingStateText

                val sortValueText =
                    context.getString(R.string.format_sort_value, getSortValue(restaurant))
                itemSortingValue.text = sortValueText

                val drawable =
                    if (restaurant.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                itemFavorite.apply {
                    setImageDrawable(ContextCompat.getDrawable(context, drawable))

                    /**
                     * Item clicks will be passed to listener
                     */
                    setOnClickListener {
                        val position = adapterPosition

                        /**
                         * Check if position is valid.
                         *
                         * In delete or new insertion processes if list item clicked,
                         * it is possible to click on an item which is animating during process
                         * and its position might be invalid.
                         */
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClicked(position)
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val restaurant = listDiffer.currentList[position]
        holder.bind(restaurant)
    }

    private fun getSortValue(restaurant: Restaurant): String {
        return when (sortingOption) {
            SortType.bestMatch -> restaurant.sortingValues.bestMatch.toString()
            SortType.newest -> restaurant.sortingValues.newest.toString()
            SortType.ratingAverage -> restaurant.sortingValues.ratingAverage.toString()
            SortType.distance -> restaurant.sortingValues.distance.toString()
            SortType.popularity -> restaurant.sortingValues.popularity.toString()
            SortType.averageProductPrice -> restaurant.sortingValues.averageProductPrice.toString()
            SortType.deliveryCosts -> restaurant.sortingValues.deliveryCosts.toString()
            SortType.minCost -> restaurant.sortingValues.minCost.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ListItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }
}