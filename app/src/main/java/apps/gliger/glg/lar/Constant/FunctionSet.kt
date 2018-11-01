package apps.gliger.glg.lar.Constant

import android.content.Context
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.widget.EditText

class FunctionSet {
    companion object {
        fun isFieldsAllFilled(editTextList : List<EditText>) : Boolean{
            for(editText in editTextList)
            {
                if(TextUtils.isEmpty(editText.text)) {
                    editText.error = "Please Fill this Field"
                    return false
                }
            }

            return true
        }

        fun setMessage(message:String, layout: View){
            Snackbar.make(layout,message,Snackbar.LENGTH_SHORT).show()
        }
    }
}