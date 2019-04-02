package ru.s4nchez.translator.presentation.view.translator

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.s4nchez.translator.domain.translatorfacade.model.Language

class ListDialogFragment : DialogFragment() {

    companion object {
        const val RESULT_SELECTED = "RESULT_SELECTED"
        const val RESULT_POSITION = "RESULT_POSITION"

        private const val ARG_ITEMS = "ARG_ITEMS"
        private const val ARG_POSITION = "ARG_POSITION" // from or to

        fun newInstance(items: ArrayList<Language>, position: Int): ListDialogFragment {
            return ListDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_ITEMS, items)
                    putInt(ARG_POSITION, position)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val itemsLabel = arguments?.getParcelableArrayList<Language>(ARG_ITEMS)?.map { it.label }?.toTypedArray()

        val builder = AlertDialog.Builder(activity!!)
                .setItems(itemsLabel, ::sendResult)
                .setNegativeButton(android.R.string.cancel) { _, _ -> }

        return builder.create()
    }

    private fun sendResult(dialog: DialogInterface, which: Int) {
        val selectedLanguage = arguments?.getParcelableArrayList<Language>(ARG_ITEMS)?.get(which)
        val intent = Intent().apply {
            putExtra(RESULT_POSITION, arguments?.getInt(ARG_POSITION))
            putExtra(RESULT_SELECTED, selectedLanguage)
        }

        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    }
}