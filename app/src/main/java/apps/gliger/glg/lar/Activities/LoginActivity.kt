package apps.gliger.glg.lar.Activities

import apps.gliger.glg.lar.RoomDB.Repository
import apps.gliger.glg.lar.RoomDB.User
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import android.widget.*
import apps.gliger.glg.lar.Constant.FunctionSet
import apps.gliger.glg.lar.Constant.FunctionSet.Companion.setMessage
import apps.gliger.glg.lar.R
import com.multidots.fingerprintauth.FingerPrintAuthCallback
import com.multidots.fingerprintauth.FingerPrintAuthHelper
import com.multidots.fingerprintauth.AuthErrorCodes



class LoginActivity : AppCompatActivity(), FingerPrintAuthCallback {

    var fingerprint : FingerPrintAuthHelper? = null
    lateinit var txt_username : EditText
    lateinit var txt_password : EditText
    lateinit var txt_fingerprint_status : TextView
    lateinit var img_fingerprint_status : ImageView
    lateinit var chk_stay_login : CheckBox
    lateinit var btn_login : Button
    lateinit var btn_signup : Button
    lateinit var shared : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    lateinit var layout: ConstraintLayout
    var isRegistered = false
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Transparent Status Bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_login)

        init()
        functions()
    }

    private fun functions() {
        //Button SignUp
        btn_signup.setOnClickListener { startActivity(Intent(applicationContext, SignUp::class.java)) }
    }

    fun init(){
        fingerprint = FingerPrintAuthHelper.getHelper(applicationContext,this)
        txt_username = findViewById(R.id.txt_username)
        txt_password = findViewById(R.id.txt_password)
        txt_fingerprint_status = findViewById(R.id.txt_fingerprint_status)
        img_fingerprint_status = findViewById(R.id.img_fingerprint_status)
        chk_stay_login = findViewById(R.id.chk_stayLogin)
        btn_login = findViewById(R.id.btn_login)
        btn_signup = findViewById(R.id.btn_signup)
        layout = findViewById(R.id.layout_login)
        shared = getSharedPreferences("lar_shared", Context.MODE_PRIVATE)
        editor = shared.edit()

        repository = Repository(applicationContext)
        isRegistered = shared.getBoolean("registered_user",false)
    }

    override fun onNoFingerPrintHardwareFound() {}

    override fun onAuthFailed(errorCode: Int, errorMessage: String?) {
        when (errorCode) {
            AuthErrorCodes.CANNOT_RECOGNIZE_ERROR -> {
                setErrorFingerPrint("Cannot Recognize Fingerprint!")
            }
            AuthErrorCodes.NON_RECOVERABLE_ERROR -> {
                setErrorFingerPrint("Use User Password to Authenticate")
            }
            AuthErrorCodes.RECOVERABLE_ERROR -> {
                setErrorFingerPrint("Please Clean the Sensor & Try Again.")
            }
        }
    }

    override fun onNoFingerPrintRegistered() {
        txt_fingerprint_status.visibility = View.INVISIBLE
        img_fingerprint_status.visibility = View.INVISIBLE
    }

    override fun onBelowMarshmallow() {
        txt_fingerprint_status.visibility = View.INVISIBLE
        img_fingerprint_status.visibility = View.INVISIBLE
    }

    override fun onAuthSuccess(cryptoObject: FingerprintManager.CryptoObject?) {
        if(isRegistered){
            checkStayLogin()
            setSuccessFingerPrint("You have Successfully Authenticated")
            startActivity(Intent(applicationContext, MainMenu::class.java))
            finish()
        }else
            setErrorFingerPrint("Pleases SignUp before Authenticate")
    }

    override fun onResume() {
        super.onResume()
        fingerprint!!.startAuth()
        isRegistered = shared.getBoolean("registered_user",false)
    }

    override fun onStop() {
        super.onStop()
        fingerprint!!.stopAuth()
    }

    fun setErrorFingerPrint(message: String){
        txt_fingerprint_status.text = message
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
            txt_fingerprint_status.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorError))
        else
            txt_fingerprint_status.setTextColor(resources.getColor(R.color.colorError))
        img_fingerprint_status.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.icon_touchid_error))
    }

    fun setSuccessFingerPrint(message: String){
        txt_fingerprint_status.text = message
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
            txt_fingerprint_status.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
        else
            txt_fingerprint_status.setTextColor(resources.getColor(R.color.colorAccent))
        img_fingerprint_status.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.icon_touchid_enable))
    }

    fun loginUser(view : View){
        if(FunctionSet.isFieldsAllFilled(listOf(txt_username, txt_password))){
            if(repository.isUserExists(txt_username.text.toString())){
                if(repository.checkCredentials(User(txt_username.text.toString(),txt_password.text.toString()))){
                    checkStayLogin()
                    startActivity(Intent(applicationContext, MainMenu::class.java))
                    finish()
                }else
                    setMessage("Username or Password Invalid",layout)
            }else
                setMessage("User doesn't exists!",layout)

        }
    }

    private fun checkStayLogin(){
        if(chk_stay_login.isChecked)
            editor.putBoolean("stay_login", true)
        else
            editor.putBoolean("stay_login", false)
        editor.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

