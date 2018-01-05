/*
 * Copyright (C) 2016 University of Pittsburgh.
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.cmu.tetrad.cli.validation;

import edu.cmu.tetrad.io.DataReader;
import edu.cmu.tetrad.io.TabularContinuousDataReader;
import edu.cmu.tetrad.data.DataSet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * Mar 18, 2016 11:44:11 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class NonZeroVarianceTest {

    @ClassRule
    public static TemporaryFolder tmpDir = new TemporaryFolder();

    public NonZeroVarianceTest() {
    }

    @AfterClass
    public static void tearDownClass() {
        tmpDir.delete();
    }

    /**
     * Test of validate method, of class NonZeroVariance.
     *
     * @throws IOException
     */
    @Test
    public void testValidate() throws IOException {
        System.out.println("validate");

        Path dataFile = Paths.get("test", "data", "zero_variance_vars", "sim_data_20vars_100cases.csv");

        char delimiter = ',';
        DataReader dataReader = new TabularContinuousDataReader(dataFile, delimiter);
        DataSet dataSet = dataReader.readInData();

        int numOfThreads = 2;
        String dirOut = tmpDir.newFolder("validation_zero_variance").toString();
        Path outputFile = Paths.get(dirOut, "output.txt");
        DataValidation dataValidation = new NonZeroVariance(dataSet, numOfThreads, outputFile);
        dataValidation.validate(System.err, false);

        String errMsg = outputFile.getFileName().toString() + " does not exist.";
        Assert.assertTrue(errMsg, Files.exists(outputFile, LinkOption.NOFOLLOW_LINKS));
    }

}
