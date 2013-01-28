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

// DATA_TEMPLATE: deferred_table
/*
 */
oTest.fnStart("Defer loading tests");

$(document).ready(function () {
    var gotServerData = false;

    $('#example').dataTable({
        "iDeferLoading": 57,
        "bServerSide": true,
        "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
        "fnServerData": function (url, data, fn) {
            $.ajax({
                "url": url,
                "data": data,
                "success": function (json) {
                    gotServerData = true;
                    fn(json);
                },
                "dataType": "json",
                "cache": false
            });
        }
    });

    oTest.fnWaitTest(
        "10 rows shown on the first page",
        null,
        function () {
            return $('#example tbody tr').length == 10;
        }
    );

    oTest.fnWaitTest(
        "No request to the server yet",
        null,
        function () {
            return !gotServerData;
        }
    );

    oTest.fnTest(
        "Information on zero config",
        null,
        function () {
            return document.getElementById('example_info').innerHTML == "Showing 1 to 10 of 57 entries";
        }
    );

    oTest.fnWaitTest(
        "Initial data order retained",
        null,
        function () {
            return $('#example tbody td:eq(0)').html() == "Gecko";
        }
    );

    oTest.fnWaitTest(
        "Initial data order retained 2",
        null,
        function () {
            return $('#example tbody td:eq(1)').html() == "Firefox 1.0";
        }
    );

    oTest.fnWaitTest(
        "Still no request to the server yet",
        null,
        function () {
            return !gotServerData;
        }
    );

    oTest.fnWaitTest(
        "Sorting (first click) on second column",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "All others";
        }
    );

    oTest.fnWaitTest(
        "Now we've had a request",
        null,
        function () {
            return gotServerData;
        }
    );

    oTest.fnTest(
        "Information after sort",
        null,
        function () {
            return document.getElementById('example_info').innerHTML == "Showing 1 to 10 of 57 entries";
        }
    );

    oTest.fnWaitTest(
        "Sorting (second click) on second column",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1";
        }
    );

    oTest.fnWaitTest(
        "Sorting (third click) on second column",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "All others";
        }
    );


    oTest.fnComplete();
});