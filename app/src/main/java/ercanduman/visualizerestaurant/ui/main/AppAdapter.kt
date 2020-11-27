package ercanduman.visualizerestaurant.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ercanduman.visualizerestaurant.R
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import kotlinx.android.synthetic.main.list_item_restaurant.view.*

/**
 * Responsible to provide views that represents items in list.
 *
 * @author ERCAN DUMAN
 * @since  27.11.2020
 */
class AppAdapter : RecyclerView.Adapter<AppAdapter.ItemViewHolder>() {

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

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val restaurant = listDiffer.currentList[position]
        holder.itemView.apply {
            item_name.text = restaurant.name
            item_opening_state.text = restaurant.status
            item_sorting_value.text = restaurant.sortingValues.bestMatch.toString()

            val drawable =
                if (restaurant.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            item_favorite.setImageDrawable(ContextCompat.getDrawable(context, drawable))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_restaurant, parent, false)
        )
    }

    override fun getItemCount(): Int = listDiffer.currentList.size
}