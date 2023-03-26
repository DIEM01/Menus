package unam.fca.menus

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color

import android.media.RingtoneManager
import android.os.Build
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), Dialogos.NotificaDialogo {
    private lateinit var texto: TextView
    private lateinit var lista: ListView
    private val opciones = arrayOf("Listado 1", "Listado 2", "Listado 3", "Listado4")
    private val CHANNEL_ID = "YICENCIADO"
    private val NOTIFICATION_ID = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        texto = findViewById(R.id.texto)
        lista = findViewById(R.id.lista)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, opciones)
        lista.adapter = adaptador


        registerForContextMenu(texto)
        registerForContextMenu(lista)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.principal, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                texto.text = getString(R.string.menu1)
                val dl: Dialogos.DialogoLista = Dialogos.DialogoLista()
                dl.show(supportFragmentManager, null)
                return true
            }
            R.id.item2 -> {
                texto.text = getString(R.string.menu2)
                enviarNotificacion()
                return true
            }
            R.id.item3 -> {
                texto.text = getString(R.string.menu3)
                return true
            }
            R.id.dialogo -> {
                texto.text = getString(R.string.dialogo)
                val dp: Dialogos.DialogoPersonalizado = Dialogos.DialogoPersonalizado(this)
                dp.show(supportFragmentManager, null)
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
        when (v?.id) {
            R.id.texto -> {
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
        val mensaje: String
        when (item.itemId) {
            R.id.etiquetaOpcion1 -> {
                texto.text = getString(R.string.etiqueta_opc1)
                return true
            }
            R.id.etiquetaOpcion2 -> {
                texto.text = getString(R.string.etiqueta_opc2)
                return true
            }
            R.id.listaOpcion1 -> {
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                mensaje = "Lista[${info.position}] opcion 1"
                texto.text = mensaje
            }
            R.id.listaOpcion2 -> {
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                mensaje = "Lista[${info.position}] opcion 2"
                texto.text = mensaje
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDialogoPositivoClic(entrada: String) {
        texto.text = entrada
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val ds: Dialogos.DialogoSalir = Dialogos.DialogoSalir()
        ds.show(supportFragmentManager, null)
    }

    private fun enviarNotificacion() {
        val nombre = "Mi canal *"
        val descripcion = "Envio notficacion"
        val importancia = NotificationManager.IMPORTANCE_DEFAULT
        val canal = NotificationChannel(CHANNEL_ID, nombre, importancia).apply {
            description = descripcion
        }
        canal.enableVibration(true)
        canal.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
        canal.enableLights(true)
        canal.lightColor = Color.GRAY

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(canal)

        val titulo = "creando una notificacion"
        val texto = "programacion de dsipositivos moviles"
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    android.R.drawable.ic_menu_agenda
                )
            )
            .setContentTitle(titulo)
            .setContentText(texto)
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(
                    BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_directions)
                )
            )

            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(uri)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        this@MainActivity, "debe dat permiso de notificacion ",
                        Toast.LENGTH_LONG
                    ).show()
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1
                    )
                } else {
                    notify(NOTIFICATION_ID, builder.build())
                }
            }   else {
                    notify(NOTIFICATION_ID, builder.build())
                }
            }
        }
    }
