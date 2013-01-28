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
oTest.fnStart("2600 - Display rewind when changing length");

$(document).ready(function () {
    $('#example').dataTable({
        "bServerSide": true,
        "sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
    });

    oTest.fnWaitTest(
        "Info correct on init",
        null,
        function () {
            return $('#example_info').html() == "Showing 1 to 10 of 57 entries";
        }
    );

    oTest.fnWaitTest(
        "Page 2",
        function () {
            $('#example_next').click();
        },
        function () {
            return $('#example_info').html() == "Showing 11 to 20 of 57 entries";
        }
    );

    oTest.fnWaitTest(
        "Page 3",
        function () {
            $('#example_next').click();
        },
        function () {
            return $('#example_info').html() == "Showing 21 to 30 of 57 entries";
        }
    );

    oTest.fnWaitTest(
        "Page 4",
        function () {
            $('#example_next').click();
        },
        function () {
            return $('#example_info').html() == "Showing 31 to 40 of 57 entries";
        }
    );

    oTest.fnWaitTest(
        "Page 5",
        function () {
            $('#example_next').click();
        },
        function () {
            return $('#example_info').html() == "Showing 41 to 50 of 57 entries";
        }
    );

    oTest.fnWaitTest(
        "Rewind",
        function () {
            $('#example_length select').val('100');
            $('#example_length select').change();
        },
        function () {
            return $('#example_info').html() == "Showing 1 to 57 of 57 entries";
        }
    );

    oTest.fnComplete();
});