package com.webmazix.shmooops.Tech.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.webmazix.shmooops.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//help us
//https://github.com/sd6352051/NiftyDialogEffects/blob/master/app/src/main/res/layout/activity_main.xml
public class AlertMessage {

    View vw;

	@SuppressWarnings("deprecation")
	public static void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.warning : R.drawable.warning);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    //animated alert dialog....
    public void dialogShow(final Context context) {
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);

        Effectstype effect = Effectstype.SlideBottom;
        LayoutInflater LI = LayoutInflater.from(context);
        vw = LI.inflate(R.layout.custom_view, null, false);

        dialogBuilder
                .withTitle("Forget Password")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#ec4103")                               //def  | withDialogColor(int resid)                               //def
                //.withIcon(context.getResources().getDrawable(R.drawable.ic_like))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("Submit")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .setCustomView(vw, context)         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ETemail=(EditText)vw.findViewById(R.id.ET_email);
                        String email=ETemail.getText().toString();
                        if(TextUtils.isEmpty(email))
                        {
                            ETemail.setText("Email is incorrect");
                            return;
                        }else{

                        }
                       // Toast.makeText(context,ETemail.getText().toString(),Toast.LENGTH_LONG).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"jskfjkjsf",Toast.LENGTH_LONG).show();
                    }
                })
                .show();

    }

    public static boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
//AlertMessage.showAlertDialog(this, "Alert!", alert_msg, false);