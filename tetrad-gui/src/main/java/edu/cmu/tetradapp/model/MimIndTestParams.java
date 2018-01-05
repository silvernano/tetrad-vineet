///////////////////////////////////////////////////////////////////////////////
// For information as to what this class does, see the Javadoc, below.       //
// Copyright (C) 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006,       //
// 2007, 2008, 2009, 2010, 2014, 2015 by Peter Spirtes, Richard Scheines, Joseph   //
// Ramsey, and Clark Glymour.                                                //
//                                                                           //
// This program is free software; you can redistribute it and/or modify      //
// it under the terms of the GNU General Public License as published by      //
// the Free Software Foundation; either version 2 of the License, or         //
// (at your option) any later version.                                       //
//                                                                           //
// This program is distributed in the hope that it will be useful,           //
// but WITHOUT ANY WARRANTY; without even the implied warranty of            //
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             //
// GNU General Public License for more details.                              //
//                                                                           //
// You should have received a copy of the GNU General Public License         //
// along with this program; if not, write to the Free Software               //
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA //
///////////////////////////////////////////////////////////////////////////////

package edu.cmu.tetradapp.model;

import edu.cmu.tetrad.data.Clusters;
import edu.cmu.tetrad.data.IKnowledge;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.util.TetradSerializable;

import java.util.List;

/**
 * Stores basic independence test parameters.
 *
 * @author Joseph Ramsey
 */
public interface MimIndTestParams extends TetradSerializable {
    static final long serialVersionUID = 23L;

    /**
     * @return the alpha level of the test, in [0, 1].
     */
    double getAlpha();

    /**
     * Sets the alpha level of the test, in [0, 1].
     */
    void setAlpha(double alpha);

    /**
     * @return the most recently set list of variable names (Strings).
     */
    List getVarNames();

    /**
     * Sets the list of variable names (Strings).
     */
    void setVarNames(List<String> varNames);

    List<String> getLatentVarNames();

    void setLatentVarNames(List<String> latentVarNames);

    Graph getSourceGraph();

    IKnowledge getKnowledge();

    Clusters getClusters();
}





