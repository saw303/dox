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
oTest.fnStart("Destroy with hidden columns");

$(document).ready(function () {
    var mTest;


    $('#example').dataTable({
        "bServerSide": true,
        "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
        "aoColumnDefs": [
            { "bSearchable": false, "bVisible": false, "aTargets": [ 2 ] },
            { "bVisible": false, "aTargets": [ 3 ] }
        ],
        "fnInitComplete": function () {
            this.fnDestroy();
        }
    });

    oTest.fnWaitTest(
        "Check that the number of columns in table is correct",
        null,
        function () {
            return $('#example tbody tr:eq(0) td').length == 5;
        }
    );


    oTest.fnTest(
        "And with scrolling",
        function () {
            $('#example').dataTable({
                "bServerSide": true,
                "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
                "sScrollY": 200,
                "aoColumnDefs": [
                    { "bSearchable": false, "bVisible": false, "aTargets": [ 2 ] },
                    { "bVisible": false, "aTargets": [ 3 ] }
                ],
                "fnInitComplete": function () {
                    this.fnDestroy();
                }
            });
        },
        function () {
            return $('#example tbody tr:eq(0) td').length == 5;
        }
    );

    oTest.fnComplete();
});