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
oTest.fnStart("fnDrawCallback");

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
            return oSettings.fnDrawCallback == null;
        }
    );


    oTest.fnTest(
        "One argument passed",
        function () {
            oSession.fnRestore();

            mPass = -1;
            $('#example').dataTable({
                "fnDrawCallback": function () {
                    mPass = arguments.length;
                }
            });
        },
        function () {
            return mPass == 1;
        }
    );


    oTest.fnTest(
        "That one argument is the settings object",
        function () {
            oSession.fnRestore();

            oTable = $('#example').dataTable({
                "fnDrawCallback": function (oSettings) {
                    mPass = oSettings;
                }
            });
        },
        function () {
            return oTable.fnSettings() == mPass;
        }
    );


    oTest.fnTest(
        "fnRowCallback called once on first draw",
        function () {
            oSession.fnRestore();

            mPass = 0;
            $('#example').dataTable({
                "fnDrawCallback": function () {
                    mPass++;
                }
            });
        },
        function () {
            return mPass == 1;
        }
    );

    oTest.fnTest(
        "fnRowCallback called once on each draw there after as well",
        function () {
            $('#example_next').click();
            $('#example_next').click();
            $('#example_next').click();
        },
        function () {
            return mPass == 4;
        }
    );


    oTest.fnComplete();
});