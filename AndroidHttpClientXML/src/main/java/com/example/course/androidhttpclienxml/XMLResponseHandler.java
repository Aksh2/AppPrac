package com.example.course.androidhttpclienxml;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 27/7/16.
 */
public class XMLResponseHandler implements ResponseHandler<List<String>> {

    private static final String MAGNITUDE_TAG ="magnitude";
    private static final String LONGITUDE_TAG ="lng";
    private static final String LATITUDE_TAG ="lat";
    private String mLat,mLng,mMag;
    private boolean mIsParsingLat,misParsingLng,mIsParsingMag;
    private final List<String> mResults= new ArrayList<String>();




    @Override
    public List<String> handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(httpResponse.getEntity().getContent()));

            int eventType = xpp.getEventType();

            while (eventType!= XmlPullParser.END_DOCUMENT){
                if(eventType==XmlPullParser.START_TAG){
                    startTag(xpp.getName());
                }else if(eventType==XmlPullParser.END_TAG){
                    endTag(xpp.getName());
                }else if(eventType==XmlPullParser.TEXT){
                    text(xpp.getText());
                }
                eventType = xpp.next();

            }
            return mResults;
        }catch (XmlPullParserException e){

        }

        return null;
    }

    public void startTag(String localName){
        if(localName.equals(LATITUDE_TAG)){
            mIsParsingLat = true;
        }
        else if(localName.equals(LONGITUDE_TAG)){
            misParsingLng = true;
        }
        else if(localName.equals(MAGNITUDE_TAG)){
            mIsParsingMag = true;
        }
    }

    public void text(String text){
        if(mIsParsingLat){
            mLat=text.trim();
        }
        else if(misParsingLng){
            mLng=text.trim();
        }
        else if(mIsParsingMag){
            mMag=text.trim();
        }
    }

    public void endTag(String localName){
        if(localName.equals(LATITUDE_TAG)){
            mIsParsingLat=false;
        }
        else if(localName.equals(LONGITUDE_TAG)){
            misParsingLng=false;
        }
        else if(localName.equals(MAGNITUDE_TAG)){
            mIsParsingMag=false;
        }
        else if(localName.equals("earthquake")){
            mResults.add(MAGNITUDE_TAG+":"+mMag+","+LATITUDE_TAG+":"
            +mLat+","+LONGITUDE_TAG+":"+mLng);

            mLng=null;
            mMag=null;
            mLat=null;

        }
    }

}
