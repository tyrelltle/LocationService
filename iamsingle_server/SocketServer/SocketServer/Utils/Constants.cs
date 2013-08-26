using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer
{
    class Constants
    {
        //proxy type strings
        public const string PROXY_TEST="test";
        public const string PROXY_LOC = "loc";
        public const string PROXY_MAP = "map";
        public const string PROXY_MSG = "msg";
        public const string PROXY_REG = "reg";
        public const string PROXY_ACK = "ack";
        public const string PROXY_CLOSE = "close";
        public const string PROXY_UNREACHABLE = "unreachable";
        public const string PROXY_Listen = "listen";
        public const string PROXY_REGIP = "regip";
         
        public const string EOF= "\n";//<!>"; 

    }
}
