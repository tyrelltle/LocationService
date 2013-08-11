using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IResources" in both code and config file together.
    [ServiceContract]

    public interface IUserInfo
    {
        [OperationContract]
        [WebInvoke(UriTemplate = "test", Method = "GET")]
        string test();

        [OperationContract]
        [WebInvoke(UriTemplate = "getUserIcon?uid={input}", Method = "GET")]
        Stream getUserIcon(int input);

        [OperationContract]
        [WebInvoke(UriTemplate = "setUserIcon?uid={uid}", Method = "POST")]
        void setUserIcon(int uid, Stream file);

        [OperationContract]
        [WebInvoke(UriTemplate = "getuserinfo?uid={uid}", ResponseFormat = WebMessageFormat.Json, RequestFormat=WebMessageFormat.Json, Method = "GET")]
        UserInfo getuserinfo(int uid);

    }


    [DataContract]
    public class UserInfo
    {
        private string mUname;
        private string mUdesc;
        private string mUhobby;
        [DataMember]
        public string username
        {
            get { return mUname; }
            set { mUname = value; }
        }
         [DataMember]
        public string userdescription
        {
            get { return mUdesc; }
            set { mUdesc = value; }
        }
         [DataMember]
        public string userhobby
        {
            get { return mUhobby; }
            set { mUhobby = value; }
        }
    }


    [DataContract]
    public class UserIconParam
    {
        private Stream mfile;
        private int mUid;
        [DataMember]
        public int userid
        {
            get { return mUid; }
            set { mUid = value; }
        }

        public Stream file {
            get { return mfile; }
            set { mfile = value; }
        }
    }

}
