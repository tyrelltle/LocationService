/*
 Each client should register to this server as a message reciever, which
 is then stored within this collection.
 Whenever other clients send messages to it, server will query
 this collection the find the target reciever client, then forward the msg to it.
 
 */

using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace SocketServer.Utils
{


   
    public class TCPRecieverCollection
    {




        /*for singleton*/
        private static TCPRecieverCollection mInstance = null;

        /*the list storing the clients*/
        private Dictionary<int, NetworkStream> mLis = null;
        



        /*for singleton*/
        public static TCPRecieverCollection instance()
        {
            if (mInstance == null)
                mInstance = new TCPRecieverCollection();
            return mInstance;
        }

        private TCPRecieverCollection()
        {
            mLis = new Dictionary<int, NetworkStream>();
        }

        
        
        public int size()
        {
            return mLis.Count();
        }

        public void add(int uid, NetworkStream r)
        {
            if (mLis.ContainsKey(uid))
                this.removeByUid(uid);
            mLis.Add(uid, r); 
        
        }

        public NetworkStream getByUid(int uid)
        {

            NetworkStream rcv;
                try
                {
                    rcv = mLis[uid];
                    return rcv;
                }
                catch {
                    return null;
                }
        }
        [MethodImpl(MethodImplOptions.Synchronized)]
        public void removeByUid(int uid)
        {
            try
            {
                mLis.Remove(uid);
            }
            catch { }
        }

    }
}
