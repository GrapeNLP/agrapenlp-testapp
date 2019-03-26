/*
 * GRAPENLP
 *
 * Copyright (C) 2004-2019 Javier Miguel Sastre Martínez <javier.sastre@telefonica.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 *
 */

package com.grapenlp.testapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.grapenlp.GrammarEngine;
import com.grapenlp.GrammarParse;
import com.grapenlp.Segment;
import com.grapenlp.core.uaui_simple_segment_array_x_weight_array;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    public static final File DATA_FOLDER=new File(Environment.getExternalStorageDirectory().getPath(), "grapenlpdata");
    public static final File GRAMMAR_FILE=new File(DATA_FOLDER, "grammar.fst2");
    public static final File DICO_BIN_FILE =new File(DATA_FOLDER, "delaf_norm.bin");
    public static final File DICO_INF_FILE =new File(DATA_FOLDER, "delaf_norm.inf");

    public static final int LIST_FILES = 1;
    public static final int LOAD_TAGGER = 2;

    private EditText sentenceBox;
    private EditText keyBoxes[];
    private EditText valueBoxes[];
    private TextView sentenceTagsBox;
    private GrammarEngine grammarEngine=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sentenceBox = findViewById(R.id.sentence);
        sentenceTagsBox = findViewById(R.id.sentenceTags);
        keyBoxes = new EditText[4];
        valueBoxes = new EditText[4];
        keyBoxes[0] = findViewById(R.id.contextKey1);
        keyBoxes[1] = findViewById(R.id.contextKey2);
        keyBoxes[2] = findViewById(R.id.contextKey3);
        keyBoxes[3] = findViewById(R.id.contextKey4);
        valueBoxes[0] = findViewById(R.id.contextValue1);
        valueBoxes[1] = findViewById(R.id.contextValue2);
        valueBoxes[2] = findViewById(R.id.contextValue3);
        valueBoxes[3] = findViewById(R.id.contextValue4);
        sentenceBox.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sentenceTagsBox.setText(sentenceBox.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private boolean getReadExternalStoragePermission(int taskCode)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return true;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, taskCode);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            switch (requestCode)
            {
                case LIST_FILES:
                {
                    listFiles(null);
                    break;
                }
                case LOAD_TAGGER:
                    loadTagger(null);
            }
        }
        else sentenceTagsBox.setText("Permission for reading the external storage is needed for loading the grammar and dictionary files, which you should have placed inside a folder " + DATA_FOLDER.getName() + " at the root of the device storage");
    }

    public void listFiles(View view)
    {
        if (!getReadExternalStoragePermission(LIST_FILES))
            return;
        if (!DATA_FOLDER.exists())
        {
            sentenceTagsBox.setText("Data folder " + DATA_FOLDER.getAbsolutePath() + " does not exist; please create it and put the grammar and dictionary files there");
            return;
        }
        if (!DATA_FOLDER.isDirectory())
        {
            sentenceTagsBox.setText("Data folder " + DATA_FOLDER.getAbsolutePath() + " is not a directory");
            return;
        }
        File[] dataFiles = DATA_FOLDER.listFiles();
        if (dataFiles.length == 0)
        {
            sentenceTagsBox.setText("Data folder " + DATA_FOLDER.getAbsolutePath() + " is empty; please put the grammar and dictionary files inside");
            return;
        }
        StringBuilder files = new StringBuilder("Files in ");
        files.append(DATA_FOLDER.getAbsolutePath());
        files.append(":\n");
        for (File file : dataFiles)
        {
            files.append('\n');
            files.append(file.getName());
        }
        sentenceTagsBox.setText(files.toString());
    }

    public void loadLib(View view)
    {
        try
        {
            sentenceTagsBox.setText(GrammarEngine.loadLib());
        }
        catch (RuntimeException e)
        {
            sentenceTagsBox.setText(e.getMessage());
        }
    }

    private boolean checkLibraryIsLoaded()
    {
        if (!GrammarEngine.isLibraryLoaded())
        {
            sentenceTagsBox.setText("You must first load the native library");
            return false;
        }
        return true;
    }

    private void showMissingFiles(ArrayList<File> missingFiles)
    {
        StringBuilder message = new StringBuilder("Missing data files:\n");
        for (File f : missingFiles)
        {
            message.append(f.getAbsolutePath());
            message.append("\n");
        }
        message.append("Please copy them to your Android device in those paths. You may check that the files are present by pressing button LIST FILES");
        sentenceTagsBox.setText(message);
    }

    private boolean checkDataFiles()
    {
        ArrayList<File> missingFiles = new ArrayList<File>();
        if (!GRAMMAR_FILE.exists())
            missingFiles.add(GRAMMAR_FILE);
        if (!DICO_BIN_FILE.exists())
            missingFiles.add(DICO_BIN_FILE);
        if (!DICO_INF_FILE.exists())
            missingFiles.add(DICO_INF_FILE);
        if (!missingFiles.isEmpty())
        {
            showMissingFiles(missingFiles);
            return false;
        }
        return true;
    }

    public void loadTagger(View view)
    {
        if (!(checkLibraryIsLoaded() &&
              getReadExternalStoragePermission(LOAD_TAGGER) &&
              checkDataFiles()))
            return;
        try
        {
            if (grammarEngine == null)
            {
                grammarEngine = new GrammarEngine(GRAMMAR_FILE.getAbsolutePath(), DICO_BIN_FILE.getAbsolutePath());
                sentenceTagsBox.setText("Tagger loaded");
            }
            else
            {
                grammarEngine.resetModels(GRAMMAR_FILE.getAbsolutePath(), DICO_BIN_FILE.getAbsolutePath());
                sentenceTagsBox.setText("Tagger reloaded");
            }
        }
        catch (RuntimeException e)
        {
            sentenceTagsBox.setText(e.getMessage());
        }
    }

    private String serializeResult(String sentence, uaui_simple_segment_array_x_weight_array nativeResult)
    {
        GrammarParse[] parses = GrammarParse.nativeGrammarParsesToJava(nativeResult);
        if (parses.length == 0)
            return "Unrecognized sentence";
        Segment[] topSegments = parses[0].getSegments();
        if (topSegments.length == 0)
            return "Sentence recognized but no tags generated";
        StringBuilder builder = new StringBuilder();
        builder.append("Sentence recognized with the following tags:\n");
        for (Segment segment : topSegments)
        {
            builder.append('\n');
            builder.append(segment.getName());
            if (segment.getStart() < segment.getEnd())
            {
                builder.append(": ");
                builder.append(sentence.substring((int)segment.getStart(), (int)segment.getEnd()));
            }
        }
        return builder.toString();
    }

    public void tag(View view)
    {
        if (grammarEngine == null)
        {
            sentenceTagsBox.setText("You must first load the tagger");
            return;
        }
        try
        {
            String sentence = sentenceBox.getText().toString();
            TreeMap<String, String> context = new TreeMap<String, String>();
            for (int i = 0; i < keyBoxes.length; ++i)
            {
                String key = keyBoxes[i].getText().toString();
                String value = valueBoxes[i].getText().toString();
                if (!key.isEmpty() && !value.isEmpty())
                    context.put(key, value);
            }
            uaui_simple_segment_array_x_weight_array nativeResult = grammarEngine.tag(sentence, context);
            String result = serializeResult(sentence, nativeResult);
            sentenceTagsBox.setText(result);
        }
        catch (RuntimeException e)
        {
            sentenceTagsBox.setText(e.getMessage());
        }
    }
}
