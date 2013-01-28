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
oTest.fnStart("fnInitComplete");

/* Fairly boring function compared to the others! */

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
        "aoColumns": [
            { "mDataProp": "engine" },
            { "mDataProp": "browser" },
            { "mDataProp": "platform" },
            { "mDataProp": "version" },
            { "mDataProp": "grade" }
        ]
    });
    var oSettings = oTable.fnSettings();
    var mPass;

    oTest.fnWaitTest(
        "Default should be null",
        null,
        function () {
            return oSettings.fnInitComplete == null;
        }
    );


    oTest.fnWaitTest(
        "Two arguments passed (for Ajax!)",
        function () {
            oSession.fnRestore();

            mPass = -1;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "fnInitComplete": function () {
                    mPass = arguments.length;
                }
            });
        },
        function () {
            return mPass == 2;
        }
    );


    oTest.fnWaitTest(
        "That one argument is the settings object",
        function () {
            oSession.fnRestore();

            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "fnInitComplete": function (oSettings) {
                    mPass = oSettings;
                }
            });
        },
        function () {
            return oTable.fnSettings() == mPass;
        }
    );


    oTest.fnWaitTest(
        "fnInitComplete called once on first draw",
        function () {
            oSession.fnRestore();

            mPass = 0;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "fnInitComplete": function () {
                    mPass++;
                }
            });
        },
        function () {
            return mPass == 1;
        }
    );

    oTest.fnWaitTest(
        "fnInitComplete never called there after",
        function () {
            $('#example_next').click();
            $('#example_next').click();
            $('#example_next').click();
        },
        function () {
            return mPass == 1;
        }
    );


    oTest.fnWaitTest(
        "10 rows in the table on complete",
        function () {
            oSession.fnRestore();

            mPass = 0;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "fnInitComplete": function () {
                    mPass = $('#example tbody tr').length;
                }
            });
        },
        function () {
            return mPass == 10;
        }
    );


    oTest.fnComplete();
});