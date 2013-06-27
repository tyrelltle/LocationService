﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IsampleService" in both code and config file together.
    [ServiceContract]
    public interface IsampleService
    {
        [OperationContract]
        [WebGet]
        string DoWork();
    }
}
