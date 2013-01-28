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
oTest.fnStart("iDraw - check that iDraw increments for each draw");


$(document).ready(function () {
    var oTable = $('#example').dataTable();
    var oSettings = oTable.fnSettings();

    oTest.fnTest(
        "After first draw, iDraw is 1",
        null,
        function () {
            return oSettings.iDraw == 1;
        }
    );

    oTest.fnTest(
        "After second draw, iDraw is 2",
        function () {
            oTable.fnDraw()
        },
        function () {
            return oSettings.iDraw == 2;
        }
    );

    oTest.fnTest(
        "After sort",
        function () {
            oTable.fnSort([
                [1, 'asc']
            ])
        },
        function () {
            return oSettings.iDraw == 3;
        }
    );

    oTest.fnTest(
        "After filter",
        function () {
            oTable.fnFilter('gecko')
        },
        function () {
            return oSettings.iDraw == 4;
        }
    );

    oTest.fnTest(
        "After another filter",
        function () {
            oTable.fnFilter('gec')
        },
        function () {
            return oSettings.iDraw == 5;
        }
    );


    oTest.fnComplete();
});