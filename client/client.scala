package client 

import org.zeromq.ZMQ

object Client {
  def main(args: Array[String]) {
    val context = ZMQ.context(1)
    //make socket for connecting to server 
    val socket = context.socket(ZMQ.REQ)
    socket.connect("tcp://localhost:5555")
    
    println("connecting to server...")
    for (i <- 1 until 10) {
      val request = "ping"
      println("--sending to server--")
      socket.send(request.getBytes, 0)
      val reply = socket.recv(0)
      println("received: " + (new String(reply)))
    }
    //we should clean up...
    socket.close
    context.term
    println("all done")
  }
}
