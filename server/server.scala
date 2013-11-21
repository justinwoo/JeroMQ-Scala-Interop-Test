package server

import org.zeromq.ZMQ

object Server {
  def main(args: Array[String]) {
    val context = ZMQ.context(1)
    //make socket for listening for clients
    val socket = context.socket(ZMQ.REP)
    socket.bind("tcp://*:5555")

    println("waiting for clients...")
    while (!Thread.currentThread.isInterrupted) {
      val reply = socket.recv(0)
      println("--client connected--")
      println("received: " + (new String(reply)))
      val request = "pong"
      socket.send(request.getBytes, 0)
      Thread.sleep(1000) //wait time
    }
    //we should clean up...
    socket.close
    context.term
    println("all done")
  }
}
