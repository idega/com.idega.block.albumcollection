package com.idega.block.albumcollection.presentation;

import com.idega.idegaweb.presentation.IWAdminWindow;
import com.idega.presentation.Table;
import com.idega.presentation.IWContext;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.SelectionBox;
import com.idega.presentation.ui.TextInput;
import com.idega.presentation.ui.CloseButton;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.IntegerInput;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.TextArea;
import com.idega.presentation.text.Text;
import com.idega.presentation.PresentationObject;
import com.idega.block.albumcollection.data.Author;
import com.idega.block.albumcollection.data.Performer;
import com.idega.block.albumcollection.data.Lyric;
import com.idega.block.albumcollection.data.Track;
import com.idega.block.albumcollection.business.AlbumCollectionBusiness;


import java.util.List;
import java.util.Iterator;


/**
 * Title:        AlbumCollection
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega.is
 * @author <a href="mailto:gummi@idega.is">Gu�mundur �g�st S�mundsson</a>
 * @version 1.0
 */

public class InsertLyric extends IWAdminWindow {

  private Form myForm;

  private HiddenInput _fieldTrackId;
  private HiddenInput _fieldLyricId;
  private TextInput _fieldLyricName;
  private TextArea _fieldLyric;
  private TextArea _fieldDescription;
  private SelectionBox _fieldAuthors;
  private SelectionBox _fieldCategories;

  private static String _fieldNameTrackId = AlbumCollectionBusiness._PRM_TRACK_ID;
  private static String _fieldNameLyricId = AlbumCollectionBusiness._PRM_LYRIC_ID;
  private static String _fieldNameLyricName = "ac_track_name";
  private static String _fieldNameLyric = "ac_lyric";
  private static String _fieldNameDescription = "ac_lyric_description";
  private static String _fieldNameAuthors = "ac_authors";
  private static String _fieldNameCategories = "ac_categories";


  public InsertLyric() {
    super();
    this.setHeight(450);
    this.setScrollbar(false);

    myForm = new Form();
  }

  public void initFields(IWContext iwc) throws Exception{

    String lyrId = iwc.getParameter(AlbumCollectionBusiness._PRM_LYRIC_ID);
    Lyric lyric = null;
    if(lyrId != null){
      //update
      if(lyrId != null && !lyrId.equals("")){
        _fieldLyricId = new HiddenInput(_fieldNameLyricId,lyrId);
        if(iwc.getParameter(AlbumCollectionBusiness._PRM_UPDATE) != null){
          lyric = new Lyric(Integer.parseInt(lyrId));
        }
      }
      myForm.add(_fieldLyricId);
      _fieldLyricId.keepStatusOnAction();
    }

    String str = iwc.getParameter(_fieldNameTrackId);
    if(str != null && !str.equals("")){
      _fieldTrackId = new HiddenInput(this._fieldNameTrackId,str);
    }else{
      _fieldTrackId = new HiddenInput(this._fieldNameTrackId);
      _fieldTrackId.setContent("");
    }
    _fieldTrackId.keepStatusOnAction();


    _fieldLyricName = new TextInput(_fieldNameLyricName);
    if(lyric != null){
      _fieldLyricName.setContent(lyric.getName());
    } else if(iwc.getParameter(_fieldNameLyricName)==null){
      try {
        _fieldLyricName.setContent(new Track(Integer.parseInt(iwc.getParameter(_fieldNameTrackId))).getName());
      }
      catch (Exception ex) {
        // do nothing, can be normal if lyric does not link to any track
      }
    }
    _fieldLyricName.keepStatusOnAction();


    _fieldLyric = new TextArea(this._fieldNameLyric);
    _fieldLyric.setHeight(10);
    _fieldLyric.setWidth(42);
    _fieldLyric.setWrap(false);
    if(lyric != null){
      _fieldLyric.setContent(lyric.getLyric());
    }
    _fieldLyric.keepStatusOnAction();

    _fieldDescription = new TextArea(this._fieldNameDescription);
    _fieldDescription.setHeight(6);
    _fieldDescription.setWidth(26);
    if(lyric != null){
      _fieldDescription.setContent(lyric.getDescription());
    }
    _fieldDescription.keepStatusOnAction();

    _fieldAuthors = new SelectionBox(_fieldNameAuthors);
    _fieldAuthors.setHeight(6);
    List authorList = AlbumCollectionBusiness.getAuthors();
    if(authorList != null){
      Iterator iter = authorList.iterator();
      while (iter.hasNext()) {
        Author item = (Author)iter.next();
        this._fieldAuthors.addMenuElement(item.getID(),item.getDisplayName());
      }
    }
    if(lyric != null){
      int[] IDs = lyric.findRelatedIDs(Author.getStaticInstance(Author.class));
      for (int i = 0; i < IDs.length; i++) {
        _fieldAuthors.setSelectedElement(Integer.toString(IDs[i]));
      }
    }
    _fieldAuthors.keepStatusOnAction();

  }


