package com.roush.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val TimeGap: String = "时间计算"

@Composable
fun App() {
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
fun item(name: String, onClick: (name: String) -> Unit,select:Boolean=false) {
    Button(onClick = {
        onClick.invoke(name)
    }) {
        Text(name, color = if(select) Color.Red else Color.White)
    }
}

@Composable
fun CommonTextItem(name: String, onClick: (name: String) -> Unit,select:Boolean=false) {
    Text(name, color = if(select) Color.Red else Color.Black, modifier = Modifier.clickable {
        onClick.invoke(name)
    })
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
    Box(modifier = Modifier.fillMaxSize()) {



        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val startTime by mutableStateOf("")
            val endTime by mutableStateOf("")
            val gapTime by mutableStateOf("")

            Box(modifier = Modifier.height(100.dp).background(Color.Red)){
                var showSelect by remember {
                    mutableStateOf(false)
                }
                Button(modifier = Modifier.padding(20.dp), onClick = {
                    showSelect=true
                }) {
                    Text(text = "开始时间 $startTime")
                }
                if(showSelect)
                    TimeSelectView(0,0, timeSelect = {
                        println("timeSelect-->$it")
                        showSelect=false
                    })



            }



            Button(modifier = Modifier.padding(20.dp), onClick = {


            }) {
                Text(text = "结束时间 $endTime")
            }
            Box(){
                Text(modifier = Modifier.padding(20.dp), text = "时间段 $gapTime")

            }

        }



    }
}
@Composable
fun TimeSelectView(hourSelect: Int, minuteSelect: Int, timeSelect: (totalMinute: Int) -> Unit,modifier:Modifier=Modifier) {
    Row(modifier = modifier.height(200.dp), horizontalArrangement = Arrangement.spacedBy(30.dp)) {

        var hour by remember {
            mutableStateOf(hourSelect)
        }

        var minute by remember {
            mutableStateOf(minuteSelect)
        }

        val hourClick: (name: String) -> Unit = {
            hour = it.toInt()
            timeSelect.invoke(hour * 60 + minute)
        }

        val minuteClick: (name: String) -> Unit = {
            minute = it.toInt()
            timeSelect.invoke(hour * 60 + minute)
        }


        Column {
            for (i in 0..11) {
                CommonTextItem(name = "$i", onClick = hourClick,select = (hour==i))
            }
        }
        Text("：")

        Column {
            for (i in 0..59) {
                CommonTextItem(name = "$i", onClick = minuteClick, select = (minute==i))
            }
        }
    }

}