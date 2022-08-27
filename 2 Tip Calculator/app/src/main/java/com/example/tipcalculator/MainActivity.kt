package com.example.tipcalculator

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.components.InputField
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import com.example.tipcalculator.ulti.calculateTotalPerPerson
import com.example.tipcalculator.widgets.RoundIconButton
import com.example.tipcalculator.ulti.calculateTotalTip as calculateTotalTip1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            myApp {

                MainContent()
            }
        }
    }
}
@Composable
fun myApp(content : @Composable () -> Unit){
    TipCalculatorTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}
@Composable
fun TopHeader(totalPerPerson: Double = 0.0){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(170.dp)
        .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 15.dp)
        .clip(RoundedCornerShape(12.dp))
        ,color = Color(0xFFE9d7f7)
    ) {
        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total Per Person"
                , style = MaterialTheme.typography.h5)
            Text(text = "$$total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        myApp {
            TopHeader()
        }
    }
}


@Preview
@Composable
fun MainContent(){


        BillForm(){billAmt->
            Log.d("AMT" , "Main content $billAmt")
    }


}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier,
             onValChange:(String)->Unit = {}){
    val totalBillState = remember{
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()

    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPosition  = remember {
        mutableStateOf(0f)
    }
    val splitByState = remember {
        mutableStateOf(1)
    }
    val tipPercentage = (sliderPosition.value*100).toInt()

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }

    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
        , shape = RoundedCornerShape(CornerSize(8.dp))
        , border = BorderStroke(1.dp, color = Color.LightGray)

    ) { Column(modifier = Modifier.padding(6.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start ) {
            TopHeader(totalPerPersonState.value)
            InputField(valueState = totalBillState,
                labelID ="Enter Bill" ,
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if (!validState) return@KeyboardActions
                    //Todo - onvaluechange
                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()
                })
            if (validState){
                Row(modifier = Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start,
                    ) {
                      Text(
                          text = "Split",
                          modifier = Modifier.align(
                              alignment =Alignment.CenterVertically
                                                ))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End) {
                        RoundIconButton(
                            imageVector = Icons.Default.Remove,
                            onclick = {
                                splitByState.value =
                                if(splitByState.value > 1) splitByState.value - 1 else 1
                                totalPerPersonState.value =   calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitBy =splitByState.value )


                            })
                        Text(text = "${splitByState.value}",
                            modifier = Modifier
                                .align(CenterVertically)
                                .padding(start = 9.dp, end = 9.dp))

                        RoundIconButton(
                            imageVector = Icons.Default.Add,
                            onclick = { splitByState.value = splitByState.value+1
                                totalPerPersonState.value =   calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitBy =splitByState.value )
                            })
                    }
                }

            Row(modifier = Modifier.padding(horizontal = 3.dp,
                                            vertical = 12.dp)

            ) {
                Text(text = "Tip",
                     modifier = Modifier.align(alignment = Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(200.dp))
                
                Text(text = "$ ${tipAmountState.value}",modifier = Modifier.align(alignment = Alignment.CenterVertically))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "$tipPercentage %")
                Spacer(modifier = Modifier.height(14.dp))

                Slider(value = sliderPosition.value,
                    onValueChange = {newVal ->
                        sliderPosition.value = newVal
                        tipAmountState.value =
                            calculateTotalTip1(totalBill = totalBillState.value.toDouble()
                            ,tipPercentage = tipPercentage)
                        totalPerPersonState.value =   calculateTotalPerPerson(
                            totalBill = totalBillState.value.toDouble(),
                            tipPercentage = tipPercentage,
                            splitBy =splitByState.value )

                        },
                    modifier = Modifier.padding(start=16.dp,end = 16.dp),
                    steps = 5
                )
            }}
            else{
                Box() {}
            }
        }

    }

}


