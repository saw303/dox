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
oTest.fnStart("aoColumns.bSortable");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "aaData": gaaData
    });
    var oSettings = oTable.fnSettings();

    oTest.fnTest(
        "All columns are sortable by default",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "All others";
        }
    );

    oTest.fnTest(
        "Can disable sorting from one column",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "aaData": gaaData,
                "aoColumns": [
                    null,
                    { "bSortable": false },
                    null,
                    null,
                    null
                ]
            });
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() != "All others";
        }
    );

    oTest.fnTest(
        "Disabled column has no sorting class",
        null,
        function () {
            return $('#example thead th:eq(1)').hasClass("sorting_asc") == false;
        }
    );

    oTest.fnTest(
        "Other columns can still sort",
        function () {
            $('#example thead th:eq(4)').click();
            $('#example thead th:eq(4)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(4)').html() == "X";
        }
    );

    oTest.fnTest(
        "Disable sorting on multiple columns - no sorting classes",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "aaData": gaaData,
                "aoColumns": [
                    null,
                    { "bSortable": false },
                    null,
                    { "bSortable": false },
                    null
                ]
            });
        },
        function () {
            var bReturn =
                $('#example thead th:eq(1)').hasClass("sorting") ||
                    $('#example thead th:eq(3)').hasClass("sorting")
            return bReturn == false;
        }
    );

    oTest.fnTest(
        "Sorting on disabled column 1 has no effect",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() != "All others";
        }
    );

    oTest.fnTest(
        "Sorting on disabled column 2 has no effect",
        function () {
            $('#example thead th:eq(3)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(3)').html() != "-";
        }
    );

    oTest.fnTest(
        "Second sort on disabled column 2 has no effect",
        function () {
            $('#example thead th:eq(3)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(3)').html() != "-";
        }
    );

    oTest.fnTest(
        "Even with multiple disabled sorting columns other columns can still sort",
        function () {
            $('#example thead th:eq(4)').click();
            $('#example thead th:eq(4)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(4)').html() == "X";
        }
    );


    oTest.fnComplete();
});