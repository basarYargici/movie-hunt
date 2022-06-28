package com.basar.moviehunter.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import timber.log.Timber

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    abstract fun getLayoutID(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.v("onCreate ${javaClass.simpleName}")
        binding = getLayoutID()
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        Timber.v("onResume ${javaClass.simpleName}")
        super.onResume()
    }

    override fun onStop() {
        Timber.v("onStop ${javaClass.simpleName}")
        super.onStop()
    }

    override fun onRestart() {
        Timber.v("onRestart ${javaClass.simpleName}")
        super.onRestart()
    }

    override fun onDestroy() {
        Timber.v("onDestroy ${javaClass.simpleName}")
        super.onDestroy()
    }

    fun showToast(isLong: Boolean? = false, message: String) {
        val length = if (isLong == true) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(this, message, length).show()
    }
}