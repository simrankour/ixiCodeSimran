package NetworkingLib.dialogs;

import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class DialogParams {
    private String message = "";
    private boolean cancellable = false;
    private boolean indeterminate = true;
    private boolean canceledOnTouchOutside = false;
    private DialogInterface.OnCancelListener onCancelListener = null;
    private ProgressDialog progressDialog = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public boolean isIndeterminate() {
        return indeterminate;
    }

    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
    }

    public boolean isCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    public DialogInterface.OnCancelListener getOnCancelListener() {
        return onCancelListener;
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}
