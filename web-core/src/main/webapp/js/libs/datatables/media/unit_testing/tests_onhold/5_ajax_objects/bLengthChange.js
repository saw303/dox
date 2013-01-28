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
oTest.fnStart("bLengthChange");

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
        "Length div exists by default",
        null,
        function () {
            return document.getElementById('example_length') != null;
        }
    );

    oTest.fnWaitTest(
        "Four default options",
        null,
        function () {
            return $("select[name=example_length] option").length == 4;
        }
    );

    oTest.fnWaitTest(
        "Default options",
        null,
        function () {
            var opts = $("select[name='example_length'] option");
            return opts[0].getAttribute('value') == 10 && opts[1].getAttribute('value') == 25 &&
                opts[2].getAttribute('value') == 50 && opts[3].getAttribute('value') == 100;
        }
    );

    oTest.fnWaitTest(
        "Info takes length into account",
        null,
        function () {
            return document.getElementById('example_info').innerHTML ==
                "Showing 1 to 10 of 57 entries";
        }
    );

    /* Check can disable */
    oTest.fnWaitTest(
        "Change length can be disabled",
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
                "bLengthChange": false
            });
        },
        function () {
            return document.getElementById('example_length') == null;
        }
    );

    oTest.fnWaitTest(
        "Information takes length disabled into account",
        null,
        function () {
            return document.getElementById('example_info').innerHTML ==
                "Showing 1 to 10 of 57 entries";
        }
    );

    /* Enable makes no difference */
    oTest.fnWaitTest(
        "Length change enabled override",
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
                "bLengthChange": true
            });
        },
        function () {
            return document.getElementById('example_length') != null;
        }
    );


    oTest.fnComplete();
});