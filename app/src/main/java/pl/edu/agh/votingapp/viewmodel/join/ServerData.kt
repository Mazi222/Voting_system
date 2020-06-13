package pl.edu.agh.votingapp.viewmodel.join

import java.net.InetAddress

class ServerData {
    companion object {
        lateinit var host: InetAddress
        var port: Int = 8080

        fun getUrl(): String {
            return "http://$host:$port/"
        }
    }
}