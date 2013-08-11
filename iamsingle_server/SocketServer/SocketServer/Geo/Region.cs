using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.Geo
{
    class Region
    {
        public LatLng northeast,southwest=null;
        public Region(LatLng ne, LatLng sw)
        {
            northeast = ne;
            southwest = sw;
       
        }

        public bool contains(LatLng loc)
        {
            return contains(loc.latitude, loc.longitude);
        }

        public bool contains(double lat, double lng)
        {
            /*       -------------------
             *       |                  |
             *       |                  |
             *       |      *           |                    
             *       |                  |                         
             *       |                  |                      
             *       -------------------*/
     
            bool left= (southwest.latitude <=lat) && (lat<= northeast.latitude);
            bool right = false;
            if (southwest.longitude <= northeast.longitude)
                right = southwest.longitude <= lng && lng <= northeast.longitude;
            else
                right = southwest.longitude <= lng || lng <= northeast.longitude;


            return left&&right;
        }
              
    }
}
