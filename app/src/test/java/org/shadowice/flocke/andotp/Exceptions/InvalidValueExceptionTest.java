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

package org.shadowice.flocke.andotp.Exceptions;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.shadowice.flocke.andotp.TestUtils.*;

@RunWith(DataProviderRunner.class)
public class InvalidValueExceptionTest {

    @DataProvider
    public static Object[][] messages() {
        return new Object[][] {
                {"Computer says no"},
                {"Siiiilent night, hooooly night"},
                {"Funny test data is important!"},
        };
    }

    @DataProvider
    public static Object[][] classes() {
        return new Object[][]{
                {InvalidValueException.class},
                {Integer.class},
                {String.class},
                {Object.class},
        };
    }

    @Test
    public void isInstanceOfException() {
        assertInstanceOf(Exception.class, new InvalidValueException());
    }

    @Test
    public void hasDefaultConstructor() {
        Exception ex = new InvalidValueException();

        assertNull(ex.getMessage());
    }

    @Test
    @UseDataProvider("messages")
    public void constructorAcceptsMessage(String message) {
        Exception ex = new InvalidValueException(message);

        assertEquals(message, ex.getMessage());
    }

    @Test
    @UseDataProvider("classes")
    public void constructorAcceptsClassAndReason(Class className) {
        String reason = "TEST";
        String expectedMessage = String.format(
                "Invalid value for \"%s\": %s",
                className, reason
        );

        Exception ex = new InvalidValueException(className, reason);

        assertEquals(expectedMessage, ex.getMessage());
    }

}
