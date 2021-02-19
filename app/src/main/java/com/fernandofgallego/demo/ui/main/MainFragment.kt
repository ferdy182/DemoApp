package com.fernandofgallego.demo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandofgallego.demo.R
import com.fernandofgallego.demo.data.AssetsJsonProvider
import com.fernandofgallego.demo.data.NetworkJsonProvider
import com.fernandofgallego.demo.data.Repository
import com.fernandofgallego.demo.databinding.MainFragmentBinding
import com.fernandofgallego.demo.domain.JsonParser
import com.fernandofgallego.demo.domain.NumberProcessor
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
                    AssetsJsonProvider(context!!.assets, resources.getString(R.string.json_file)),
                    NetworkJsonProvider(resources.getString(R.string.backend_url)),
                    JsonParser()
                ),
                NumberProcessor()
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