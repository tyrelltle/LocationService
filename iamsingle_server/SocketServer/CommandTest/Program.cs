using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace CommandTest
{/*
  test chat function, this test is a sender
  */
    class Program
    {
        static void Main(string[] args)
        {
            int uid = 29;
            TcpClient clnt = new TcpClient();
            clnt.Connect("localhost", 12000);
            NetworkStream steam = clnt.GetStream();
             byte[]  response=System.Text.Encoding.UTF8.GetBytes("reg 29");
             steam.Write(response, 0, response.Length);
            steam.Flush();
            while(true){
                Console.In.Read();

                response = System.Text.Encoding.UTF8.GetBytes("msg 31 sb");
                steam.Write(response, 0, response.Length);
            }
            clnt.Close();
        }
    }
}
