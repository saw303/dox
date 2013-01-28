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
oTest.fnStart("fnDrawCallback");

/* Fairly boring function compared to the others! */

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
    });
    var oSettings = oTable.fnSettings();
    var mPass, bInit;

    oTest.fnWaitTest(
        "Default should be null",
        null,
        function () {
            return oSettings.fnDrawCallback == null;
        }
    );


    oTest.fnWaitTest(
        "One argument passed",
        function () {
            oSession.fnRestore();

            mPass = -1;
            bInit = false;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "fnDrawCallback": function () {
                    mPass = arguments.length;
                },
                "fnInitComplete": function () {
                    bInit = true;
                }
            });
        },
        function () {
            return mPass == 1 && bInit;
        }
    );


    oTest.fnWaitTest(
        "That one argument is the settings object",
        function () {
            oSession.fnRestore();

            bInit = false;
            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "fnDrawCallback": function (oSettings) {
                    mPass = oSettings;
                },
                "fnInitComplete": function () {
                    bInit = true;
                }
            });
        },
        function () {
            return oTable.fnSettings() == mPass && bInit;
        }
    );


    /* The draw callback is called once for the init and then when the data is added */
    oTest.fnWaitTest(
        "fnRowCallback called once on first draw",
        function () {
            oSession.fnRestore();

            mPass = 0;
            bInit = false;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "fnDrawCallback": function () {
                    mPass++;
                },
                "fnInitComplete": function () {
                    bInit = true;
                }
            });
        },
        function () {
            return mPass == 2 && bInit;
        }
    );

    oTest.fnWaitTest(
        "fnRowCallback called once on each draw there after as well",
        function () {
            $('#example_next').click();
            $('#example_next').click();
            $('#example_next').click();
        },
        function () {
            return mPass == 5;
        }
    );


    oTest.fnComplete();
});