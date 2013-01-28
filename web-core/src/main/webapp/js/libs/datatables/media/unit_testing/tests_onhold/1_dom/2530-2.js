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
oTest.fnStart("User given with is left when no scrolling");

$(document).ready(function () {
    $('#example')[0].style.width = "80%";
    $('#example').dataTable();

    oTest.fnTest(
        "Check user width is left",
        null,
        function () {
            return $('#example').width() == 640;
        }
    );

    oTest.fnComplete();
});