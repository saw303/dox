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
oTest.fnStart("asStripeClasses");

$(document).ready(function () {
    /* Check the default */
    $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
        "bDeferRender": true
    });

    oTest.fnWaitTest(
        "Default row striping is applied",
        null,
        function () {
            return $('#example tbody tr:eq(0)').hasClass('odd') &&
                $('#example tbody tr:eq(1)').hasClass('even') &&
                $('#example tbody tr:eq(2)').hasClass('odd') &&
                $('#example tbody tr:eq(3)').hasClass('even');
        }
    );

    oTest.fnWaitTest(
        "Row striping on the second page",
        function () {
            $('#example_next').click();
        },
        function () {
            return $('#example tbody tr:eq(0)').hasClass('odd') &&
                $('#example tbody tr:eq(1)').hasClass('even') &&
                $('#example tbody tr:eq(2)').hasClass('odd') &&
                $('#example tbody tr:eq(3)').hasClass('even');
        }
    );

    /* No striping */
    oTest.fnWaitTest(
        "No row striping",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "asStripeClasses": []
            });
        },
        function () {
            if (typeof $('#example tbody tr:eq(1)')[0] == 'undefined') {
                /* Use the 'wait for' to allow this to become true */
                return false;
            }
            return $('#example tbody tr:eq(0)')[0].className == "" &&
                $('#example tbody tr:eq(1)')[0].className == "" &&
                $('#example tbody tr:eq(2)')[0].className == "" &&
                $('#example tbody tr:eq(3)')[0].className == "";
        }
    );

    /* Custom striping */
    oTest.fnWaitTest(
        "Custom striping [2]",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "asStripeClasses": [ 'test1', 'test2' ]
            });
        },
        function () {
            return $('#example tbody tr:eq(0)').hasClass('test1') &&
                $('#example tbody tr:eq(1)').hasClass('test2') &&
                $('#example tbody tr:eq(2)').hasClass('test1') &&
                $('#example tbody tr:eq(3)').hasClass('test2');
        }
    );


    /* long array of striping */
    oTest.fnWaitTest(
        "Custom striping [4]",
        function () {
            oSession.fnRestore();
            $('#example').dataTable({
                "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
                "bDeferRender": true,
                "asStripeClasses": [ 'test1', 'test2', 'test3', 'test4' ]
            });
        },
        function () {
            return $('#example tbody tr:eq(0)').hasClass('test1') &&
                $('#example tbody tr:eq(1)').hasClass('test2') &&
                $('#example tbody tr:eq(2)').hasClass('test3') &&
                $('#example tbody tr:eq(3)').hasClass('test4');
        }
    );

    oTest.fnWaitTest(
        "Custom striping is restarted on second page [2]",
        function () {
            $('#example_next').click();
        },
        function () {
            return $('#example tbody tr:eq(0)').hasClass('test1') &&
                $('#example tbody tr:eq(1)').hasClass('test2') &&
                $('#example tbody tr:eq(2)').hasClass('test3') &&
                $('#example tbody tr:eq(3)').hasClass('test4');
        }
    );


    oTest.fnComplete();
});