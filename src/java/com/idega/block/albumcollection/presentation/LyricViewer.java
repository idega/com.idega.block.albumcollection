package com.idega.block.albumcollection.presentation;

import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.block.albumcollection.business.AlbumCollectionBusiness;
import com.idega.presentation.text.Text;
import com.idega.presentation.text.Link;
import com.idega.presentation.Table;
import com.idega.presentation.ui.BackButton;
import com.idega.block.albumcollection.data.Lyric;
import com.idega.block.albumcollection.data.Author;
import com.idega.data.EntityFinder;
import com.idega.util.text.TextSoap;

import java.util.Iterator;
import java.util.List;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="gummi@idega.is">Gu�mundur �g�st S�mundsson</a>
 * @version 1.0
 */

public class LyricViewer extends Block {

  private Text headingText;
  private Text mainText;

  public LyricViewer() {
    headingText = AlbumCollectionBusiness.getHeaderTextClone();

    mainText = AlbumCollectionBusiness.getMainTextClone();

  }

  public void main(IWContext iwc) throws Exception {

    this.empty();

    Table frameTable = new Table(1,1);
    frameTable.setCellspacing(1);
    frameTable.setCellpadding(10);
    frameTable.setColor(AlbumCollection._COLOR_BRIGHTEST);
    frameTable.setColor(1,1,AlbumCollection._COLOR_BRIGHT);
    frameTable.setWidth("550");
    frameTable.setAlignment("center");
    frameTable.setAlignment(1,1,"center");

    Table contentTable = new Table(1,6);
    contentTable.setAlignment(1,4,"right");

    frameTable.add(contentTable);

    //this.add("lyric - "+iwc.getParameter(AlbumCollectionBusiness._PRM_LYRIC_ID));
    String lyricId = iwc.getParameter(AlbumCollectionBusiness._PRM_LYRIC_ID);
    if(lyricId != null){
      Lyric lyric = AlbumCollectionBusiness.getLyric(Integer.parseInt(lyricId));
      if(lyric != null){
        Text heading = (Text)headingText.clone();
        heading.setText(lyric.getName());

        Text text = (Text)mainText.clone();
        text.setText(TextSoap.formatString(lyric.getLyric()));

        contentTable.add(heading,1,1);
        //contentTable.add(Text.getBreak());
        contentTable.add(text,1,3);

        List T_authors = EntityFinder.findRelated(lyric,Author.getStaticInstance(Author.class));
        if(T_authors != null){
          Iterator iter2 = T_authors.iterator();
          boolean f = false;
          String name2 = "";
          while (iter2.hasNext()) {
            Author T_author = (Author)iter2.next();
            if(f){
              name2 += ", ";
            }
            name2 += T_author.getDisplayName();
            f=true;
          }
          contentTable.add(AlbumCollectionBusiness.getMainTextBoldClone(name2),1,4);
        }

        if(hasEditPermission()){

          Link updateLyricLink = AlbumCollectionBusiness.getMainLinkClone("Breyta");
          updateLyricLink.setWindowToOpen(InsertLyric.class);
          updateLyricLink.addParameter(AlbumCollectionBusiness._PRM_UPDATE,"true");
          updateLyricLink.addParameter(AlbumCollectionBusiness._PRM_LYRIC_ID,lyric.getID());

          contentTable.add(updateLyricLink,1,6);

          Link deleteTrackLinkTemplate = AlbumCollectionBusiness.getMainLinkClone("Ey�a");
          deleteTrackLinkTemplate.setWindowToOpen(DeleteConfirmWindow.class);
          deleteTrackLinkTemplate.addParameter(AlbumCollectionBusiness._PRM_DELETE,AlbumCollectionBusiness._CONST_LYRIC);
          deleteTrackLinkTemplate.addParameter(DeleteConfirmWindow._PRM_ID,lyric.getID());

          contentTable.add(deleteTrackLinkTemplate,1,6);
          contentTable.add(Text.getBreak(),1,6);
          contentTable.add(Text.getBreak(),1,6);
        }

      } else {
        contentTable.add(AlbumCollectionBusiness.getMainTextClone("Texti finnst ekki"),1,2);
      }
    } else {
      contentTable.add(AlbumCollectionBusiness.getMainTextClone("Texti finnst ekki"),1,2);
    }

    contentTable.add(new BackButton("Til baka"),1,6);

    this.add(Text.getBreak());
    this.add(Text.getBreak());
    this.add(frameTable);
    this.add(Text.getBreak());
    this.add(Text.getBreak());

  }
}