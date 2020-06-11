package pl.edu.agh.votingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.widget.Toast

class WifiReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val wifiStateExtra = intent?.getIntExtra(
            WifiManager.EXTRA_WIFI_STATE,
            WifiManager.WIFI_STATE_UNKNOWN
        )

        when (wifiStateExtra) {
            WifiManager.WIFI_STATE_ENABLED -> {
                Toast.makeText(
                    context,
                    "Wifi has been enabled!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            WifiManager.WIFI_STATE_DISABLED -> {
                Toast.makeText(
                    context,
                    "Wifi has been disabled!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}