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
import edu.cmu.tetrad.data.Knowledge2;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.search.TestType;
import edu.cmu.tetrad.util.TetradSerializableUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Stores the parameters needed for the BuildPureClusters search and wizard.
 *
 * @author Ricardo Silva rbas@cs.cmu.edu
 */
public final class FofcParams implements MimParams {
    static final long serialVersionUID = 23L;

    /**
     * @serial Cannot be null.
     */
    private IKnowledge knowledge = new Knowledge2();

    /**
     * @serial Cannot be null.
     */
    private Clusters clusters = new Clusters();

    /**
     * @serial Cannot be null.
     */
    private FofcIndTestParams indTestParams;

    /**
     * @serial Can be null.
     */
    private List varNames;

    /**
     * @serial Can be null.
     */
    private Graph sourceGraph;
    private double maxP = -1;
    private Graph maxGraph = null;
    private boolean showMaxPSelected;
    private boolean include3Clusters;
    private Graph maxFullGraph;
    private Clusters maxClusters;
    private double maxAlpha;


    //=================================CONSTRUCTORS========================//

    /**
     * Constructs a new parameter object. Must have a blank constructor.
     */
    public FofcParams() {
        indTestParams = new FofcIndTestParams();
    }

    /**
     * Generates a simple exemplar of this class to test serialization.
     *
     * @see TetradSerializableUtils
     */
    public static FofcParams serializableInstance() {
        return new FofcParams();
    }

    //=================================PUBLIC METHODS======================//

    /**
     * indTestParams is not in use yet
     */
    public MimIndTestParams getMimIndTestParams() {
        return indTestParams;
    }

    public List<String> getVarNames() {
        return this.varNames;
    }

    public void setVarNames(List<String> varNames) {
        this.varNames = varNames;
    }

    public Graph getSourceGraph() {
        return this.sourceGraph;
    }

    public void setSourceGraph(Graph graph) {
        this.sourceGraph = graph;
    }

    /**
     * Sets a new Knowledge2 for the algorithm. Not in use yet.
     */
    public void setKnowledge(IKnowledge knowledge) {
        if (knowledge == null) {
            throw new NullPointerException("Cannot set a null knowledge.");
        }

        this.knowledge = knowledge.copy();
    }

    public IKnowledge getKnowledge() {
        return knowledge;
    }

    public Clusters getClusters() {
        return this.clusters;
    }

    public void setClusters(Clusters clusters) {
        if (clusters == null) {
            throw new NullPointerException();
        }
        this.clusters = clusters;
    }

    /**
     * Gets the significance level for the search.
     */
    public double getAlpha() {
        return indTestParams.getAlpha();
    }

    /**
     * Sets the significance level for the search.
     */
    public void setAlpha(double alpha) {
        indTestParams.setAlpha(alpha);
    }

    /**
     * Gets the type of significance test.
     */
    public TestType getTetradTestType() {
        return indTestParams.getTetradTestType();
    }

    /**
     * Sets the type of significance test.
     */
    public void setTetradTestType(TestType tetradTestType) {
        indTestParams.setTetradTestType(tetradTestType);
    }

    /**
     * Gets the type of significance test.
     */
    public TestType getPurifyTestType() {
        return indTestParams.getPurifyTestType();
    }

    /**
     * Sets the type of significance test.
     */
    public void setPurifyTestType(TestType purifyTestType) {
        indTestParams.setPurifyTestType(purifyTestType);
    }

    public int getAlgorithmType() {
        throw new UnsupportedOperationException();
    }

    public void setAlgorithmType(int tt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getMaxP() {
        return maxP;
    }

    @Override
    public void setMaxP(double p) {
        this.maxP = p;
    }

    @Override
    public void setMaxStructureGraph(Graph maxGraph) {
        this.maxGraph = maxGraph;
    }

    public Graph getMaxStructureGraph() {
        return maxGraph;
    }

    @Override
    public boolean isShowMaxP() {
        return showMaxPSelected;
    }

    @Override
    public void setShowMaxP(boolean b) {
        this.showMaxPSelected = b;
    }

    @Override
    public boolean isInclude3Clusters() {
        return include3Clusters;
    }

    @Override
    public void setInclude3Clusters(boolean selected) {
        this.include3Clusters = selected;
    }

    @Override
    public void setMaxFullGraph(Graph fullGraph) {
        this.maxFullGraph = fullGraph;
    }

    public Graph getMaxFullGraph() {
        return this.maxFullGraph;
    }

    @Override
    public void setMaxClusters(Clusters clusters) {
        this.maxClusters = clusters;
    }

    @Override
    public void setMaxAlpha(double alpha) {
        this.maxAlpha = alpha;
    }

    @Override
    public Clusters getMaxClusters() {
        return this.maxClusters;
    }
}





