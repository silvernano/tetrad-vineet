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

import edu.cmu.tetrad.data.DataModel;
import edu.cmu.tetrad.data.DataModelList;
import edu.cmu.tetrad.data.DataSet;
import edu.cmu.tetrad.data.LogDataUtils;
import edu.cmu.tetrad.sem.GeneralizedSemIm;
import edu.cmu.tetrad.sem.SemIm;
import edu.cmu.tetrad.sem.Simulator;
import edu.cmu.tetrad.session.SessionModel;
import edu.cmu.tetrad.util.Params;
import edu.cmu.tetrad.util.RandomUtil;
import edu.cmu.tetrad.util.TetradSerializableUtils;

import java.rmi.MarshalledObject;


/**
 * Wraps a data model so that a random sample will automatically be drawn on
 * construction from a SemIm.
 *
 * @author Joseph Ramsey jdramsey@andrew.cmu.edu
 */
public class GeneralizedSemDataWrapper extends DataWrapper implements SessionModel {
    static final long serialVersionUID = 23L;
    private GeneralizedSemIm semIm = null;
    private SemDataParams params;
    private long seed;

    private transient DataModelList dataModelList;

    //==============================CONSTRUCTORS=============================//

    public GeneralizedSemDataWrapper(GeneralizedSemImWrapper wrapper, SemDataParams params) {
        GeneralizedSemIm semIm = null;

        try {
            semIm = new MarshalledObject<>(wrapper.getSemIm()).get();
        } catch (Exception e) {
            throw new RuntimeException("Could not clone the SEM IM.");
        }

        this.semIm = semIm;

        try {
            params = new MarshalledObject<>(params).get();
        } catch (Exception e) {
            throw new RuntimeException("Could not clone the SemDataParams.");
        }

        setParams(params);

        setSeed();

        this.setSourceGraph(semIm.getSemPm().getGraph());
        setParams(params);
        this.semIm = semIm;
        LogDataUtils.logDataModelList("Data simulated from a generalized SEM model.", getDataModelList());
    }

    public GeneralizedSemIm getSemIm() {
        return this.semIm;
    }

    /**
     * Generates a simple exemplar of this class to test serialization.
     *
     * @see TetradSerializableUtils
     */
    public static DataWrapper serializableInstance() {
        return new GeneralizedSemDataWrapper(GeneralizedSemImWrapper.serializableInstance(),
                SemDataParams.serializableInstance());
    }

    /**
     * Sets the data model.
     */
    public void setDataModel(DataModel dataModel) {
//        if (dataModel == null) {
//            dataModel = new ColtDataSet(0, new LinkedList<Node>());
//        }
//
//        if (dataModel instanceof DataModelList) {
//            this.dataModelList = (DataModelList) dataModel;
//        } else {
//            this.dataModelList = new DataModelList();
//            this.dataModelList.add(dataModel);
//        }

        // These are generated from seeds.
    }

    private DataModelList simulateData(Simulator simulator, SemDataParams params) {
        if (this.dataModelList != null) {
            return this.dataModelList;
        }

        DataModelList list = new DataModelList();
        int sampleSize = params.getSampleSize();
        boolean latentDataSaved = params.isLatentDataSaved();

        for (int i = 0; i < params.getNumDataSets(); i++) {
            DataSet dataSet = simulator.simulateData(sampleSize, seed, latentDataSaved);
            list.add(dataSet);
        }

        this.dataModelList = list;

        return list;
    }

    /**
     * @return the list of models.
     */
    public DataModelList getDataModelList() {
        return simulateData(semIm, params);
//        return this.dataModelList;
    }

    public void setDataModelList(DataModelList dataModelList) {
//        this.dataModelList = dataModelList;
    }

    public void setParams(Params params) {
        this.params = (SemDataParams) params;
    }

    private void setSeed() {
        this.seed = RandomUtil.getInstance().getSeed();
    }
}


