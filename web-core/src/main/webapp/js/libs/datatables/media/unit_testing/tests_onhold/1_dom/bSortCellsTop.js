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

// DATA_TEMPLATE: dom_data_two_headers
oTest.fnStart("bSortCellsTop");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable();
    var oSettings = oTable.fnSettings();

    oTest.fnTest(
        "Sorting class is on the bottom cells by default",
        null,
        function () {
            return $('#example thead tr:eq(1) th:eq(0)').hasClass('sorting_asc');
        }
    );

    oTest.fnTest(
        "Sorting is performed on bottom cells",
        function () {
            return $('#example thead tr:eq(1) th:eq(0)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit";
        }
    );

    oTest.fnTest(
        "Sorting class is updated on the bottom cells",
        null,
        function () {
            return $('#example thead tr:eq(1) th:eq(0)').hasClass('sorting_desc');
        }
    );

    oTest.fnTest(
        "Clicking on top cells has no effect",
        function () {
            return $('#example thead tr:eq(0) th:eq(0)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit";
        }
    );

    oTest.fnTest(
        "Clicking on another top cell has no effect",
        function () {
            return $('#example thead tr:eq(0) th:eq(3)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit";
        }
    );


    oTest.fnTest(
        "Sorting class is on the top cell when bSortCellsTop is true",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "bSortCellsTop": true
            });
        },
        function () {
            return $('#example thead tr:eq(0) th:eq(0)').hasClass('sorting_asc');
        }
    );

    oTest.fnTest(
        "Sorting is performed on top cells now",
        function () {
            return $('#example thead tr:eq(0) th:eq(0)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit";
        }
    );

    oTest.fnTest(
        "Sorting class is updated on the top cells",
        null,
        function () {
            return $('#example thead tr:eq(0) th:eq(0)').hasClass('sorting_desc');
        }
    );

    oTest.fnTest(
        "Clicking on bottom cells has no effect",
        function () {
            return $('#example thead tr:eq(1) th:eq(0)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit";
        }
    );

    oTest.fnTest(
        "Clicking on another bottom cell has no effect",
        function () {
            return $('#example thead tr:eq(1) th:eq(3)').click();
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit";
        }
    );


    oTest.fnComplete();
});