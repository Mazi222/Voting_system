package pl.edu.agh.votingapp.viewmodel.join

import android.net.nsd.NsdServiceInfo
import android.util.Log
import java.net.InetAddress

class OngoingVotings{

    companion object {
        var votings: MutableList<OngoingVoting> = arrayListOf()
    }

    fun getVotings() : MutableList<OngoingVoting> {
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
        Log.d("BallotBull: FROM NSD", "Name: ${nsdInfo.serviceName}, port: ${nsdInfo.port}, host: ${nsdInfo.port}")
        this.name = nsdInfo.serviceName
        this.port = nsdInfo.port
        this.host = nsdInfo.host
    }
}