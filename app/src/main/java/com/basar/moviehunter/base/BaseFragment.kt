package com.basar.moviehunter.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.basar.moviehunter.R
import com.basar.moviehunter.util.NavOption
import com.basar.moviehunter.util.ResProvider
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): VB

    @Inject
    lateinit var resProvider: ResProvider

    @MenuRes
    open fun getMenuId(): Int = -1
    abstract fun initViews()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.v("onCreateView ${javaClass.simpleName}")
        if (_binding == null) {
            _binding = inflateLayout(inflater, container, savedInstanceState)
        }

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.v("onViewCreated ${javaClass.simpleName}")

        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViews()
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
        _binding = null
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

    fun showToast(message: String, isLong: Boolean? = false) {
        val length = if (isLong == true) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(requireContext(), message, length).show()
    }

    fun openCustomTabWebpage(url: String) =
        customTabIntentBuilder().build().launchUrl(requireContext(), Uri.parse(url))

    private fun customTabIntentBuilder(): CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        .setUrlBarHidingEnabled(true)
        .setShowTitle(true)
        .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
        .setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder().setToolbarColor(
                resProvider.getColor(R.color.cadet_grey)
            ).build()
        )
}