package com.example.arken.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.arken.R
import com.example.arken.model.LoginUser
import com.example.arken.util.RetroClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(), View.OnClickListener {
    lateinit var signupButton: LinearLayout
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        signupButton = view.findViewById(R.id.login_signupButton_layout)
        signupButton.setOnClickListener(this)
        loginButton = view.findViewById(R.id.login_login_button)
        loginButton.setOnClickListener(this)
        emailEditText = view.findViewById(R.id.login_email_editText)
        passwordEditText = view.findViewById(R.id.login_password_editText)
        val layout = view.findViewById<ConstraintLayout>(R.id.login_background)
        layout.setOnClickListener(this)
        return view
    }


    override fun onClick(view: View) {
        if (view.id != R.id.login_email_editText && view.id != R.id.login_password_editText) {
            val inputMethodManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        }
        when (view.id) {
            R.id.login_signupButton_layout -> {
                findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
            }
            R.id.login_login_button -> {
                if (emailEditText.text.toString().trim { it <= ' ' } == "") {
                    Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                    return
                }
                if (passwordEditText.text.toString().trim { it <= ' ' } == "") {
                    Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                    return
                }

                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        val response = RetroClient.apiService.login(LoginUser(email, password))
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "You are logged in!", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(
                                    context,
                                    response.raw().toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }
}
