package com.idega.block.albumcollection.data;

import com.idega.data.*;
import java.sql.SQLException;
import java.sql.Date;


/**
 * Title:        AlbumCollection
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega.is
 * @author <a href="mailto:gummi@idega.is">Gu�mundur �g�st S�mundsson</a>
 * @version 1.0
 */

public class AlbumBMPBean extends com.idega.data.GenericEntity implements com.idega.block.albumcollection.data.Album {

  public static final String _COLUMNNAME_NAME = "name";
  public static final String _COLUMNNAME_DESCRIPTION = "description";
  public static final String _COLUMNNAME_NUMBER = "number";
  public static final String _COLUMNNAME_PUBLISHINGDAY = "publishingday";
  public static final String _COLUMNNAME_COVER_FRONT = "cover_front";
  public static final String _COLUMNNAME_COVER_BACK = "cover_back";
  public static final String _COLUMNNAME_ALBUM_TYPE_ID = "album_type_id";

  public AlbumBMPBean() {
    super();
  }

  public AlbumBMPBean(int id) throws SQLException {
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(this.getIDColumnName());
    this.addAttribute(_COLUMNNAME_NAME,"Nafn",true,true,String.class,255);
    this.addAttribute(_COLUMNNAME_DESCRIPTION,"Um pl�tuna",true,true,String.class,1000);
    this.addAttribute(_COLUMNNAME_NUMBER,"N�mer",true,true,Integer.class);
    this.addAttribute(_COLUMNNAME_PUBLISHINGDAY,"�tg�fudagur",true,true,java.sql.Date.class);
    this.addAttribute(_COLUMNNAME_ALBUM_TYPE_ID,"Ger�",true,true,Integer.class,"one_to_many",AlbumType.class);
    this.addAttribute(_COLUMNNAME_COVER_FRONT,"Framhli� umslags",true,true,Integer.class,"one_to_many",com.idega.core.data.ICFile.class);
    this.addAttribute(_COLUMNNAME_COVER_BACK,"Bakhli� umslags",true,true,Integer.class,"one_to_many",com.idega.core.data.ICFile.class);

    this.setNullable(_COLUMNNAME_COVER_FRONT,true);
    this.setNullable(_COLUMNNAME_COVER_BACK,true);

    this.addTreeRelationShip();
    this.addManyToManyRelationShip(Category.class,"ac_album_category");
    this.addManyToManyRelationShip(Author.class,"ac_album_author");
    this.addManyToManyRelationShip(Performer.class, "ac_album_performer");
    //this.addManyToManyRelationShip(com.idega.core.data.ICFile.class,"ac_album_ic_file");
  }

  public String getEntityName() {
    return "ac_album";
  }

  public String getName(){
    return getStringColumnValue(_COLUMNNAME_NAME);
  }

  public void setName(String name){
    setColumn(_COLUMNNAME_NAME, name);
  }

  public String getDescription(){
    return (String) getColumnValue(_COLUMNNAME_DESCRIPTION);
  }

  public void setDescription(String description){
    setColumn(_COLUMNNAME_DESCRIPTION,description);
  }


  public String getOrder(){
    return getStringColumnValue(_COLUMNNAME_NUMBER);
  }

  public void setOrder(String order){
    setColumn(_COLUMNNAME_NUMBER, order);
  }

  public Date getPublishingDay(){
    return (Date)this.getColumnValue(_COLUMNNAME_PUBLISHINGDAY);
  }

  public void setPublishingDay(Date date){
    this.setColumn(_COLUMNNAME_PUBLISHINGDAY,date);
  }

  public int getAlbumTypeId(){
    return this.getIntColumnValue(_COLUMNNAME_ALBUM_TYPE_ID);
  }

  public void setAlbumTypeId(int id){
    this.setColumn(_COLUMNNAME_ALBUM_TYPE_ID,id);
  }

  public void setAlbumTypeId(Integer id){
    this.setColumn(_COLUMNNAME_ALBUM_TYPE_ID,id);
  }



  public int getFrontCoverFileId(){
    return getIntColumnValue(_COLUMNNAME_COVER_FRONT);
  }

  public void setFrontCoverFileId(int id){
    this.setColumn(_COLUMNNAME_COVER_FRONT,id);
  }

  public void setFrontCoverFileIdAsNull() throws SQLException{
    this.setColumnAsNull(_COLUMNNAME_COVER_FRONT);
  }

  public int getBackCoverFileId(){
    return getIntColumnValue(_COLUMNNAME_COVER_BACK);
  }

  public void setBackCoverFileId(int id){
    this.setColumn(_COLUMNNAME_COVER_BACK,id);
  }

  public void setBackCoverFileIdAsNull() throws SQLException{
    this.setColumnAsNull(_COLUMNNAME_COVER_BACK);
  }

}
