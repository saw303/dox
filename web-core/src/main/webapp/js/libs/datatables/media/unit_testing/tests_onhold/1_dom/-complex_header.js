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

// DATA_TEMPLATE: -complex_header
oTest.fnStart("Complex header");


$(document).ready(function () {
    $('#example').dataTable();

    oTest.fnTest(
        "Sorting on colspan has no effect",
        function () {
            $('#example thead th:eq(1)').click();
        },
        function () {
            return $('#example tbody tr td:eq(1)').html() == "Firefox 1.0";
        }
    );

    oTest.fnTest(
        "Sorting on non-unique TH and first TH has no effect",
        function () {
            $('#example thead th:eq(2)').click();
        },
        function () {
            return $('#example tbody tr td:eq(1)').html() == "Firefox 1.0";
        }
    );

    oTest.fnTest(
        "Sorting on non-unique TH and second TH will sort",
        function () {
            $('#example thead th:eq(6)').click();
        },
        function () {
            return $('#example tbody tr td:eq(4)').html() == "A";
        }
    );

    oTest.fnTest(
        "Sorting on non-unique TH and second TH will sort - reserve",
        function () {
            $('#example thead th:eq(6)').click();
        },
        function () {
            return $('#example tbody tr td:eq(4)').html() == "X";
        }
    );

    oTest.fnTest(
        "Sorting on unique TH will sort",
        function () {
            $('#example thead th:eq(5)').click();
        },
        function () {
            return $('#example tbody tr td:eq(3)').html() == "-";
        }
    );

    oTest.fnTest(
        "Sorting on unique TH will sort - reserve",
        function () {
            $('#example thead th:eq(5)').click();
        },
        function () {
            return $('#example tbody tr td:eq(3)').html() == "522.1";
        }
    );

    oTest.fnTest(
        "Sorting on unique rowspan TH will sort",
        function () {
            $('#example thead th:eq(0)').click();
        },
        function () {
            return $('#example tbody tr td:eq(0)').html() == "Gecko";
        }
    );


    oTest.fnComplete();
});