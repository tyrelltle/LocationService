using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WebService.WebServices
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "UserAuth" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select UserAuth.svc or UserAuth.svc.cs at the Solution Explorer and start debugging.
    public class UserAuth : IUserAuth
    {

        public string test()
        {
            return "success";
        
        }
        public registerReturn register(UserInfo user)
        {
            DataClassesDataContext context = new DataClassesDataContext();

            if(user==null||user.email==null)
                //invalid input
                return new registerReturn { userid = -1 };

            User retuser=(from u in context.Users
                            where u.email == user.email && u.password==user.password
                            select u).First();
            if (retuser==null)
                //user already exists
                return new registerReturn { userid = -1 };


            return new registerReturn() { userid = retuser.userid };


        }




        public registerReturn logon(UserInfo user)
        {
            DataClassesDataContext context = new DataClassesDataContext();

            if (user == null || user.email == null)
                //invalid input
                return new registerReturn { userid = -1 };

            var usr = (from u in context.Users
                            where u.email == user.email
                            select u);
            if (usr==null||usr.Count()==0)
                //user not exist
                return new registerReturn { userid = -1 };

            return new registerReturn() { userid = usr.Single().userid };


        }
    }
}
