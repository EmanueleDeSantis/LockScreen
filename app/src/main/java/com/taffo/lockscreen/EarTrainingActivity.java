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

package com.taffo.lockscreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.taffo.lockscreen.utils.XMLParser;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class EarTrainingActivity extends AppCompatActivity {
    private final XMLParser parser = new XMLParser();
    private final int NOTES = parser.getNotes();
    private int UNIQUE_NOTES = NOTES;
    private final int TOTAL_NOTES = parser.getTotalNotes();
    private final Document doc = parser.getDocum();

    private TextView textViewNotes;
    private Button buttonDo;
    private Button buttonDodie;
    private Button buttonRe;
    private Button buttonRedie;
    private Button buttonMi;
    private Button buttonFa;
    private Button buttonFadie;
    private Button buttonSol;
    private Button buttonSoldie;
    private Button buttonLa;
    private Button buttonLadie;
    private Button buttonSi;

    private final List<String> outputtedNotesList = new ArrayList<>(NOTES);
    private final List<String> selectedNotesList = new ArrayList<>(NOTES);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);

        play();
        UNIQUE_NOTES = (int) outputtedNotesList.stream().distinct().count();

        //Initializes the view's elements
        textViewNotes = findViewById(R.id.textViewNotes);
        buttonDo = findViewById(R.id.buttonDo);
        buttonDodie = findViewById(R.id.buttonDodie);
        buttonRe = findViewById(R.id.buttonRe);
        buttonRedie = findViewById(R.id.buttonRedie);
        buttonMi = findViewById(R.id.buttonMi);
        buttonFa = findViewById(R.id.buttonFa);
        buttonFadie = findViewById(R.id.buttonFadie);
        buttonSol = findViewById(R.id.buttonSol);
        buttonSoldie = findViewById(R.id.buttonSoldie);
        buttonLa = findViewById(R.id.buttonLa);
        buttonLadie = findViewById(R.id.buttonLadie);
        buttonSi = findViewById(R.id.buttonSi);

        buttonDo.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("do")) {
                    selectedNotesList.remove("do");
                    buttonDo.setSelected(false);
                } else {
                    selectedNotesList.add("do");
                    buttonDo.setSelected(true);
                }
                unlock();
            }
        });
        buttonDodie.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("dodie")) {
                    selectedNotesList.remove("dodie");
                    buttonDodie.setSelected(false);
                } else {
                    selectedNotesList.add("dodie");
                    buttonDodie.setSelected(true);
                }
                unlock();
            }
        });
        buttonRe.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("re")) {
                    selectedNotesList.remove("re");
                    buttonRe.setSelected(false);
                } else {
                    selectedNotesList.add("re");
                    buttonRe.setSelected(true);
                }
                unlock();
            }
        });
        buttonRedie.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("redie")) {
                    selectedNotesList.remove("redie");
                    buttonRedie.setSelected(false);
                } else {
                    selectedNotesList.add("redie");
                    buttonRedie.setSelected(true);
                }
                unlock();
            }
        });
        buttonMi.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("mi")) {
                    selectedNotesList.remove("mi");
                    buttonMi.setSelected(false);
                } else {
                    selectedNotesList.add("mi");
                    buttonMi.setSelected(true);
                }
                unlock();
            }
        });
        buttonFa.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("fa")) {
                    selectedNotesList.remove("fa");
                    buttonFa.setSelected(false);
                } else {
                    selectedNotesList.add("fa");
                    buttonFa.setSelected(true);
                }
                unlock();
            }
        });
        buttonFadie.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("fadie")) {
                    selectedNotesList.remove("fadie");
                    buttonFadie.setSelected(false);
                } else {
                    selectedNotesList.add("fadie");
                    buttonFadie.setSelected(true);
                }
                unlock();
            }
        });
        buttonSol.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("sol")) {
                    selectedNotesList.remove("sol");
                    buttonSol.setSelected(false);
                } else {
                    selectedNotesList.add("sol");
                    buttonSol.setSelected(true);
                }
                unlock();
            }
        });
        buttonSoldie.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("soldie")) {
                    selectedNotesList.remove("soldie");
                    buttonSoldie.setSelected(false);
                } else {
                    selectedNotesList.add("soldie");
                    buttonSoldie.setSelected(true);
                }
                unlock();
            }
        });
        buttonLa.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("la")) {
                    selectedNotesList.remove("la");
                    buttonLa.setSelected(false);
                } else {
                    selectedNotesList.add("la");
                    buttonLa.setSelected(true);
                }
                unlock();
            }
        });
        buttonLadie.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("ladie")) {
                    selectedNotesList.remove("ladie");
                    buttonLadie.setSelected(false);
                } else {
                    selectedNotesList.add("ladie");
                    buttonLadie.setSelected(true);
                }
                unlock();
            }
        });
        buttonSi.setOnClickListener(v -> {
            if (selectedNotesList.size() < UNIQUE_NOTES) {
                if (selectedNotesList.contains("si")) {
                    selectedNotesList.remove("si");
                    buttonSi.setSelected(false);
                } else {
                    selectedNotesList.add("si");
                    buttonSi.setSelected(true);
                }
                unlock();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finishAndRemoveTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (MediaPlayer mPlayer : mediaPlayer)
            mPlayer.release();
    }

    private final List<Integer> randomNotesList = new ArrayList<>(NOTES);
    private final MediaPlayer[] mediaPlayer = new MediaPlayer[NOTES];

    //Plays random notes got from the xml document "notes.xml" in "src/main/assets" folder. Notes are stored in "res/raw" folder
    private void play() {
        try {
            for (int i = 0; i < NOTES; i++) {
                //To avoid same note/same octave repetitions, for example
                //La2 and La3 are ok, so they both will be played
                //La2 and La2 are NOT ok, so the second instance of La2 will be replaced by another note
                int randomId;
                do {
                    randomId = new Random().nextInt(TOTAL_NOTES) + 1;
                } while (randomNotesList.contains(randomId));
                randomNotesList.add(i, randomId);
                outputtedNotesList.add(i, doc.getElementById(String.valueOf(randomId))
                        .getElementsByTagName("name").item(0).getTextContent());
                mediaPlayer[i] = MediaPlayer.create(getApplicationContext(),
                        getResources().getIdentifier(doc.getElementById(String.valueOf(randomId))
                                .getElementsByTagName("sound_name").item(0).getTextContent(), "raw", getPackageName()));
            }

            for (MediaPlayer mPlayer : mediaPlayer)
                mPlayer.start();

            Collections.sort(randomNotesList);
        } catch (NullPointerException e) {
            sendBroadcast(new Intent("parsingError"));
        }
    }

    private final List<Integer> notesColor = new ArrayList<>(NOTES);
    private final StringBuilder coloredStringTextViewNotes = new StringBuilder();
    //Unlocks if user guessed all and only the outputted notes
    private void unlock() {
        if (selectedNotesList.containsAll(outputtedNotesList) && outputtedNotesList.containsAll(selectedNotesList))
            finish();
        else if (selectedNotesList.size() >= UNIQUE_NOTES) {
            //Colors in red the buttons of the wrong notes
            for (String snl : selectedNotesList) {
                switch (snl) {
                    case "do":
                        if (!outputtedNotesList.contains(snl))
                            buttonDo.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "dodie":
                        if (!outputtedNotesList.contains(snl))
                            buttonDodie.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "re":
                        if (!outputtedNotesList.contains(snl))
                            buttonRe.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "redie":
                        if (!outputtedNotesList.contains(snl))
                            buttonRedie.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "mi":
                        if (!outputtedNotesList.contains(snl))
                            buttonMi.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "fa":
                        if (!outputtedNotesList.contains(snl))
                            buttonFa.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "fadie":
                        if (!outputtedNotesList.contains(snl))
                            buttonFadie.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "sol":
                        if (!outputtedNotesList.contains(snl))
                            buttonSol.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "soldie":
                        if (!outputtedNotesList.contains(snl))
                            buttonSoldie.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "la":
                        if (!outputtedNotesList.contains(snl))
                            buttonLa.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "ladie":
                        if (!outputtedNotesList.contains(snl))
                            buttonLadie.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                    case "si":
                        if (!outputtedNotesList.contains(snl))
                            buttonSi.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_round));
                        break;
                }
            }

            //Converts the name of the outputted notes based on the chosen language instead of the name present in the xml file and
            //Colors the outputted notes in the text view and the buttons of this notes:
            //green if the entry is correct,
            //yellow if the entry is missing in the outputted notes
            for (String onl : outputtedNotesList) {
                switch (onl) {
                    case "do":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Do));
                        if (selectedNotesList.contains(onl)) {
                            buttonDo.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonDo.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "dodie":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Dodie));
                        if (selectedNotesList.contains(onl)) {
                            buttonDodie.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonDodie.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "re":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Re));
                        if (selectedNotesList.contains(onl)) {
                            buttonRe.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonRe.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "redie":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Redie));
                        if (selectedNotesList.contains(onl)) {
                            buttonRedie.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonRedie.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "mi":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Mi));
                        if (selectedNotesList.contains(onl)) {
                            buttonMi.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonMi.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "fa":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Fa));
                        if (selectedNotesList.contains(onl)) {
                            buttonFa.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonFa.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "fadie":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Fadie));
                        if (selectedNotesList.contains(onl)) {
                            buttonFadie.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonFadie.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "sol":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Sol));
                        if (selectedNotesList.contains(onl)) {
                            buttonSol.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonSol.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "soldie":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Soldie));
                        if (selectedNotesList.contains(onl)) {
                            buttonSoldie.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonSoldie.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "la":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.La));
                        if (selectedNotesList.contains(onl)) {
                            buttonLa.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonLa.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "ladie":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Ladie));
                        if (selectedNotesList.contains(onl)) {
                            buttonLadie.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonLadie.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                    case "si":
                        outputtedNotesList.set(outputtedNotesList.indexOf(onl), getString(R.string.Si));
                        if (selectedNotesList.contains(onl)) {
                            buttonSi.setBackground(ContextCompat.getDrawable(this, R.drawable.right_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_right_round_green));
                        } else {
                            buttonSi.setBackground(ContextCompat.getDrawable(this, R.drawable.missed_round));
                            notesColor.add(ContextCompat.getColor(this, R.color.custom_missed_round_yellow));
                        }
                        break;
                }
            }

            //Prints all the outputted notes on the text view, since the user inserted an incorrect entry (see also the functions above)
            for (int i = 0; i < NOTES; i++) {
                if (i != NOTES-1)
                    coloredStringTextViewNotes.append("<font color='")
                            .append(notesColor.get(i)).append("'>").append(outputtedNotesList.get(i)).append(" ")
                            .append("</font>");
                else
                    //String does not have the final whitespace
                    coloredStringTextViewNotes.append("<font color='")
                            .append(notesColor.get(i)).append("'>").append(outputtedNotesList.get(i))
                            .append("</font>");
            }
            textViewNotes.setText(HtmlCompat.fromHtml(coloredStringTextViewNotes.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
    }

}