package sv.udb.edu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class RegisterActivity : AppCompatActivity() {
    // Creamos la referencia del objeto FirebaseAuth
    private lateinit var auth: FirebaseAuth

    // Referencia a componentes de nuestro layout
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView

    private lateinit var AuthStateListener: FirebaseAuth.AuthStateListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializamos el objeto FirebaseAuth
        auth = FirebaseAuth.getInstance()

        buttonRegister = findViewById(R.id.btnRegistrer)
        buttonRegister.setOnClickListener {
            val email = findViewById<EditText>(R.id.txtEmail).text.toString()
            val password = findViewById<EditText>(R.id.txtPass).text.toString()
            this.register(email, password)
        }

        textViewLogin = findViewById(R.id.txtLogin)
        textViewLogin.setOnClickListener {
            this.goToLogin()
        }

    //Validar si existe un usuario en sesión

        this.checkUser()
    }

    override fun onResume() {
        super.onResume()
        auth.addAuthStateListener(AuthStateListener)
    }

    override fun onPause() {
        super.onPause()
        auth.addAuthStateListener(AuthStateListener)
    }


    private fun checkUser() {
        // Verificacion del ususario
        AuthStateListener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                // Cambiando la vista
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent( this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}

