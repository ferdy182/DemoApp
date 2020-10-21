package com.fernandofgallego.navviscodingchallenge.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandofgallego.navviscodingchallenge.R
import com.fernandofgallego.navviscodingchallenge.data.AssetsJsonProvider
import com.fernandofgallego.navviscodingchallenge.data.Repository
import com.fernandofgallego.navviscodingchallenge.databinding.MainFragmentBinding

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

        adapter.setItems(listOf(
            DataItem.Section("Section 1"),
            DataItem.Item("Item 1", false),
            DataItem.Item("Item 2", true),
            DataItem.Section("Section 2"),
            DataItem.Section("Section 3"),
            DataItem.Item("Item 1", true)
        ))

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(
                Repository(
                    AssetsJsonProvider(context!!.assets)
                )
            )
        ).get(MainViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.data.observe(this) {
            adapter.setItems(it)
            adapter.notifyDataSetChanged()
        }

        viewModel.parseNumbers(viewModel.getNumbers())



    }
}