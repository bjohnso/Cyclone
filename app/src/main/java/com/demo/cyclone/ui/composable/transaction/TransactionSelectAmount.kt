package com.demo.cyclone.ui.composable.transaction

import android.content.pm.ActivityInfo
import android.util.Log
import android.view.Window
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.solver.state.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.demo.cyclone.R
import com.demo.cyclone.extensions.ConfigurationExtensions.heightPercentageDP
import com.demo.cyclone.extensions.ConfigurationExtensions.heightPercentageSP
import com.demo.cyclone.extensions.ConfigurationExtensions.widthPercentageDP
import com.demo.cyclone.extensions.ConfigurationExtensions.widthPercentageSP
import com.demo.cyclone.extensions.ContextExtensions.activity
import com.demo.cyclone.extensions.StringExtensions.isDecimal
import com.demo.cyclone.interfaces.NavigatorInterface
import com.demo.cyclone.ui.composable.shared.LockScreenOrientation
import com.demo.cyclone.ui.composable.shared.SystemUi
import com.demo.cyclone.viewmodel.TransactionSelectAddressViewModel
import com.demo.cyclone.viewmodel.TransactionSelectAmountViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun TransactionSelectAmountScreen(
    window: Window,
    navigatorInterface: NavigatorInterface? = null
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val coroutine = rememberCoroutineScope()

    val viewModel = ViewModelProvider(
        LocalContext.current.activity() as ViewModelStoreOwner
    )[TransactionSelectAmountViewModel::class.java]

    LaunchedEffect(true) {
        viewModel.flowOnTransaction(context).collect {
            viewModel.updateTransaction(it)
        }
        viewModel.getUSDQuote(context)
    }

    SystemUi(
        window = window,
        statusBarColor = "#222222".toColorInt(),
        navigationBarColor = "#222222".toColorInt()
    )

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(topBar = { AppBar() }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color("#222222".toColorInt()),
                            Color("#222222".toColorInt()),
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                AddressInputFieldSelectAmount(viewModel.uiState.recipientAddress) { address -> }

                Divider(
                    color = Color("#A5A5A5".toColorInt()),
                    thickness = .5.dp
                )

                Spacer(modifier = Modifier.padding(bottom = 5.dp))

                AmountInputField(
                    viewModel.uiState.tokenAmount,
                    viewModel.getDisplayTokenAmount(),
                    viewModel.getDisplayUsdAmount(),

                ) { amount ->
                    viewModel.updateTokenAmount(amount)
                }

                Spacer(modifier = Modifier.padding(bottom = 5.dp))

                Divider(
                    modifier = Modifier.padding(bottom = 5.dp),
                    color = Color("#A5A5A5".toColorInt()),
                    thickness = .5.dp
                )

                BalancePreview()

                Spacer(modifier = Modifier.padding(bottom = 10.dp))

                NextButton {

                }
            }
        }
    }
}

@Composable
fun AppBar() {
    val configuration = LocalConfiguration.current

    TopAppBar(
        backgroundColor = Color("#222222".toColorInt()),
        contentColor = Color.White,
        elevation = 12.dp
    ) {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.padding(start = 5.dp))

        Text(
            text = "Enter Amount",
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    start = configuration.widthPercentageDP(5f),
                    end = configuration.widthPercentageDP(5f),
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            ClickableText(
                text = AnnotatedString(
                    text = "Next"
                ),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    color = Color("#707070".toColorInt()),
                    fontWeight = FontWeight.Bold
                ),
                onClick = {}
            )
        }
    }
}

