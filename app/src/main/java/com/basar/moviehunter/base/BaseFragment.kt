package com.basar.moviehunter.base

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.basar.moviehunter.util.NavOption
import timber.log.Timber

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    lateinit var binding: VB
    private var isViewCreated = false
    abstract fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VB

    @MenuRes
    open fun getMenuId(): Int = -1
    abstract fun initViews()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.v("onCreateView ${javaClass.simpleName}")

        if (!::binding.isInitialized) {
            binding = inflateLayout(inflater, container, savedInstanceState)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.v("onViewCreated ${javaClass.simpleName}")

        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if (!isViewCreated) {
            isViewCreated = true

            initViews()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

        getMenuId().takeIf {
            it != -1
        }?.let {
            inflater.inflate(it, menu)
        }
    }

    override fun onDestroyView() {
        Timber.v("onDestroyView ${javaClass.simpleName}")
        super.onDestroyView()
    }

    protected fun navigate(
        navDirections: NavDirections,
        navOption: NavOption? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        val navOptions = navOption?.let {
            NavOptions.Builder().setEnterAnim(navOption.enter)
                .setExitAnim(navOption.exit)
                .setPopEnterAnim(navOption.popEnter)
                .setPopExitAnim(navOption.popExit)
                .build()
        } ?: run { null }

        findNavController().navigate(
            navDirections.actionId,
            navDirections.arguments,
            navOptions,
            navigatorExtras
        )
    }

    protected fun shareMessage(message: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}