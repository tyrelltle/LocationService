using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.Model
{

    public class UserMessage
    {
        
        public int userid;
        public string msg;
        public string username;

        public UserMessage(int id, string uname, string msg)
        {
            this.userid = id;
            this.msg = msg;
            username = uname;
        }
        public string toString()
        {
            return userid + " " + username + " " + msg;
        }
    }



}
