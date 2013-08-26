using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.Server
{
    interface IServer
    {
        void handleInput(object input);
        void start();
    }
}
