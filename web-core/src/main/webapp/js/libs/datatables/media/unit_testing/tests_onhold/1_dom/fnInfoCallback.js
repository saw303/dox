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
oTest.fnStart("fnInfoCallback checks");

$(document).ready(function () {
    var mPass;

    $('#example').dataTable();

    /* Basic checks */
    oTest.fnTest(
        "null by default",
        null,
        function () {
            return $('#example').dataTable().fnSettings().oLanguage.fnInfoCallback == null;
        }
    );

    oTest.fnTest(
        "Agrument length",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "fnInfoCallback": function (oS, iStart, iEnd, iMax, iTotal, sPre) {
                    mPass = arguments.length;
                    return sPre;
                }
            });
        },
        function () {
            return mPass == 6;
        }
    );

    oTest.fnTest(
        "Settings first",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "fnInfoCallback": function (oS, iStart, iEnd, iMax, iTotal, sPre) {
                    mPass = (oS == $('#example').dataTable().fnSettings()) ? true : false;
                    return sPre;
                }
            });
        },
        function () {
            return mPass;
        }
    );

    oTest.fnTest(
        "Start arg",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "fnInfoCallback": function (oS, iStart, iEnd, iMax, iTotal, sPre) {
                    return iStart;
                }
            });
        },
        function () {
            return $('#example_info').html() == "1";
        }
    );

    oTest.fnTest(
        "End arg",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "fnInfoCallback": function (oS, iStart, iEnd, iMax, iTotal, sPre) {
                    return iEnd;
                }
            });
        },
        function () {
            return $('#example_info').html() == "10";
        }
    );

    oTest.fnTest(
        "Max arg",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "fnInfoCallback": function (oS, iStart, iEnd, iMax, iTotal, sPre) {
                    return iMax;
                }
            });
        },
        function () {
            return $('#example_info').html() == "57";
        }
    );

    oTest.fnTest(
        "Max arg - filter",
        function () {
            $('#example').dataTable().fnFilter("1.0");
        },
        function () {
            return $('#example_info').html() == "57";
        }
    );

    oTest.fnTest(
        "Total arg",
        function () {
            $('#example').dataTable({
                "bDestroy": true,
                "fnInfoCallback": function (oS, iStart, iEnd, iMax, iTotal, sPre) {
                    return iTotal;
                }
            });
        },
        function () {
            return $('#example_info').html() == "57";
        }
    );

    oTest.fnTest(
        "Total arg - filter",
        function () {
            $('#example').dataTable().fnFilter("1.0");
        },
        function () {
            return $('#example_info').html() == "3";
        }
    );


    oTest.fnComplete();
});