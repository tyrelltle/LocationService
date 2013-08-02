using SocketServer.Geo;
/*
 * Protocol input:   map northeastlat_northeastlng_sourthwestlat_sourthwestlng
 * Note: defines the visible region
 * 
 * protocol output:
 * numOfMessages userid_username_latitude_longtitude  userid_username_latitude_longtitude    userid_username_latitude_longtitude
 * numOfMessages userid_username_latitude_longtitude  userid_username_latitude_longtitude    userid_username_latitude_longtitude
 * numOfMessages userid_username_latitude_longtitude  userid_username_latitude_longtitude    userid_username_latitude_longtitude
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
            

            if (vals.Length != 4)
            {                
                //input ill formated
                Console.Out.Write("Wrong input format " + base.param.input + ". correct is 'loc userid.altitude.latitude.longtitude'");
                return;
                
            }

            
            LINQClassesDataContext context = new LINQClassesDataContext();
            
            double northeast_lati = Convert.ToDouble(vals[0]);
            double northeast_longi = Convert.ToDouble(vals[1]);
            double southeast_lati = Convert.ToDouble(vals[2]);
            double southeast_longi = Convert.ToDouble(vals[3]);

            Region region = new Region(new LatLng(northeast_lati, northeast_longi), new LatLng(southeast_lati, southeast_longi));



            var locs = context.InBoundBox(northeast_lati, northeast_longi, southeast_lati, southeast_longi).ToList();
            if (locs.Count() == 0)
            {
                return;
            }
                
            string accumulat = string.Empty;
            int maxsize = Convert.ToInt32(ConfigurationSettings.AppSettings["packetsize"]);
            int numPacket = (int) Math.Ceiling(((float)locdatalength(locs.First())*locs.Count()/(float)maxsize));
            foreach (InBoundBoxResult loc in locs)
            {//send multiple packet to client. approximately 15 loc info in each packet. fixed size is AppSettings["packetsize"]
                string curlocdata = loc.userid + "_" + loc.username + "_" + loc.latitude + "_" + loc.longtitude;

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

        private int locdatalength(InBoundBoxResult loc)
        {
            return loc.userid.ToString().Length +
                   loc.username.ToString().Length +
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
