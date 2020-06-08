package pl.edu.agh.votingapp.viewmodel.join

import android.net.nsd.NsdServiceInfo
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
    var name: String = nsdInfo.serviceName
    var port: Int = nsdInfo.port
    var host: InetAddress = nsdInfo.host!!
}