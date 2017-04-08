package NetworkingLib.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.simran.ixicode.R;


/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class DialogHandler {

    private Context mContext;
    private ProgressDialog mProgressDialog;

    public DialogHandler(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    public DialogParams showProgressDialog(DialogParams dialogParams) {

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        try {
            mProgressDialog.show();
        } catch (WindowManager.BadTokenException ex) {
            Log.e("CodeForIndia2015", "Issue in creating custom progress dialog", ex);
        }
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setContentView(R.layout.view_progress_dialog);
        return dialogParams;
    }

    public void showDefaultProgressDialog() {
        showProgressDialog(new DialogParams());
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
