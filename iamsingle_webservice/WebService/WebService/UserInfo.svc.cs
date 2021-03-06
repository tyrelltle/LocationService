﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using AntsCode.Util;
namespace WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Resources" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Resources.svc or Resources.svc.cs at the Solution Explorer and start debugging.
    public class UserInfoService : IUserInfo
    {
        public string test()
        {
            return "hello world";
        }

        public UserIP getuserip(int uid)
        {
            DataClassesDataContext context = new DataClassesDataContext();
            var a = (from i in context.Locations
                    where i.userid == uid
                    select i).First();
            return new UserIP{ip=a.}

        
        }
        public UserInfo getuserinfofull(SecureUserInfoParam input)
        {
            UserInfo ret = new UserInfo(UserInfo.Action.POST);
            DataClassesDataContext context = new DataClassesDataContext();

            try
            {
                var user = (from u in context.Users
                            where u.userid == input.id &&
                                  u.password == input.password &&
                                  u.email == input.email
                            select u).First();
                if (user == null)
                    return null;

                ret.email = user.email;
                ret.userdescription = user.desc;
                ret.userhobby = user.hobby;
                ret.password = user.password;
                ret.username = user.username;

                return ret;


            }
            catch { return null; }
            return null;
        
        }


        public UserInfo getuserinfo(int uid)
        {
            try
            {

                DataClassesDataContext context = new DataClassesDataContext();
                var user = (from u in context.Users
                            where u.userid == uid
                            select u).First();
                
             
                return new UserInfo {userdescription=user.desc, 
                                     userhobby=user.hobby, 
                                     username=user.username};
                
            }
            catch { }
            return null;
        }

        public Stream getUserIcon(int input)
        {
            WebOperationContext.Current.OutgoingResponse.ContentType="image/jpeg";

          

            DataClassesDataContext context = new DataClassesDataContext();
            MemoryStream ret = new MemoryStream();
            try
            {
                User user = (from u in context.Users
                             where u.userid == input
                             select u).First();
                if (user == null)
                    return null;
                byte[] bytes = user.icon.ToArray();
                ret.Write(bytes, 0, bytes.Length);
                ret.Position = 0;
                return ret;
            }
            catch (Exception e)
            {
                Console.Out.WriteLine(e.Message);
            }

            return null;


             
        
        }

        public void setUserIcon(int uid, Stream file)
        {

            

            DataClassesDataContext context = new DataClassesDataContext();

            
                User user = (from u in context.Users
                             where u.userid == uid
                             select u).First();
                if (user == null)
                    return;

                MultipartParser parser = new MultipartParser(file);
                if (parser.Success)
                {
                    user.icon = parser.FileContents;

                    context.SubmitChanges();
                }
        }
    }
}
