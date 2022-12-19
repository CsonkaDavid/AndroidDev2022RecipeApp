package net.csonkadavid.androiddev2022recipeapp.view.recycler

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.csonkadavid.androiddev2022recipeapp.persistence.model.RecipeEntity
import net.csonkadavid.webdev2022recipeapp.R

class RecipeListRecyclerViewAdapter(
    private var recipeModelList: List<RecipeEntity>,
    private val recyclerInterface: IClickableRecycler
    ) :
    ListAdapter<RecipeListRecyclerViewAdapter.Item, RecyclerView.ViewHolder>(RecipeDiffUtil()) {

    companion object {
        class RecipeListItemViewHolder(itemView: View, recyclerInterface: IClickableRecycler) :
            RecyclerView.ViewHolder(itemView) {

            var recipeNameView :TextView
            var recipeCategoryView :TextView
            var recipePrepTimeView :TextView
            var recipeImageView :ImageView

            init {
                recipeNameView = itemView.findViewById(R.id.element_recipe_name)
                recipeCategoryView = itemView.findViewById(R.id.element_recipe_category)
                recipePrepTimeView = itemView.findViewById(R.id.element_recipe_prepTime)
                recipeImageView = itemView.findViewById(R.id.element_recipe_image)

                itemView.setOnClickListener {
                    val pos = adapterPosition

                    if(pos != RecyclerView.NO_POSITION) {
                        recyclerInterface.onItemClick(pos)
                    }
                }
            }
        }

        class RecipeListViewHolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var title : TextView

            init {
                title = itemView.findViewById(R.id.recipe_all_header)
            }

            fun bind(title :String) {
                this.title.text = title
            }
        }
    }

    private val TYPE_HEADER : Int = 0
    private val TYPE_LIST : Int = 1

    override fun getItemViewType(position: Int) :Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_LIST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view :View
        return when (viewType) {
            TYPE_HEADER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_recipe_list_all_header, parent, false)

                RecipeListViewHolderHeader(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_element, parent, false)

                RecipeListItemViewHolder(view, recyclerInterface)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is RecipeListViewHolderHeader -> {

                holder.bind("Recipes")
            }

            is RecipeListItemViewHolder -> {
                val recipe = recipeModelList[position-1]

                holder.recipeNameView.text = recipe.name
                holder.recipeImageView.setImageResource(R.drawable.pngegg)
                holder.recipeCategoryView.text = recipe.category
                holder.recipePrepTimeView.text = recipe.prepTime.toString()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRecipeList(list :List<RecipeEntity>) {
        recipeModelList = list
        notifyDataSetChanged()
    }

    fun addHeaderAndSubmitList(list: List<RecipeEntity>?) {
        CoroutineScope(Dispatchers.Default).launch {
            val items = when (list) {
                null -> listOf(Item.Header)
                else -> listOf(Item.Header) + list.map { Item.RecipeItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class RecipeDiffUtil : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    sealed class Item {
        abstract val id: Int

        data class RecipeItem(val recipeEntity: RecipeEntity) : Item() {
            override val id: Int
                get() = recipeEntity.id
        }

        object Header : Item() {
            override val id: Int
                get() = Int.MIN_VALUE
        }
    }
}

