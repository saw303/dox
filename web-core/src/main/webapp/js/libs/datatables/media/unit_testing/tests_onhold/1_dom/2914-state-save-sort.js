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
oTest.fnStart("2914 - State saving with an empty array");

$(document).ready(function () {
    document.cookie = "";
    $('#example').dataTable({
        "bStateSave": true,
        "aaSorting": []
    });

    oTest.fnTest(
        "No sort",
        null,
        function () {
            return $('#example tbody td:eq(3)').html() == "4";
        }
    );

    oTest.fnTest(
        "Next page",
        function () {
            $('#example').dataTable().fnPageChange('next');
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "Camino 1.0";
        }
    );

    oTest.fnTest(
        "Destroy the table and remake it - checking we are still on the next page",
        function () {
            $('#example').dataTable({
                "bStateSave": true,
                "aaSorting": [],
                "bDestroy": true
            });
        },
        function () {
            return $('#example tbody td:eq(1)').html() == "Camino 1.0";
        }
    );

    oTest.fnCookieDestroy($('#example').dataTable());
    oTest.fnComplete();
});