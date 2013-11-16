using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "FriendService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select FriendService.svc or FriendService.svc.cs at the Solution Explorer and start debugging.
    public class FriendService : IFriendService
    {

        public  const string FRIEND_CONNECTED="Friend Successfully Connected";//database has both (uid,fid) and (fid,uid) pair
        public  const string PENDING_REQUEST = "Friend Connection Pending To Be Validated";//database has only (uid,fid)

        public void DoWork()
        {
        }

        public FriendList getfriends(int uid)
        {
            FriendList ret = new FriendList();
            DataClassesDataContext context=new DataClassesDataContext();
            var baselist = (from f in context.Friends select new {a=f.uid,b=f.fuid } ).Intersect(
                            from f in context.Friends select new {a=f.fuid,b=f.uid });

            var finalist = from i in baselist
                           join u in context.Users
                           on i.b equals u.userid
                           where i.a == uid
                           select u;

            foreach (var a in finalist)
                ret.add(new FriendData { Name=a.username,Uid=a.userid });
          return ret;
          
        }

        public ReturnStatus addFriendById(int uid, int touid) {
            LinkedList<FriendData> ret = new LinkedList<FriendData>();
            
            DataClassesDataContext context = new DataClassesDataContext();

            Boolean exist = this.recordexists(context, uid, touid);
            if (!exist) {
                Friend newf = new Friend { uid = uid, fuid = touid };
                context.Friends.InsertOnSubmit(newf);
                context.SubmitChanges();
            
            }


            return this.getReturn(context, uid, touid);


        }


        public ReturnStatus addFriendByEmail(int uid, string toemail)
        {
            LinkedList<FriendData> ret = new LinkedList<FriendData>();

            DataClassesDataContext context = new DataClassesDataContext();
            int touid = this.getuid(context, toemail);
            Boolean exists = this.recordexists(context, uid, touid);
            if (!exists)
            {
                Friend newf = new Friend { uid = uid, fuid = touid };
                context.Friends.InsertOnSubmit(newf);
                context.SubmitChanges();

            }

            return this.getReturn(context, uid, touid);


        }

        private int getuid(DataClassesDataContext c, string email)
        {
            int uid = (from i in c.Users
                       where i.email == email
                       select i.userid).First();
            return uid;
        }
        private Boolean recordexists(DataClassesDataContext c, int uid, int touid)
        {
            return  c.Friends.Where(a => a.uid == uid && a.fuid == touid).Any();        
        }

        private ReturnStatus getReturn(DataClassesDataContext context,int uid, int touid) 
        {
            //now lets find out if your new friend already added u as friend
            var tmp = (from i in context.Friends
                       where i.fuid == uid && i.uid == touid
                       select i);
            switch (tmp.Count())
            {
                case 0:  //your new friend has not added u as friend yet 
                    return new ReturnStatus { msg = PENDING_REQUEST, statuscode = 0 };
                    break;

                default: return new ReturnStatus { msg = FRIEND_CONNECTED, statuscode = 1 };


            }
        }
    }
}
