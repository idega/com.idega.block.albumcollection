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

public class Author extends GenericEntity {

  public static final String _COLUMNNAME_DISPLAY_NAME = "display_name";
  public static final String _COLUMNNAME_NAME = "name";

  public Author() {
    super();
  }

  public Author(int id) throws SQLException {
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(this.getIDColumnName());
    this.addAttribute(_COLUMNNAME_DISPLAY_NAME,"Nafn � vef",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_NAME,"Nafn",true,true,String.class,255);

    this.addManyToManyRelationShip(Album.class,"ac_album_author");
    this.addManyToManyRelationShip(Track.class,"ac_track_author");
    this.addManyToManyRelationShip(Lyric.class,"ac_lyric_author");
  }

  public String getEntityName() {
    return "ac_author";
  }

  public String getName(){
    return getStringColumnValue(_COLUMNNAME_NAME);
  }

  public void setName(String name){
    setColumn(_COLUMNNAME_NAME, name);
  }

  public String getDisplayName(){
    return getStringColumnValue(_COLUMNNAME_DISPLAY_NAME);
  }

  public void setDisplayName(String displayName){
    setColumn(_COLUMNNAME_DISPLAY_NAME, displayName);
  }


}