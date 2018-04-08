/*
 * Copyright (C) 2018 Björn Richter
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.shadowice.flocke.andotp.Utilities.ValueObjects;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
public class LabelTest {

    @DataProvider
    public static Object[][] validValues() {
        return new Object[][]{
                {"TEST_1"},
                {"TEST_2"},
                {"These aren't the droids you're looking for"},
        };
    }

    @Test
    @UseDataProvider("validValues")
    public void toStringReturnsValue(String labelString) {
        Label label = Label.fromString(labelString);

        assertEquals(labelString, label.toString());
    }

    @Test
    @UseDataProvider("validValues")
    public void canBeComparedWithString(String labelString) {
        Label label = Label.fromString(labelString);

        assertTrue(label.equals(labelString));
    }

    @Test
    @UseDataProvider("validValues")
    public void producesCorrectHashCode(String labelString) {
        Label label = Label.fromString(labelString);

        assertEquals(labelString.hashCode(), label.hashCode());
    }

    @Test
    public void emptyValueProducesException() {
        Label.fromString("");
    }
}

