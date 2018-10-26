package apps.gliger.glg.lar

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.view.WindowManager
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast


class SignUp : AppCompatActivity() {

    lateinit var txt_username : EditText
    lateinit var txt_email : EditText
    lateinit var txt_password : EditText
    lateinit var txt_repeatpwd : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Transparent Status Bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_sign_up)

        init()
        functions()
    }

    private fun functions() {

    }

    private fun setRepeatError() {
        txt_repeatpwd.background = ContextCompat.getDrawable(applicationContext,R.drawable.button_round_error)
        txt_repeatpwd.setTextColor(resources.getColor(R.color.colorError))
    }

    private fun setRepeatSuccess(){
        txt_repeatpwd.background = ContextCompat.getDrawable(applicationContext,R.drawable.text_field_border)
        txt_repeatpwd.setTextColor(resources.getColor(R.color.colorTextWhite))
    }

    private fun isPasswordMatch(pwd : String, rptpwd : String):Boolean = TextUtils.equals(pwd,rptpwd)

    private fun init() {
        txt_username = findViewById(R.id.txt_su_username)
        txt_email = findViewById(R.id.txt_su_email)
        txt_password = findViewById(R.id.txt_su_password)
        txt_repeatpwd = findViewById(R.id.txt_su_repeat_password)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFieldsAllFilled(editTextList : List<EditText>) : Boolean{
        for(editText in editTextList)
        {
            if(TextUtils.isEmpty(editText.text)) {
                editText.error = "Please Fill this Field"
                return false
            }
        }

        return true
    }

    fun userValidationSuccess() : Boolean{
        if(isFieldsAllFilled(listOf(txt_username,txt_email,txt_password,txt_repeatpwd))){
            if(isPasswordMatch(txt_password.text.toString(),txt_repeatpwd.text.toString())){
                setRepeatSuccess()
                if(isValidEmail(txt_email.text.toString()))
                    return true
                else
                    txt_email.error = "Email isn't in Correct Format"
            }else
                setRepeatError()
        }

        return false
    }

    fun signUpUser(v:View){
        if(userValidationSuccess()){
            Toast.makeText(applicationContext,"Success",Toast.LENGTH_LONG).show()
        }
    }
}

