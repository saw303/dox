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

$.extend(DataTable.ext.oSort, {
    /*
     * text sorting
     */
    "string-pre": function (a) {
        if (typeof a != 'string') {
            a = (a !== null && a.toString) ? a.toString() : '';
        }
        return a.toLowerCase();
    },

    "string-asc": function (x, y) {
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    },

    "string-desc": function (x, y) {
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    },


    /*
     * html sorting (ignore html tags)
     */
    "html-pre": function (a) {
        return a.replace(/<.*?>/g, "").toLowerCase();
    },

    "html-asc": function (x, y) {
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    },

    "html-desc": function (x, y) {
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    },


    /*
     * date sorting
     */
    "date-pre": function (a) {
        var x = Date.parse(a);

        if (isNaN(x) || x === "") {
            x = Date.parse("01/01/1970 00:00:00");
        }
        return x;
    },

    "date-asc": function (x, y) {
        return x - y;
    },

    "date-desc": function (x, y) {
        return y - x;
    },


    /*
     * numerical sorting
     */
    "numeric-pre": function (a) {
        return (a == "-" || a === "") ? 0 : a * 1;
    },

    "numeric-asc": function (x, y) {
        return x - y;
    },

    "numeric-desc": function (x, y) {
        return y - x;
    }
});
