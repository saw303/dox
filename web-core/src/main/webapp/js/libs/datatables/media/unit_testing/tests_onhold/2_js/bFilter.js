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

// DATA_TEMPLATE: js_data
oTest.fnStart("bFilter");

$(document).ready(function () {
    /* Check the default */
    $('#example').dataTable({
        "aaData": gaaData
    });

    oTest.fnTest(
        "Filtering div exists by default",
        null,
        function () {
            return document.getElementById('example_filter') != null;
        }
    );

    /* Check can disable */
    oTest.fnTest(
        "Fltering can be disabled",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "aaData": gaaData,
                "bFilter": false
            });
        },
        function () {
            return document.getElementById('example_filter') == null;
        }
    );

    /* Enable makes no difference */
    oTest.fnTest(
        "Filtering enabled override",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "aaData": gaaData,
                "bFilter": true
            });
        },
        function () {
            return document.getElementById('example_filter') != null;
        }
    );


    oTest.fnComplete();
});