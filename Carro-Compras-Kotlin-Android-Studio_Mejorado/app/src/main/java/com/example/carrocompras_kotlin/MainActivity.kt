package com.example.carrocompras_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrocompras_kotlin.databinding.ActivityMainBinding
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdaptadorProducto

    var listaProductos = ArrayList<Producto>()
    var carroCompras = ArrayList<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        agregarProductos()

        setupRecyclerView()

        leerSharedPreferences()
    }

    fun setupRecyclerView() {
        binding.rvListaProductos.layoutManager = LinearLayoutManager(this)
        adapter = AdaptadorProducto(this, binding.tvCantProductos, binding.btnVerCarro, listaProductos, carroCompras)
        binding.rvListaProductos.adapter = adapter
    }

    fun agregarProductos() {
        listaProductos.add(Producto("1", "Producto 1", "Descripcion del Producto 1", 50.0))
        listaProductos.add(Producto("2", "Producto 2", "Descripcion del Producto 2", 80.0))
        listaProductos.add(Producto("3", "Producto 3", "Descripcion del Producto 3", 40.0))
        listaProductos.add(Producto("4", "Producto 4", "Descripcion del Producto 4", 20.0))
        listaProductos.add(Producto("5", "Producto 5", "Descripcion del Producto 5", 56.0))
    }

    fun leerSharedPreferences() {
        val sp = this.getSharedPreferences("carro_compras", MODE_PRIVATE)

        val jsonString = sp.getString("productos", "")

        val jsonArray = JSONArray(jsonString)

        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                carroCompras.add(
                    Producto(
                    jsonObject.getString("idProducto"),
                    jsonObject.getString("nomProducto"),
                    jsonObject.getString("descripcion"),
                    jsonObject.getDouble("precio")
                )
                )
            }
        }
    }
}
