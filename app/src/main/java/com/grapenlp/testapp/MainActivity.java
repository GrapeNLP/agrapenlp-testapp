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

public class MainActivity extends AppCompatActivity {
    public static final File DATA_FOLDER=new File("/sdcard/grapenlpdata");
    public static final File GRAMMAR_FILE=new File(DATA_FOLDER, "grammar.fst2");
    public static final File DICO_FILE=new File(DATA_FOLDER, "delaf_norm.bin");

    private EditText et;
    private TextView tv;
    private GrammarEngine grammarEngine=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        tv = findViewById(R.id.textView);

        et.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tv.setText(et.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    public void listFiles(View view)
    {
        if (!DATA_FOLDER.exists())
        {
            tv.setText("Data folder " + DATA_FOLDER.getAbsolutePath() + " does not exist");
            return;
        }
        if (!DATA_FOLDER.isDirectory())
        {
            tv.setText("Data folder " + DATA_FOLDER.getAbsolutePath() + " is not a directory");
            return;
        }
        File[] dataFiles = DATA_FOLDER.listFiles();
        if (dataFiles.length == 0)
        {
            tv.setText("Data folder " + DATA_FOLDER.getAbsolutePath() + " is empty");
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
        tv.setText(files.toString());
    }

    public void loadLib(View view)
    {
        try
        {
            tv.setText(GrammarEngine.loadLib());
        }
        catch (RuntimeException e)
        {
            tv.setText(e.getMessage());
        }
    }

    public void loadTagger(View view)
    {
        if (!GrammarEngine.isLibraryLoaded())
        {
            tv.setText("You must first load the native library");
            return;
        }
        try
        {
            if (grammarEngine == null)
            {
                grammarEngine = new GrammarEngine(GRAMMAR_FILE.getAbsolutePath(), DICO_FILE.getAbsolutePath());
                tv.setText("Tagger loaded");
            }
            else
            {
                grammarEngine.resetModels(GRAMMAR_FILE.getAbsolutePath(), DICO_FILE.getAbsolutePath());
                tv.setText("Tagger reloaded");
            }
        }
        catch (RuntimeException e)
        {
            tv.setText(e.getMessage());
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
            tv.setText("You must first load the tagger");
            return;
        }
        try
        {
            String sentence = et.getText().toString();
            uaui_simple_segment_array_x_weight_array nativeResult = grammarEngine.tag(et.getText().toString());
            String result = serializeResult(sentence, nativeResult);
            tv.setText(result);
        }
        catch (RuntimeException e)
        {
            tv.setText(e.getMessage());
        }
    }
}
