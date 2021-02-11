package com.dhruv.apps.historyapp.ui.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhruv.apps.historyapp.adapters.GitUserAdapter
import com.dhruv.apps.historyapp.adapters.GitUserSelectedAdapter
import com.dhruv.apps.historyapp.databinding.FragmentUserBinding
import com.dhruv.apps.historyapp.databinding.FragmentUserSelectedBinding
import com.dhruv.apps.historyapp.models.GitUser
import com.dhruv.apps.historyapp.utils.observe
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A placeholder fragment containing a simple view.
 */
class SelectedUserFragment : Fragment() {

    private lateinit var binding: FragmentUserSelectedBinding
    private lateinit var mAdapter: GitUserSelectedAdapter
    private var arrayListGit =  ArrayList<GitUser.GitUserItem>()
    private lateinit var mEventBus: EventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSelectedBinding.inflate(layoutInflater)
        mEventBus = EventBus.getDefault()
        mEventBus.register(this)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInit()
    }

    private fun onInit() {
        initializeObservers()
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        binding.recyclerviewUser.setHasFixedSize(true)
        binding.recyclerviewUser.layoutManager = LinearLayoutManager(activity)
        mAdapter = GitUserSelectedAdapter(arrayListGit)
        binding.recyclerviewUser.adapter = mAdapter
    }
    private fun initializeObservers() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: List<GitUser.GitUserItem>) {
        Log.e("TAG", "come_here: ")
        if (event.isNotEmpty()) {
            arrayListGit.clear()
            arrayListGit.addAll(event as ArrayList)
            mAdapter.setData(arrayListGit)
        }else{
            arrayListGit.clear()
            mAdapter.setData(arrayListGit)
        }
        mEventBus.removeStickyEvent(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        mEventBus.unregister(this)
    }
}