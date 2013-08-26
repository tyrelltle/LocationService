
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.ProxyHandlers
{
    

    public abstract class ProxyHandler
    {
         public Object param;
         public void setParam(Object param)
         {
             this.param = param;
         }
        //todo: delete below commented
         /*protected void sendMsgToClnt(string msg,   ref IPEndPoint  client)
         {
             byte[] response = System.Text.Encoding.UTF8.GetBytes(msg);
             param.server.Send(response, response.Length, client);
         }*/
         public abstract void process();
         public abstract string getHandlerType();
         public abstract bool autoCloseClient();
    }
}
