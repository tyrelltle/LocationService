﻿#pragma warning disable 1591
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.17929
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WebService
{
	using System.Data.Linq;
	using System.Data.Linq.Mapping;
	using System.Data;
	using System.Collections.Generic;
	using System.Reflection;
	using System.Linq;
	using System.Linq.Expressions;
	using System.ComponentModel;
	using System;
	
	
	[global::System.Data.Linq.Mapping.DatabaseAttribute(Name="LocationService")]
	public partial class DataClassesDataContext : System.Data.Linq.DataContext
	{
		
		private static System.Data.Linq.Mapping.MappingSource mappingSource = new AttributeMappingSource();
		
    #region Extensibility Method Definitions
    partial void OnCreated();
    partial void InsertLocation(Location instance);
    partial void UpdateLocation(Location instance);
    partial void DeleteLocation(Location instance);
    partial void InsertUser(User instance);
    partial void UpdateUser(User instance);
    partial void DeleteUser(User instance);
    #endregion
		
		public DataClassesDataContext() : 
				base(global::System.Configuration.ConfigurationManager.ConnectionStrings["LocationServiceConnectionString"].ConnectionString, mappingSource)
		{
			OnCreated();
		}
		
		public DataClassesDataContext(string connection) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public DataClassesDataContext(System.Data.IDbConnection connection) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public DataClassesDataContext(string connection, System.Data.Linq.Mapping.MappingSource mappingSource) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public DataClassesDataContext(System.Data.IDbConnection connection, System.Data.Linq.Mapping.MappingSource mappingSource) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public System.Data.Linq.Table<Location> Locations
		{
			get
			{
				return this.GetTable<Location>();
			}
		}
		
		public System.Data.Linq.Table<User> Users
		{
			get
			{
				return this.GetTable<User>();
			}
		}
		
		[global::System.Data.Linq.Mapping.FunctionAttribute(Name="dbo.InBoundBox")]
		public ISingleResult<InBoundBoxResult> InBoundBox([global::System.Data.Linq.Mapping.ParameterAttribute(DbType="Float")] System.Nullable<double> northEastLat, [global::System.Data.Linq.Mapping.ParameterAttribute(DbType="Float")] System.Nullable<double> northEastLng, [global::System.Data.Linq.Mapping.ParameterAttribute(DbType="Float")] System.Nullable<double> southWestLat, [global::System.Data.Linq.Mapping.ParameterAttribute(DbType="Float")] System.Nullable<double> southWestLng)
		{
			IExecuteResult result = this.ExecuteMethodCall(this, ((MethodInfo)(MethodInfo.GetCurrentMethod())), northEastLat, northEastLng, southWestLat, southWestLng);
			return ((ISingleResult<InBoundBoxResult>)(result.ReturnValue));
		}
	}
	
	[global::System.Data.Linq.Mapping.TableAttribute(Name="dbo.Location")]
	public partial class Location : INotifyPropertyChanging, INotifyPropertyChanged
	{
		
		private static PropertyChangingEventArgs emptyChangingEventArgs = new PropertyChangingEventArgs(String.Empty);
		
		private int _locationId;
		
		private int _userid;
		
		private System.Nullable<double> _altitude;
		
		private System.Nullable<double> _latitude;
		
		private System.Nullable<double> _longtitude;
		
		private System.Nullable<System.DateTime> _lastupdate;
		
    #region Extensibility Method Definitions
    partial void OnLoaded();
    partial void OnValidate(System.Data.Linq.ChangeAction action);
    partial void OnCreated();
    partial void OnlocationIdChanging(int value);
    partial void OnlocationIdChanged();
    partial void OnuseridChanging(int value);
    partial void OnuseridChanged();
    partial void OnaltitudeChanging(System.Nullable<double> value);
    partial void OnaltitudeChanged();
    partial void OnlatitudeChanging(System.Nullable<double> value);
    partial void OnlatitudeChanged();
    partial void OnlongtitudeChanging(System.Nullable<double> value);
    partial void OnlongtitudeChanged();
    partial void OnlastupdateChanging(System.Nullable<System.DateTime> value);
    partial void OnlastupdateChanged();
    #endregion
		
		public Location()
		{
			OnCreated();
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_locationId", AutoSync=AutoSync.OnInsert, DbType="Int NOT NULL IDENTITY", IsPrimaryKey=true, IsDbGenerated=true)]
		public int locationId
		{
			get
			{
				return this._locationId;
			}
			set
			{
				if ((this._locationId != value))
				{
					this.OnlocationIdChanging(value);
					this.SendPropertyChanging();
					this._locationId = value;
					this.SendPropertyChanged("locationId");
					this.OnlocationIdChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_userid", DbType="Int NOT NULL")]
		public int userid
		{
			get
			{
				return this._userid;
			}
			set
			{
				if ((this._userid != value))
				{
					this.OnuseridChanging(value);
					this.SendPropertyChanging();
					this._userid = value;
					this.SendPropertyChanged("userid");
					this.OnuseridChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_altitude", DbType="Float")]
		public System.Nullable<double> altitude
		{
			get
			{
				return this._altitude;
			}
			set
			{
				if ((this._altitude != value))
				{
					this.OnaltitudeChanging(value);
					this.SendPropertyChanging();
					this._altitude = value;
					this.SendPropertyChanged("altitude");
					this.OnaltitudeChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_latitude", DbType="Float")]
		public System.Nullable<double> latitude
		{
			get
			{
				return this._latitude;
			}
			set
			{
				if ((this._latitude != value))
				{
					this.OnlatitudeChanging(value);
					this.SendPropertyChanging();
					this._latitude = value;
					this.SendPropertyChanged("latitude");
					this.OnlatitudeChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_longtitude", DbType="Float")]
		public System.Nullable<double> longtitude
		{
			get
			{
				return this._longtitude;
			}
			set
			{
				if ((this._longtitude != value))
				{
					this.OnlongtitudeChanging(value);
					this.SendPropertyChanging();
					this._longtitude = value;
					this.SendPropertyChanged("longtitude");
					this.OnlongtitudeChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_lastupdate", DbType="DateTime")]
		public System.Nullable<System.DateTime> lastupdate
		{
			get
			{
				return this._lastupdate;
			}
			set
			{
				if ((this._lastupdate != value))
				{
					this.OnlastupdateChanging(value);
					this.SendPropertyChanging();
					this._lastupdate = value;
					this.SendPropertyChanged("lastupdate");
					this.OnlastupdateChanged();
				}
			}
		}
		
		public event PropertyChangingEventHandler PropertyChanging;
		
		public event PropertyChangedEventHandler PropertyChanged;
		
		protected virtual void SendPropertyChanging()
		{
			if ((this.PropertyChanging != null))
			{
				this.PropertyChanging(this, emptyChangingEventArgs);
			}
		}
		
		protected virtual void SendPropertyChanged(String propertyName)
		{
			if ((this.PropertyChanged != null))
			{
				this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
			}
		}
	}
	
	[global::System.Data.Linq.Mapping.TableAttribute(Name="dbo.[User]")]
	public partial class User : INotifyPropertyChanging, INotifyPropertyChanged
	{
		
		private static PropertyChangingEventArgs emptyChangingEventArgs = new PropertyChangingEventArgs(String.Empty);
		
		private int _userid;
		
		private string _username;
		
		private string _email;
		
		private System.Nullable<int> _locationId;
		
		private string _ip;
		
		private string _password;
		
		private System.Data.Linq.Binary _icon;
		
		private string _hobby;
		
		private string _desc;
		
    #region Extensibility Method Definitions
    partial void OnLoaded();
    partial void OnValidate(System.Data.Linq.ChangeAction action);
    partial void OnCreated();
    partial void OnuseridChanging(int value);
    partial void OnuseridChanged();
    partial void OnusernameChanging(string value);
    partial void OnusernameChanged();
    partial void OnemailChanging(string value);
    partial void OnemailChanged();
    partial void OnlocationIdChanging(System.Nullable<int> value);
    partial void OnlocationIdChanged();
    partial void OnipChanging(string value);
    partial void OnipChanged();
    partial void OnpasswordChanging(string value);
    partial void OnpasswordChanged();
    partial void OniconChanging(System.Data.Linq.Binary value);
    partial void OniconChanged();
    partial void OnhobbyChanging(string value);
    partial void OnhobbyChanged();
    partial void OndescChanging(string value);
    partial void OndescChanged();
    #endregion
		
		public User()
		{
			OnCreated();
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_userid", AutoSync=AutoSync.OnInsert, DbType="Int NOT NULL IDENTITY", IsPrimaryKey=true, IsDbGenerated=true)]
		public int userid
		{
			get
			{
				return this._userid;
			}
			set
			{
				if ((this._userid != value))
				{
					this.OnuseridChanging(value);
					this.SendPropertyChanging();
					this._userid = value;
					this.SendPropertyChanged("userid");
					this.OnuseridChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_username", DbType="VarChar(MAX)")]
		public string username
		{
			get
			{
				return this._username;
			}
			set
			{
				if ((this._username != value))
				{
					this.OnusernameChanging(value);
					this.SendPropertyChanging();
					this._username = value;
					this.SendPropertyChanged("username");
					this.OnusernameChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_email", DbType="VarChar(MAX)")]
		public string email
		{
			get
			{
				return this._email;
			}
			set
			{
				if ((this._email != value))
				{
					this.OnemailChanging(value);
					this.SendPropertyChanging();
					this._email = value;
					this.SendPropertyChanged("email");
					this.OnemailChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_locationId", DbType="Int")]
		public System.Nullable<int> locationId
		{
			get
			{
				return this._locationId;
			}
			set
			{
				if ((this._locationId != value))
				{
					this.OnlocationIdChanging(value);
					this.SendPropertyChanging();
					this._locationId = value;
					this.SendPropertyChanged("locationId");
					this.OnlocationIdChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_ip", DbType="VarChar(MAX)")]
		public string ip
		{
			get
			{
				return this._ip;
			}
			set
			{
				if ((this._ip != value))
				{
					this.OnipChanging(value);
					this.SendPropertyChanging();
					this._ip = value;
					this.SendPropertyChanged("ip");
					this.OnipChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_password", DbType="NVarChar(MAX)")]
		public string password
		{
			get
			{
				return this._password;
			}
			set
			{
				if ((this._password != value))
				{
					this.OnpasswordChanging(value);
					this.SendPropertyChanging();
					this._password = value;
					this.SendPropertyChanged("password");
					this.OnpasswordChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_icon", DbType="VarBinary(MAX)", UpdateCheck=UpdateCheck.Never)]
		public System.Data.Linq.Binary icon
		{
			get
			{
				return this._icon;
			}
			set
			{
				if ((this._icon != value))
				{
					this.OniconChanging(value);
					this.SendPropertyChanging();
					this._icon = value;
					this.SendPropertyChanged("icon");
					this.OniconChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_hobby", DbType="VarChar(MAX)")]
		public string hobby
		{
			get
			{
				return this._hobby;
			}
			set
			{
				if ((this._hobby != value))
				{
					this.OnhobbyChanging(value);
					this.SendPropertyChanging();
					this._hobby = value;
					this.SendPropertyChanged("hobby");
					this.OnhobbyChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Name="[desc]", Storage="_desc", DbType="VarChar(MAX)")]
		public string desc
		{
			get
			{
				return this._desc;
			}
			set
			{
				if ((this._desc != value))
				{
					this.OndescChanging(value);
					this.SendPropertyChanging();
					this._desc = value;
					this.SendPropertyChanged("desc");
					this.OndescChanged();
				}
			}
		}
		
		public event PropertyChangingEventHandler PropertyChanging;
		
		public event PropertyChangedEventHandler PropertyChanged;
		
		protected virtual void SendPropertyChanging()
		{
			if ((this.PropertyChanging != null))
			{
				this.PropertyChanging(this, emptyChangingEventArgs);
			}
		}
		
		protected virtual void SendPropertyChanged(String propertyName)
		{
			if ((this.PropertyChanged != null))
			{
				this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
			}
		}
	}
	
	public partial class InBoundBoxResult
	{
		
		private string _username;
		
		private int _userid;
		
		private System.Nullable<double> _latitude;
		
		private System.Nullable<double> _longtitude;
		
		public InBoundBoxResult()
		{
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_username", DbType="VarChar(MAX)")]
		public string username
		{
			get
			{
				return this._username;
			}
			set
			{
				if ((this._username != value))
				{
					this._username = value;
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_userid", DbType="Int NOT NULL")]
		public int userid
		{
			get
			{
				return this._userid;
			}
			set
			{
				if ((this._userid != value))
				{
					this._userid = value;
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_latitude", DbType="Float")]
		public System.Nullable<double> latitude
		{
			get
			{
				return this._latitude;
			}
			set
			{
				if ((this._latitude != value))
				{
					this._latitude = value;
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_longtitude", DbType="Float")]
		public System.Nullable<double> longtitude
		{
			get
			{
				return this._longtitude;
			}
			set
			{
				if ((this._longtitude != value))
				{
					this._longtitude = value;
				}
			}
		}
	}
}
#pragma warning restore 1591
