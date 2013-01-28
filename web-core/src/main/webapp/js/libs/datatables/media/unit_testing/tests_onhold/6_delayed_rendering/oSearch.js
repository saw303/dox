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
oTest.fnStart("oSearch");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
        "bDeferRender": true
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "Default values should be blank",
        null,
        function () {
            var bReturn = oSettings.oPreviousSearch.sSearch == "" && !oSettings.oPreviousSearch.bRegex;
            return bReturn;
        }
    );

    /* This test might be considered iffy since the full object isn't given, but it's reasonable to
     * expect DataTables to cope with this. It should just assumine regex false
     */
    oTest.fnWaitTest(
        "Search term only in object",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "oSearch": {
                    "sSearch": "Mozilla"
                }
            });
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Gecko";
        }
    );

    oTest.fnWaitTest(
        "New search will kill old one",
        function () {
            oTable.fnFilter("Opera");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Presto";
        }
    );

    oTest.fnWaitTest(
        "Search plain text term and escape regex true",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "oSearch": {
                    "sSearch": "DS",
                    "bRegex": false
                }
            });
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "Nintendo DS browser";
        }
    );

    oTest.fnWaitTest(
        "Search plain text term and escape regex false",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "oSearch": {
                    "sSearch": "Opera",
                    "bRegex": true
                }
            });
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Presto";
        }
    );

    oTest.fnWaitTest(
        "Search regex text term and escape regex true",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "oSearch": {
                    "sSearch": "1.*",
                    "bRegex": false
                }
            });
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "No matching records found";
        }
    );

    oTest.fnWaitTest(
        "Search regex text term and escape regex false",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "oSearch": {
                    "sSearch": "1.*",
                    "bRegex": true
                }
            });
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(0)').html() == "Gecko";
        }
    );


    oTest.fnComplete();
});