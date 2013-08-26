/*
 
 * 
 * UDP End Point
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
   
    public class Server:IServer
    {
        public struct Parameter
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
        // Thread signal.
        public static ManualResetEvent allDone = new ManualResetEvent(false);

        public Server()
        {
        }


        public  void  handleInput(object input)
        {

            Parameter param = (Parameter)input;

            string inputstr = param.input;
            if (!inputstr.Contains(" "))
                return;
            string[] arr = inputstr.Split(' ');
            ProxyHandler handler=ProxyHandlerFactory.Instance.getHandler(arr[0]);
            string handparam=inputstr.Substring(inputstr.IndexOf(' '));
            param.input = handparam;
            handler.setParam(param);
            handler.process();
            //send to client

            
        }



        public void start()
        {
            int srvrport = Convert.ToInt32(ConfigurationSettings.AppSettings["port"]);
            System.Net.Sockets.UdpClient server = new System.Net.Sockets.UdpClient(srvrport);

            IPEndPoint sender = new IPEndPoint(IPAddress.Any, 0);

            byte[] data = new byte[1024];
            

            while (true)
            {
                try
                {
                    data = server.Receive(ref sender);
                    string stringData = Encoding.ASCII.GetString(data, 0, data.Length);
                    Thread t = new Thread(handleInput);
                    t.Start(new Parameter(stringData, ref server, sender.Address, sender.Port));
                    Console.WriteLine(stringData);
                    Array.Clear(data, 0, data.Length);
                }
                catch (Exception e)
                {
                    Console.Out.WriteLine(e.Message);
                }
            }
            server.Close();

           

            Console.ReadLine();

        }

        

    }
}