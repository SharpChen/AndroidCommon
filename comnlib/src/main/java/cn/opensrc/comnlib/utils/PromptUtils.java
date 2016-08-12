package cn.opensrc.comnlib.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

/**
 * Author:       sharp
 * Created on:   8/9/16 1:55 PM
 * Description:  提示
 * Revisions:
 */
public final class PromptUtils {
    private PromptUtils(){}

    private static Toast mToast;

    /**
     *  AlertDialog
     *  @param context Application Context
     *  @param title AlertDialog title
     *  @param msg AlertDialog message
     *  @param positiveButtonText button text
     *  @param negativeButtonText button text
     *  @param positiveButtonListener positiveButton listener
     *  @param negativeButtonListener negativeButton listener
     */
    public static void showPromptWithAlertDialog(Context context,
                                         String title,
                                         String msg,
                                         String positiveButtonText,
                                         String negativeButtonText,
                                         DialogInterface.OnClickListener positiveButtonListener,
                                         DialogInterface.OnClickListener negativeButtonListener){

        if (title == null) title = "";
        if (msg == null) msg = "";

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
               .setMessage(msg)
               .setPositiveButton(positiveButtonText,positiveButtonListener)
               .setNegativeButton(negativeButtonText,negativeButtonListener)
               .show();

    }

    /**
     *  AlertDialog
     *  @param context Application Context
     *  @param title AlertDialog title
     *  @param msg AlertDialog message
     *  @param positiveButtonListener positiveButton listener
     */
    public static void showPromptWithAlertDialog(Context context,
                                                 String title,
                                                 String msg,
                                                 DialogInterface.OnClickListener positiveButtonListener){

        showPromptWithAlertDialog(context,title,msg,"确定","取消",positiveButtonListener,null);

    }

    /**
     * Toast
     * @param context Application Context
     * @param toastMsg the message to show
     * @param gravity the Toast postion in screen
     */
    public static void showPromptWithToast(Context context, String toastMsg, int gravity){

        if (mToast != null){
            mToast.setText(toastMsg);
        }else {
            mToast = Toast.makeText(context,toastMsg,Toast.LENGTH_SHORT);
        }

        if (gravity != -1)
            mToast.setGravity(gravity,0,0);
        mToast.show();

    }

    /**
     * Toast
     * @param context Application Context
     * @param toastMsg the message to show
     */
    public static void showPromptWithToast(Context context, String toastMsg){

        showPromptWithToast(context,toastMsg, -1);

    }

    /**
     * Snackbar
     * @param view either view in current ui
     * @param cont the content to show
     * @param action the action text
     * @param clickListener the action clickListener
     */
    public static void showPromptWithSnackbar(View view,String cont,String action,View.OnClickListener clickListener){

        Snackbar.make(view,cont,Snackbar.LENGTH_LONG)
                .setAction(action,clickListener)
                .show();

    }

    /**
     * Snackbar
     * @param view either view in current ui
     * @param cont the content to show
     * @param action the action text
     */
    public static void showPromptWithSnackbar(View view, String cont, String action){

        showPromptWithSnackbar(view,cont,action,null);

    }

}
