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
oTest.fnStart("aaSortingFixed");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
        "bDeferRender": true
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "No fixed sorting by default",
        null,
        function () {
            return oSettings.aaSortingFixed == null;
        }
    );


    oTest.fnWaitTest(
        "Fixed sorting on first column (string/asc) with user sorting on second column (string/asc)",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "aaSortingFixed": [
                    ['0', 'asc']
                ],
                "fnInitComplete": function () {
                    $('#example thead th:eq(1)').click();
                }
            });
            //
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "Camino 1.0";
        }
    );

    oTest.fnWaitTest(
        "Fixed sorting on first column (string/asc) with user sorting on second column (string/desc)",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1";
        }
    );

    oTest.fnWaitTest(
        "Fixed sorting on fourth column (int/asc) with user sorting on second column (string/asc)",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "aaSortingFixed": [
                    ['3', 'asc']
                ]
            });
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "All others";
        }
    );

    oTest.fnWaitTest(
        "Fixed sorting on fourth column (int/asc) with user sorting on second column (string/desc)",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "PSP browser";
        }
    );


    oTest.fnComplete();
});