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
package fr.theshark34.reactors.reprops.props;

import fr.theshark34.reactors.reprops.RProp;
import fr.theshark34.reactors.reprops.Reprops;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * The String RProp
 *
 * <p>
 *    The RProp version of the string.
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public class RPropString extends RProp {

    /**
     * The RPropString identifier
     */
    public static final byte IDENTIFIER = 1;

    /**
     * The string of the prop
     */
    private String string;

    /**
     * Constructor with identifier only, reserved for Reprops
     * to use it.
     *
     * @param identifier
     *            The identifier of the prop
     */
    private RPropString(String identifier) {
        super(identifier);
    }

    /**
     * A String prop
     *
     * @param identifier
     *            The identifier of the prop
     * @param string
     *            The string
     */
    public RPropString(String identifier, String string) {
        super(identifier);

        this.string = string;
    }

    @Override
    public String getName() {
        return "String";
    }

    @Override
    public byte[] write() {
        return Reprops.crypt(string).getBytes();
    }

    @Override
    public void read(byte[] bytes) {
        this.string = Reprops.decrypt(new String(bytes));
    }

    /**
     * Set the string of the prop
     *
     * @param string
     *            The prop string
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * Return the prop string
     *
     * @return The string
     */
    public String getString() {
        return this.string;
    }

    @Override
    public String toString() {
        return this.string;
    }

}
