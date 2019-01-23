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

import com.grapenlp.core.uau_simple_segment_array;
import com.grapenlp.core.uaui_simple_segment_array_x_weight;
import com.grapenlp.core.uaui_simple_segment_array_x_weight_array;

public class GrammarParse
{
    private Segment[] segments;
    private int weight;

    public GrammarParse(uaui_simple_segment_array_x_weight nativeGrammarParse)
    {
        uau_simple_segment_array nativeSegments = nativeGrammarParse.getSsa();
        int segmentCount = (int) nativeSegments.size();
        segments = new Segment[segmentCount];
        for (int i = 0; i < segmentCount; ++i)
            segments[i] = new Segment(nativeSegments.get_elem_at(i));
        weight = nativeGrammarParse.getW();
    }

    public Segment[] getSegments()
    {
        return segments;
    }

    public void setSegments(Segment[] segments)
    {
        this.segments = segments;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public static GrammarParse[] nativeGrammarParsesToJava(uaui_simple_segment_array_x_weight_array nativeGrammarParses)
    {
        int grammarParseCount = (int) nativeGrammarParses.size();
        GrammarParse[] grammarParses = new GrammarParse[grammarParseCount];
        for (int i = 0; i < grammarParseCount; ++i)
            grammarParses[i] = new GrammarParse(nativeGrammarParses.get_elem_at(i));
        return grammarParses;
    }
}
