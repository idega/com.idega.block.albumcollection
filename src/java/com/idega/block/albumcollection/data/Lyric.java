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

public class Lyric extends GenericEntity {

  public static final String _COLUMNNAME_NAME = "name";
  public static final String _COLUMNNAME_LYRIC = "lyric";

 public Lyric() {
    super();
  }

  public Lyric(int id) throws SQLException {
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(this.getIDColumnName());
    this.addAttribute(_COLUMNNAME_NAME,"Nafn",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_LYRIC,"S�ngtexti",true,true,String.class,4000);

    this.addManyToManyRelationShip(Author.class,"ac_lyric_author");
  }

  public String getEntityName() {
    return "ac_lyric";
  }


  public String getName(){
    return getStringColumnValue(_COLUMNNAME_NAME);
  }

  public void setName(String name){
    setColumn(_COLUMNNAME_NAME, name);
  }


  public String getLyric(){
    return getStringColumnValue(_COLUMNNAME_LYRIC);
  }

  public void setLyric(String lyric){
    setColumn(_COLUMNNAME_LYRIC, lyric);
  }


}