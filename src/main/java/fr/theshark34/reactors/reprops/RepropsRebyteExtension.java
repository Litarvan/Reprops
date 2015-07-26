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

import fr.theshark34.reactors.rebyte.RebyteExtension;
import fr.theshark34.reactors.rebyte.Rebyte;

/**
 * The Reprops Rebyte Extension
 *
 * <p>
 *    The Reprops extension for Rebyte.
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public class RepropsRebyteExtension extends RebyteExtension {

    /**
     * The current reprops instance
     */
    private Reprops reprops;

    /**
     * The current reprops instance
     *
     * @param reprops
     *            The current reprops instance
     */
    public RepropsRebyteExtension(Reprops reprops) {
        this.reprops = reprops;
    }

    @Override
    public void onLoad(Rebyte rebyte) {
        this.addCode(new RCodePropStart(reprops));
    }

}
