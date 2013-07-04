/*
 * Protocol input:   map userid_altitude_latitude_longtitude 
 * Note: param.input dosent contatin 'loc'
 * 
 * protocol output:
 * numOfMessages userid_altitude_latitude_longtitude  userid_altitude_latitude_longtitude    userid_altitude_latitude_longtitude
 * numOfMessages userid_altitude_latitude_longtitude  userid_altitude_latitude_longtitude    userid_altitude_latitude_longtitude
 * numOfMessages userid_altitude_latitude_longtitude  userid_altitude_latitude_longtitude    userid_altitude_latitude_longtitude
 * ......

*/
using SocketServer.Param;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.ProxyHandlers
{
    public class GetLocMapHandler : ProxyHandler
    {
        
        public override void process()
        {


            
            Parameter param = base.param;
            byte[] response = null;
            IPEndPoint client = new IPEndPoint(param.clientip, param.clientport);
            string[] vals = base.param.input.Split('_');
            //now vals' value is like  {userid,altitude,latitude,longtitude }

            if (vals.Length != 4)
            {                
                //input ill formated
                Console.Out.Write("Wrong input format " + base.param.input + ". correct is 'loc userid.altitude.latitude.longtitude'");
                return;
                
            }

            //retrieve user locs that is close to cur user
            LINQClassesDataContext context = new LINQClassesDataContext();
            int uid = Convert.ToInt32(vals[0]);
            double lati = Convert.ToDouble(vals[2]);
            double longti = Convert.ToDouble(vals[3]);
            int nearRadius=Convert.ToInt32(ConfigurationSettings.AppSettings["nearRadius"]);
            
            var locs = from l in context.Locations
                         where Math.Acos(Math.Sin(lati)*Math.Sin((double)l.latitude)+
                                         Math.Cos(lati)*Math.Cos((double)l.latitude)*
                                         Math.Cos((double)l.longtitude-longti))*6371
                                         <=nearRadius
                         select l;
            if (locs.Count() == 0)
            {
                Console.Out.Write("didnt find friends with location " + "(lati= " + lati + " ,lonti= " + longti + " )\n");
                return;
            }
                
            string accumulat = string.Empty;
            int maxsize = Convert.ToInt32(ConfigurationSettings.AppSettings["packetsize"]);
            int numPacket = (int) Math.Ceiling(((float)locdatalength(locs.First())*locs.Count()/(float)maxsize));
            foreach (Location loc in locs)
            {//send multiple packet to client. approximately 15 loc info in each packet. fixed size is AppSettings["packetsize"]
                string curlocdata = loc.userid + "_" + loc.altitude + "_" + loc.latitude + "_" + loc.longtitude;

                if (maxsize <= curlocdata.Length + accumulat.Length)
                { //current packet is large enough. send to client
                    response = System.Text.Encoding.UTF8.GetBytes(numPacket+" "+accumulat);
                    param.server.Send(response, response.Length, client);
                    Console.Out.Write("sent client:" + accumulat +"\n");
                    accumulat = "";
                }

                accumulat += curlocdata+" ";
            }
            
            if(accumulat.Length!=0)
            {
                response = System.Text.Encoding.UTF8.GetBytes(numPacket + " " + accumulat);
                    param.server.Send(response, response.Length, client);
                    Console.Out.Write("sent client:" + accumulat +"\n");
                
            }



            Console.Out.Write("ok");

         }

        private int locdatalength(Location loc)
        {
            return loc.userid.ToString().Length +
                   loc.altitude.ToString().Length +
                   loc.latitude.ToString().Length +
                   loc.longtitude.ToString().Length +
                   4 +//count for the 4 '_' chars in each loc data 
                   3;//the first info in loc data is the num of packages to be sent, here we count 3, assuming num of packages wont exist hundreds

        }

        private void updateLoc()
        {
        
        
        }

        public override String getHandlerType()
        {
            return Constants.PROXY_LOC;
        }
    }
}
