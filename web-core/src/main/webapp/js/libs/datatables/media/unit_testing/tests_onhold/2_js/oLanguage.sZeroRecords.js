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
oTest.fnStart("oLanguage.sZeroRecords");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "aaData": gaaData
    });
    var oSettings = oTable.fnSettings();

    oTest.fnTest(
        "Zero records language is 'No matching records found' by default",
        null,
        function () {
            return oSettings.oLanguage.sZeroRecords == "No matching records found";
        }
    );

    oTest.fnTest(
        "Text is shown when empty table (after filtering)",
        function () {
            oTable.fnFilter('nothinghere');
        },
        function () {
            return $('#example tbody tr td')[0].innerHTML == "No matching records found"
        }
    );


    oTest.fnTest(
        "Zero records language can be defined",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "aaData": gaaData,
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

    oTest.fnTest(
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