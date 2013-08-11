using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace WebService.WebServices
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IUserAuth" in both code and config file together.
    [ServiceContract]
    public interface IUserAuth
    {
        [OperationContract]
        [WebInvoke(UriTemplate = "test", Method = "GET")]
        string test();

        [OperationContract]
        [WebInvoke(UriTemplate = "register", RequestFormat=WebMessageFormat.Json,ResponseFormat=WebMessageFormat.Json, Method = "PUT")]
        UserIdsingle register(UserInfo user);

        [OperationContract]
        [WebInvoke(UriTemplate = "logon", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, Method = "POST")]
        UserIdsingle logon(UserInfo user);

    }








    [DataContract]
    public class UserIdsingle
    {
        private int mUid;
        [DataMember]
        public int userid { 
            get{return mUid;}
            set{mUid=value;}
        }
    }


    [DataContract]
    public class UserInfo
    {
        private string mEmail;
        private string mPassword;
        // Apply the DataMemberAttribute to the property.
        [DataMember]
        public string email
        {
            get { return mEmail; }
            set { mEmail = value; }
        }
        [DataMember]
        public string password
        {
            get { return mPassword; }
            set { mPassword = value; }
        }
    }
}
