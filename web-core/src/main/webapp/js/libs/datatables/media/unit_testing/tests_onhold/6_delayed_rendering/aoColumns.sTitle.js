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
oTest.fnStart("aoColumns.sTitle");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
        "bDeferRender": true
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "If not given, then the columns titles are empty",
        null,
        function () {
            var jqNodes = $('#example thead tr:eq(0) th');
            var bReturn =
                jqNodes[0].innerHTML == "Rendering engine" &&
                    jqNodes[1].innerHTML == "Browser" &&
                    jqNodes[2].innerHTML == "Platform(s)" &&
                    jqNodes[3].innerHTML == "Engine version" &&
                    jqNodes[4].innerHTML == "CSS grade";
            return bReturn;
        }
    );

    oTest.fnWaitTest(
        "Can set a single column title - and others are read from DOM",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "aoColumns": [
                    null,
                    { "sTitle": 'unit test' },
                    null,
                    null,
                    null
                ]
            });
        },
        function () {
            var jqNodes = $('#example thead tr:eq(0) th');
            var bReturn =
                jqNodes[0].innerHTML == "Rendering engine" &&
                    jqNodes[1].innerHTML == "unit test" &&
                    jqNodes[2].innerHTML == "Platform(s)" &&
                    jqNodes[3].innerHTML == "Engine version" &&
                    jqNodes[4].innerHTML == "CSS grade";
            return bReturn;
        }
    );

    oTest.fnWaitTest(
        "Can set multiple column titles",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "aoColumns": [
                    null,
                    { "sTitle": 'unit test 1' },
                    null,
                    null,
                    { "sTitle": 'unit test 2' }
                ]
            });
        },
        function () {
            var jqNodes = $('#example thead tr:eq(0) th');
            var bReturn =
                jqNodes[0].innerHTML == "Rendering engine" &&
                    jqNodes[1].innerHTML == "unit test 1" &&
                    jqNodes[2].innerHTML == "Platform(s)" &&
                    jqNodes[3].innerHTML == "Engine version" &&
                    jqNodes[4].innerHTML == "unit test 2";
            return bReturn;
        }
    );


    oTest.fnComplete();
});