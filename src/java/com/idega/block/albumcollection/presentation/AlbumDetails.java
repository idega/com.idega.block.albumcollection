package com.idega.block.albumcollection.presentation;

import com.idega.presentation.Block;
import com.idega.presentation.text.Text;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Link;
import com.idega.presentation.Table;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.ui.BackButton;
import com.idega.block.albumcollection.business.AlbumCollectionBusiness;
import com.idega.block.albumcollection.data.Album;
import com.idega.block.albumcollection.data.Track;
import com.idega.block.albumcollection.data.Author;
import com.idega.block.albumcollection.data.Performer;
import com.idega.util.text.TextSoap;

import java.util.List;
import java.util.Iterator;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="gummi@idega.is">Gu�mundur �g�st S�mundsson</a>
 * @version 1.0
 */

public class AlbumDetails extends Block {

  public final static String _PRM_ALBUM_ID = AlbumCollectionBusiness._PRM_ALBUM_ID;
  private Text trackNameTemplate;
  private Text trackNumberTemplate;
  private Link setLyricLinkTemplate;
  private Link lyricViewerLinkTemplate;
  private Link updateTrackLinkTemplate;
  private Link deleteTrackLinkTemplate;

  public AlbumDetails() {
    trackNameTemplate = AlbumCollectionBusiness.getMainTextClone();
    trackNumberTemplate= AlbumCollectionBusiness.getMainTextClone();

    setLyricLinkTemplate = AlbumCollectionBusiness.getMainLinkClone();
    setLyricLinkTemplate.setWindowToOpen(InsertLyric.class);

    lyricViewerLinkTemplate = AlbumCollectionBusiness.getMainLinkClone();
    lyricViewerLinkTemplate.addParameter(AlbumCollection._PRM_STATE,AlbumCollection._STATE_LYRIC);

    updateTrackLinkTemplate = AlbumCollectionBusiness.getMainLinkClone("Breyta");
    updateTrackLinkTemplate.setWindowToOpen(AddTrack.class);
    updateTrackLinkTemplate.addParameter(AlbumCollectionBusiness._PRM_UPDATE,"true");

    deleteTrackLinkTemplate = AlbumCollectionBusiness.getMainLinkClone("Ey�a");
    deleteTrackLinkTemplate.setWindowToOpen(DeleteConfirmWindow.class);
    deleteTrackLinkTemplate.addParameter(AlbumCollectionBusiness._PRM_DELETE,AlbumCollectionBusiness._CONST_TRACK);


  }

  public void lineUpElements(IWContext iwc)throws Exception {
    Table frameTable = new Table(1,1);
    frameTable.setCellspacing(1);
    frameTable.setColor(AlbumCollection._COLOR_BRIGHTEST);
    frameTable.setColor(1,1,AlbumCollection._COLOR_BRIGHT);
    frameTable.setWidth("550");


    Table contentTable = new Table(1,2);
    String albumId = iwc.getParameter(this._PRM_ALBUM_ID);
    if(albumId != null && !"".equals(albumId)){
      Album album = AlbumCollectionBusiness.getAlbum(Integer.parseInt(albumId));
      if(album != null){
        contentTable.add(AlbumCollectionBusiness.getHeaderTextClone(album.getName()),1,1);
        contentTable.add(Text.getBreak(),1,1);
        int imageId = album.getFrontCoverFileId();
        if(imageId > 0){
          Image fCover = new Image(imageId,album.getName());
          fCover.setAlignment("right");
          contentTable.add(fCover,1,1);
        }
        contentTable.add(Text.getBreak(),1,1);
        contentTable.add(TextSoap.formatText(album.getDescription()),1,1);
      }
    }

    contentTable.add(getTrackList(iwc),1,2);

    frameTable.add(contentTable);
    this.add(Text.getBreak());
    this.add(Text.getBreak());
    this.add(frameTable);
  }

  public Table getTrackList(IWContext iwc)throws Exception {
    Table trackTable = null;
    String albumId = iwc.getParameter(this._PRM_ALBUM_ID);
    if(albumId != null && !"".equals(albumId)){
      List tracks = AlbumCollectionBusiness.getTracks(Integer.parseInt(albumId));
      if(tracks != null && tracks.size() > 0){
        trackTable = new Table(5,tracks.size()+1);
        trackTable.setWidth("100%");
        trackTable.setCellspacing(2);
        trackTable.setCellpadding(0);
        int index=1;
        trackTable.add(AlbumCollectionBusiness.getMainTextClone("N�mer"),1,index);
        trackTable.add(AlbumCollectionBusiness.getMainTextClone("Heiti"),2,index);
        trackTable.add(AlbumCollectionBusiness.getMainTextClone("Texti"),5,index);
        index++;
        Iterator iter = tracks.iterator();
        while (iter.hasNext()) {
          Track item = (Track)iter.next();

          trackTable.setRowColor(index,AlbumCollection._COLOR_DARK);

          if(item.getNumber() > 0){
            Text trackNumber = (Text)trackNumberTemplate.clone();
            trackNumber.setText(Integer.toString(item.getNumber()));
            trackTable.add(trackNumber,1,index);
          }

          Text trackName = (Text)trackNameTemplate.clone();
          trackName.setText(item.getName());
          trackTable.add(trackName,2,index);

          if(hasEditPermission()){
            Link update = (Link)updateTrackLinkTemplate.clone();
            update.addParameter(AlbumCollectionBusiness._PRM_TRACK_ID,item.getID());
            trackTable.add(update,3,index);

            Link delete = (Link)deleteTrackLinkTemplate.clone();
            delete.addParameter(DeleteConfirmWindow._PRM_ID,item.getID());
            trackTable.add(delete,4,index);
          }


          if(item.getLyricId() < 0){
            if(hasEditPermission()){
              Link setLyric = (Link)setLyricLinkTemplate.clone();
              setLyric.setText("setja texta");
              setLyric.addParameter(AlbumCollectionBusiness._PRM_TRACK_ID,item.getID());

              trackTable.add(setLyric,5,index);
            }
          } else {
            Link setLyric = (Link)lyricViewerLinkTemplate.clone();
            setLyric.setText("Texti");
            setLyric.addParameter(AlbumCollectionBusiness._PRM_LYRIC_ID,item.getLyricId());

            trackTable.add(setLyric,5,index);
          }

          index++;
        }
      }

    }


    return trackTable;
  }

  public void main(IWContext iwc) throws Exception {

    lineUpElements(iwc);

    Link addTrackLink = AlbumCollectionBusiness.getMainLinkClone("add track");
    //addTrackLink.setFontColor("#EEEEEE");
    addTrackLink.setBold();
    addTrackLink.setWindowToOpen(AddTrack.class);
    String albumId = iwc.getParameter(_PRM_ALBUM_ID);
    if(albumId != null && !albumId.equals("")){
      addTrackLink.addParameter(_PRM_ALBUM_ID,albumId);
    }

    if(hasEditPermission()){
      this.add(addTrackLink);
    }

    this.add(Text.getBreak());
    this.add(Text.getBreak());
    this.add(new BackButton("Til baka"));

  }
}