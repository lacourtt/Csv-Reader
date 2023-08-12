package com.dicane.csvreader.datasource

import android.content.Context
import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.io.InputStream

open class CsvDatasource() {
    suspend fun fetchFromCsv(context: Context, scope: CoroutineScope) : List<Contact> = scope.async {
        val file = context.resources.openRawResource(
            R.raw.sample_contacts
        )
        readCsv(file)
    }.await()

    fun readCsv(inputStream: InputStream): List<Contact> {
        val reader = inputStream.bufferedReader()
        val header = reader.readLine()
        val fields: MutableList<String> = mutableListOf()

        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { row ->
                var start = 0
                var end = 0

                row.forEachIndexed { i, char ->
                    when {
                        char == '"' -> {
                            if(row[start] != '"') {
                                start = i
                            } else {
                                end = i
                                fields.add(
                                    row.substring(start, end + 1)
                                        .replace("\"", "")
                                )
                            }
                        }
                        char == ',' -> {
                            when {
                                start == 0 -> {
                                    end = i
                                    fields.add(
                                        row.substring(start, end + 1)
                                            .replace(",", "")
                                    )
                                    start = i
                                }
                                row[end] == '"' && row[start] == '"' -> {
                                    start = i
                                }
                                row[start] == ',' -> {
                                    end = i
                                    fields.add(
                                        row.substring(start, end + 1)
                                            .replace(",", "")
                                    )
                                    start = i
                                }
                            }

                        }
                        i == row.length - 1 -> {
                            end = i
                            fields.add(
                                row.substring(start, end + 1)
                                    .replace(",", "")
                            )
                        }
                    }
                }

                val contact = Contact(
                    null,
                    firstName = fields[0],
                    lastName = fields[1],
                    companyName = fields[2],
                    address = fields[3],
                    city = fields[4],
                    county = fields[5],
                    state = fields[6],
                    zip = fields[7],
                    phone1 = fields[8],
                    phone = fields[9],
                    email = fields[10],
                )

                fields.clear()

                contact
            }.toList()
    }
}