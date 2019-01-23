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

package com.grapenlp;

import com.grapenlp.core.*;

public class GrammarEngine
{
    private static boolean libraryLoadedFlag = false;

    public static boolean isLibraryLoaded()
    {
        return libraryLoadedFlag;
    }

    public static String loadLib()
    {
        if (libraryLoadedFlag)
            return "Already loaded";
        else
        {
            System.loadLibrary("grapenlp");
            System.loadLibrary("jgrapenlp");
            libraryLoadedFlag = true;
            return "Loaded";
        }
    }

    private ualxiw_manager nativeGrammarEngine;

    public void resetModels(String grammarPathName, String binDelafPathName)
    {
        nativeGrammarEngine = new ualxiw_manager(rtno_type.LEXMASK_X_WEIGHTED_EXTRACTION_RTNO, grammarPathName, binDelafPathName);
    }

    public GrammarEngine(String grammarPathName, String binDelafPathName)
    {
        resetModels(grammarPathName, binDelafPathName);
    }

    public uaui_simple_segment_array_x_weight_array tag(String sentence)
    {
        u_array native_sentence = UArray.stringToUArray(sentence);
        nativeGrammarEngine.process(native_sentence.const_begin(), native_sentence.const_end(),
                rtno_parser_type.TO_FPRTN_AND_TOP_BLACKBOARD_EXTRACT_RTNO_PARSER, true, false, assoc_container_impl_choice.LRB_TREE, assoc_container_impl_choice.STD);
        return nativeGrammarEngine.get_simplified_weighted_output();
    }
}
