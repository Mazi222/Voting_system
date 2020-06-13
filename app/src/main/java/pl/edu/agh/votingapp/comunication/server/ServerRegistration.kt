package pl.edu.agh.votingapp.comunication.server

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import pl.edu.agh.votingapp.comunication.NsdHelper
import java.net.InetAddress

class ServerRegistration(context: Context) {

    private var nsdHelper : NsdHelper = NsdHelper(context)

    fun registerServer(votingName : String, port: Int, serverAddress : InetAddress) {
        Log.d("BallotBull", "Registering server: $votingName, $serverAddress:$port")
        nsdHelper.tearDownRegistration() // Cancel any previous registration request
        nsdHelper.initializeRegistrationListener()
        val serviceInfo = NsdServiceInfo().apply {
            setPort(port)
            serviceName = votingName + "ə" + serverAddress.hostAddress
            serviceType = nsdHelper.SERVICE_TYPE
            host = serverAddress
        }
        nsdHelper.mNsdManager!!.registerService(
            serviceInfo, NsdManager.PROTOCOL_DNS_SD, NsdHelper.mRegistrationListener
        )
    }
}