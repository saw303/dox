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
oTest.fnStart("aoColumns.sName");

/* This has no effect at all in DOM methods - so we just check that it has applied the name */

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
        "aoColumns": [
            null,
            null,
            null,
            { "sName": 'unit test' },
            null
        ]
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "Names are stored in the columns object",
        null,
        function () {
            return oSettings.aoColumns[3].sName == "unit test";
        }
    );


    oTest.fnComplete();
});