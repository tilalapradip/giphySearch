package com.example.espl.sampleappkotlin.giphysearch

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import com.example.espl.sampleappkotlin.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import android.app.SearchManager
import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.TypedValue
import com.example.espl.sampleappkotlin.R
import com.example.espl.sampleappkotlin.exoplayer.ExoPlayerActivity
import com.example.espl.sampleappkotlin.models.DataItem
import com.example.espl.sampleappkotlin.models.ImageData



class MainActivity : AppCompatActivity() {
    var searchView: SearchView? = null
    private lateinit var viewModel: SimpleViewModel
    lateinit var queryStr:String;
    val itemadapter: DataItemsAdapter =
    DataItemsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_main
    )
        viewModel = ViewModelProviders.of(this)[SimpleViewModel::class.java]

        val layoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter =itemadapter
        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                dpToPx(14),
                true
            )
        )
        binding.setLifecycleOwner(this);
        binding.viewModel = viewModel.catRes

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val  totalItemCount = layoutManager.getItemCount();
                val  firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!viewModel.catRes.isLoading) {
                    if ((firstVisibleItemPosition+7) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if( viewModel.catRes.islastPage==false){
                            viewModel.catRes.isVisible=false;
                            viewModel.catRes.isVisibleProgress=true;
                            viewModel.catRes.isLoading=true

                            viewModel.catRes.notifyChange()

                            viewModel.getDataItemsPage(queryStr,itemadapter.itemCount)
                        }
                    }
                }
            }
        })

        binding.executePendingBindings()
    }


    fun onClickItem(pos:Int) {
        val intent=Intent(this@MainActivity, ExoPlayerActivity::class.java)
        intent.putExtra(
            this@MainActivity.resources.getString(R.string.id),""+itemadapter.getItemdata(pos).id)
        intent.putExtra(this@MainActivity.resources.getString(R.string.url),""+ itemadapter.getItemdata(pos).images!!.get("original")!!.mp4)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.dashboard, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchManager = this@MainActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(this@MainActivity.componentName))
        }

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                queryStr=query
                viewModel.catRes.isVisible=false;
                viewModel.catRes.isVisibleProgress=true;
                viewModel.catRes.isLoading=true
                viewModel.catRes.notifyChange()
                viewModel.catRes.data.clear()
                viewModel.catRes.islastPage=false
                viewModel.getDataItemsPage(query,0)

                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        searchView?.clearFocus()
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }
}
