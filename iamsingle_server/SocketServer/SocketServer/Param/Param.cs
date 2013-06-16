using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.Param
{
    public class Parameter
    {
         public string input;
            public UdpClient server;
            public IPAddress clientip;
            public int clientport;
            public Parameter(string i, ref UdpClient s, IPAddress ci, int cp)
            {
                input = i;
                server = s;
                clientip = ci;
                clientport = cp;
            }
    }
}
