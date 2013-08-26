using SocketServer.Geo;
using SocketServer.Utils;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.ProxyHandlers
{

    public class TcpMessageSenderHandler : ProxyHandler
    {
        private struct MSG
        {
            public int uid;
            public string msg;
        }
        public override void process()
        {

            SocketServer.Server.TcpServer.Param param = (SocketServer.Server.TcpServer.Param)base.param;
            NetworkStream netstm = param.netstm;          
            byte[] response = null;           
            string[] vals = param.input.Split('_');
            if (vals.Length != 1)
            {                
                //input ill formated
                Console.Out.WriteLine("Wrong input format " + param.input + ". correct is 'reg uid'");
                return;               
            }
            
            int uid = Convert.ToInt32(vals[0]);
            //ack sender reg
            response=System.Text.Encoding.UTF8.GetBytes(Helper.makeTCPMsg(Constants.PROXY_ACK));
            netstm.Write(response, 0, response.Length);
            netstm.Flush();
            Console.Out.WriteLine("uid " + uid + "connected to tcp server end point");
            try
            {
                while (true)
                {
                    byte[] data = new byte[1024];
                    int recv = netstm.Read(data, 0, data.Length);
                    if (recv == 0)
                        break;
                    //parse msg
                    MSG msg;
                    try
                    { 
                        msg = getMsgFromRequest(data, 0, recv);
                        if (Constants.PROXY_CLOSE.Equals(msg.msg) && msg.uid == -1)
                        {   /*client leaving, kill connection as well as delete 
                             *this client's listening tcpclient from TCPRecieverCollection*/
                            TCPRecieverCollection coll=TCPRecieverCollection.instance();
                            NetworkStream stm=coll.getByUid(uid);
                            if (stm != null)
                            {
                                response = System.Text.Encoding.UTF8.GetBytes(Helper.makeTCPMsg(Constants.PROXY_CLOSE));
                                stm.Write(response, 0, response.Length);
                                stm.Close();
                                coll.removeByUid(uid);
                            }
                            response = System.Text.Encoding.UTF8.GetBytes(Helper.makeTCPMsg(Constants.PROXY_ACK));
                            netstm.Write(response, 0, response.Length);
                            
                            break;
                        }
                    }
                    catch (TcpInvalidMsgException e) { 
                        break; 
                    }

                    //send msg to user in reciever collection
                    sendToTarget(msg);


                    //ack
                    response = System.Text.Encoding.UTF8.GetBytes(Helper.makeTCPMsg(Constants.PROXY_ACK));
                    netstm.Write(response, 0, response.Length);

                }
            }
            catch { return; }

            Console.Out.WriteLine("uid "+uid+ "disconnected from tcp server end point");


        }

        private MSG getMsgFromRequest(byte[] data,int start, int len)
        {
            MSG msg;
            msg.uid = -1;
            string str=Encoding.ASCII.GetString(data, start, len);
            if(str.Equals(Constants.PROXY_CLOSE))
            {   //client telling server 'i dont want to listen any msg, and i dont want to send any msg neither'
                msg.uid=-1;
                msg.msg = Constants.PROXY_CLOSE;
                return msg;
            }
            string []strlis=str.Split(' ');
            if (strlis.Length != 3 || !strlis[0].Equals(Constants.PROXY_MSG))
            {
                throw new TcpInvalidMsgException("invalid msg formate: "+str);
            }
            int uid=Convert.ToInt32( strlis[1]);
            
            msg.uid = uid;
            msg.msg = strlis[2];
            return msg;
            

        }
        [MethodImpl(MethodImplOptions.Synchronized)]
        private  void sendToTarget(MSG msg)
        {
            TCPRecieverCollection coll= TCPRecieverCollection.instance();
            NetworkStream netstm = coll.getByUid(msg.uid);
            if(netstm==null)
                return;
            if (!netstm.CanRead || !netstm.CanWrite)
            {
                coll.removeByUid(msg.uid);
                return;
            }
            byte[] bytes=Encoding.ASCII.GetBytes(Helper.makeTCPMsg(msg.uid+" "+ msg.msg));
            netstm.Write(bytes,0,bytes.Count());
            netstm.Flush();
            byte[] data = new byte[1024];
            int recv=0;
            int max_try=6;
            Console.Out.WriteLine("msg redirected to client. msg is  '" + msg.uid + " " + msg.msg+"'");
             recv = netstm.Read(data, 0, data.Length);
           
             string str=Encoding.ASCII.GetString(data, 0, recv);
             if (str.Equals(Constants.PROXY_ACK))
             {
                 Console.Out.WriteLine("ACK recieved after msg redirected to client ");
                 return;
             }
        }

        private int datalength()
        {
            return 1024;
        }

   

        public override String getHandlerType()
        {
            return Constants.PROXY_REG;
        }

        public override bool autoCloseClient()
        {
            return true;
        }
    }
}
