import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sliide.usermanagement.Constants
import com.sliide.usermanagement.network.exceptions.CustomHttpException
import com.sliide.usermanagement.network.exceptions.ExceptionAlertDialogFragment
import com.sliide.usermanagement.network.exceptions.UnauthorizedAccessExceptionCustom
import java.io.EOFException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException

object ExceptionHandler {
    fun handle(t: Throwable, context: Context?) {
        if (context != null) {
            if (t is UnauthorizedAccessExceptionCustom) {
                showDialog(Constants.UNAUTHORIZED_ACCESS, context)
            } else if (t is CustomHttpException) {
                showDialog(t.message!!, context)
            } else if (t is SocketTimeoutException || t is SocketException || t is InterruptedIOException || t is EOFException) {
                showDialog(Constants.REQUEST_TIMED_OUT, context)
            } else {
                t.localizedMessage?.let { showDialog(it, context) } ?: kotlin.run {
                    Toast.makeText(
                        context, Constants.SOME_ERROR_OCCURRED,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showDialog(message: String, context: Context?) {
        if (context != null && context is AppCompatActivity) {
            var fragment: ExceptionAlertDialogFragment? =
                context.supportFragmentManager.findFragmentByTag(ExceptionAlertDialogFragment::class.java.simpleName) as ExceptionAlertDialogFragment?
            if (fragment != null && fragment.isAdded) {
                fragment.setMessage(message)
            } else {
                fragment = ExceptionAlertDialogFragment.newInstance(message)
                val fragmentTransaction = context.supportFragmentManager.beginTransaction()
                fragmentTransaction.add(
                    fragment,
                    ExceptionAlertDialogFragment::class.java.simpleName
                )
                fragmentTransaction.commitAllowingStateLoss()
            }
        }
    }
}