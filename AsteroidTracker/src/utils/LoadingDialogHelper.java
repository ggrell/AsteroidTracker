package utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class LoadingDialogHelper{

    public static ProgressDialog dialog;
    static Handler handler;
    public static int closeDialog = 0;
    public static int closeDialogLimit = 4;
    public static String messageTitle = "";
    
    public static void progressDialog(Context context){
        dialog = ProgressDialog.show(context, "", "Checking Asteroid Service", true);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                dialog.dismiss();}
            };
  }
    
    public static void closeDialog(){
        closeDialog++;
        if(closeDialog >= closeDialogLimit){
            handler.sendEmptyMessage(0);
            }
        }
    
    public static void killDialog(){
        handler.sendEmptyMessage(0);
    }
    
    public static void setMessage(String message){
        dialog.setMessage(messageTitle+"\n"+message);
    }
    
    
}
