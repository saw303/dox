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

// DATA_TEMPLATE: 6776
oTest.fnStart("Actions on a scrolling table keep width");


$(document).ready(function () {
    var oTable = $('#example').dataTable({
        "bFilter": true,
        "bSort": true,
        "sScrollY": "100px",
        "bPaginate": false
    });

    var iWidth = $('div.dataTables_wrapper').width();

    oTest.fnTest(
        "First sort has no effect on width",
        function () {
            $('th:eq(1)').click();
        },
        function () {
            return $('div.dataTables_wrapper').width() == iWidth;
        }
    );

    oTest.fnTest(
        "Second sort has no effect on width",
        function () {
            $('th:eq(1)').click();
        },
        function () {
            return $('div.dataTables_wrapper').width() == iWidth;
        }
    );

    oTest.fnTest(
        "Third sort has no effect on width",
        function () {
            $('th:eq(2)').click();
        },
        function () {
            return $('div.dataTables_wrapper').width() == iWidth;
        }
    );

    oTest.fnTest(
        "Filter has no effect on width",
        function () {
            oTable.fnFilter('i');
        },
        function () {
            return $('div.dataTables_wrapper').width() == iWidth;
        }
    );

    oTest.fnTest(
        "Filter 2 has no effect on width",
        function () {
            oTable.fnFilter('in');
        },
        function () {
            return $('div.dataTables_wrapper').width() == iWidth;
        }
    );

    oTest.fnTest(
        "No result filter has header and body at same width",
        function () {
            oTable.fnFilter('xxx');
        },
        function () {
            return $('#example').width() == $('div.dataTables_scrollHeadInner').width();
        }
    );

    oTest.fnTest(
        "Filter with no results has no effect on width",
        function () {
            oTable.fnFilter('xxx');
        },
        function () {
            return $('div.dataTables_wrapper').width() == iWidth;
        }
    );

    oTest.fnTest(
        "Filter with no results has table equal to wrapper width",
        function () {
            oTable.fnFilter('xxx');
        },
        function () {
            return $('div.dataTables_wrapper').width() == $('#example').width();
        }
    );

    oTest.fnComplete();
});