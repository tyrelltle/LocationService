using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SocketServer;
using SocketServer.ProxyHandlers;
using SocketServer.Param;
using System.Net.Sockets;
namespace Test
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestMethod1()
        {
            string a = "qwesdasddsadsad1_0.11123_2.123124_3.123941_0.11123_2";
            for (int i = 0; i < 15; i++)
                a += "qwesdasddsadsad1_0.11123_2.123124_3.123941_0.11123_2";
                
            int w=System.Text.Encoding.UTF8.GetBytes(a).GetLength(0);
           
           
        }
    }
}
