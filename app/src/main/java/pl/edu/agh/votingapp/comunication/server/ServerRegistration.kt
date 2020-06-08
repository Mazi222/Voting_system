package pl.edu.agh.votingapp.comunication.server

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import pl.edu.agh.votingapp.comunication.NsdHelper

class ServerRegistration(context: Context) {

    private var nsdHelper : NsdHelper = NsdHelper(context)

    fun registerServer(votingName : String, port: Int) {
        nsdHelper.tearDownRegistration() // Cancel any previous registration request
        nsdHelper.initializeRegistrationListener()
        val serviceInfo = NsdServiceInfo().apply {
            setPort(port)
            serviceName = votingName
            serviceType = nsdHelper.SERVICE_TYPE
        }
        nsdHelper.mNsdManager!!.registerService(
            serviceInfo, NsdManager.PROTOCOL_DNS_SD, NsdHelper.mRegistrationListener
        )
    }
}