﻿
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.Utils
{
    public static class Helper
    {
        public static string makeTCPMsg(string str)
        {
            return str += Constants.EOF;
        }
    }

}
