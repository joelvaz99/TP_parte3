package ipvc.estg.tp_parte3

//package ipvc.estg.tp_parte3


//import retrofit2.Response

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import ipvc.estg.tp_parte3.api.EndPoints
import ipvc.estg.tp_parte3.api.OutputPost
import ipvc.estg.tp_parte3.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_add_nota.edit_word
import kotlinx.android.synthetic.main.activity_add_nota.edit_word2
import kotlinx.android.synthetic.main.activity_add_problem.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import java.io.File

/*
class td : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var imageButton: Button
    private lateinit var sendButton: Button
    private var imageData: ByteArray? = null
    private var selectedFileUri: Uri? = null
    lateinit var dialog: ProgressDialog
    private val postURL: String =
        "https://joe-lvaz.000webhostapp.com/myslim/api/images" // remember to use your own ap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_problem)
        val ir_notas = findViewById<Button>(R.id.button_save)
        ir_notas.setOnClickListener {}
        var longitude: String? = intent.getStringExtra("longitude")
        var latitude: String? = intent.getStringExtra("latitude")
        var username: String? = intent.getStringExtra("loginusername")
        edit_word.setText(longitude)
        edit_word2.setText(latitude)

        /*
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

         */
        /*
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

             */

        button_save.setOnClickListener {

            val descricao = edit_word3.text.toString().trim()
            //Inserir
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.postPonto(descricao, latitude, longitude, username)

            call.enqueue(object : Callback<OutputPost> {

                override fun onResponse(
                    call: Call<OutputPost>,
                    response: retrofit2.Response<OutputPost>
                ) {

                    if (response.isSuccessful) {
                        //Toast.makeText(this@AddProblem, "Login 222", Toast.LENGTH_SHORT).show()
                        if (response.body()?.error == false) {
                            val c: OutputPost = response.body()!!
                            Toast.makeText(
                                this@AddProblem,
                                "Username ou Palavra-passe errrado",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {

                            val intent = Intent(this@AddProblem, MapsActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this@AddProblem, "Login Efectuado", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                    Toast.makeText(this@AddProblem, "Login 222", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                    // Toast.makeText(this@AddProblem, "${t.message}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@AddProblem, "Login 222", Toast.LENGTH_SHORT).show()
                }
            })
        }


    }

    /*
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
               // image_View.setImageURI(data?.data)
                //Toast.makeText(this@AddProblem, "$data", Toast.LENGTH_LONG).show()
               // Log.e("$data","$data")
               // val uri = data?.data
                if (data != null) {
                    selectedFileUri= data.data
                    if (selectedFileUri != null && !selectedFileUri!!.path?.isEmpty()!!)
                        image_View.setImageURI(selectedFileUri)
                    //imageView.setImageURI(uri)
                    //createImageData(uri)
                }
            }
            if(requestCode == IMAGE_CAPTURE_CODE){
               image_View.setImageURI(image_uri)
                val uri = image_uri
                if (uri != null) {
                   //imageView.setImageURI(uri)
                   // createImageData(uri)
                }

            }

        }

    }

    companion object{
        private val IMAGE_PICK_CODE = 1000;
        private val IMAGE_CAPTURE_CODE = 1002;
        private val PERMISSION_CODE = 1001;
        var image_uri: Uri? = null
    }
    /*
      private fun uploadFile(fileUri: Uri) {
        val service: FileUploadService = Serv.createService(
            FileUploadService::class.java,
            FileUploadService.BASE_URL
        )
        val typedFile =
            TypedFile("multipart/form-data", File("path/to/your/file"))
        val description = "hello, this is description speaking"

        service.upload(typedFile, description, object : Callback<String?> {
            fun success(s: String?, response: Response<*>?) {
                Log.e("Upload", "success")
            }

            fun failure(error: RetrofitError?) {
                Log.e("Upload", "error")
            }
        })
    }

     */




/*
    private fun uploadImage() {
        imageData?: return
        val request = object : VolleyFileUploadRequest(
            Method.POST,
            postURL,
            Response.Listener {
                println("response is: $it")
            },
            Response.ErrorListener {
                println("error is: $it")
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                var params = HashMap<String, FileDataPart>()
                params["imageFile"] = FileDataPart("image", imageData!!, "jpeg")
                return params
            }
        }
        Volley.newRequestQueue(this).add(request)
    }


    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    override fun onProgrogressUpdate(percentage: Int) {

    }
    */

    /*

    private fun uploadFile(){
        if (selectedFileUri != null){
            dialog = ProgressDialog(this@AddProblem)
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            dialog.setMessage("Uploading...")
            dialog.isIndeterminate =false
            dialog.setCancelable(false)
            dialog.max=100
            dialog.show()


            //https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java

            val file = FileUtils.getFile(this,selectedFileUri)


            val files = File(selectedFileUri)

            val requestFile = ProguesRequestBody(file,this)
            val body = MultipartBody.Part.createFormData("image",file.name,requestFile)

            Thread(Runnable {
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.upload(body)

                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                        if (response.isSuccessful) {

                            Toast.makeText(this@AddProblem, "Upload sucess", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@AddProblem, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })


            }).start()

        }
    }

    override fun onProgrogressUpdate(percentage: Int) {
        dialog.progress = percentage
    }
*/

     */

     */

 */
//}



