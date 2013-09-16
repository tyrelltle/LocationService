using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.Geo
{
    class LatLng
    {
        public double latitude;
        public double longitude;

        public LatLng(double la, double lon)
        {
            latitude = la;
            longitude = lon;
        }
    }
}
