package io.sh4.shop_kotlin.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.models.ShopAddress
import io.sh4.shop_kotlin.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(50.049683, 19.944544)
        val call : Call<List<ShopAddress>> = RetrofitClient.shopAddressApiService.getShopAddresses()
        var shopAddresses : List<ShopAddress>? = null
        Log.d("shopAddresses before", "true")
        call.enqueue(object : Callback<List<ShopAddress>> {
            override fun onResponse(call : Call<List<ShopAddress>>?, response: Response<List<ShopAddress>>?) {
                shopAddresses = response!!.body()
//                Log.d("shopAddresses after", shopAddresses.toString())
                shopAddresses?.forEach { address ->
                    val lat : Double = address.lat
                    val lon : Double = address.lon
                    Log.d("shopAddresses", "lat: $lat lon: $lon")
                    googleMap.addMarker(MarkerOptions().position(LatLng(lat, lon)).title("Shop $lat $lon" ))
                }
            }
            override fun onFailure(call : Call<List<ShopAddress>>?, t: Throwable) {
                Log.d("products after", call.toString())
                Log.d("products after", t.toString())
            }
        })
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}