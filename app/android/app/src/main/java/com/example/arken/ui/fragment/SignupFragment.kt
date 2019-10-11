package com.example.arken.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.arken.R
import com.example.arken.model.SignupUser
import com.example.arken.util.RetroClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupFragment : Fragment(), View.OnClickListener {
    lateinit var loginButton: LinearLayout
    lateinit var nameEditText: EditText
    lateinit var surnameEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var isTraderSwitch: Switch
    lateinit var signupButton: Button
    lateinit var ibanEditText: EditText
    lateinit var tcknEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        ibanEditText = view.findViewById(R.id.signup_iban_editText)
        tcknEditText = view.findViewById(R.id.signup_tckn_editText)
        val layout = view.findViewById<ConstraintLayout>(R.id.signup_background)
        layout.setOnClickListener(this)
        nameEditText = view.findViewById(R.id.signup_name_editText)
        surnameEditText = view.findViewById(R.id.signup_surname_editText)
        emailEditText = view.findViewById(R.id.signup_email_editText)
        passwordEditText = view.findViewById(R.id.signup_password_editText)
        signupButton = view.findViewById(R.id.signup_signup_button)
        signupButton.setOnClickListener(this)
        isTraderSwitch = view.findViewById(R.id.signup_isTrader_switch)
        isTraderSwitch.setOnCheckedChangeListener { _, b ->
            if (b) {
                ibanEditText.visibility = View.VISIBLE
                tcknEditText.visibility = View.VISIBLE
            } else {
                ibanEditText.visibility = View.GONE
                tcknEditText.visibility = View.GONE
            }
        }
        return view
    }

    override fun onClick(view: View) {
        if (view.id != R.id.signup_email_editText && view.id != R.id.signup_iban_editText &&
            view.id != R.id.signup_name_editText && view.id != R.id.signup_password_editText &&
            view.id != R.id.signup_surname_editText && view.id != R.id.signup_tckn_editText
        ) {
            val inputMethodManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        }
        if (view.id == R.id.signup_signup_button) {
            if (nameEditText.text.toString().trim { it <= ' ' } == "") {
                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
                return
            }
            if (surnameEditText.text.toString().trim { it <= ' ' } == "") {
                Toast.makeText(context, "Please enter your surname", Toast.LENGTH_SHORT).show()
                return
            }
            if (emailEditText.text.toString().trim { it <= ' ' } == "") {
                Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                return
            }
            if (passwordEditText.text.toString().trim { it <= ' ' } == "") {
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                return
            }
            val name = nameEditText.text.toString()
            val surname = surnameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val location = "Turkey"
            val isTrader = isTraderSwitch.isChecked
            if (isTrader) {
                if (tcknEditText.text.toString().trim { it <= ' ' } == "") {
                    Toast.makeText(context, "Please enter your TC", Toast.LENGTH_SHORT).show()
                    return
                }
                if (ibanEditText.text.toString().trim { it <= ' ' } == "") {
                    Toast.makeText(context, "Please enter your iban", Toast.LENGTH_SHORT).show()
                    return
                }
            }


            val request = if (isTrader) {
                val tckn = tcknEditText.text.toString()
                val iban = ibanEditText.text.toString()
                SignupUser(
                    name,
                    surname, email, password, location, isTrader, tckn, iban
                )
            } else {
                SignupUser(
                    name,
                    surname, email, password, location
                )
            }

            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    val response = RetroClient.apiService.signup(request)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "You are registered!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, response.raw().toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}
