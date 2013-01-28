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

// DATA_TEMPLATE: html_table
oTest.fnStart("HTML auto detect");

$(document).ready(function () {
    var oTable = $('#example').dataTable();

    oTest.fnTest(
        "Initial sort",
        null,
        function () {
            var ret =
                $('#example tbody tr:eq(0) td:eq(0)').html() == '1' &&
                    $('#example tbody tr:eq(1) td:eq(0)').html() == '2' &&
                    $('#example tbody tr:eq(2) td:eq(0)').html() == '3';
            return ret;
        }
    );

    oTest.fnTest(
        "HTML sort",
        function () {
            $('#example thead th:eq(1)').click()
        },
        function () {
            var ret =
                $('#example tbody tr:eq(0) td:eq(0)').html() == '2' &&
                    $('#example tbody tr:eq(1) td:eq(0)').html() == '1' &&
                    $('#example tbody tr:eq(2) td:eq(0)').html() == '4';
            return ret;
        }
    );

    oTest.fnTest(
        "HTML reverse sort",
        function () {
            $('#example thead th:eq(1)').click()
        },
        function () {
            var ret =
                $('#example tbody tr:eq(0) td:eq(0)').html() == '3' &&
                    $('#example tbody tr:eq(1) td:eq(0)').html() == '4' &&
                    $('#example tbody tr:eq(2) td:eq(0)').html() == '1';
            return ret;
        }
    );

    oTest.fnTest(
        "Numeric sort",
        function () {
            $('#example thead th:eq(0)').click()
        },
        function () {
            var ret =
                $('#example tbody tr:eq(0) td:eq(0)').html() == '1' &&
                    $('#example tbody tr:eq(1) td:eq(0)').html() == '2' &&
                    $('#example tbody tr:eq(2) td:eq(0)').html() == '3';
            return ret;
        }
    );


    oTest.fnComplete();
});