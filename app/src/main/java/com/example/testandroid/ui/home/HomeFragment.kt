package com.example.testandroid.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testandroid.R
import com.example.testandroid.databinding.FragmentHomeBinding
import com.example.testandroid.model.Asset
import com.example.testandroid.model.User
import com.example.testandroid.services.Status
import com.example.testandroid.ui.profile.EditProfileActivity
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.File

class HomeFragment : Fragment() {
    companion object {
        const val EDIT_PROFILE_REQUEST = 1
    }
    private lateinit var adapter: RecyclerAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val homeBinding : FragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setupObservers()
        homeBinding.homeViewModel = homeViewModel
        homeBinding.homeFragment = this
        homeBinding.lifecycleOwner = this
        val homeFragment = homeBinding.getRoot()
        initAppBarLayout(homeFragment)
        activity?.actionBar?.hide()

        return homeFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java).apply {
                putExtra("username", homeViewModel.user.value!!.username)
                putExtra("name", homeViewModel.user.value!!.name)
                putExtra("biography", homeViewModel.user.value!!.biography)
                putExtra("path", homeViewModel.user.value!!.profilePicture)
            }
            startActivityForResult(intent, EDIT_PROFILE_REQUEST)
        }
        setupUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupObservers() {
        homeViewModel.getAssets().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        println(it.message)
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerAdapter(arrayListOf())
        recyclerView.adapter = adapter
        Glide.with(avatar.context)
            .load(ContextCompat.getDrawable(requireActivity(), R.drawable.photo))
            .circleCrop()
            .into(avatar)
    }

    private fun retrieveList(assets: List<Asset>) {
        adapter.apply {
            addAssets(assets)
            notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PROFILE_REQUEST && data != null) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                data.apply {
                    var user1: User = User()
                    user1.username = getStringExtra("username")
                    user1.name = getStringExtra("name")
                    user1.biography = getStringExtra("biography")
                    user1.profilePicture = getStringExtra("path")

                    homeViewModel.setUser(user1)
                    if (!getStringExtra("path").isNullOrBlank())
                        loadPicture(getStringExtra("path"))
                }

            }
        }
    }

    private fun loadPicture(path: String) {
        Glide.with(avatar.context)
            .load(File(path))
            .circleCrop()
            .into(avatar)
    }

    private fun initAppBarLayout(homeFragment: View) {
        var isShow = true
        var scrollRange = -1
        homeFragment.app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                homeFragment.toolbar_layout.title = homeViewModel.user.value!!.name
                isShow = true
            } else if (isShow){
                homeFragment.toolbar_layout.title = " " //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })
    }

}