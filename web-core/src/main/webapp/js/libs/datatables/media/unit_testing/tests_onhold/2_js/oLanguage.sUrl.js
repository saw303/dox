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
oTest.fnStart("oLanguage.sUrl");

/* Note that we only test the internal storage of language information pulled form a file here
 * as the other language tests will check it goes into the DOM correctly
 */

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "aaData": gaaData
    });
    var oSettings = oTable.fnSettings();

    oTest.fnTest(
        "sUrl is blank by default",
        null,
        function () {
            return oSettings.oLanguage.sUrl == "";
        }
    );


    oTest.fnWaitTest(
        "Loading of German file loads language information",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "aaData": gaaData,
                "oLanguage": {
                    "sUrl": "../../../examples/examples_support/de_DE.txt"
                }
            });
            oSettings = oTable.fnSettings();
        },
        function () {
            var bReturn =
                oSettings.oLanguage.sProcessing == "Bitte warten..." &&
                    oSettings.oLanguage.sLengthMenu == "_MENU_ Einträge anzeigen" &&
                    oSettings.oLanguage.sZeroRecords == "Keine Einträge vorhanden." &&
                    oSettings.oLanguage.sInfo == "_START_ bis _END_ von _TOTAL_ Einträgen" &&
                    oSettings.oLanguage.sInfoEmpty == "0 bis 0 von 0 Einträgen" &&
                    oSettings.oLanguage.sInfoFiltered == "(gefiltert von _MAX_  Einträgen)" &&
                    oSettings.oLanguage.sInfoPostFix == "" &&
                    oSettings.oLanguage.sSearch == "Suchen" &&
                    oSettings.oLanguage.oPaginate.sFirst == "Erster" &&
                    oSettings.oLanguage.oPaginate.sPrevious == "Zurück" &&
                    oSettings.oLanguage.oPaginate.sNext == "Nächster" &&
                    oSettings.oLanguage.oPaginate.sLast == "Letzter";

            return bReturn;
        }
    );

    /* One DOM check just to ensure that they go into the DOM */
    oTest.fnTest(
        "Loaded language goes into the DOM",
        null,
        function () {
            return document.getElementById('example_info').innerHTML = "1 bis 10 von 57 Einträgen";
        }
    );


    oTest.fnComplete();
});