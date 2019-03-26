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

import com.grapenlp.core.u_array;
import com.grapenlp.core.u_context;
import com.grapenlp.core.u_context_key_value_hasher;

import java.util.Map;

public class UContext
{
    static u_context mapToUContext(Map<String, String> m, u_context_key_value_hasher cHasher)
    {
        u_context ctx = new u_context(cHasher);
        for (Map.Entry<String, String> e : m.entrySet())
        {
            u_array nativeKey = UArray.stringToUArray(e.getKey());
            u_array nativeValue = UArray.stringToUArray(e.getValue());
            ctx.ua_set(nativeKey.const_begin(), nativeKey.const_end(), nativeValue.const_begin(), nativeValue.const_end());
        }
        return ctx;
    }
}
