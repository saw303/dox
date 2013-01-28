/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

$.extend(DataTable.ext.aTypes, [
    /*
     * Function: -
     * Purpose:  Check to see if a string is numeric
     * Returns:  string:'numeric' or null
     * Inputs:   mixed:sText - string to check
     */
    function (sData) {
        /* Allow zero length strings as a number */
        if (typeof sData === 'number') {
            return 'numeric';
        }
        else if (typeof sData !== 'string') {
            return null;
        }

        var sValidFirstChars = "0123456789-";
        var sValidChars = "0123456789.";
        var Char;
        var bDecimal = false;

        /* Check for a valid first char (no period and allow negatives) */
        Char = sData.charAt(0);
        if (sValidFirstChars.indexOf(Char) == -1) {
            return null;
        }

        /* Check all the other characters are valid */
        for (var i = 1; i < sData.length; i++) {
            Char = sData.charAt(i);
            if (sValidChars.indexOf(Char) == -1) {
                return null;
            }

            /* Only allowed one decimal place... */
            if (Char == ".") {
                if (bDecimal) {
                    return null;
                }
                bDecimal = true;
            }
        }

        return 'numeric';
    },

    /*
     * Function: -
     * Purpose:  Check to see if a string is actually a formatted date
     * Returns:  string:'date' or null
     * Inputs:   string:sText - string to check
     */
    function (sData) {
        var iParse = Date.parse(sData);
        if ((iParse !== null && !isNaN(iParse)) || (typeof sData === 'string' && sData.length === 0)) {
            return 'date';
        }
        return null;
    },

    /*
     * Function: -
     * Purpose:  Check to see if a string should be treated as an HTML string
     * Returns:  string:'html' or null
     * Inputs:   string:sText - string to check
     */
    function (sData) {
        if (typeof sData === 'string' && sData.indexOf('<') != -1 && sData.indexOf('>') != -1) {
            return 'html';
        }
        return null;
    }
]);

