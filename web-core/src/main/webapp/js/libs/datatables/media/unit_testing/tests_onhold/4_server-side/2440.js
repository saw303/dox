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
/*
 * NOTE: There are some differences in this zero config script for server-side
 * processing compared to the other data sources. The main reason for this is the
 * difference in how the server-side processing does it's filtering. Also the
 * sorting state is always reset on each draw.
 */
oTest.fnStart("Info element with display all");

$(document).ready(function () {
    var oTable = $('#example').dataTable({
        "bServerSide": true,
        "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
    });

    oTable.fnSettings()._iDisplayLength = -1;
    oTable.oApi._fnCalculateEnd(oTable.fnSettings());
    oTable.fnDraw();


    /* Basic checks */
    oTest.fnWaitTest(
        "Check length is correct when -1 length given",
        null,
        function () {
            return document.getElementById('example_info').innerHTML ==
                "Showing 1 to 57 of 57 entries";
        }
    );

    oTest.fnComplete();
});