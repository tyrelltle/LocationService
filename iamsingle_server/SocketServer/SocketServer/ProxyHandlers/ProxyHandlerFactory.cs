using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.ProxyHandlers
{
    class ProxyHandlerFactory
    {
        private static ProxyHandlerFactory instance;
        private ProxyHandlerFactory() { }
        public static ProxyHandlerFactory Instance
        {
            get 
            {
                if (instance == null)
                    instance = new ProxyHandlerFactory();
                return instance;
            }
        
        }

        public ProxyHandler getHandler(string type)
        { 
            if(type==null)
                return null;
            if (type.Equals(Constants.PROXY_TEST))
                return new TestProxyHandler();
            if (type.Equals(Constants.PROXY_LOC))
                return new LocUpdateHandler();
            return null;
        }
        


    }
}
