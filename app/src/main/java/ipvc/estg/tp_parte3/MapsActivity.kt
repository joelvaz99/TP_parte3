package ipvc.estg.tp_parte3

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.tp_parte3.api.EndPoints
import ipvc.estg.tp_parte3.api.ServiceBuilder
import ipvc.estg.tp_parte3.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var users: List<User>

    // add to implement last known location
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

// initialize fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Chamar o web service
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontos()
        var position: LatLng
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                if (response.isSuccessful){

                    users=response.body()!!
                    for(user in users){
                        position= LatLng(user.lat.toString().toDouble(),user.longitude.toString().toDouble())
                        mMap.addMarker(MarkerOptions().position(position).title("abc"))
                    }

                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
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

                startActivity(intent)
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
       // val sydney = LatLng(-34.0, 151.0)
      //  mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        setUpMap()
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
                    Toast.makeText(
                        this@MapsActivity,
                        lastLocation.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }
}