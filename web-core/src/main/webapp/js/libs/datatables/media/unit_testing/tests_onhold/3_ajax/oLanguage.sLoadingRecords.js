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
oTest.fnStart("oLanguage.sLoadingRecords");

$(document).ready(function () {
    var tmp = false;
    oTest.fnTest(
        "Default loading text is 'Loading...'",
        function () {
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
            });
            tmp = $('#example tbody tr td')[0].innerHTML == "Loading...";
        },
        function () {
            return tmp;
        }
    );

    oTest.fnTest(
        "Text can be overriden",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "oLanguage": {
                    "sLoadingRecords": "unitest"
                },
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
            });
            tmp = $('#example tbody tr td')[0].innerHTML == "unitest";
        },
        function () {
            return tmp;
        }
    );

    oTest.fnTest(
        "When sZeroRecords is given but sLoadingRecords is not, sZeroRecords is used",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "oLanguage": {
                    "sZeroRecords": "unitest_sZeroRecords"
                },
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
            });
            tmp = $('#example tbody tr td')[0].innerHTML == "unitest_sZeroRecords";
        },
        function () {
            return tmp;
        }
    );

    oTest.fnTest(
        "sLoadingRecords and sZeroRecords both given",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "oLanguage": {
                    "sZeroRecords": "unitest_sZeroRecords2",
                    "sLoadingRecords": "unitest2"
                },
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
            });
            tmp = $('#example tbody tr td')[0].innerHTML == "unitest2";
        },
        function () {
            return tmp;
        }
    );


    oTest.fnComplete();
});