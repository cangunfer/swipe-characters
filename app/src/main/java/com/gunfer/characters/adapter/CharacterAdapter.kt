package com.gunfer.characters.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.gunfer.characters.R
import com.gunfer.characters.cardstack.CardContainerAdapter
import com.gunfer.characters.model.CharacterModel
import com.gunfer.characters.util.downloadFromUrl

class CharacterAdapter(private val list: MutableList<CharacterModel>, context: Context) : CardContainerAdapter() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItem(position: Int) = list[position]

    @SuppressLint("InflateParams")
    override fun getView(position: Int): View {
        val v = layoutInflater.inflate(R.layout.card_view, null)
        val characterImageView = v.findViewById<ImageView>(R.id.image)
        val characterName = v.findViewById<TextView>(R.id.name)
        val status = v.findViewById<TextView>(R.id.status)
        val lastLocation = v.findViewById<TextView>(R.id.locationName)

        val characterModel = getItem(position)

        characterModel.image?.let { characterImageView.downloadFromUrl(it, placeholderProgress(v.context)) }
        characterName.text = characterModel.name
        characterModel.location?.let { lastLocation.text = it.name }

        status.text = characterModel.status
        return v
    }

    override fun getCount(): Int = list.size

    fun placeholderProgress(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context)
            .apply {
                strokeWidth = 8f
                centerRadius = 40f
                start()
            }
    }

    fun updateProductList(newProductList: List<CharacterModel>)  {
        list.clear()
        list.addAll(newProductList)
    }
}