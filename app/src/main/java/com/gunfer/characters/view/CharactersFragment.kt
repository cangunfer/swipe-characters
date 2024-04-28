package com.gunfer.characters.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gunfer.characters.R
import com.gunfer.characters.adapter.CharacterAdapter
import com.gunfer.characters.cardstack.CardContainer
import com.gunfer.characters.cardstack.CardListener
import com.gunfer.characters.cardstack.pulse
import com.gunfer.characters.cardstack.px
import com.gunfer.characters.model.CharacterModel
import com.gunfer.characters.model.CharacterResponse
import com.gunfer.characters.viewmodel.CharactersViewModel


class CharactersFragment : Fragment(), CardListener {

    private val viewModel: CharactersViewModel by viewModels()
    lateinit var cardContainer: CardContainer
    lateinit var adapter: CharacterAdapter
    private lateinit var characterResponse : CharacterResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardContainer = view.findViewById(R.id.cardContainer)
        cardContainer.setOnCardActionListener(this)

        cardContainer.maxStackSize = 3
        cardContainer.marginTop = 13.px
        cardContainer.margin = 20.px

        cardContainer.setEmptyView(generateEmptyView())
        cardContainer.addFooterView(generateFooterView())

        adapter = CharacterAdapter(mutableListOf(), requireActivity())
        cardContainer.setAdapter(adapter)

        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.characterResponseLiveData.observe(
            viewLifecycleOwner,
            Observer { characterResponse ->
                characterResponse?.let {
                    this.characterResponse = it
                    val dataList = mutableListOf<CharacterModel>()
                    dataList.addAll(characterResponse.results)
                    cardContainer.getViewList()
                    adapter.updateProductList(dataList)
                    cardContainer.setAdapter(adapter)
                }
            })
    }

    override fun onLeftSwipe(position: Int, model: Any) {
        Log.e("SwipeLog", "onLeftSwipe pos: $position model: " + (model as CharacterModel).toString())
    }

    override fun onRightSwipe(position: Int, model: Any) {
        Log.e("SwipeLog", "onRightSwipe pos: $position model: " + (model as CharacterModel).toString())
    }

    override fun onItemShow(position: Int, model: Any) {
        Log.e("SwipeLog", "onItemShow pos: $position model: " + (model as CharacterModel).toString())
    }

    override fun onSwipeCancel(position: Int, model: Any) {
        Log.e("SwipeLog", "onSwipeCancel pos: $position model: " + (model as CharacterModel).toString())
    }

    override fun onSwipeCompleted() {
        Log.e("SwipeLog", "Out of swipe data")
    }

    override fun onRefreshAdapter(position: Int, model: Any) {
        characterResponse.info.next?.let {
            viewModel.getDataFromAPI(it.split("page=").last().toInt())
        }
    }

    private fun generateEmptyView(): View {
        return LayoutInflater.from(activity).inflate(R.layout.empty_layout, null)
    }

    private fun generateFooterView(): View {
        val v = LayoutInflater.from(activity).inflate(R.layout.footer_view, null)
        val cancelView = v.findViewById<ImageView>(R.id.cancel)
        val likeView = v.findViewById<ImageView>(R.id.like)

        cancelView.setOnClickListener {
            it.pulse()
            adapter.swipeLeft()
        }
        likeView.setOnClickListener {
            it.pulse()
            adapter.swipeRight()
        }
        return v
    }
}