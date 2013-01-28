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
oTest.fnStart("2635 - Hiding column and state saving");

$(document).ready(function () {
    $('#example').dataTable({
        "bStateSave": true
    });

    oTest.fnTest(
        "Set the hidden column",
        function () {
            $('#example').dataTable().fnSetColumnVis(2, false);
        },
        function () {
            return $('#example thead th').length == 4;
        }
    );

    oTest.fnTest(
        "Destroy the table and remake it - checking one column was removed",
        function () {
            $('#example').dataTable({
                "bStateSave": true,
                "bDestroy": true
            });
        },
        function () {
            return $('#example thead th').length == 4;
        }
    );

    oTest.fnTest(
        "Do it again without state saving and make sure we are back to 5 columns",
        function () {
            $('#example').dataTable({
                "bDestroy": true
            });
        },
        function () {
            return $('#example thead th').length == 5;
        }
    );

    oTest.fnCookieDestroy($('#example').dataTable());
    oTest.fnComplete();
});