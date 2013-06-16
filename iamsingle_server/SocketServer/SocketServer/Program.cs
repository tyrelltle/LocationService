using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SocketServer.Server;
namespace SocketServer
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Out.Write("Socket Server Program Started\n"+
                              "Start to Listen to Clients\n");
            Server.Server server = new Server.Server();
            server.start();
        }
    }
}
