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

import fr.theshark34.reactors.rebyte.RebyteMalformedBytesException;

/**
 * The RProp
 *
 * <p>
 *    A RProp is a property, that can be saved or read
 *    in a Rebyte file.
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public abstract class RProp {

    /**
     * The identifier of the props, each prop instance in a
     * file has an identifier to find it.
     */
    private String identifier;

    /**
     * A RProp
     *
     * @param identifier
     *            The identifier of the props (each prop instance
     *            as one to find it)
     */
    public RProp(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Return the name of the RProp
     *
     * @return The name of the prop
     */
    public abstract String getName();

    /**
     * Set the prop identifier (each prop as one to find it)
     *
     * @param identifier
     *            The new prop identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Return the identifier of the prop (each prop as one
     * to find it)
     *
     * @return The prop identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Write the prop to a file, by converting it
     * to a byte array.
     *
     * @return The byte array corresponding to the current prop.
     */
    public abstract byte[] write();

    /**
     * Read the prop data from the given bytes.
     *
     * @param bytes
     *            The read bytes, to convert to the RProp data
     */
    public abstract void read(byte[] bytes) throws RebyteMalformedBytesException;

}
