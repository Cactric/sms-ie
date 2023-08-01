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
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

/**
 * The fragment for the export screen
 */
class ExportFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_export, container, false)

        val exportMessagesButton: Button = v.findViewById(R.id.export_messages_button)
        val exportCallLogButton: Button = v.findViewById(R.id.export_call_log_button)
        val exportContactsButton: Button = v.findViewById(R.id.export_contacts_button)

        exportMessagesButton.setOnClickListener { exportMessagesManual() }
        exportCallLogButton.setOnClickListener { exportCallLogManual() }
        exportContactsButton.setOnClickListener { exportContactsManual() }

        return v
    }

    private fun exportMessagesManual() {
        /*if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        )*/
        if (checkReadSMSContactsPermissions(this.requireActivity())) {
            val date = getCurrentDateTime()
            val dateInString = date.toString("yyyy-MM-dd")
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/zip"
                putExtra(Intent.EXTRA_TITLE, "messages-$dateInString.zip")
            }
            Log.d(LOG_TAG, "Starting file picker activity")
            requireActivity().startActivityForResult(intent, EXPORT_MESSAGES)
        } else {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.sms_permissions_required),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun exportCallLogManual() {
        if (checkReadCallLogsContactsPermissions(this.requireContext())) {
            val date = getCurrentDateTime()
            val dateInString = date.toString("yyyy-MM-dd")
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/json"
                putExtra(Intent.EXTRA_TITLE, "calls-$dateInString.json")
            }
            requireActivity().startActivityForResult(intent, EXPORT_CALL_LOG)
        } else {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.call_logs_permissions_required),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun exportContactsManual() {
        if (checkReadContactsPermission(this.requireContext())) {
            val date = getCurrentDateTime()
            val dateInString = date.toString("yyyy-MM-dd")
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/json"
                putExtra(Intent.EXTRA_TITLE, "contacts-$dateInString.json")
            }
            requireActivity().startActivityForResult(intent, EXPORT_CONTACTS)
        } else {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.contacts_read_permission_required),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}