package apps.gliger.glg.lar.Activities

import RoomDB.Repository
import RoomDB.User
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import apps.gliger.glg.lar.Constant.FunctionSet
import apps.gliger.glg.lar.Constant.FunctionSet.Companion.isFieldsAllFilled
import apps.gliger.glg.lar.R


class SignUp : AppCompatActivity() {

    lateinit var txt_username : EditText
    lateinit var txt_password : EditText
    lateinit var txt_repeatpwd : EditText
    lateinit var shared : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    lateinit var repository: Repository
    lateinit var layout: ConstraintLayout

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
        txt_repeatpwd.background = ContextCompat.getDrawable(applicationContext, R.drawable.button_round_error)
        txt_repeatpwd.setTextColor(resources.getColor(R.color.colorError))
    }

    private fun setRepeatSuccess(){
        txt_repeatpwd.background = ContextCompat.getDrawable(applicationContext, R.drawable.text_field_border)
        txt_repeatpwd.setTextColor(resources.getColor(R.color.colorTextWhite))
    }

    private fun isPasswordMatch(pwd : String, rptpwd : String):Boolean = TextUtils.equals(pwd,rptpwd)

    private fun init() {
        txt_username = findViewById(R.id.txt_su_username)
        txt_password = findViewById(R.id.txt_su_password)
        txt_repeatpwd = findViewById(R.id.txt_su_repeat_password)
        repository = Repository(applicationContext)
        shared = getSharedPreferences("lar_shared", Context.MODE_PRIVATE)
        editor = shared.edit()
        layout = findViewById(R.id.layout_signup)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



    fun userValidationSuccess() : Boolean{
        if(isFieldsAllFilled(listOf(txt_username,txt_password,txt_repeatpwd))){
            if(isPasswordMatch(txt_password.text.toString(),txt_repeatpwd.text.toString())){
                setRepeatSuccess()
                return true
            }else
                setRepeatError()
        }

        return false
    }

    fun signUpUser(v:View){
        if(userValidationSuccess()){
            if(!repository.isUserExists(txt_username.text.toString())) {
                repository.registerNewUser(User(txt_username.text.toString(), txt_password.text.toString()))
                editor.putBoolean("registered_user", true)
                editor.commit()
                finish()
            }else
                FunctionSet.setMessage("User is Exists", layout)
        }
    }


}