@Composable
fun ColumnScope.BalancePreview() {
    val configuration = LocalConfiguration.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(start = configuration.widthPercentageDP(8f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Balance:",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color("#A5A5A5".toColorInt()),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                ),
                textAlign = TextAlign.Start
            )

            Text(
                text = "0 SOL",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                ),
                textAlign = TextAlign.Start
            )
        }

        Button(
            onClick = {
            },
            modifier = Modifier
                .padding(
                    start = configuration.widthPercentageDP(5f),
                    end = configuration.widthPercentageDP(5f)
                )
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color("#2C2C2C".toColorInt())
            ),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Max",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun ColumnScope.AmountInputField(
    key: Float,
    tokenAmount: String,
    usdAmount: String,
    onAmountChange: (amount: String) -> Unit
) {
    val configuration = LocalConfiguration.current

    var tokenText by remember(true) {
        mutableStateOf(TextFieldValue(tokenAmount))
    }

    val usdText by remember(key) {
        mutableStateOf(TextFieldValue(usdAmount))
    }

    val scroll by remember(true) {
        mutableStateOf(ScrollState(0))
    }

    Box(modifier = Modifier.weight(3f)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .horizontalScroll(state = scroll, enabled = true),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    BasicTextField(
                        enabled = true,
                        readOnly = false,
                        value = tokenText,
                        cursorBrush = SolidColor(Color.White),
                        textStyle = TextStyle(
                            fontSize = when {
                                tokenText.text.length > 9 -> 16.sp
                                tokenText.text.length > 6 -> 24.sp
                                tokenText.text.length > 3 -> 32.sp
                                else -> 52.sp
                            },
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            imeAction = ImeAction.Default,
                            keyboardType = KeyboardType.Number
                        ),
                        decorationBox = { innerTextField ->
                            Row {
                                if (tokenText.text.isEmpty()) {
                                    Text(
                                        text = "0",
                                        style = TextStyle(
                                            fontSize = when {
                                                tokenText.text.length > 9 -> 16.sp
                                                tokenText.text.length > 6 -> 24.sp
                                                tokenText.text.length > 3 -> 32.sp
                                                else -> 52.sp
                                            },
                                            color = Color("#A5A5A5".toColorInt()),
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Start
                                        )
                                    )
                                }

                                innerTextField()
                            }
                        },
                        keyboardActions = KeyboardActions(),
                        onValueChange = {
                            var newText = ""
                            var hasPeriod = false

                            it.copy().text
                                .trimStart { c -> c == '0' }
                                .forEachIndexed { i, c ->
                                    var newChars = when {
                                        c.isDecimal() -> "$c"
                                        (c == '.' || c == ',') && !hasPeriod -> "."
                                        else -> ""
                                    }

                                    if (i == 0 && newChars == ".") {
                                        hasPeriod = true
                                        newChars = "0."
                                    }

                                    newText += newChars
                            }

                            val parts = newText.split('.')
                            newText = parts.take(2).joinToString(".")

                            tokenText = TextFieldValue(
                                text = newText,
                                selection = TextRange(newText.length)
                            )

                            onAmountChange.invoke(newText)

                        },
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                    )

                    Spacer(modifier = Modifier.padding(start = 10.dp))

                    Text(
                        text = "SOL",
                        style = TextStyle(
                            fontSize = when {
                                tokenText.text.length > 9 -> 16.sp
                                tokenText.text.length > 6 -> 24.sp
                                tokenText.text.length > 3 -> 32.sp
                                else -> 52.sp
                            },
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                    )
                }

                Text(
                    text = "$${usdText.text}",
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = Color("#A5A5A5".toColorInt()),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = configuration.widthPercentageDP(8f))
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = Color("#2C2C2C".toColorInt()),
                            shape = RoundedCornerShape(25.dp)
                        )
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_swap_vert),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddressInputFieldSelectAmount(address: String, onAddressChanged: (String) -> Unit) {
    val configuration = LocalConfiguration.current

    var text by remember {
        mutableStateOf(address)
    }

    Row(
        modifier = Modifier.height(height = 50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color("#1A1A1A".toColorInt()),
                            Color("#1A1A1A".toColorInt())
                        )
                    )
                ),
        ) {
            TextField(
                enabled = false,
                readOnly = true,
                value = text,
                placeholder = {
                    Text(
                        text = "To: Name or address",
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color("#707070".toColorInt()),
                            fontWeight = FontWeight.Medium
                        ),
                    ) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color("#1A1A1A".toColorInt()),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    imeAction = ImeAction.Default
                ),
                keyboardActions = KeyboardActions(

                ),
                onValueChange = {
                    text = it
                    onAddressChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()

            )
        }
    }
}

@Composable
private fun NextButton(onClick: () -> Unit) {
    val configuration = LocalConfiguration.current

    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier
            .padding(
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f)
            )
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color("#0AB9EE".toColorInt())),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = "Next",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}