/*
 * SMS Import / Export: a simple Android app for importing and exporting SMS messages from and to JSON files.
 * Copyright (c) 2021-2023 Thomas More
 *
 * This file is part of SMS Import / Export.
 *
 * SMS Import / Export is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SMS Import / Export is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SMS Import / Export.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.tmo1.sms_ie

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

/**
 * The fragment for the import screen
 */
class ImportFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_import, container, false)
        val importMessagesButton: Button = v.findViewById(R.id.import_messages_button)
        val importCallLogButton: Button = v.findViewById(R.id.import_call_log_button)
        val importContactsButton: Button = v.findViewById(R.id.import_contacts_button)
        importMessagesButton.setOnClickListener { importMessagesManual() }
        importCallLogButton.setOnClickListener { importCallLogManual() }
        importContactsButton.setOnClickListener { importContactsManual() }
        return v
    }

    private fun importMessagesManual() {
        if (Telephony.Sms.getDefaultSmsPackage(this.requireContext()) == requireActivity().packageName) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type =
                    if (Build.VERSION.SDK_INT < 29) "*/*" else "application/zip" //see https://github.com/tmo1/sms-ie/issues/3#issuecomment-900518890
            }
            requireActivity().startActivityForResult(intent, IMPORT_MESSAGES)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.default_sms_app_requirement),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun importCallLogManual() {
        if (checkReadWriteCallLogPermissions(this.requireContext())) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type =
                    if (Build.VERSION.SDK_INT < 29) "*/*" else "application/json" //see https://github.com/tmo1/sms-ie/issues/3#issuecomment-900518890
            }
            requireActivity().startActivityForResult(intent, IMPORT_CALL_LOG)
        } else {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.call_logs_read_write_permissions_required),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun importContactsManual() {
        if (checkWriteContactsPermission(this.requireContext())) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type =
                    if (Build.VERSION.SDK_INT < 29) "*/*" else "application/json" //see https://github.com/tmo1/sms-ie/issues/3#issuecomment-900518890
            }
            requireActivity().startActivityForResult(intent, IMPORT_CONTACTS)
        } else {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.contacts_write_permissions_required),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}