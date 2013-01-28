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
oTest.fnStart("fnHeaderCallback");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
        "bDeferRender": true
    });
    var oSettings = oTable.fnSettings();
    var mPass, bInit;

    oTest.fnWaitTest(
        "Default should be null",
        null,
        function () {
            return oSettings.fnHeaderCallback == null;
        }
    );


    oTest.fnWaitTest(
        "Five arguments passed",
        function () {
            oSession.fnRestore();

            mPass = -1;
            bInit = false;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "fnHeaderCallback": function () {
                    mPass = arguments.length;
                },
                "fnInitComplete": function () {
                    bInit = true;
                }
            });
        },
        function () {
            return mPass == 5 && bInit;
        }
    );


    /* The header callback is called once for the init and then when the data is added */
    oTest.fnWaitTest(
        "fnHeaderCallback called once per draw",
        function () {
            oSession.fnRestore();

            mPass = 0;
            bInit = false;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
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
        "fnRowCallback called on paging (i.e. another draw)",
        function () {
            $('#example_next').click();
        },
        function () {
            return mPass == 3;
        }
    );


    oTest.fnWaitTest(
        "fnRowCallback allows us to alter row information",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    nHead.getElementsByTagName('th')[0].innerHTML = "Displaying " + (iEnd - iStart) + " records";
                }
            });
        },
        function () {
            return $('#example thead th:eq(0)').html() == "Displaying 10 records";
        }
    );


    oTest.fnWaitTest(
        "iStart correct on first page",
        function () {
            oSession.fnRestore();

            mPass = true;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (iStart != 0) {
                        mPass = false;
                    }
                }
            });
        },
        function () {
            return mPass;
        }
    );


    oTest.fnWaitTest(
        "iStart correct on second page",
        function () {
            oSession.fnRestore();

            mPass = false;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (iStart == 10) {
                        mPass = true;
                    }
                },
                "fnInitComplete": function () {
                    $('#example_next').click();
                }
            });
        },
        function () {
            return mPass;
        }
    );


    oTest.fnWaitTest(
        "iEnd correct on second page",
        function () {
            oSession.fnRestore();

            mPass = false;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (iEnd == 20) {
                        mPass = true;
                    }
                },
                "fnInitComplete": function () {
                    $('#example_next').click();
                }
            });
        },
        function () {
            return mPass;
        }
    );


    oTest.fnWaitTest(
        "aiDisplay length is full data when not filtered",
        function () {
            oSession.fnRestore();

            mPass = false;
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (aiDisplay.length == 57) {
                        mPass = true;
                    }
                }
            });
        },
        function () {
            return mPass;
        }
    );

    oTest.fnWaitTest(
        "aiDisplay length is 9 when filtering on 'Mozilla'",
        function () {
            oSession.fnRestore();

            mPass = false;
            oTable = $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (aiDisplay.length == 9) {
                        mPass = true;
                    }
                }
            });
            oTable.fnFilter("Mozilla");
        },
        function () {
            return mPass;
        }
    );


    oTest.fnComplete();
});