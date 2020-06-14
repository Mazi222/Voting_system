package pl.edu.agh.votingapp.viewmodel.join

import android.net.nsd.NsdServiceInfo
import android.util.Log
import java.net.InetAddress

class OngoingVotings {

    var votings: MutableList<OngoingVoting> = arrayListOf()

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
            "BallotBull",
            "Ongoing Votng from NSD - Name: ${nsdInfo.serviceName}, port: ${nsdInfo.port}, host: ${nsdInfo.host}"+
                    "host2 : ${InetAddress.getByName(nsdInfo.serviceName.substringAfter('ə'))}"
        )


        this.name = nsdInfo.serviceName.substringBefore('ə')
        this.port = 8080
        this.host = InetAddress.getByName(nsdInfo.serviceName.substringAfter('ə'))
    }
}