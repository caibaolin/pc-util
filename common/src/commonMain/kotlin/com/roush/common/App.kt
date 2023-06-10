package com.roush.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


val TimeGap: String = "时间计算"

@Composable
fun App() {
    println("app start--")
    var selectName by remember {
        mutableStateOf("")
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (selectName.isNullOrEmpty())
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item(TimeGap, onClick = { name ->
                    selectName = name
                })
            }
        else funPanel(selectName)
    }
}


@Composable
fun item(name: String, onClick: (name: String) -> Unit, select: Boolean = false) {
    Button(onClick = {
        onClick.invoke(name)
    }) {
        Text(name, color = if (select) Color.Red else Color.White)
    }
}

@Composable
fun CommonTextItem(name: String, onClick: (name: String) -> Unit, select: Boolean = false, hasLine: Boolean = false) {
    Text(name, color = if (select) Color.Red else Color.Black, modifier = Modifier.clickable {
        onClick.invoke(name)
    })
    if (hasLine) {
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.DarkGray))
    }
}

@Composable
fun funPanel(name: String) {
    if (name == TimeGap)
        timeGapPanel()
    else
        Text("暂无内容")
}

@Composable
fun timeGapPanel() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {


        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {

            var startHour by remember {
                mutableStateOf(0)
            }
            var startMinute by remember {
                mutableStateOf(0)
            }

            var endHour by remember {
                mutableStateOf(0)
            }
            var endMinute by remember {
                mutableStateOf(0)
            }


            var gapTime by mutableStateOf(0)


            TimeSelectView(hourSelect = startHour, minuteSelect = startMinute, timeSelect = { i, j ->
                startHour = i
                startMinute = j

            })
            Text(":")

            TimeSelectView(hourSelect = endHour, minuteSelect = endMinute, timeSelect = { i, j ->
                startHour = i
                startMinute = j
            })

            Box() {
                Text(modifier = Modifier.padding(20.dp), text = "时间段 $gapTime")

            }

        }


    }
}

@Composable
fun TimeSelectView(
    hourSelect: Int = 0, minuteSelect: Int = 0,
    timeSelect: (hour: Int, minute: Int) -> Unit = { hour, minute -> }, modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(Color.Yellow)) {

        var showSelect by remember {
            mutableStateOf(false)
        }

        var hour by remember {
            mutableStateOf(hourSelect)
        }

        var minute by remember {
            mutableStateOf(minuteSelect)
        }

        TextField("$hour:$minute", onValueChange = {

        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.clickable {
            showSelect = true
        })
        if (showSelect) {
            Row(modifier = modifier.height(200.dp), horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                val hourClick: (name: String) -> Unit = {
                    hour = it.toInt()
                    timeSelect.invoke(hour, minute)
                    showSelect = false
                }

                val minuteClick: (name: String) -> Unit = {
                    minute = it.toInt()
                    timeSelect.invoke(hour, minute)
                    showSelect = false
                }


                val state1 = rememberScrollState()
                Column(modifier.verticalScroll(state1)) {
                    for (i in 0..11) {
                        CommonTextItem(name = "$i", onClick = hourClick, select = (hour == i), hasLine = true)
                    }
                }
                Text("：")
                val state2 = rememberScrollState()

                Column(modifier.verticalScroll(state2)) {
                    for (i in 0..59) {
                        CommonTextItem(name = "$i", onClick = minuteClick, select = (minute == i), hasLine = true)
                    }
                }
            }
        }


    }

}