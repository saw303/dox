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
oTest.fnStart("aoColumns.bSortable");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "bServerSide": true,
        "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "All columns are sortable by default",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "All others";
        }
    );

    oTest.fnWaitTest(
        "Can disable sorting from one column",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "bServerSide": true,
                "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
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
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "Firefox 1.0";
        }
    );

    oTest.fnWaitTest(
        "Disabled column has no sorting class",
        null,
        function () {
            return $('#example thead th:eq(1)').hasClass("sorting_asc") == false;
        }
    );

    oTest.fnWaitTest(
        "Other columns can still sort",
        function () {
            $('#example thead th:eq(4)').click();
            $('#example thead th:eq(4)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "Internet Explorer 4.0";
        }
    );

    oTest.fnWaitTest(
        "Disable sorting on multiple columns - no sorting classes",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "bServerSide": true,
                "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
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

    oTest.fnWaitTest(
        "Sorting on disabled column 1 has no effect",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "Firefox 1.0";
        }
    );

    oTest.fnWaitTest(
        "Sorting on disabled column 2 has no effect",
        function () {
            $('#example thead th:eq(3)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "Firefox 1.0";
        }
    );

    oTest.fnWaitTest(
        "Second sort on disabled column 2 has no effect",
        function () {
            $('#example thead th:eq(3)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "Firefox 1.0";
        }
    );

    oTest.fnWaitTest(
        "Even with multiple disabled sorting columns other columns can still sort",
        function () {
            $('#example thead th:eq(4)').click();
            $('#example thead th:eq(4)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "Internet Explorer 4.0";
        }
    );


    oTest.fnComplete();
});