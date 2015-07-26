/*
 * Copyright 2015 TheShark34
 *
 * This file is part of Reprops.

 * Reprops is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Reprops is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Reprops.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.theshark34.reactors.reprops;

/**
 * The Reprops codes
 *
 * <p>
 *    This final class, contains the byte codes of Reprops.
 *    Like PROP_START, PROP_LENGTH_END, etc...
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public final class RepropsCodes {

    /**
     * The PROP_START byte code that indicate the start
     * of an RProp
     *
     * @see RCodePropStart
     */
    public static final byte PROP_START = 1;

    /**
     * The PROP_TYPE_IDENTIFIER byte code that indicate the
     * start of a RProp type identifier
     *
     * @see RPropsManager
     */
    public static final byte PROP_TYPE_IDENTIFIER = 2;

}
