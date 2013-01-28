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
oTest.fnStart("bPaginate");

$(document).ready(function () {
    /* Check the default */
    $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
        "aoColumns": [
            { "mDataProp": "engine" },
            { "mDataProp": "browser" },
            { "mDataProp": "platform" },
            { "mDataProp": "version" },
            { "mDataProp": "grade" }
        ]
    });

    oTest.fnWaitTest(
        "Pagiantion div exists by default",
        null,
        function () {
            return document.getElementById('example_paginate') != null;
        }
    );

    oTest.fnWaitTest(
        "Information div takes paging into account",
        null,
        function () {
            return document.getElementById('example_info').innerHTML ==
                "Showing 1 to 10 of 57 entries";
        }
    );

    /* Check can disable */
    oTest.fnWaitTest(
        "Pagiantion can be disabled",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "bPaginate": false
            });
        },
        function () {
            return document.getElementById('example_paginate') == null;
        }
    );

    oTest.fnWaitTest(
        "Information div takes paging disabled into account",
        null,
        function () {
            return document.getElementById('example_info').innerHTML ==
                "Showing 1 to 57 of 57 entries";
        }
    );

    /* Enable makes no difference */
    oTest.fnWaitTest(
        "Pagiantion enabled override",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "bPaginate": true
            });
        },
        function () {
            return document.getElementById('example_paginate') != null;
        }
    );


    oTest.fnComplete();
});