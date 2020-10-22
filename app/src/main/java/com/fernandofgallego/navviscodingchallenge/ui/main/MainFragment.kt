package com.fernandofgallego.navviscodingchallenge.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandofgallego.navviscodingchallenge.data.AssetsJsonProvider
import com.fernandofgallego.navviscodingchallenge.data.NetworkJsonProvider
import com.fernandofgallego.navviscodingchallenge.data.Repository
import com.fernandofgallego.navviscodingchallenge.databinding.MainFragmentBinding
import com.fernandofgallego.navviscodingchallenge.domain.JsonParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: DataAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.layoutManager = LinearLayoutManager(context).also {
            it.orientation = LinearLayoutManager.VERTICAL
        }

        adapter = DataAdapter()

        binding.list.adapter = adapter

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(
                Repository(
                    AssetsJsonProvider(context!!.assets, "numbers.json"),
                    NetworkJsonProvider("https://navvis.com/numbers.json"),
                    JsonParser()
                )
            )
        ).get(MainViewModel::class.java)

        viewModel.data.observe(this) {
            adapter.setItems(it)
            adapter.notifyDataSetChanged()
        }

        GlobalScope.launch {
            viewModel.update()
        }
    }
}