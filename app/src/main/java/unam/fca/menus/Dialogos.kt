package unam.fca.menus

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment


    class Dialogos {

        class DialogoLista: DialogFragment() {
            private val item = arrayOf("Bluetooth", "Wi-Fi", "Whatsapp")
            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                with(builder) {
                    setTitle("Compartir")
                    setIcon(android.R.drawable.ic_dialog_info)
                    setItems(item) { _, which ->
                        Toast.makeText(context.applicationContext,
                            "Eligio: "+item[which], Toast.LENGTH_SHORT).show()
                    }
                }
                return builder.create()
            }
        }
   interface  NotificaDialogo{
       fun onDialogoPositivoClic(entrada:String)
   }
    class DialogoPersonalizado(private var dialogoListener: NotificaDialogo): DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(activity)
            val inflater = activity?.layoutInflater
            val dialogo = inflater?.inflate(R.layout.dialogo, null)
            builder.setView(dialogo)
            with(builder) {
                setPositiveButton("Aceptar") { dialog, _ ->
                    val editText = dialogo?.findViewById<EditText>(R.id.edtDialogo)
                    val entrada: String = editText?.text.toString()
                    dialogoListener.onDialogoPositivoClic(entrada)
                    dialog.dismiss()
                }
                setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
            }
            return builder.create()
        }
    }

    class DialogoSalir: DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(activity)
            with(builder) {
                setTitle("Deseas salir de la app?")
                setIcon(android.R.drawable.ic_dialog_alert)
                setPositiveButton("Si") { _, _ ->
                    activity?.finish()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
            }
            return builder.create()
        }
    }

}


