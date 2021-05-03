package com.ajinkya.assignmentapp

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajinkya.assignmentapp.adapters.UsersAdapter
import com.ajinkya.assignmentapp.databinding.ActivityMainBinding
import com.ajinkya.assignmentapp.listeners.IUserInfoListener
import com.ajinkya.assignmentapp.models.UsersInfoModel
import com.ajinkya.assignmentapp.utils.ConnectionLiveData
import com.ajinkya.assignmentapp.viewmodels.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class MainActivity : AppCompatActivity(), IUserInfoListener {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter
    private lateinit var layoutManagerAdapter: LinearLayoutManager
    private lateinit var userList: ArrayList<UsersInfoModel.Data>
    private var perPageCount = 5
    private var pageNo = 0
    var endOfList = false
    private var isInternetConnected = true
    private lateinit var connectionLiveData: ConnectionLiveData

    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        connectionLiveData.observe(this, { isNetwork ->
            isNetwork.also { this.isInternetConnected = it }
            showSnack(isNetwork)
        })
        initData()
        initScrollListener()
        observeData()
        swipeRefreshFunction()


    }


    private fun showSnack(isConnected: Boolean) {
        val message: String
        val color: Int
        if (isConnected) {
            message = "Good! Connected to Internet"
            color = Color.WHITE
        } else {
            message = "Sorry! Not connected to internet"
            color = Color.RED
        }


        val snackBar = Snackbar.make(
            binding.root, message,
            Snackbar.LENGTH_LONG
        )
//        snackBar.setActionTextColor(Color.BLUE)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.BLACK)
        val textView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(color)
        snackBar.show()
    }

    private fun swipeRefreshFunction() {

        binding.swipeRefresh.setOnRefreshListener {
            pageNo = 0
            endOfList = false
            isLoading = false
            userList = arrayListOf()
            loadMore()
        }
    }

    private fun observeData() {
        viewModel.getUserListInfo()!!.observe(this, { t ->

            Handler(Looper.getMainLooper()!!).postDelayed({
                if (userList.size != 0) {
                    userList.removeAt(userList.size - 1)
                }

                val scrollPosition: Int = userList.size
                adapter.notifyItemRemoved(scrollPosition)
                var currentSize = scrollPosition
                val nextLimit = currentSize + perPageCount

                while (currentSize - 1 < nextLimit) {
                    //userList.add("Item $currentSize")
                    currentSize++
                }
                if (t!!.data.size != 0) {
                    perPageCount = t.perPage
                    userList.addAll(t.data)
                    adapter.setData(userList)
                } else {
                    endOfList = true
                }
                if (binding.swipeRefresh.isRefreshing) {
                    binding.swipeRefresh.isRefreshing = false
                }

                isLoading = false

            }, 2000)


        })

        viewModel.getErrorInfo()!!.observe(this, { throwable ->
            if (throwable is HttpException) {
                showSnack(false)
                // callback.onFailure(((HttpException) throwable).code());
            } else if (throwable is SocketTimeoutException
                || throwable is UnknownHostException
                || throwable is ConnectException
            ) {
                showSnack(false)
            } else {
                showSnack(false)
            }
            binding.swipeRefresh.isRefreshing = false
        })

    }


    private fun initData() {

        userList = arrayListOf()
        viewModel.listener = this
        adapter = UsersAdapter(userList)
        binding.rvUserList.setHasFixedSize(true)
        layoutManagerAdapter = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvUserList.layoutManager = layoutManagerAdapter
        binding.rvUserList.adapter = adapter
        binding.swipeRefresh.isRefreshing = true



        loadMore()
    }

    override fun getPageNo(): Int {
        return pageNo
    }

    override fun getPerPageCount(): Int {
        return perPageCount
    }


    private fun initScrollListener() {
        binding.rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == userList.size - 1) {
                        if (!endOfList) {
                            loadMore()
                        }
                        isLoading = true
                    }
                }
            }
        })


    }

    private fun loadMore() {
        if (pageNo != 0) {
            userList.add(UsersInfoModel.Data())
            adapter.notifyDataSetChanged()

        }

        if (isInternetConnected) {

            pageNo++
            viewModel.callAPIForUserList()
        } else {
            showSnack(false)
            binding.swipeRefresh.isRefreshing = false
        }


    }

}