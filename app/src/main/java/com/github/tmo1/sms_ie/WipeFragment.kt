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

import android.os.Bundle
import android.provider.Telephony
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

/**
 * The fragment used for the wipe screen
 */
class WipeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_wipe, container, false)
        val wipeAllMessagesButton: Button = v.findViewById(R.id.wipe_all_messages_button)
        wipeAllMessagesButton.setOnClickListener { wipeMessagesManual() }
        return v
    }

    private fun wipeMessagesManual() {
        if (Telephony.Sms.getDefaultSmsPackage(requireContext()) == requireActivity().packageName) {
            ConfirmWipeFragment().show(requireActivity().supportFragmentManager, "wipe")
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.default_sms_app_requirement),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}