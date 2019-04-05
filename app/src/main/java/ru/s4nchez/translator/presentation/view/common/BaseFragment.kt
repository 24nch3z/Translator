package ru.s4nchez.translator.presentation.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    abstract val layout: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, container, false)
    }

    protected fun showMessage(message: String) {
        Snackbar.make(activity?.findViewById(android.R.id.content)!!, message, Snackbar.LENGTH_LONG).show()
    }
}