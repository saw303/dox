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
 * Generate the node required for the processing node
 *  @param {object} oSettings dataTables settings object
 *  @returns {node} Processing element
 *  @memberof DataTable#oApi
 */
function _fnFeatureHtmlProcessing(oSettings) {
    var nProcessing = document.createElement('div');

    if (!oSettings.aanFeatures.r) {
        nProcessing.id = oSettings.sTableId + '_processing';
    }
    nProcessing.innerHTML = oSettings.oLanguage.sProcessing;
    nProcessing.className = oSettings.oClasses.sProcessing;
    oSettings.nTable.parentNode.insertBefore(nProcessing, oSettings.nTable);

    return nProcessing;
}


/**
 * Display or hide the processing indicator
 *  @param {object} oSettings dataTables settings object
 *  @param {bool} bShow Show the processing indicator (true) or not (false)
 *  @memberof DataTable#oApi
 */
function _fnProcessingDisplay(oSettings, bShow) {
    if (oSettings.oFeatures.bProcessing) {
        var an = oSettings.aanFeatures.r;
        for (var i = 0, iLen = an.length; i < iLen; i++) {
            an[i].style.visibility = bShow ? "visible" : "hidden";
        }
    }

    $(oSettings.oInstance).trigger('processing', [oSettings, bShow]);
}

