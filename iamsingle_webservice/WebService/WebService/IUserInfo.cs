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

        [OperationContract]
        [WebInvoke(UriTemplate = "getuserinfofull", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json, Method = "POST")]
        UserInfo getuserinfofull(SecureUserInfoParam input);
    }

    [DataContract]
    public class SecureUserInfoParam
    {
        private int mid;
        private string mmail;
        private string mpsw;

        [DataMember]
        public int id
        {  set { mid = value; }
            get { return mid; } 
        }

        [DataMember]
        public string email
        {
            set { mmail = value; }
            get { return mmail; }
        }

        [DataMember]
        public string password
        {
            set { mpsw = value; }
            get { return mpsw; }
        }
    }

    [DataContract]
    public class UserInfo
    {
        protected string mMail;
        protected string mUname;
        protected string mUdesc;
        protected string mUhobby;
        private string mPsw;

        private Action action;
        //need action=post to make secure fields enabled.  ex, password
        public enum Action{ GET,POST}


        public UserInfo()
        {
            action = Action.GET;
        }
        public UserInfo(Action a)
        {
            action = a;
        }
            

        private static string valueOnAction(string val, Action action)
        {
            switch (action) { 
                case Action.GET:
                    return "";
                case Action.POST:
                    return val;
                default: return "";
            }
        }
        [DataMember]
        public string username
        {
            get { return mUname; }
            set { mUname = value; }
        }
        
        [DataMember]
        public string email
        {
            get { return UserInfo.valueOnAction(mMail,action); }
            set { mMail = value; }
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

         [DataMember]
        public string password
        {
            get { return UserInfo.valueOnAction(mPsw,action); }
            set { mPsw = value; }
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
