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
oTest.fnStart("bServerSide");

/* Not interested in server-side processing here other than to check that it is off */

$(document).ready(function () {
    /* Check the default */
    var oTable = $('#example').dataTable({
        "sAjaxSource": "../../../examples/ajax/sources/objects.txt",
        "aoColumns": [
            { "mDataProp": "engine" },
            { "mDataProp": "browser" },
            { "mDataProp": "platform" },
            { "mDataProp": "version" },
            { "mDataProp": "grade" }
        ]
    });
    var oSettings = oTable.fnSettings();

    oTest.fnWaitTest(
        "Server side is off by default",
        null,
        function () {
            return oSettings.oFeatures.bServerSide == false;
        }
    );

    oTest.fnComplete();
});