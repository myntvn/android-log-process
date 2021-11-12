package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader

val log = mutableStateOf("")

@Preview
@Composable
fun Home() {

    val myLog by log

    MaterialTheme {
        Column(modifier = Modifier.padding(30.dp)) {

            Text("Path: " + getCurrentFilePath())

            Spacer(modifier = Modifier.height(20.dp))

            Row {

                // Clear device log
                Button(onClick = { clearDeviceLog() }) {
                    Text("Clear Device Log")
                }

                Spacer(modifier = Modifier.width(50.dp))

                // Get log
                Button(onClick = { getLogAndStore() }) {
                    Text("Start Get Log")
                }

                Spacer(modifier = Modifier.width(50.dp))

                // Stop log
                Button(onClick = { stopAdb() }) {
                    Text("Stop")
                }

                Spacer(modifier = Modifier.width(50.dp))

                // Open file
                Button(onClick = { stopAdb() }) {
                    Text("Open File")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(myLog)
        }
    }
}

fun getCurrentFilePath(): String {
    val process = Runtime.getRuntime().exec("pwd")
    val path = process?.inputReader()?.let {
        val bufferedReader = BufferedReader(it)
        bufferedReader.readLine()
    } ?: ""
    return "${path}/log.txt"
}

fun stopAdb() {
    Runtime.getRuntime().exec("adb kill-server")
    println("Stop")
}

fun clearDeviceLog() {
    Runtime.getRuntime().exec("adb logcat -c")
    println("Clear device log")
}

fun getLogAndStore() {
    val filePath = getCurrentFilePath()
    println("adb logcat > $filePath")
    Runtime.getRuntime().exec("adb logcat > $filePath")
    println("Start getting log")
}

//fun getLogAndPrint() {
//    println("Start get log")
//    GlobalScope.launch {
//        val process = Runtime.getRuntime().exec("adb logcat")
//        process?.inputReader()?.let {
//            val bufferedReader = BufferedReader(it)
//            while (true) {
//                val line = bufferedReader.readLine()
//                if (line != null) {
//                    println(line)
//                    log.value += "\r\n${line}"
//                } else break
//            }
//
//            println("End get log")
//        }
//    }
//}

fun getDevices() {
    val process = Runtime.getRuntime().exec("adb devices")
    process?.inputReader()?.let {
        val bufferedReader = BufferedReader(it)
        bufferedReader.readLine()
        while (true) {
            val line = bufferedReader.readLine()
            if (line != null) {
                println(line)
            } else break
        }
    }
}