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

import com.grapenlp.core.SWIGTYPE_p_unsigned_char;
import com.grapenlp.core.jgrapenlp;
import com.grapenlp.core.u_array;
import java.nio.charset.StandardCharsets;

public class UArray
{
    static u_array stringToUArray(String s)
    {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_16LE);
        u_array uArray = new u_array(bytes.length >> 1);
        SWIGTYPE_p_unsigned_char native_bytes = uArray.get_bytes();
        for (int i = 0; i < bytes.length; ++i)
            jgrapenlp.byte_array_setitem(native_bytes, i, bytes[i]);
        return uArray;
    }

    static String uArrayToString(u_array uArray)
    {
        SWIGTYPE_p_unsigned_char native_bytes = uArray.get_bytes();
        int byteCount = (int) uArray.size_in_bytes();
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; ++i)
            bytes[i] = (byte) jgrapenlp.byte_array_getitem(native_bytes, i);
        return new String(bytes, StandardCharsets.UTF_16LE);
    }
}
