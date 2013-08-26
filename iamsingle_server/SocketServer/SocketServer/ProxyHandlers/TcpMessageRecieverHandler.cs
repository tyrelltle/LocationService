using SocketServer.Geo;
using SocketServer.Utils;
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
    public class TcpMessageRecieverHandler : ProxyHandler
    {
        
        public override void process()
        {

            SocketServer.Server.TcpServer.Param param = (SocketServer.Server.TcpServer.Param)base.param;
            NetworkStream netstm = param.netstm;          
            byte[] response = null;


            int uid = Convert.ToInt32(param.input);

            TCPRecieverCollection.instance().add(uid,param.netstm);
            //ack 
            response=System.Text.Encoding.UTF8.GetBytes(Helper.makeTCPMsg(Constants.PROXY_ACK));
            netstm.Write(response, 0, response.Length);
            netstm.Flush();
            Console.Out.WriteLine("uid " + uid + "tcpClient recorded into  tcp reciever collection");


        
         }

        private int datalength()
        {
            return 1024;
        }

        private void updateLoc()
        {
        
        
        }

        public override String getHandlerType()
        {
            return Constants.PROXY_REG;
        }

        public override bool autoCloseClient()
        {
            return false;
        }
    }
}
