package com.fabledt5.moviescleanarchitecture.presentation.utils

import android.content.Context
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.moviescleanarchitecture.MoviesApplication
import com.fabledt5.moviescleanarchitecture.di.AppComponent
import com.fabledt5.moviescleanarchitecture.domain.util.Constants
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
val Context.applicationComponent: AppComponent
    get() = when (this) {
        is MoviesApplication -> appComponent
        else -> this.applicationContext.applicationComponent
    }

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME)

fun <T> Flow<T>.launchWhenStarted(lifeCycleScope: LifecycleCoroutineScope) = lifeCycleScope
    .launchWhenStarted {
        this@launchWhenStarted.collect()
    }

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.animateAlpha(targetAlpha: Float, duration: Long = 1000): ViewPropertyAnimator =
    animate()
        .alpha(targetAlpha)
        .setDuration(duration)
        .setStartDelay(0)
        .setListener(null)

fun View.rotate(angle: Float, duration: Long = 200) = animate()
    .rotationBy(angle)
    .setDuration(duration)
    .setInterpolator(LinearInterpolator())
    .start()

fun RecyclerView.setDrawableDivider(@DrawableRes resId: Int) {
    DividerItemDecoration(
        context,
        LinearLayoutManager.HORIZONTAL
    ).also { decoration ->
        decoration.setDrawable(ContextCompat.getDrawable(context, resId)!!)
    }.let(::addItemDecoration)
}

fun MaterialButton.setBackgroundTint(context: Context, @ColorRes color: Int) {
    backgroundTintList = ContextCompat.getColorStateList(context, color)
}

fun Fragment.arguments(vararg arguments: Pair<String, Any>): Fragment {
    this.arguments = bundleOf(*arguments)
    return this
}

fun Fragment.showKeyboard(view: EditText) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}