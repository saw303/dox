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
oTest.fnStart("oLanguage.oPaginate");

/* Note that the paging language information only has relevence in full numbers */

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "bServerSide": true,
        "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
        "sPaginationType": "full_numbers"
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "oLanguage.oPaginate defaults",
        null,
        function () {
            var bReturn =
                oSettings.oLanguage.oPaginate.sFirst == "First" &&
                    oSettings.oLanguage.oPaginate.sPrevious == "Previous" &&
                    oSettings.oLanguage.oPaginate.sNext == "Next" &&
                    oSettings.oLanguage.oPaginate.sLast == "Last";
            return bReturn;
        }
    );

    oTest.fnTest(
        "oLanguage.oPaginate defaults are in the DOM",
        null,
        function () {
            var bReturn =
                $('#example_paginate .first').html() == "First" &&
                    $('#example_paginate .previous').html() == "Previous" &&
                    $('#example_paginate .next').html() == "Next" &&
                    $('#example_paginate .last').html() == "Last";
            return bReturn;
        }
    );


    oTest.fnWaitTest(
        "oLanguage.oPaginate can be defined",
        function () {
            oSession.fnRestore();
            oTable = $('#example').dataTable({
                "bServerSide": true,
                "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
                "sPaginationType": "full_numbers",
                "oLanguage": {
                    "oPaginate": {
                        "sFirst": "unit1",
                        "sPrevious": "test2",
                        "sNext": "unit3",
                        "sLast": "test4"
                    }
                }
            });
            oSettings = oTable.fnSettings();
        },
        function () {
            var bReturn =
                oSettings.oLanguage.oPaginate.sFirst == "unit1" &&
                    oSettings.oLanguage.oPaginate.sPrevious == "test2" &&
                    oSettings.oLanguage.oPaginate.sNext == "unit3" &&
                    oSettings.oLanguage.oPaginate.sLast == "test4";
            return bReturn;
        }
    );

    oTest.fnTest(
        "oLanguage.oPaginate definitions are in the DOM",
        null,
        function () {
            var bReturn =
                $('#example_paginate .first').html() == "unit1" &&
                    $('#example_paginate .previous').html() == "test2" &&
                    $('#example_paginate .next').html() == "unit3" &&
                    $('#example_paginate .last').html() == "test4";
            return bReturn;
        }
    );


    oTest.fnComplete();
});