using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SocketServer;
using SocketServer.ProxyHandlers;

using System.Net.Sockets;
using SocketServer.Utils;
using System.Text;
namespace Test
{
    [TestClass]
    public class UnitTest1
    {
        struct structure {
            public int a ;
        
        }
        structure func()
        {
            structure a;
            a.a = 101;
            return a;
        }
        [TestMethod]
        public void test()
        {

            structure a = func();
            Assert.IsTrue(a.a == 101);

        
        }

/*
        [TestMethod]
        public void TCPReceiverColleciton_TesT()
        {

            TCPRecieverCollection coll = TCPRecieverCollection.instance();
            coll.add(0, new NetworkStream(new Socket(new SocketInformation())));
            Assert.IsTrue(coll.getByUid(0)!=null);
            Assert.IsTrue(coll.getByUid(1) == null);
            coll.removeByUid(0);
            Assert.IsTrue(coll.getByUid(0) == null);
        }
        */
    }
}