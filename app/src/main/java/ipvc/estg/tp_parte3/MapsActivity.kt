package ipvc.estg.tp_parte3

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.tp_parte3.api.EndPoints
import ipvc.estg.tp_parte3.api.ServiceBuilder
import ipvc.estg.tp_parte3.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var users: List<User>
    private var latitude1: String? = null
    private var longitude1: String? = null
    private var loginusername: String? = null
    private var distancia: String? = null
    private var id: String? = null


    // add to implement last known location
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //added to implement location periodic updates
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

// initialize fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var loginusername1 = intent.getStringExtra("username")
        loginusername = loginusername1

        //Chamar o web service
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontos()
        var position: LatLng
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                if (response.isSuccessful) {

                    mMap.clear()
                    users = response.body()!!
                    for (user in users) {
                        if(user.user_id == loginusername1){
                            position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                            mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.type_id).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).setTag(user.id.toString())
                        }else{
                            position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                            mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.type_id)).setTag(user.id.toString())

                        }


                    }

                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                latitude1 = loc.latitude.toString()
                longitude1 = loc.longitude.toString()
                Log.d("**** Joel", "new location received - " + loc.latitude + " -" + loc.longitude)


            }
        }

        // request creation
        createLocationRequest()



    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        // interval specifies the rate at which your app will like to receive updates.
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    // Logout
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                var token = getSharedPreferences("username", Context.MODE_PRIVATE)
                val intent = Intent(this@MapsActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                var editor = token.edit()
                editor.putString("loginusername", " ")
                editor.commit()
                startActivity(intent)
                true
            }
            R.id.add -> {
                val intent = Intent(this@MapsActivity, AddProblem::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("latitude", latitude1)
                intent.putExtra("longitude", longitude1)
                intent.putExtra("loginusername", loginusername)
                startActivity(intent)

                true
            }
            R.id.obras ->{

                //OBRAS
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getPontoType(2)
                var position: LatLng
                call.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                        if (response.isSuccessful) {

                            mMap.clear()
                            users = response.body()!!
                            for (user in users) {
                                if(user.user_id == loginusername){
                                    position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                                    mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.id.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).setTag(user.id.toString())
                                }else{
                                    position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                                    mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.id.toString())).setTag(user.id.toString())

                                }


                            }

                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })



                true
            }

            R.id.acidentes ->{

                //acidentes
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getPontoType(1)
                var position: LatLng
                call.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                        if (response.isSuccessful) {

                            mMap.clear()
                            users = response.body()!!
                            for (user in users) {
                                if(user.user_id == loginusername){
                                    position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                                    mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.id.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).setTag(user.id.toString())
                                }else{
                                    position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                                    mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.id.toString())).setTag(user.id.toString())

                                }


                            }

                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })



                true
            }

            R.id.distancia -> {

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getPontos()
                var position: LatLng
                call.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                        if (response.isSuccessful) {

                            mMap.clear()
                            users = response.body()!!
                            for (user in users) {
                                val distancia=calculateDistance(lastLocation.latitude, lastLocation.longitude,
                                    user.lat!!.toDouble(), user.longitude!!.toDouble())

                                if (distancia <= 30000){
                                    if(user.user_id == loginusername){
                                        position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.type_id).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).setTag(user.id.toString())
                                    }else{
                                        position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.type_id)).setTag(user.id.toString())

                                    }

                                }


                            }

                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })



                true
            }



            R.id.todos ->{
                mMap.clear()

                //Chamar o web service
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getPontos()
                var position: LatLng
                call.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                        if (response.isSuccessful) {

                            users = response.body()!!
                            for (user in users) {
                                if(user.user_id == loginusername){
                                    position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                                    mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.id.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).setTag(user.id.toString())
                                }else{
                                    position = LatLng(user.lat.toString().toDouble(), user.longitude.toString().toDouble())
                                    mMap.addMarker(MarkerOptions().position(position).title(user.descricao).snippet(user.id.toString())).setTag(user.id.toString())

                                }


                            }

                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })





                true
            }



            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setUpMap()

        mMap.setOnMarkerClickListener(this);



    }

    /** Called when the user clicks a marker.  */
    override fun onMarkerClick(marker: Marker): Boolean {

        val id = marker.tag

       // Toast.makeText(this@MapsActivity, "${id}", Toast.LENGTH_SHORT).show()
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontoid(id)
        var position: LatLng
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                if (response.isSuccessful) {


                    users = response.body()!!
                    for (user in users) {
                        if(user.user_id == loginusername){
                            val intent = Intent(this@MapsActivity, UpdateProblem::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("descricao", user.descricao)
                            intent.putExtra("type", user.type_id)
                            intent.putExtra("username", loginusername)
                            intent.putExtra("id", id.toString())
                            startActivity(intent)
                        }


                    }

                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })



        return false;



    }


    override fun onInfoWindowClick(marker: Marker?) {
        Toast.makeText(this, "Info window clicked", Toast.LENGTH_SHORT).show()
    }



        companion object {
        // add to implement last known location
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        //added to implement location periodic updates
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        } else {
            // 1
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.

                if (location != null) {
                    lastLocation = location
                    //Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }
    
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("**** SARA", "onPause - removeLocationUpdates")
    }
    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("**** SARA", "onResume - startLocationUpdates")

    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        // distance in meter
        return results[0]
    }


}