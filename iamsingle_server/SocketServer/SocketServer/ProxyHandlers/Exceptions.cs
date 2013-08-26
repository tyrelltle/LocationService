using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.ProxyHandlers
{
    public class TcpInvalidMsgException : Exception
    {
        public TcpInvalidMsgException(string msg) : base(msg) { }
    }

}
