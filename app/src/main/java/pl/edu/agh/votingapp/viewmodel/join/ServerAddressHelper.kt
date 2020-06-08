package pl.edu.agh.votingapp.viewmodel.join

import java.net.InetAddress

class ServerData(host_ : InetAddress, port_ : Int)  {

    companion object{
        lateinit var host : InetAddress
        var port : Int = 8080
    }

    init{
        var host : InetAddress = host_
        var port : Int = port_
    }

}