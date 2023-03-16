package unam.fca.menus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var texto: TextView
    private  lateinit var  lista : ListView
    private val opciones = arrayOf("Listado 1", "Listado 2", "Listado 3", "Listado4")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        texto = findViewById(R.id.texto)
        lista = findViewById(R.id.lista)
        val adaptador = ArrayAdapter (this, android.R.layout.simple_list_item_1,opciones)
        lista.adapter = adaptador


        registerForContextMenu(texto)
        registerForContextMenu(lista)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.principal, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item1 -> {
                texto.text=getString(R.string.menu1)
                return true
            }
            R.id.item2 -> {
                texto.text=getString(R.string.menu2)
                return true
            }
            R.id.item3 -> {
                texto.text=getString(R.string.menu3)
                return true
            }
            R.id.dialogo -> {
                texto.text=getString(R.string.dialogo)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        where(v?.id) {ññ
            R.idtexto-> {
                inflater.inflate(R.menu.contextual_etiqueta, menu)
            }
            R.id.lista -> {
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            menu?.setHeaderTitle(lista.adapter.getItem(info.position).toString())
            inflater.inflate(R.menu.contextual_lista, menu)
        }
        }
        menu?.add(2, v!!.id, 0, "otra opcion")

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val mesaje: String
        where(item.itemId) {
            R.id.etiquetaOpcion1 -> {
                texto.text=getString(R.string.etiqueta_opc1)
                return true
             }
            R.id.etiquetaOpcion2 -> {
                texto.text=getString(R.string.etiqueta_opc2)
                return true
            }
           R.id.listaOpcion1 -> {
                val info =item.menuInfo as AdapterView.AdapterContextMenuInfo
                mesaje="Lista[${info.position}] opcion 1"
                texte.text =mensaje
            }
            R.id.listaOpcion2 -> {
                val info =item.menuInfo as AdapterView.AdapterContextMenuInfo
                mesaje="Lista[${info.position}] opcion 2"
                texte.text =mensaje
            }
        }
        return super.onContextItemSelected(item)
    }
}