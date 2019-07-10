package com.blockadm.common.comstomview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by hp on 2018/12/24.
 */

public class CheckEmptyTextView extends android.support.v7.widget.AppCompatTextView {
    public CheckEmptyTextView(Context context) {
        this(context,null);
    }

    public CheckEmptyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CheckEmptyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public void setCheckEmptyEditTexts(final EditText ... editTexts){
     if (editTexts!=null ){
         for (int i = 0; i < editTexts.length; i++) {
             final EditText  editTexts1 = editTexts[i];
                     if(editTexts1!=null){
                         editTexts1.addTextChangedListener(new TextWatcher() {
                             @Override
                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                             }

                             @Override
                             public void onTextChanged(CharSequence s, int start, int before, int count) {

                             }

                             @Override
                             public void afterTextChanged(Editable s) {
                                 for (int j = 0;j < editTexts.length ; j++) {
                                      EditText  editTexts1 = editTexts[j];
                                      if (TextUtils.isEmpty(editTexts1.getText().toString().trim())){
                                          setEnabled(false);
                                      }else{
                                          setEnabled(true);
                                      }
                                 }
                             }
                         });
                     }
         }
     }


    }
}
