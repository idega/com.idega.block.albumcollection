package com.idega.block.albumcollection.data;

import com.idega.data.*;
import java.sql.SQLException;


/**
 * Title:        AlbumCollection
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega.is
 * @author <a href="mailto:gummi@idega.is">Gu�mundur �g�st S�mundsson</a>
 * @version 1.0
 */

public class CriticBMPBean extends com.idega.data.GenericEntity implements com.idega.block.albumcollection.data.Critic {

  public static final String _COLUMNNAME_DISPLAY_NAME = "display_name";
  public static final String _COLUMNNAME_NAME = "name";
  public static final String _COLUMNNAME_EMAIL_ADDRESS = "email_address";
  public static final String _COLUMNNAME_PHONE = "phone";
  public static final String _COLUMNNAME_ADDRESS = "address";
  public static final String _COLUMNNAME_POSTALCODE = "postalcode";
  public static final String _COLUMNNAME_PROVINCE = "city";
  public static final String _COLUMNNAME_COUNTRY = "country";

  public CriticBMPBean() {
    super();
  }

  public CriticBMPBean(int id) throws SQLException {
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(this.getIDColumnName());
    this.addAttribute(_COLUMNNAME_DISPLAY_NAME,"Nafn � vef",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_NAME,"Nafn",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_EMAIL_ADDRESS,"Netfang",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_PHONE,"S�mi",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_ADDRESS,"Heimilisfang",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_POSTALCODE,"P�stn�mer",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_PROVINCE,"Sv��i",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_COUNTRY,"Land",true,true,String.class,255);
  }

  public String getEntityName() {
    return "ac_critic";
  }

  public String getName(){
    return getStringColumnValue(_COLUMNNAME_NAME);
  }

  public void setName(String name){
    setColumn(_COLUMNNAME_NAME, name);
  }

}
