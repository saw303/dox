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
oTest.fnStart("iDisplayLength");

$(document).ready(function () {
    /* Check the default */
    $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
        "bDeferRender": true
    });

    oTest.fnWaitTest(
        "Default length is ten",
        null,
        function () {
            return $('#example tbody tr').length == 10;
        }
    );

    oTest.fnWaitTest(
        "Select menu shows 10",
        null,
        function () {
            return $('#example_length select').val() == 10;
        }
    );


    oTest.fnWaitTest(
        "Set initial length to 25",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "iDisplayLength": 25
            });
        },
        function () {
            return $('#example tbody tr').length == 25;
        }
    );

    oTest.fnWaitTest(
        "Select menu shows 25",
        null,
        function () {
            return $('#example_length select').val() == 25;
        }
    );


    oTest.fnWaitTest(
        "Set initial length to 100",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "iDisplayLength": 100
            });
        },
        function () {
            return $('#example tbody tr').length == 57;
        }
    );

    oTest.fnWaitTest(
        "Select menu shows 25",
        null,
        function () {
            return $('#example_length select').val() == 100;
        }
    );


    oTest.fnWaitTest(
        "Set initial length to 23 (unknown select menu length)",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "iDisplayLength": 23
            });
        },
        function () {
            return $('#example tbody tr').length == 23;
        }
    );

    oTest.fnWaitTest(
        "Select menu shows 10 (since 23 is unknow)",
        null,
        function () {
            return $('#example_length select').val() == 10;
        }
    );


    oTest.fnComplete();
});