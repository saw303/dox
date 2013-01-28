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

// DATA_TEMPLATE: empty_table
oTest.fnStart("aoColumns.bSeachable");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
        "aoColumns": [
            { "mDataProp": "engine" },
            { "mDataProp": "browser" },
            { "mDataProp": "platform" },
            { "mDataProp": "version" },
            { "mDataProp": "grade" }
        ]
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "Columns are searchable by default",
        function () {
            oTable.fnFilter("Camino");
        },
        function () {
            if ($('#example tbody tr:eq(0) td:eq(1)')[0])
                return $('#example tbody tr:eq(0) td:eq(1)').html().match(/Camino/);
            else
                return null;
        }
    );

    oTest.fnWaitTest(
        "Disabling sorting on a column removes it from the global filter",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumns": [
                    { "mDataProp": "engine" },
                    { "mDataProp": "browser", "bSearchable": false },
                    { "mDataProp": "platform" },
                    { "mDataProp": "version" },
                    { "mDataProp": "grade" }
                ]
            });
            oSettings = oTable.fnSettings();
            oTable.fnFilter("Camino");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "No matching records found";
        }
    );

    oTest.fnWaitTest(
        "Disabled on one column has no effect on other columns",
        function () {
            oTable.fnFilter("Webkit");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit";
        }
    );

    oTest.fnWaitTest(
        "Disable filtering on multiple columns",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumns": [
                    { "mDataProp": "engine", "bSearchable": false },
                    { "mDataProp": "browser", "bSearchable": false },
                    { "mDataProp": "platform" },
                    { "mDataProp": "version" },
                    { "mDataProp": "grade" }
                ]
            });
            oSettings = oTable.fnSettings();
            oTable.fnFilter("Webkit");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "No matching records found";
        }
    );

    oTest.fnWaitTest(
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