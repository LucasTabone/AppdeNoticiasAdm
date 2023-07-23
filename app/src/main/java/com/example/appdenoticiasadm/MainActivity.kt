package com.example.appdenoticiasadm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appdenoticiasadm.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.btPlNoticia.setOnClickListener {
            val titulo = binding.txtTitulo.text.toString()
            val noticia = binding.txtNoticia.text.toString()
            val data = binding.txtData.text.toString()
            val autor = binding.txtAutor.text.toString()

            if (titulo.isEmpty() || noticia.isEmpty() || data.isEmpty() || autor.isEmpty()) {
                Toast.makeText(this, "Preencha todos os Campos", Toast.LENGTH_SHORT).show()
            } else {
                salvarNoticia(titulo,noticia,data,autor)
            }

        }
    }

    private fun salvarNoticia(titulo: String, noticia: String, data: String, autor: String) {

        val mapNoticias = hashMapOf(
            "titulo" to titulo,
            "noticia" to noticia,
            "data" to data,
            "autor" to autor
        )
        db.collection("Noticias").document("noticia")
            .set(mapNoticias).addOnCompleteListener { tarefa ->
                if (tarefa.isSuccessful){
                    Toast.makeText(this, "Notcia Publicada com Sucesso", Toast.LENGTH_SHORT).show()
                    limparCampos()
                }
            }.addOnFailureListener {  }
    }

    private fun limparCampos(){
        binding.txtTitulo.setText("")
        binding.btPlNoticia.setText("")
        binding.txtData.setText("")
        binding.txtAutor.setText("")

    }

}