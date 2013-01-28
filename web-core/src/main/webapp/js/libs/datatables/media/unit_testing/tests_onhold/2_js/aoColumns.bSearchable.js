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

// DATA_TEMPLATE: js_data
oTest.fnStart("aoColumns.bSeachable");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "aaData": gaaData
    });
    var oSettings = oTable.fnSettings();

    oTest.fnTest(
        "Columns are searchable by default",
        function () {
            oTable.fnFilter("Camino");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html().match(/Camino/);
        }
    );

    oTest.fnTest(
        "Disabling sorting on a column removes it from the global filter",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "aaData": gaaData,
                "aoColumns": [
                    null,
                    { "bSearchable": false },
                    null,
                    null,
                    null
                ]
            });
            oSettings = oTable.fnSettings();
            oTable.fnFilter("Camino");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "No matching records found";
        }
    );

    oTest.fnTest(
        "Disabled on one column has no effect on other columns",
        function () {
            oTable.fnFilter("Webkit");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit";
        }
    );

    oTest.fnTest(
        "Disable filtering on multiple columns",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "aaData": gaaData,
                "aoColumns": [
                    { "bSearchable": false },
                    { "bSearchable": false },
                    null,
                    null,
                    null
                ]
            });
            oSettings = oTable.fnSettings();
            oTable.fnFilter("Webkit");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "No matching records found";
        }
    );

    oTest.fnTest(
        "Filter on second disabled column",
        function () {
            oTable.fnFilter("Camino");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "No matching records found";
        }
    );


    oTest.fnComplete();
});