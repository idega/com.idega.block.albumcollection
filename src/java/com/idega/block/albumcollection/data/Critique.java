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

public class Critique extends GenericEntity {

  public static final String _COLUMNNAME_TRACK_ID = "track_id";
  public static final String _COLUMNNAME_ALBUM_ID = "album_id";
  public static final String _COLUMNNAME_CRITIC_ID = "critic_id";
  public static final String _COLUMNNAME_HEADLINE = "headline";
  public static final String _COLUMNNAME_CRITIQUE = "critique";
  public static final String _COLUMNNAME_RECORD_TIME = "record_time";


  public Critique() {
    super();
  }

  public Critique(int id) throws SQLException {
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(this.getIDColumnName());
    this.addAttribute(_COLUMNNAME_TRACK_ID,"Lag",true,true,Integer.class,"one_to_many",Track.class);
    this.addAttribute(_COLUMNNAME_ALBUM_ID,"Hlj�mplata",true,true,Integer.class,"one_to_many",Album.class);
    this.addAttribute(_COLUMNNAME_CRITIC_ID,"Gagnr�nandi",true,true,Integer.class,"one_to_many",Critic.class);
    this.addAttribute(_COLUMNNAME_HEADLINE,"Fyrirs�gn",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_CRITIQUE,"Gagnr�ni",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_RECORD_TIME,"T�mi",true,true,java.sql.Timestamp.class);
  }

  public String getEntityName() {
    return "ac_critique";
  }
}