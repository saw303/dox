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
oTest.fnStart("oLanguage.sSearch");

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
        "Search language is 'Search:' by default",
        null,
        function () {
            return oSettings.oLanguage.sSearch == "Search:";
        }
    );

    oTest.fnTest(
        "A label input is used",
        null,
        function () {
            return $('label', oSettings.aanFeatures.f[0]).length == 1
        }
    );

    oTest.fnTest(
        "Search language default is in the DOM",
        null,
        function () {
            return $('label', oSettings.aanFeatures.f[0]).text()
                == "Search: ";
        }
    );


    oTest.fnWaitTest(
        "Search language can be defined",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "oLanguage": {
                    "sSearch": "unit test"
                }
            });
            oSettings = oTable.fnSettings();
        },
        function () {
            return oSettings.oLanguage.sSearch == "unit test";
        }
    );

    oTest.fnTest(
        "Info language definition is in the DOM",
        null,
        function () {
            return $('label', oSettings.aanFeatures.f[0]).text().indexOf('unit test') !== -1;
        }
    );


    oTest.fnWaitTest(
        "Blank search has a no space (separator) inserted",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "oLanguage": {
                    "sSearch": ""
                }
            });
            oSettings = oTable.fnSettings();
        },
        function () {
            return document.getElementById('example_filter').childNodes.length == 1;
        }
    );


    oTest.fnComplete();
});