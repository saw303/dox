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
oTest.fnStart("fnInitComplete");

/* Fairly boring function compared to the others! */

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable();
    var oSettings = oTable.fnSettings();
    var mPass;

    oTest.fnTest(
        "Default should be null",
        null,
        function () {
            return oSettings.fnInitComplete == null;
        }
    );


    oTest.fnTest(
        "Two arguments passed",
        function () {
            oSession.fnRestore();

            mPass = -1;
            $('#example').dataTable({
                "fnInitComplete": function () {
                    mPass = arguments.length === 2 && arguments[1] === undefined;
                }
            });
        },
        function () {
            return mPass;
        }
    );


    oTest.fnTest(
        "That one argument is the settings object",
        function () {
            oSession.fnRestore();

            oTable = $('#example').dataTable({
                "fnInitComplete": function (oSettings) {
                    mPass = oSettings;
                }
            });
        },
        function () {
            return oTable.fnSettings() == mPass;
        }
    );


    oTest.fnTest(
        "fnInitComplete called once on first draw",
        function () {
            oSession.fnRestore();

            mPass = 0;
            $('#example').dataTable({
                "fnInitComplete": function () {
                    mPass++;
                }
            });
        },
        function () {
            return mPass == 1;
        }
    );

    oTest.fnTest(
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