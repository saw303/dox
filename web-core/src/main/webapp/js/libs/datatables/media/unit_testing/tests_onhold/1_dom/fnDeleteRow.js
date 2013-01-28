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

// DATA_TEMPLATE: dom_data
oTest.fnStart("fnDeleteRow");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable();
    var oSettings = oTable.fnSettings();

    oTest.fnTest(
        "Check that the default data is sane",
        null,
        function () {
            return oSettings.asDataSearch.join(' ').match(/4.0/g).length == 3;
        }
    );

    oTest.fnTest(
        "Remove the first data row, and check that hte search data has been updated",
        function () {
            oTable.fnDeleteRow(0);
        },
        function () {
            return oSettings.asDataSearch.join(' ').match(/4.0/g).length == 2;
        }
    );

    oTest.fnTest(
        "Check that the info element has been updated",
        null,
        function () {
            return $('#example_info').html() == "Showing 1 to 10 of 56 entries";
        }
    );


    oTest.fnComplete();
});