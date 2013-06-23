/*
 * Protocol input:   map userid_altitude_latitude_longtitude 
 * Note: param.input dosent contatin 'loc'
 * 
 * protocol output:
 * multiple

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
            
            foreach (Location loc in locs)
            {
                response = System.Text.Encoding.UTF8.GetBytes(locs.Count() + "_" + loc.userid + "_" + loc.altitude + "_" + loc.latitude + "_" + loc.longtitude);
                param.server.Send(response, response.Length, client);
                Console.Out.Write("sent client:" + loc.userid + "_"+loc.altitude+"_"+loc.latitude+"_"+loc.longtitude+"\n");
            }
            Console.Out.Write("ok");

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
