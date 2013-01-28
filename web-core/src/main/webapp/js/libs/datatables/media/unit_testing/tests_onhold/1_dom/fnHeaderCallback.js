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
oTest.fnStart("fnHeaderCallback");

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable();
    var oSettings = oTable.fnSettings();
    var mPass;

    oTest.fnTest(
        "Default should be null",
        null,
        function () {
            return oSettings.fnHeaderCallback == null;
        }
    );


    oTest.fnTest(
        "Five arguments passed",
        function () {
            oSession.fnRestore();

            mPass = -1;
            $('#example').dataTable({
                "fnHeaderCallback": function () {
                    mPass = arguments.length;
                }
            });
        },
        function () {
            return mPass == 5;
        }
    );


    oTest.fnTest(
        "fnRowCallback called once per draw",
        function () {
            oSession.fnRestore();

            mPass = 0;
            $('#example').dataTable({
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    mPass++;
                }
            });
        },
        function () {
            return mPass == 1;
        }
    );

    oTest.fnTest(
        "fnRowCallback called on paging (i.e. another draw)",
        function () {
            $('#example_next').click();
        },
        function () {
            return mPass == 2;
        }
    );


    oTest.fnTest(
        "fnRowCallback allows us to alter row information",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    nHead.getElementsByTagName('th')[0].innerHTML = "Displaying " + (iEnd - iStart) + " records";
                }
            });
        },
        function () {
            return $('#example thead th:eq(0)').html() == "Displaying 10 records";
        }
    );


    oTest.fnTest(
        "Data array has length matching original data",
        function () {
            oSession.fnRestore();

            mPass = true;
            $('#example').dataTable({
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (aasData.length != 57) {
                        mPass = false;
                    }
                }
            });
        },
        function () {
            return mPass;
        }
    );

    oTest.fnTest(
        "Data array's column lengths match original data",
        function () {
            oSession.fnRestore();

            mPass = true;
            $('#example').dataTable({
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    for (var i = 0, iLen = aasData.length; i < iLen; i++) {
                        if (aasData[i].length != 5) {
                            mPass = false;
                        }
                    }
                }
            });
        },
        function () {
            return mPass;
        }
    );


    oTest.fnTest(
        "iStart correct on first page",
        function () {
            oSession.fnRestore();

            mPass = true;
            $('#example').dataTable({
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


    oTest.fnTest(
        "iStart correct on second page",
        function () {
            oSession.fnRestore();

            mPass = false;
            $('#example').dataTable({
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (iStart == 10) {
                        mPass = true;
                    }
                }
            });
            $('#example_next').click();
        },
        function () {
            return mPass;
        }
    );


    oTest.fnTest(
        "iEnd correct on first page",
        function () {
            oSession.fnRestore();

            mPass = true;
            $('#example').dataTable({
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (iEnd != 10) {
                        mPass = false;
                    }
                }
            });
        },
        function () {
            return mPass;
        }
    );


    oTest.fnTest(
        "iEnd correct on second page",
        function () {
            oSession.fnRestore();

            mPass = false;
            $('#example').dataTable({
                "fnHeaderCallback": function (nHead, aasData, iStart, iEnd, aiDisplay) {
                    if (iEnd == 20) {
                        mPass = true;
                    }
                }
            });
            $('#example_next').click();
        },
        function () {
            return mPass;
        }
    );


    oTest.fnTest(
        "aiDisplay length is full data when not filtered",
        function () {
            oSession.fnRestore();

            mPass = false;
            $('#example').dataTable({
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

    oTest.fnTest(
        "aiDisplay length is 9 when filtering on 'Mozilla'",
        function () {
            oSession.fnRestore();

            mPass = false;
            oTable = $('#example').dataTable({
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