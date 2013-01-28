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
oTest.fnStart("6872 - mDataProp and sDefaultContent for deep objects");

$(document).ready(function () {
    var test = false;

    $.fn.dataTable.ext.sErrMode = "throw";


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Shallow properties
     */

    $('#example').dataTable({
        "aaData": [
            {
                "a": "a",
                "b": "b",
                "c": "c",
                "d": "d",
                "e": "e"
            }
        ],
        "aoColumns": [
            { "mDataProp": "a" },
            { "mDataProp": "b" },
            { "mDataProp": "c" },
            { "mDataProp": "d" },
            { "mDataProp": "e" }
        ]
    });

    oTest.fnTest(
        "Basic initialisation of objects works",
        null,
        function () {
            return $('#example tbody td:eq(0)').html() === "a";
        }
    );

    oTest.fnTest(
        "Error when property missing (no default content)",
        function () {
            oSession.fnRestore();
            test = false;

            try {
                $('#example').dataTable({
                    "aaData": [
                        {
                            "a": "a",
                            "b": "b",
                            "d": "d",
                            "e": "e"
                        }
                    ],
                    "aoColumns": [
                        { "mDataProp": "a" },
                        { "mDataProp": "b" },
                        { "mDataProp": "c" },
                        { "mDataProp": "d" },
                        { "mDataProp": "e" }
                    ]
                });
            } catch (e) {
                test = true;
            }
        },
        function () {
            return test;
        }
    );

    oTest.fnTest(
        "Default content used for missing prop and no error",
        function () {
            oSession.fnRestore();

            $('#example').dataTable({
                "aaData": [
                    {
                        "a": "a",
                        "b": "b",
                        "d": "d",
                        "e": "e"
                    }
                ],
                "aoColumns": [
                    { "mDataProp": "a" },
                    { "mDataProp": "b" },
                    { "mDataProp": "c", "sDefaultContent": "test" },
                    { "mDataProp": "d" },
                    { "mDataProp": "e" }
                ]
            });
        },
        function () {
            return $('#example tbody td:eq(2)').html() === "test";
        }
    );


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Deep properties with a single object
     */

    oTest.fnTest(
        "Basic test with deep properties",
        function () {
            oSession.fnRestore();

            $('#example').dataTable({
                "aaData": [
                    {
                        "z": {
                            "a": "a",
                            "b": "b",
                            "c": "c",
                            "d": "d",
                            "e": "e"
                        }
                    }
                ],
                "aoColumns": [
                    { "mDataProp": "z.a" },
                    { "mDataProp": "z.b" },
                    { "mDataProp": "z.c" },
                    { "mDataProp": "z.d" },
                    { "mDataProp": "z.e" }
                ]
            });
        },
        function () {
            return $('#example tbody td:eq(0)').html() === "a";
        }
    );

    oTest.fnTest(
        "Error when property missing on deep get (no default content)",
        function () {
            oSession.fnRestore();
            test = false;

            try {
                $('#example').dataTable({
                    "aaData": [
                        {
                            "z": {
                                "a": "a",
                                "b": "b",
                                "c": "c",
                                "e": "e"
                            }
                        }
                    ],
                    "aoColumns": [
                        { "mDataProp": "z.a" },
                        { "mDataProp": "z.b" },
                        { "mDataProp": "z.c" },
                        { "mDataProp": "z.d" },
                        { "mDataProp": "z.e" }
                    ]
                });
            } catch (e) {
                test = true;
            }
        },
        function () {
            return test;
        }
    );

    oTest.fnTest(
        "Default content used for missing prop on deep get and no error",
        function () {
            oSession.fnRestore();

            $('#example').dataTable({
                "aaData": [
                    {
                        "z": {
                            "a": "a",
                            "b": "b",
                            "c": "c",
                            "e": "e"
                        }
                    }
                ],
                "aoColumns": [
                    { "mDataProp": "z.a" },
                    { "mDataProp": "z.b" },
                    { "mDataProp": "z.c" },
                    { "mDataProp": "z.d", "sDefaultContent": "test" },
                    { "mDataProp": "z.e" }
                ]
            });
        },
        function () {
            return $('#example tbody td:eq(3)').html() === "test";
        }
    );


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Deep properties with individual objects
     */

    oTest.fnTest(
        "Basic test with deep individual properties",
        function () {
            oSession.fnRestore();

            $('#example').dataTable({
                "aaData": [
                    {
                        "m": { "a": "a" },
                        "n": { "b": "b" },
                        "o": { "c": "c" },
                        "p": { "d": "d" },
                        "q": { "e": "e" }
                    }
                ],
                "aoColumns": [
                    { "mDataProp": "m.a" },
                    { "mDataProp": "n.b" },
                    { "mDataProp": "o.c" },
                    { "mDataProp": "p.d" },
                    { "mDataProp": "q.e" }
                ]
            });
        },
        function () {
            return $('#example tbody td:eq(0)').html() === "a";
        }
    );

    oTest.fnTest(
        "Error when property missing on deep individual get (no default content)",
        function () {
            oSession.fnRestore();
            test = false;

            try {
                $('#example').dataTable({
                    "aaData": [
                        {
                            "m": { "a": "a" },
                            "n": { "b": "b" },
                            "p": { "d": "d" },
                            "q": { "e": "e" }
                        }
                    ],
                    "aoColumns": [
                        { "mDataProp": "m.a" },
                        { "mDataProp": "n.b" },
                        { "mDataProp": "o.c" },
                        { "mDataProp": "p.d" },
                        { "mDataProp": "q.e" }
                    ]
                });
            } catch (e) {
                test = true;
            }
        },
        function () {
            return test;
        }
    );

    oTest.fnTest(
        "Default content used for missing prop on deep individual get and no error",
        function () {
            oSession.fnRestore();

            $('#example').dataTable({
                "aaData": [
                    {
                        "m": { "a": "a" },
                        "n": { "b": "b" },
                        "p": { "d": "d" },
                        "q": { "e": "e" }
                    }
                ],
                "aoColumns": [
                    { "mDataProp": "m.a" },
                    { "mDataProp": "n.b" },
                    { "mDataProp": "o.c", "sDefaultContent": "test" },
                    { "mDataProp": "p.d" },
                    { "mDataProp": "q.e" }
                ]
            });
        },
        function () {
            return $('#example tbody td:eq(2)').html() === "test";
        }
    );


    oTest.fnComplete();
});