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

/*
 *  @author Javier Sastre
 */

package com.grapenlp;

import com.grapenlp.core.uau_simple_segment;

public class Segment
{
    private String name;
    private long start;
    private long end;

    public Segment(String name, long start, long end)
    {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public Segment(uau_simple_segment nativeSegment)
    {
        name = UOutBoundTrie.uOutBoundTrieStringToString(nativeSegment.getName());
        start = nativeSegment.getBegin();
        end = nativeSegment.getEnd();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getStart()
    {
        return start;
    }

    public void setStart(long start)
    {
        this.start = start;
    }

    public long getEnd()
    {
        return end;
    }

    public void setEnd(long end)
    {
        this.end = end;
    }
}
