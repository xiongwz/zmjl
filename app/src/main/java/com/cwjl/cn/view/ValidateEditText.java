package com.cwjl.cn.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.cwjl.cn.R;
import com.cwjl.cn.utils.RegistryValidate;
import com.cwjl.cn.utils.Util;

import java.text.ParseException;
import java.util.regex.PatternSyntaxException;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by zx on zx on 2017/1/10.
 */

public class ValidateEditText extends AppCompatEditText {
    private boolean haveValidate=false;
    public static final int TYPE_ID=1;//身份证号码
    public static final int TYPE_PHONE=2;//手机号码
    private int type=1;
    private TextValidateInter textValidateInter;
    Drawable validateSuccessDrawable;
    Drawable validateFailDrawable;
    private int dp30;
    public ValidateEditText(Context context) {
        this(context,null);
    }

    public ValidateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

   private void init(){
       dp30= Util.dp2px(getResources(),25);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text= getText().toString();
               switch (type){
                   case TYPE_ID:
                       try {
                           loadValidate(RegistryValidate.IDCardValidate(text),type,text);
                       }catch (ParseException e){
                           loadValidate(false,type,"");
                           e.printStackTrace();
                       }
                       break;
                   case TYPE_PHONE:
                       try {
                           loadValidate(RegistryValidate.isPhoneLegal(text),type,text);
                       }catch (PatternSyntaxException e){
                           loadValidate(false,type,"");
                           e.printStackTrace();
                       }
                       break;
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
   }
   public void setTextValidateInter(TextValidateInter textValidateInter){
       this.textValidateInter=textValidateInter;
   }
  public interface TextValidateInter{
       void validateSuccess(String text);
       void validateFail(String message);
  }
  private void loadValidate(boolean isValidate, int type, String text){
       if (isValidate){
           if (type!=TYPE_ID){
               setValidate(isValidate);
           }
          if (textValidateInter!=null)
               textValidateInter.validateSuccess(text);
       }else {
           setValidate(isValidate);
           if (textValidateInter!=null)
               textValidateInter.validateFail("");
       }
  }
  public void setValidate(boolean haveValidate){
      this.haveValidate=haveValidate;
      if (haveValidate){
          if (validateSuccessDrawable==null) {
              validateSuccessDrawable = getResources().getDrawable(R.mipmap.accept);
              validateSuccessDrawable.setBounds(0, 0, dp30,dp30);
          }
          setCompoundDrawables(null,null,validateSuccessDrawable,null);
      }else {
          if (validateFailDrawable==null) {
              validateFailDrawable = getResources().getDrawable(R.mipmap.validate_fail);
              validateFailDrawable.setBounds(0, 0, dp30,dp30);
          }
          setCompoundDrawables(null,null,validateFailDrawable,null);
      }
  }
    public boolean haveValidate(){
      return haveValidate;
    }
    public void clearContent(){
        setText("");
        setCompoundDrawables(null,null,null,null);
    }

    public void setType(int type) {
        this.type = type;
    }
}
