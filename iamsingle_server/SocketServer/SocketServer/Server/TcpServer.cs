/*
 
 * 
 * TCP End Point
 * 
 * proxy input formate: 
 * 
 * client:  [proxytype] [inputs]   split with single space

 
 
 */



using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using SocketServer.ProxyHandlers;
using System.Configuration;

namespace SocketServer.Server
{
   
    public class TcpServer:IServer
    {

        public struct Param{
           public string input;
           public NetworkStream netstm;
           
        }

        // Thread signal.
        public static ManualResetEvent allDone = new ManualResetEvent(false);

        public TcpServer()
        {
        }


       public   void  handleInput(object input)
        {

            TcpClient clnt = (TcpClient)input;
            NetworkStream netstm=clnt.GetStream();
            //read first msg to determine protocol type
            byte[] data=new byte[1024];
            int recv=netstm.Read(data,0,data.Length);
            if(recv==0)
                return;

            string strdata=Encoding.ASCII.GetString(data,0,recv);
            string[] arr=strdata.Split(' ');
            ProxyHandler handler=ProxyHandlerFactory.Instance.getHandler(arr[0]);
            if(handler==null)
                return;

            string handparam=strdata.Substring(strdata.IndexOf(' ')+1);



            TcpServer.Param param=new Param();
            param.input = handparam;
            param.netstm=netstm;
            handler.setParam(param);
            handler.process();
            //send to client
            if (handler.autoCloseClient())
            {
                netstm.Close();
                clnt.Close();
            }
        }



        public void start()
        {
            int srvrport = Convert.ToInt32(ConfigurationSettings.AppSettings["tcpport"]);
            System.Net.Sockets.TcpListener server = new System.Net.Sockets.TcpListener(srvrport);

            server.Start();

            byte[] data = new byte[1024];
            

            while (true)
            {
                try
                {
                    TcpClient client = server.AcceptTcpClient();
                    

                    Thread t = new Thread(handleInput);
                   
                    t.Start(client);
                   
                   
                }
                catch (Exception e)
                {
                    Console.Out.WriteLine(e.Message);
                }
            }
            server.Stop();

           



        }

         

    }
}