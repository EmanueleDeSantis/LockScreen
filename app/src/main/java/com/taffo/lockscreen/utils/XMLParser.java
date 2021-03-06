/*
   LockScreen, an Android lockscreen for people with perfect pitch
   Copyright (C) 2021  Emanuele De Santis

   LockScreen is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as published
   by the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   LockScreen is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with LockScreen.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.taffo.lockscreen.utils;

import android.content.Context;
import android.content.Intent;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public final class XMLParser {
    //The actual number of notes to play (used in all "guess the notes" activities)
    private static String val;
    //The actual total number of stored notes in "res/raw" folder
    //got from the xml document "notes.xml" in "src/main/assets" folder (used in all "guess the notes" activities)
    private static int totalVal;
    //The Document containing information about the notes to be played
    private static Document docum;
    //Used by services to update the correct number of notes to play
    public void setNotes(String s) {
        val = s;
    }
    public int getNotes() {
        int intVal = Integer.parseInt(val);
        if (intVal < 1 || intVal > 8) //Integrity val check
            val = "3";
        return Integer.parseInt(val);
    }
    public int getTotalNotes() {
        return totalVal;
    }
    public Document getDocum() {
        return docum;
    }

    //The xml document is parsed here for more usability (used in all "guess the notes" activities)
    public void parseXmlNotes(Context context) {
        try {
            docum = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(context.getAssets().open("notes.xml"));
            docum.getDocumentElement().normalize();
            totalVal = docum.getElementsByTagName("note").getLength();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            context.sendBroadcast(new Intent("parsingError"));
        }
    }

}
