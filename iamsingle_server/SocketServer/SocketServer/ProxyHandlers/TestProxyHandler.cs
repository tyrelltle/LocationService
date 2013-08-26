
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.ProxyHandlers
{
    public class TestProxyHandler :ProxyHandler
    {
        private LINQClassesDataContext linqContext = new LINQClassesDataContext();
        public override void process()
        {


           
            SocketServer.Server.Server.Parameter param = (SocketServer.Server.Server.Parameter)base.param;
            byte[] response = System.Text.Encoding.UTF8.GetBytes("welcome to server");
            IPEndPoint client = new IPEndPoint( param.clientip, param.clientport);
            
          
            param.server.Send(response, response.Length, client);
            response = System.Text.Encoding.UTF8.GetBytes("one");
            param.server.Send(response, response.Length, client);
            response = System.Text.Encoding.UTF8.GetBytes("two");
            param.server.Send(response, response.Length, client);
            response = System.Text.Encoding.UTF8.GetBytes("three");
            param.server.Send(response, response.Length, client);
            response = System.Text.Encoding.UTF8.GetBytes("four");
            param.server.Send(response, response.Length, client);
            response = System.Text.Encoding.UTF8.GetBytes("five");
            param.server.Send(response, response.Length, client);
            Console.Out.Write(param.input + response);
            
         }

        public override String getHandlerType()
        {
            return Constants.PROXY_TEST;
        }
        public override bool autoCloseClient()
        {
            throw new NotImplementedException();
        }
    }
}
