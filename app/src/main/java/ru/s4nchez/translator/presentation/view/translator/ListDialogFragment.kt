package ru.s4nchez.translator.presentation.view.translator

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.s4nchez.translator.domain.translatorfacade.model.Language

class ListDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_ITEMS = "ARG_ITEMS"

        fun newInstance(items: ArrayList<Language>): ListDialogFragment {
            return ListDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_ITEMS, items)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val itemsLabel = arguments?.getParcelableArrayList<Language>(ARG_ITEMS)?.map { it.label }?.toTypedArray()

        val builder = AlertDialog.Builder(activity!!)
                .setItems(itemsLabel) { dialog, which -> }

        return builder.create()
    }
}