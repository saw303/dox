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

/**
 * Template object for the way in which DataTables holds information about
 * search information for the global filter and individual column filters.
 *  @namespace
 */
DataTable.models.oSearch = {
    /**
     * Flag to indicate if the filtering should be case insensitive or not
     *  @type boolean
     *  @default true
     */
    "bCaseInsensitive": true,

    /**
     * Applied search term
     *  @type string
     *  @default <i>Empty string</i>
     */
    "sSearch": "",

    /**
     * Flag to indicate if the search term should be interpreted as a
     * regular expression (true) or not (false) and therefore and special
     * regex characters escaped.
     *  @type boolean
     *  @default false
     */
    "bRegex": false,

    /**
     * Flag to indicate if DataTables is to use its smart filtering or not.
     *  @type boolean
     *  @default true
     */
    "bSmart": true
};

