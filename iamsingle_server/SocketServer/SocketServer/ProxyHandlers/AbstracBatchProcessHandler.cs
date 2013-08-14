using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.ProxyHandlers
{
    abstract class AbstracBatchProcessHandler :IProxyHandler
    {

        public void process()
        abstract byte[] GenerateOutput();
        abstract int locdatalength(object collection);
    }
}
