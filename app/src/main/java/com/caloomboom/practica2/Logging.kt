package com.caloomboom.practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.caloomboom.practica2.databinding.ActivityLoggingBinding
import com.caloomboom.practica2.ui.activities.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException


class Logging : AppCompatActivity() {

    private lateinit var binding: ActivityLoggingBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var email: String = ""
    private var contrasenia: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoggingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }

        with(binding){
            binding.ingresar.setOnClickListener{
                if(!validarCampos()) return@setOnClickListener

                //autenticamos al usuario
                autenticaUsuario(email, contrasenia)
            }
            binding.registrarse.setOnClickListener{
                if(!validarCampos()) return@setOnClickListener

                //registramos al usuario
                registraUsuario(email, contrasenia)
            }
            binding.forgotPasswordButton.setOnClickListener{
                val resetEmail = EditText(it.context)
                resetEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                val passwordResetDialog = AlertDialog.Builder(it.context)
                    .setTitle("Restablecer contraseña")
                    .setMessage("Ingresa el correo para restablecer su contraseña")
                    .setView(resetEmail)
                    .setPositiveButton("Enviar"){ _, _ ->
                        val mail = resetEmail.text.toString()
                        if(mail.isNotEmpty()){
                            firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener {
                                Toast.makeText(this@Logging, "El correo para restablecer la contraseña ha sido enviado", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(this@Logging, "No se pudo enviar el correro", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this@Logging, "Por favor ingresa un correo", Toast.LENGTH_SHORT).show()
                        }
                    }.setNegativeButton("Cancelar"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

    }

    private fun validarCampos(): Boolean {
        email = binding.emailInput.text.toString().trim()
        contrasenia = binding.passwordInput.text.toString().trim()

        if (email.isEmpty()) {
            binding.emailInput.error = "Se requiere el correo"
            binding.emailInput.requestFocus()
            return false
        }
        if (contrasenia.isEmpty()) {
            binding.passwordInput.error = "Se requiere la contraseña"
            binding.passwordInput.requestFocus()
            return false
        }
        return true
    }

    private fun manejaErrores(task:Task<AuthResult>){
        var errorCode=""
        try {
            errorCode=(task.exception as FirebaseAuthException).errorCode
        }
        catch (e:Exception){
            e.printStackTrace()
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this, "Error: El correo electrónico no tiene un formato correcto", Toast.LENGTH_SHORT).show()
                binding.emailInput.error = "Error: El correo electrónico no tiene un formato correcto"
                binding.emailInput.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this, "Error: La contraseña no es válida", Toast.LENGTH_SHORT).show()
                binding.passwordInput.error = "La contraseña no es válida"
                binding.passwordInput.requestFocus()
                binding.passwordInput.setText("")

            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this, "Error: Una cuenta ya existe con el mismo correo, pero con diferentes datos de ingreso", Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, "Error: el correo electrónico ya está en uso con otra cuenta.", Toast.LENGTH_LONG).show()
                binding.emailInput.error = ("Error: el correo electrónico ya está en uso con otra cuenta.")
                binding.emailInput.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, "Error: La sesión ha expirado. Favor de ingresar nuevamente.", Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, "Error: No existe el usuario con la información proporcionada.", Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, "La contraseña porporcionada es inválida", Toast.LENGTH_LONG).show()
                binding.passwordInput.error = "La contraseña debe de tener por lo menos 6 caracteres"
                binding.passwordInput.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, "Red no disponible o se interrumpió la conexión", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Error. No se pudo autenticar exitosamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun autenticaUsuario(usr: String, psw: String){
        binding.progressBar.visibility = View.VISIBLE

        firebaseAuth.signInWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->
            binding.progressBar.visibility = View.GONE

            if(authResult.isSuccessful){
                Toast.makeText(this, "Autenticación exitosa", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                manejaErrores(authResult)
            }
        }
    }

    private fun registraUsuario(usr: String, psw: String){
        binding.progressBar.visibility = View.VISIBLE

        firebaseAuth.createUserWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->

            if(authResult.isSuccessful){

                val user_fb = firebaseAuth.currentUser

                user_fb?.sendEmailVerification()?.addOnSuccessListener {
                    Toast.makeText(this, "El correo de verificación ha sido enviado a tu cuenta", Toast.LENGTH_SHORT).show()
                }?.addOnFailureListener {
                    Toast.makeText(this, "No se pudo enviar el correo de verificación", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                manejaErrores(authResult)
            }
        }
    }
}