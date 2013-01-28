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
oTest.fnStart("fnCreatedRow tests");

$(document).ready(function () {
    var tmp = 0;

    $('#example').dataTable({
        fnCreatedRow: function () {
            tmp++;
        }
    });

    oTest.fnTest(
        "Row created is called once for each row on init",
        null,
        function () {
            return tmp === 57;
        }
    );

    oTest.fnTest(
        "Created isn't called back on other draws",
        function () {
            $('#example th:eq(1)').click();
        },
        function () {
            return tmp === 57;
        }
    );

    oTest.fnTest(
        "Three arguments for the function",
        function () {
            oSession.fnRestore();
            tmp = true;

            $('#example').dataTable({
                fnCreatedRow: function () {
                    if (arguments.length !== 3) {
                        tmp = false;
                    }
                }
            });
        },
        function () {
            return tmp;
        }
    );

    oTest.fnTest(
        "First argument is a TR element",
        function () {
            oSession.fnRestore();
            tmp = true;

            $('#example').dataTable({
                fnCreatedRow: function () {
                    if (arguments[0].nodeName !== "TR") {
                        tmp = false;
                    }
                }
            });
        },
        function () {
            return tmp;
        }
    );

    oTest.fnTest(
        "Second argument is an array with 5 elements",
        function () {
            oSession.fnRestore();
            tmp = true;

            $('#example').dataTable({
                fnCreatedRow: function () {
                    if (arguments[1].length !== 5) {
                        tmp = false;
                    }
                }
            });
        },
        function () {
            return tmp;
        }
    );

    oTest.fnTest(
        "Third argument is the data source for the row",
        function () {
            oSession.fnRestore();
            tmp = true;

            $('#example').dataTable({
                fnCreatedRow: function () {
                    if (arguments[1] !== this.fnSettings().aoData[ arguments[2] ]._aData) {
                        tmp = false;
                    }
                }
            });
        },
        function () {
            return tmp;
        }
    );

    oTest.fnTest(
        "TR element is tied to the correct data",
        function () {
            oSession.fnRestore();
            tmp = false;

            $('#example').dataTable({
                fnCreatedRow: function (tr, data, index) {
                    if (data[1] === "Firefox 1.0") {
                        if ($('td:eq(3)', tr).html() == "1.7") {
                            tmp = true;
                        }
                    }
                }
            });
        },
        function () {
            return tmp;
        }
    );


    oTest.fnComplete();
});