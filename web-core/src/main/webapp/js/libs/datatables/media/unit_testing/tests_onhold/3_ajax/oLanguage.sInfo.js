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
oTest.fnStart("oLanguage.sInfo");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "Info language is 'Showing _START_ to _END_ of _TOTAL_ entries' by default",
        null,
        function () {
            return oSettings.oLanguage.sInfo == "Showing _START_ to _END_ of _TOTAL_ entries";
        }
    );

    oTest.fnTest(
        "Info language default is in the DOM",
        null,
        function () {
            return document.getElementById('example_info').innerHTML = "Showing 1 to 10 of 57 entries";
        }
    );


    oTest.fnWaitTest(
        "Info language can be defined - without any macros",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "oLanguage": {
                    "sInfo": "unit test"
                }
            });
            oSettings = oTable.fnSettings();
        },
        function () {
            return oSettings.oLanguage.sInfo == "unit test";
        }
    );

    oTest.fnTest(
        "Info language definition is in the DOM",
        null,
        function () {
            return document.getElementById('example_info').innerHTML = "unit test";
        }
    );

    oTest.fnWaitTest(
        "Info language can be defined - with macro _START_ only",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "oLanguage": {
                    "sInfo": "unit _START_ test"
                }
            });
        },
        function () {
            return document.getElementById('example_info').innerHTML = "unit 1 test";
        }
    );

    oTest.fnWaitTest(
        "Info language can be defined - with macro _END_ only",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "oLanguage": {
                    "sInfo": "unit _END_ test"
                }
            });
        },
        function () {
            return document.getElementById('example_info').innerHTML = "unit 10 test";
        }
    );

    oTest.fnWaitTest(
        "Info language can be defined - with macro _TOTAL_ only",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "oLanguage": {
                    "sInfo": "unit _END_ test"
                }
            });
        },
        function () {
            return document.getElementById('example_info').innerHTML = "unit 57 test";
        }
    );

    oTest.fnWaitTest(
        "Info language can be defined - with macros _START_ and _END_",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "oLanguage": {
                    "sInfo": "unit _START_ _END_ test"
                }
            });
        },
        function () {
            return document.getElementById('example_info').innerHTML = "unit 1 10 test";
        }
    );

    oTest.fnWaitTest(
        "Info language can be defined - with macros _START_, _END_ and _TOTAL_",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "oLanguage": {
                    "sInfo": "unit _START_ _END_ _TOTAL_ test"
                }
            });
        },
        function () {
            return document.getElementById('example_info').innerHTML = "unit 1 10 57 test";
        }
    );


    oTest.fnComplete();
});