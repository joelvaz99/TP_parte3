package ipvc.estg.tp_parte3
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_add_nota.*
import kotlinx.android.synthetic.main.activity_add_nota.edit_word


class AddNota : AppCompatActivity() {
    private lateinit var editWordView: EditText
    private lateinit var editWordView1: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nota)
        var titulo: String? = intent.getStringExtra("titulo")
        var desc: String? = intent.getStringExtra("descricao")
        edit_word.setText(titulo)
        edit_word2.setText(desc)
        editWordView = findViewById(R.id.edit_word)
        editWordView1 = findViewById(R.id.edit_word2)
        val button_save = findViewById<Button>(R.id.button_save)
        val cancel_button = findViewById<Button>(R.id.cancel_button)

        button_save.setOnClickListener {
            val replyIntent = Intent()
            // edit
            if (TextUtils.isEmpty(editWordView.text)  ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                val word2 = editWordView1.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_TITULO, word)
                replyIntent.putExtra(EXTRA_REPLY_DESCRICAO, word2)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        cancel_button.setOnClickListener {

            val intent = Intent(this@AddNota, MainActivity::class.java)
            startActivity(intent)

        }
    }
    companion object {
        const val EXTRA_REPLY_TITULO = "com.example.android.wordlistsql.REPLY"
        const val EXTRA_REPLY_DESCRICAO = "com.example.android.wordlistsq2.REPLY"
        const val EXTRA_REPLY_UPTITULO = "com.example.android.wordlistsql.REPLY"
        const val EXTRA_REPLY_UPDESCRICAO = "com.example.android.wordlistsq2.REPLY"
    }
}

