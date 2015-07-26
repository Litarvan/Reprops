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

/**
 * The Number RProp
 *
 * <p>
 *    A simple number RProp :)
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public class RPropNumber extends RProp {

    /**
     * The RPropNumber identifier
     */
    public static final byte IDENTIFIER = 2;

    /**
     * The number of the prop
     */
    private long number;

    /**
     * Constructor with identifier only, reserved for Reprops
     * to use it.
     *
     * @param identifier
     *            The identifier of the prop
     */
    private RPropNumber(String identifier) {
        super(identifier);
    }

    /**
     * A Number prop
     *
     * @param identifier
     *            The identifier of the prop
     * @param number
     *            The number
     */
    public RPropNumber(String identifier, long number) {
        super(identifier);

        this.number = number;
    }

    @Override
    public String getName() {
        return "String";
    }

    @Override
    public byte[] write() {
        return Reprops.fromLongToByteArray(number);
    }

    @Override
    public void read(byte[] bytes) {
        this.number = Reprops.fromByteArrayToLong(bytes);
    }

    /**
     * Set the number of the prop
     *
     * @param number
     *            The prop number
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * Return the prop number
     *
     * @return The number
     */
    public long getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

}
