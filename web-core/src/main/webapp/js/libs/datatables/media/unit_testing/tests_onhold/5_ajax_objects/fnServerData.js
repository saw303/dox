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
oTest.fnStart("fnServerData for Ajax sourced data");

$(document).ready(function () {
    var mPass;

    oTest.fnTest(
        "Argument length",
        function () {
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "fnServerData": function () {
                    mPass = arguments.length;
                }
            });
        },
        function () {
            return mPass == 4;
        }
    );

    oTest.fnTest(
        "Url",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "fnServerData": function (sUrl, aoData, fnCallback, oSettings) {
                    mPass = sUrl == "../../../examples/ajax/sources/objects.txt";
                }
            });
        },
        function () {
            return mPass;
        }
    );

    oTest.fnTest(
        "Data array",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "fnServerData": function (sUrl, aoData, fnCallback, oSettings) {
                    mPass = aoData.length == 0;
                }
            });
        },
        function () {
            return mPass;
        }
    );

    oTest.fnTest(
        "Callback function",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
                "aoColumnDefs": [
                    { "mDataProp": "engine", "aTargets": [0] },
                    { "mDataProp": "browser", "aTargets": [1] },
                    { "mDataProp": "platform", "aTargets": [2] },
                    { "mDataProp": "version", "aTargets": [3] },
                    { "mDataProp": "grade", "aTargets": [4] }
                ],
                "fnServerData": function (sUrl, aoData, fnCallback, oSettings) {
                    mPass = typeof fnCallback == 'function';
                }
            });
        },
        function () {
            return mPass;
        }
    );


    oTest.fnComplete();
});