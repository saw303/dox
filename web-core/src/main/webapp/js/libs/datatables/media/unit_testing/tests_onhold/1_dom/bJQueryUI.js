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
oTest.fnStart("bJQueryUI");

$(document).ready(function () {
    $('#example').dataTable({
        "bJQueryUI": true
    });

    oTest.fnTest(
        "Header elements are fully wrapped by DIVs",
        null,
        function () {
            var test = true;
            $('#example thead th').each(function () {
                if (this.childNodes > 1) {
                    test = false;
                }
            });
            return test;
        }
    );

    oTest.fnTest(
        "One div for each header element",
        null,
        function () {
            return $('#example thead th div').length == 5;
        }
    );

    oTest.fnTest(
        "One span for each header element, nested as child of div",
        null,
        function () {
            return $('#example thead th div>span').length == 5;
        }
    );

    oTest.fnComplete();
});