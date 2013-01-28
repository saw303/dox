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
oTest.fnStart("oLanguage.sZeroRecords");

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
        "Zero records language is 'No matching records found' by default",
        null,
        function () {
            return oSettings.oLanguage.sZeroRecords == "No matching records found";
        }
    );

    oTest.fnWaitTest(
        "Text is shown when empty table (after filtering)",
        function () {
            oTable.fnFilter('nothinghere');
        },
        function () {
            return $('#example tbody tr td')[0].innerHTML == "No matching records found"
        }
    );


    oTest.fnWaitTest(
        "Zero records language can be defined",
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
                    "sZeroRecords": "unit test"
                }
            });
            oSettings = oTable.fnSettings();
        },
        function () {
            return oSettings.oLanguage.sZeroRecords == "unit test";
        }
    );

    oTest.fnWaitTest(
        "Text is shown when empty table (after filtering)",
        function () {
            oTable.fnFilter('nothinghere2');
        },
        function () {
            return $('#example tbody tr td')[0].innerHTML == "unit test"
        }
    );


    oTest.fnComplete();
});