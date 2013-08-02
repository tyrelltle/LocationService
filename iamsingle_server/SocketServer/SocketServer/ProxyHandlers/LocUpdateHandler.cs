/*
 * Protocol input:   loc userid.latitude.longtitude 
 * Note: param.input dosent contatin 'loc'
 * 

*/
using SocketServer.Param;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.Linq;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.ProxyHandlers
{
    public class LocUpdateHandler : ProxyHandler
    {
        
        public override void process()
        {


            byte[] response = null;
            Parameter param = base.param;

            string[] vals = base.param.input.Split('_');
            //now vals' value is like  {userid,altitude,latitude,longtitude }

            if (vals.Length != 3)
            {                
                //input ill formated
                Console.Out.Write("Wrong input format " + base.param.input + ". correct is 'loc userid.latitude.longtitude'");
                return;
                
            }

            //update db tables: User and Location
            LINQClassesDataContext context = new LINQClassesDataContext();           
            int uid=Convert.ToInt32(vals[0]);
            User user=(from u in context.Users
                      where u.userid==uid
                          select u).Single();

            if(null==user)
            {
                //user dose not exist
                Console.Out.Write("user not exist");
                return;
            }

            if (user.locationId == null)
            {
                //create new location record
                Location newloc = new Location() {  userid=user.userid,
                                                    
                                                    latitude = Convert.ToDouble(vals[1]), 
                                                    longtitude = Convert.ToDouble(vals[2]), 
                                                    lastupdate=DateTime.Now};
                try
                {
                    context.Locations.InsertOnSubmit(newloc);
                    context.SubmitChanges();
                    user.locationId = newloc.locationId;
                    context.SubmitChanges();
                }
                catch (ChangeConflictException e) {
                    Console.Out.Write(      e.Message+"\n");
                }
              
            }
            else 
            { 
                //update location record
                Location loc = (from l in context.Locations
                                where l.locationId == user.locationId
                                select l).Single();
                if (null == loc)
                {
                    Console.Out.Write("Wrong input format " + base.param.input + ". correct is 'loc userid.latitude.longtitude'");
                    return;
                }

                try
                {
                   
                    loc.latitude = Convert.ToDouble(vals[1]);
                    loc.longtitude = Convert.ToDouble(vals[2]);
                    loc.lastupdate = DateTime.Now;
                    context.Refresh(System.Data.Linq.RefreshMode.KeepChanges, loc);
                    context.SubmitChanges();
                    context.Dispose();
                }
                catch (ChangeConflictException e)
                {  }

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
