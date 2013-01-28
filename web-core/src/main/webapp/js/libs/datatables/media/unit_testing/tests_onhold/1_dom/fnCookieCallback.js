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
oTest.fnStart("Cookie callback");


$(document).ready(function () {
    var mPass;
    /* Note that in order to be fully effective here for saving state, there would need to be a
     * stringify function to serialise the data array
     */

    oTest.fnTest(
        "null by default",
        function () {
            $('#example').dataTable();
        },
        function () {
            return $('#example').dataTable().fnSettings().fnCookieCallback == null;
        }
    );

    oTest.fnTest(
        "Number of arguments",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "bStateSave": true,
                "fnCookieCallback": function (sName, oData, sExpires, sPath) {
                    mPass = arguments.length;
                    return sName + "=; expires=" + sExpires + "; path=" + sPath;
                }
            });
        },
        function () {
            return mPass == 4;
        }
    );

    oTest.fnTest(
        "Name",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "bStateSave": true,
                "fnCookieCallback": function (sName, oData, sExpires, sPath) {
                    mPass = sName == "SpryMedia_DataTables_example_dom_data.php";
                    return sName + "=; expires=" + sExpires + "; path=" + sPath;
                }
            });
        },
        function () {
            return mPass;
        }
    );

    oTest.fnTest(
        "Data",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "bStateSave": true,
                "fnCookieCallback": function (sName, oData, sExpires, sPath) {
                    mPass = typeof oData.iStart != 'undefined';
                    return sName + "=; expires=" + sExpires + "; path=" + sPath;
                }
            });
        },
        function () {
            return mPass;
        }
    );

    oTest.fnTest(
        "Expires",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "bStateSave": true,
                "fnCookieCallback": function (sName, oData, sExpires, sPath) {
                    mPass = typeof sExpires == 'string';
                    return sName + "=; expires=" + sExpires + "; path=" + sPath;
                }
            });
        },
        function () {
            return mPass;
        }
    );

    oTest.fnTest(
        "Path",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "bStateSave": true,
                "fnCookieCallback": function (sName, oData, sExpires, sPath) {
                    mPass = sPath.match(/media\/unit_testing\/templates/);
                    return sName + "=; expires=" + sExpires + "; path=" + sPath;
                }
            });
        },
        function () {
            return mPass;
        }
    );


    oTest.fnCookieDestroy($('#example').dataTable());
    oTest.fnComplete();
});