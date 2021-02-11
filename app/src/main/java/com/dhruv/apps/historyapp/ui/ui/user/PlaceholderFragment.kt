package com.dhruv.apps.historyapp.ui.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.apps.historyapp.adapters.GitUserAdapter
import com.dhruv.apps.historyapp.databinding.FragmentUserBinding
import com.dhruv.apps.historyapp.models.GitUser
import com.dhruv.apps.historyapp.utils.observe
import org.greenrobot.eventbus.EventBus

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var pageViewModel: PageViewModel
    private lateinit var binding: FragmentUserBinding
    private lateinit var mAdapter: GitUserAdapter
    private var arrayListGit = ArrayList<GitUser.GitUserItem>()
    private lateinit var mEventBus: EventBus
    private var start = 0
    private val limit = 100
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    private var loading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater)
        binding.viewModel = this.pageViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInit()
    }

    private fun onInit() {
        mEventBus = EventBus.getDefault()
        initializeObservers()
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        binding.recyclerviewUser.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity)
        binding.recyclerviewUser.layoutManager = layoutManager
        mAdapter = GitUserAdapter(arrayListGit) {
            //pageViewModel.fetchSelectedUsers()
            val sListData = arrayListGit.filter {
                it.isChecked
            }
            mEventBus.postSticky(sListData)
        }
        binding.recyclerviewUser.adapter = mAdapter

        binding.recyclerviewUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false
                            pageViewModel.fetchGitUsers(requireActivity(),limit.toString(),start.toString())
                        }
                    }
                }
            }
        })
    }

    private fun initializeObservers() {
        observe(pageViewModel.fetchGitUsers(requireActivity(),limit.toString(),start.toString())) {
            if (it.isNotEmpty()) {
                arrayListGit.addAll(it as ArrayList)
                mAdapter.setData(arrayListGit)
                if (it.size >= limit) {
                    start = arrayListGit.size
                    loading = true
                } else {
                    loading = false
                }
            }
        }
        observe(pageViewModel.mShowProgressBar) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        /*observe(pageViewModel.fetchSelectedUsers()) {
            Log.e("TAG", "come_here: ")
            mEventBus.postSticky(it)
        }*/
    }
}