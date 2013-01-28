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

// DATA_TEMPLATE: dom_data
oTest.fnStart("oLanguage.sInfoEmpty");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable();
    var oSettings = oTable.fnSettings();

    oTest.fnTest(
        "Info empty language is 'Showing 0 to 0 of 0 entries' by default",
        function () {
            oTable.fnFilter("nothinghere");
        },
        function () {
            return oSettings.oLanguage.sInfoEmpty == "Showing 0 to 0 of 0 entries";
        }
    );

    oTest.fnTest(
        "Info empty language default is in the DOM",
        null,
        function () {
            var bReturn = document.getElementById('example_info').innerHTML.replace(
                ' ' + oSettings.oLanguage.sInfoFiltered.replace('_MAX_', '57'), "") ==
                "Showing 0 to 0 of 0 entries";
            return bReturn;
        }
    );


    oTest.fnTest(
        "Info empty language can be defined",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "oLanguage": {
                    "sInfoEmpty": "unit test"
                }
            });
            oSettings = oTable.fnSettings();
            oTable.fnFilter("nothinghere");
        },
        function () {
            return oSettings.oLanguage.sInfoEmpty == "unit test";
        }
    );

    oTest.fnTest(
        "Info empty language default is in the DOM",
        null,
        function () {
            var bReturn = document.getElementById('example_info').innerHTML.replace(
                ' ' + oSettings.oLanguage.sInfoFiltered.replace('_MAX_', '57'), "") ==
                "unit test";
            return bReturn;
        }
    );


    oTest.fnTest(
        "Macro's replaced",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "oLanguage": {
                    "sInfoEmpty": "unit _START_ _END_ _TOTAL_ test"
                }
            });
            oTable.fnFilter("nothinghere");
        },
        function () {
            var bReturn = document.getElementById('example_info').innerHTML.replace(
                ' ' + oSettings.oLanguage.sInfoFiltered.replace('_MAX_', '57'), "") ==
                "unit 1 0 0 test";
            return bReturn;
        }
    );


    oTest.fnComplete();
});