package ipvc.estg.tp_parte3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ipvc.estg.tp_parte3.entities.Nota
//import ipvc.estg.tp_parte3.databinding.ActivityCarDetailsBinding
//import kotlinx.android.synthetic.main.activity_add_nota.*
import kotlinx.android.synthetic.main.activity_update_nota.*
import ipvc.estg.tp_parte3.MainActivity.Companion as MainActivity1

class UpdateNota : AppCompatActivity() {
    private val newWordActivityRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this, R.layout.activity_update_nota)
        setContentView(R.layout.activity_update_nota)

        edit_word.text = getIntent().getStringExtra("TITULAR")
       // edit_word2.text = getIntent().getStringExtra("DESC")


/*
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
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
    }
    companion object {
        const val EXTRA_REPLY_TITULO = "com.example.android.wordlistsql.REPLY"
        const val EXTRA_REPLY_DESCRICAO = "com.example.android.wordlistsq2.REPLY"

    }

 */
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            edit_word.text = data?.getExtra(MainActivity1.EXTRA_REPLY_TITULAR)
            edit_word2.text = data?.getStringExtra(MainActivity1.EXTRA_REPLY_NOTA)
            edit_word.text = getIntent().getStringExtra("CARNAME")

            }else{
            Toast.makeText(
                applicationContext,
                "Titulo ou descricao vazia",
                Toast.LENGTH_LONG).show()
        }

        }



}



