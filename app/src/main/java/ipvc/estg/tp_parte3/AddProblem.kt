package ipvc.estg.tp_parte3


//import retrofit2.Response

import android.Manifest
import android.app.Activity
import android.app.AlertDialog

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.tp_parte3.api.EndPoints
import ipvc.estg.tp_parte3.api.OutputPost
import ipvc.estg.tp_parte3.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_add_nota.edit_word2

import kotlinx.android.synthetic.main.activity_add_problem.*
import kotlinx.android.synthetic.main.activity_add_problem.button_save
import retrofit2.Call
import retrofit2.Callback


class AddProblem : AppCompatActivity(){

    private lateinit var imageView: ImageView
    private lateinit var imageButton: Button
    private lateinit var sendButton: Button
    private var imageData: ByteArray? = null
    private var selectedFileUri:Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_problem)
        val ir_notas = findViewById<Button>(R.id.button_save)
        ir_notas.setOnClickListener {}
        var longitude = intent.getStringExtra("longitude")
        var latitude = intent.getStringExtra("latitude")
        var username = intent.getStringExtra("loginusername")
        var id = intent.getStringExtra("id")
        //edit_word.setText(longitude)
        edit_word2.setText(id)
        edit_word3.setText(id)



        image_View.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select Image")
            builder.setMessage("Choose you option?")
            builder.setPositiveButton("Gallery"){
                    dialog, which ->dialog.dismiss()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        //permission denied
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                        //show popup to request runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else{
                    //system OS is < Marshmallow
                    pickImageFromGallery();
                }

            }



            builder.setNegativeButton("Camera"){
                    dialog, which -> dialog.dismiss()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                        //permission was not enabled
                        val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        //show popup to request permission
                        requestPermissions(permission, PERMISSION_CODE)
                    }
                    else{
                        //permission already granted
                        openCamera()
                    }
                }
                else{
                    //system os is < marshmallow
                    openCamera()
                }

            }

            val  dialog: AlertDialog = builder.create()
            dialog.show()

        }



        button_save.setOnClickListener{

            val descricao = edit_word2.text.toString().trim()
            val tipo = edit_word3.text.toString().trim()
            //Inserir
            val request = ServiceBuilder.buildService(EndPoints::class.java)


            val call = request.postPonto1(descricao,latitude,longitude,username,tipo)


            call.enqueue(object : Callback<OutputPost>{
                override fun onResponse(call: Call<OutputPost>, response: retrofit2.Response<OutputPost>) {

                    if (response.isSuccessful){
                        if(response.body()?.error == false){
                            Toast.makeText(this@AddProblem, "Erro", Toast.LENGTH_SHORT).show()
                        }else{
                            val intent = Intent(this@AddProblem, MapsActivity::class.java)
                            intent.putExtra("username",username)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        }


                    }
                }

                override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                    Toast.makeText(this@AddProblem, "${t.message}", Toast.LENGTH_SHORT).show()

                }
            })
        }

        cancel_button.setOnClickListener {
            val intent = Intent(this@AddProblem, MapsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("username",username)
            startActivity(intent)
        }


    }


    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                    openCamera()

                }else{
                    Toast.makeText(this,"PERSSION DENIED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,IMAGE_PICK_CODE)
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == IMAGE_PICK_CODE){
                image_View.setImageURI(data?.data)

            }
            if(requestCode == IMAGE_CAPTURE_CODE){
               image_View.setImageURI(image_uri)

            }

        }

    }

    companion object{
        private val IMAGE_PICK_CODE = 1000;
        private val IMAGE_CAPTURE_CODE = 1002;
        private val PERMISSION_CODE = 1001;
        var image_uri: Uri? = null
    }



}