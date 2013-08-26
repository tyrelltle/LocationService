using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SocketServer.Server;
using System.Threading;
namespace SocketServer
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Out.Write("=============Socket Server Program Started\n"+
                              "Start to Listen to Clients================\n");
            Thread udpsrvr = new Thread(runudp);
            udpsrvr.Start();
            Thread tcpsrvr = new Thread(runtcp);
            tcpsrvr.Start();

        }
        static void runudp()
        {
            Server.Server server = new Server.Server();
            server.start();
        }

        static void runtcp()
        { 
                    TcpServer tcpserver = new TcpServer();
            tcpserver.start();
        }
    }
}
