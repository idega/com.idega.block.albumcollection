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

public class Category extends GenericEntity {

  public static final String _COLUMNNAME_NAME = "name";
  public static final String _COLUMNNAME_DESCRIPTION = "description";
  public static final String _COLUMNNAME_TYPE = "type";

  public Category() {
    super();
  }

  public Category(int id) throws SQLException {
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(this.getIDColumnName());
    this.addAttribute(_COLUMNNAME_NAME,"Nafn",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_DESCRIPTION,"l�sing",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_TYPE,"Ger�",true,true,String.class,255);
    this.addManyToManyRelationShip(Album.class,"ac_album_category");
    this.addManyToManyRelationShip(Track.class,"ac_track_category");
  }

  public String getEntityName() {
    return "ac_category";
  }

  public String getName(){
    return getStringColumnValue(_COLUMNNAME_NAME);
  }

  public void setName(String name){
    setColumn(_COLUMNNAME_NAME, name);
  }

}