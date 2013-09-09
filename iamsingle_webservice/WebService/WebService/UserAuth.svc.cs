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
        public UserIdsingle register(UserInfo user)
        {
            DataClassesDataContext context = new DataClassesDataContext();

            if(user==null||user.email==null)
                //invalid input
                return new UserIdsingle { userid = -1 };

            Boolean has=((from u in context.Users
                            where u.email == user.email
                            select u).Count())>0;
            if (has)
                //user already exists
                return new UserIdsingle { userid = -1 };

            User newuser = new User() {  email=user.email, password=user.password};
            context.Users.InsertOnSubmit(newuser);
            context.SubmitChanges();
            return new UserIdsingle() { userid = newuser.userid };


        }




        public UserIdsingle logon(UserInfo user)
        {
            DataClassesDataContext context = new DataClassesDataContext();

            if (user == null || user.email == null)
                //invalid input
                return new UserIdsingle { userid = -1 };

            var usr = (from u in context.Users
                            where u.email == user.email && u.password==user.password
                            select u);
            if (usr==null||usr.Count()==0)
                //user not exist
                return new UserIdsingle { userid = -1 };

            return new UserIdsingle() { userid = usr.Single().userid };


        }
    }
}
