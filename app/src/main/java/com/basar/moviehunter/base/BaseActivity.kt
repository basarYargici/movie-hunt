package com.basar.moviehunter.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.basar.moviehunter.base.app.AppRepository
import com.basar.moviehunter.base.app.AppRepositoryImpl.Companion.TURKISH
import com.basar.moviehunter.util.LanguageUtil
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB

    @Inject
    lateinit var appRepository: AppRepository

    abstract fun inflateLayout(): VB
    abstract fun initViews(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("onCreate ${javaClass.simpleName}")
        binding = inflateLayout()
        setContentView(binding.root)
        initViews(savedInstanceState)
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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageUtil.updateBaseContextLocale(newBase))
    }

    override fun onDestroy() {
        Timber.v("onDestroy ${javaClass.simpleName}")
        super.onDestroy()
    }

    fun showToast(isLong: Boolean? = false, message: String) {
        val length = if (isLong == true) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(this, message, length).show()
    }

    fun findNavHostFragment(navHostId: Int) =
        supportFragmentManager.findFragmentById(navHostId) as NavHostFragment

    fun isContentTurkish() = appRepository.getLanguage() == TURKISH
}