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

public class Track extends GenericEntity {

  public static final String _COLUMNNAME_ALBUM_ID = "album_id";
  public static final String _COLUMNNAME_NAME = "name";
  public static final String _COLUMNNAME_LENGTH = "length";
  public static final String _COLUMNNAME_LYRIC_ID = "lyric_id";

  public Track() {
    super();
  }

  public Track(int id) throws SQLException {
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(this.getIDColumnName());
    this.addAttribute(_COLUMNNAME_NAME,"Nafn",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_ALBUM_ID,"Hlj�mplata",true,true,Integer.class,"one_to_many",Album.class);
    this.addAttribute(_COLUMNNAME_LYRIC_ID,"S�ngtexti",true,true,Integer.class,"one_to_many",Lyric.class);
    this.addAttribute(_COLUMNNAME_LENGTH,"Lengd lags(sek)",true,true,Integer.class);
    this.addManyToManyRelationShip(Category.class,"ac_track_category");
    this.addManyToManyRelationShip(Author.class,"ac_track_author");
    this.addManyToManyRelationShip(Performer.class, "ac_track_performer");
  }

  public String getEntityName() {
    return "ac_track";
  }

  public String getName(){
    return getStringColumnValue(_COLUMNNAME_NAME);
  }

  public void setName(String name){
    setColumn(_COLUMNNAME_NAME, name);
  }
}