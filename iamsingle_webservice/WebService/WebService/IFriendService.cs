using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IFriendService" in both code and config file together.
    [ServiceContract]
    public interface IFriendService
    {
        [OperationContract]
        void DoWork();

        [OperationContract]
        [WebInvoke(UriTemplate = "getfriends?uid={uid}", ResponseFormat = WebMessageFormat.Json, Method = "GET")]
        FriendList getfriends(int uid);
        [OperationContract]
        [WebInvoke(UriTemplate = "addFriendById?myuid={uid}&touid={touid}", ResponseFormat = WebMessageFormat.Json, Method = "PUT")]
        ReturnStatus addFriendById(int uid, int touid);
        [OperationContract]
        [WebInvoke(UriTemplate = "addFriendByEmail?myuid={uid}&toemail={toemail}", ResponseFormat = WebMessageFormat.Json, Method = "PUT")]
        ReturnStatus addFriendByEmail(int uid, string toemail);

    }

    [DataContract]
    public class FriendData
    {
        private string mName;
        private int mUid;
        // Apply the DataMemberAttribute to the property.
        [DataMember]
        public string Name
        {
            get { return mName; }
            set { mName = value; }
        }
        [DataMember]
        public int Uid
        {
            get { return mUid; }
            set { mUid = value; }
        }
    }

    [DataContract]
    public class ReturnStatus
    {
        private int mStatusCode;//1 for success 0 for fail
        private string  mmsg;
        // Apply the DataMemberAttribute to the property.
        [DataMember]
        public int statuscode
        {
            get { return mStatusCode; }
            set { mStatusCode = value; }
        }
        [DataMember]
        public string msg
        {
            get { return mmsg; }
            set { mmsg = value; }
        }
    }
    [DataContract]
    public class FriendList
    {
        private LinkedList<FriendData> mLis = new LinkedList<FriendData>();
        public void add(FriendData s)
        {
            mLis.AddFirst(s);
        }
        [DataMember]
        public LinkedList<FriendData> Friends
        {
            get { return mLis; }
            set { mLis = value; }
        }
    
    }
}
