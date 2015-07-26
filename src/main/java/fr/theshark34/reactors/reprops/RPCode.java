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

import fr.theshark34.reactors.rebyte.RCode;

/**
 * A Reprops RCode
 *
 * <p>
 *    It's the same as an {@link RCode} but it contains
 *    a Reprops instance
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public abstract class RPCode extends RCode {

    /**
     * The current Reprops instance
     */
    private Reprops reprops;

    /**
     * A Reprops Code
     *
     * @param reprops
     *            The current reprops instance
     */
    public RPCode(Reprops reprops) {
        this.reprops = reprops;
    }

    /**
     * Return the current Reprops instance
     *
     * @return The current Reprops
     */
    public Reprops getReprops() {
        return reprops;
    }

}
