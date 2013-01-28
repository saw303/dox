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
oTest.fnStart("aoColumns.bSeachable");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "bServerSide": true,
        "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "Columns are searchable by default",
        function () {
            oTable.fnFilter("Camino");
        },
        function () {
            return $('#example tbody tr:eq(0) td:eq(1)').html() == "Camino 1.0";
        }
    );

    /* NOT ACTUALLY GOING TO TEST BSEARCHABLE HERE. Reason being is that it requires the server
     * side to alter it's processing, and this information about columns is not actually sent to
     * the server
     */


    oTest.fnComplete();
});