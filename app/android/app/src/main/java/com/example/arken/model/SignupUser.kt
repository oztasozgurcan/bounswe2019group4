package com.example.arken.model

data class SignupUser(var name: String,
                      var surname: String,
                      var email: String,
                      var password: String,
                      var location: String,
                      var isTrader: Boolean = false,
                      var tckn: String? = null,
                      var iban: String? = null)