  public PresentationObject getElementsOredered(IWContext iwc){
    Table contentTable = new Table();
    contentTable.setWidth("100%");
    //
    Table nameTable = new Table(2,1);
    nameTable.add(new Text("Heiti:"),1,1);
    nameTable.add(this._fieldLyricName,2,1);
    nameTable.add(this._fieldTrackId,2,1);



    contentTable.add(nameTable,1,1);

    //lyric
    Table lyricTable = new Table(1,2);
    lyricTable.setWidth("100%");
    //lyricTable.setCellpadding(0);
    //lyricTable.setCellspacing(0);
    //lyricTable.setHeight(1,"32");
    lyricTable.add(new Text("Texti"),1,1);
    lyricTable.add(this._fieldLyric,1,2);

    contentTable.add(lyricTable,1,2);

    Table t2 = new Table(2,2);
    t2.setWidth("100%");
    t2.add(new Text("H�fundar:"),1,1);
    t2.add(this._fieldAuthors,1,2);
    t2.add(new Text("Um textann:"),2,1);
    t2.add(this._fieldDescription,2,2);

    contentTable.add(t2,1,3);

    // ButtonTable
    Table bTable = new Table(2,1);
    //bTable.setCellpadding(4);
    bTable.add(new SubmitButton("  Save  ","save","true"),1,1);
    bTable.add(new CloseButton("  Close  "),2,1);

    contentTable.add(bTable,1,4);
    contentTable.setAlignment(1,4,"right");

    return contentTable;
  }

  public void saveLyric(IWContext iwc) throws Exception {

    String acLyricId = iwc.getParameter(_fieldNameLyricId);
    int lyricId = -1;
    if(acLyricId != null && !"".equals(acLyricId)){
      lyricId = Integer.parseInt(acLyricId);
    }

    String acTrackId = iwc.getParameter(_fieldNameTrackId);
    String acName = iwc.getParameter(_fieldNameLyricName);
    String acLyric = iwc.getParameter(_fieldNameLyric);
    String acDescription = iwc.getParameter(_fieldNameDescription);

    String[] acAuthors = iwc.getParameterValues(_fieldNameAuthors);


    Integer TrackId = null;
    if( acTrackId != null && !acTrackId.equals("")){
      TrackId = new Integer(acTrackId);
    }

    int[] authorIDs = null;
    if(acAuthors != null){
      authorIDs = new int[acAuthors.length];
      for (int i = 0; i < acAuthors.length; i++) {
        try {
          authorIDs[i] = Integer.parseInt(acAuthors[i]);
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }

    if(lyricId > -1){
      AlbumCollectionBusiness.updateLyric(lyricId,acName,acDescription,acLyric,authorIDs);
    } else {
      AlbumCollectionBusiness.addLyric(acName,acDescription,acLyric,TrackId,authorIDs);
    }
  }

  public void main(IWContext iwc) throws Exception {

    if(iwc.getParameter("save") == null){
      myForm.empty();
      initFields(iwc);
      this.add(myForm);
      //updateFieldStatus(iwc);
      myForm.add(getElementsOredered(iwc));
    } else {
      this.saveLyric(iwc);
      this.close();
      this.setParentToReload();
    }

  }

}