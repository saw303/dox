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
 * each individual row. This is the object format used for the settings
 * aoData array.
 *  @namespace
 */
DataTable.models.oRow = {
    /**
     * TR element for the row
     *  @type node
     *  @default null
     */
    "nTr": null,

    /**
     * Data object from the original data source for the row. This is either
     * an array if using the traditional form of DataTables, or an object if
     * using mDataProp options. The exact type will depend on the passed in
     * data from the data source, or will be an array if using DOM a data
     * source.
     *  @type array|object
     *  @default []
     */
    "_aData": [],

    /**
     * Sorting data cache - this array is ostensibly the same length as the
     * number of columns (although each index is generated only as it is
     * needed), and holds the data that is used for sorting each column in the
     * row. We do this cache generation at the start of the sort in order that
     * the formatting of the sort data need be done only once for each cell
     * per sort. This array should not be read from or written to by anything
     * other than the master sorting methods.
     *  @type array
     *  @default []
     *  @private
     */
    "_aSortData": [],

    /**
     * Array of TD elements that are cached for hidden rows, so they can be
     * reinserted into the table if a column is made visible again (or to act
     * as a store if a column is made hidden). Only hidden columns have a
     * reference in the array. For non-hidden columns the value is either
     * undefined or null.
     *  @type array nodes
     *  @default []
     *  @private
     */
    "_anHidden": [],

    /**
     * Cache of the class name that DataTables has applied to the row, so we
     * can quickly look at this variable rather than needing to do a DOM check
     * on className for the nTr property.
     *  @type string
     *  @default <i>Empty string</i>
     *  @private
     */
    "_sRowStripe": ""
};
