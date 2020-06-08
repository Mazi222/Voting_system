package pl.edu.agh.votingapp.comunication

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdManager.*
import android.net.nsd.NsdServiceInfo
import android.util.Log


class NsdHelper(context: Context) {

    var mNsdManager: NsdManager? = null

    companion object {
        var mResolveListener: ResolveListener? = null
        var mDiscoveryListener: DiscoveryListener? = null
        var mRegistrationListener: RegistrationListener? = null
    }

    val SERVICE_TYPE = "_ballotbull._tcp."
    val TAG = "BallotBull"
    var mService: NsdServiceInfo? = null
    var mServiceName = ""

    init {
        mNsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
    }

    fun stopDiscovery() {
        if (mDiscoveryListener != null) {
            try {
                mNsdManager!!.stopServiceDiscovery(mDiscoveryListener)
            } finally {
                mDiscoveryListener = null
            }
        }
    }

    fun tearDownRegistration() {
        if (mRegistrationListener != null) {
            try {
                mNsdManager!!.unregisterService(mRegistrationListener)
            } finally {
                mRegistrationListener = null
            }
        }
    }

    fun initializeDiscoveryListener(discoveryListener: DiscoveryListener) {
        mDiscoveryListener = discoveryListener
    }

    fun initializeRegistrationListener() {
        mRegistrationListener = object : RegistrationListener {
            override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
                mServiceName = NsdServiceInfo.serviceName
                Log.d(TAG, "Service registered: $mServiceName, ${NsdServiceInfo.host}, ${NsdServiceInfo.port}")
            }

            override fun onRegistrationFailed(arg0: NsdServiceInfo, arg1: Int) {
                Log.d(TAG, "Service registration failed: $arg1")
            }

            override fun onServiceUnregistered(arg0: NsdServiceInfo) {
                Log.d(TAG, "Service unregistered: " + arg0.serviceName)
            }

            override fun onUnregistrationFailed(
                serviceInfo: NsdServiceInfo,
                errorCode: Int
            ) {
                Log.d(TAG, "Service unregistration failed: $errorCode")
            }
        }
    }

    fun initializeResolveListener() {
        mResolveListener = object : ResolveListener {
            override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                Log.e(TAG, "Resolve failed$errorCode")
            }

            override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
                Log.e(TAG, "Resolve Succeeded. $serviceInfo")
                if (serviceInfo.serviceName == mServiceName) {
                    Log.d(TAG, "Same IP.")
                    return
                }
                mService = serviceInfo
            }
        }
    }


}