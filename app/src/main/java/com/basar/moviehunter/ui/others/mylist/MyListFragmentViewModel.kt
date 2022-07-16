package com.basar.moviehunter.ui.others.mylist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.ConstantsHelper.MB_SIZE
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyListFragmentViewModel @Inject constructor(
) : BaseViewModel() {
    val savedMovieURL = MutableLiveData<List<Bitmap>>()
    var storageRef = Firebase.storage.reference
    private var movieList = arrayListOf<Bitmap>()

    fun downloadImage() = launch {
        try {
            val maxDownloadSize: Long = 5L * MB_SIZE
            val bytes = storageRef.child("image/").listAll().await()
            bytes.items.forEach { result ->
                result.getBytes(maxDownloadSize).addOnFailureListener {
                    Timber.d("failure" + it.message)
                }.addOnSuccessListener {
                    Timber.d("success" + it.size.toString())
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    movieList.add(bitmap)
                }.addOnCompleteListener {
                    savedMovieURL.postValue(movieList)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Timber.d("error" + e.message)
            }
        }
    }
}