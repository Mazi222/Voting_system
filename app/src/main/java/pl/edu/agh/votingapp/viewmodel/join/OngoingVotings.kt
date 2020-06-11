package pl.edu.agh.votingapp.viewmodel.join

import android.net.nsd.NsdServiceInfo
import android.os.AsyncTask
import android.util.Log
import java.net.Inet4Address
import java.net.InetAddress

class OngoingVotings {

    init {
        val nsdServiceInfo = NsdServiceInfo()
        nsdServiceInfo.serviceName = "Wybieramy ziemniaki"
        nsdServiceInfo.port = 8080
        AsyncTask.execute {
            nsdServiceInfo.host = InetAddress.getByName("127.0.0.1")
            votings.add(OngoingVoting(nsdServiceInfo))
        }
    }

    companion object {
        var votings: MutableList<OngoingVoting> = arrayListOf()
    }

    fun getVotings(): MutableList<OngoingVoting> {
        return votings
    }

    fun addVoting(nsdInfo: NsdServiceInfo) {
        votings.add(OngoingVoting(nsdInfo))
    }
}

class OngoingVoting(nsdInfo: NsdServiceInfo) {

    var name: String
    var port: Int
    var host: InetAddress

    init {
        Log.d(
            "BallotBull: FROM NSD",
            "Name: ${nsdInfo.serviceName}, port: ${nsdInfo.port}, host: ${nsdInfo.port}"
        )
        this.name = nsdInfo.serviceName
        this.port = nsdInfo.port
        this.host = nsdInfo.host
    }
}