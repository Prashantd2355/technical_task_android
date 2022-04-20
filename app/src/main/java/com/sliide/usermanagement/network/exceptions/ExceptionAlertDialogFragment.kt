package com.sliide.usermanagement.network.exceptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.sliide.usermanagement.Constants
import com.sliide.usermanagement.R
import com.sliide.usermanagement.databinding.DialogAlertDialogBinding

class ExceptionAlertDialogFragment : DialogFragment(), View.OnClickListener {

    private lateinit var binding: DialogAlertDialogBinding

    private var message: String? = null

    companion object {
        private const val DIALOG_MESSAGE = "DIALOG_MESSAGE"

        @JvmStatic
        fun newInstance(dialogMessage: String): ExceptionAlertDialogFragment {
            val fragment = ExceptionAlertDialogFragment()
            val args = Bundle()
            args.putString(DIALOG_MESSAGE, dialogMessage)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_alert_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractArguments()
        setMessage()
        binding.positiveBtn.setOnClickListener(this)
    }

    private fun extractArguments() {
        arguments?.let {
            message = it.getString(DIALOG_MESSAGE)
        }
    }

    private fun setMessage() {
        message?.let {
            binding.message.text = fromHtml(it)
        }
    }
    fun fromHtml(source: String): CharSequence {
        return if (!isEmpty(source)) {
            HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_LEGACY, null, null)
        } else Constants.EMPTY
    }
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.length == 0
    }
    override fun onClick(v: View?) {
        dismissAllowingStateLoss()
    }

    fun setMessage(message: String) {
        this.message = message
        setMessage()
    }
}