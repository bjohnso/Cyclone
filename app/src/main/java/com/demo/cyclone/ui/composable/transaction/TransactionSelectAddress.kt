package com.demo.cyclone.ui.composable.transaction

import android.content.pm.ActivityInfo
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.demo.cyclone.R
import com.demo.cyclone.extensions.ConfigurationExtensions.heightPercentageDP
import com.demo.cyclone.extensions.ConfigurationExtensions.widthPercentageDP
import com.demo.cyclone.extensions.ContextExtensions.activity
import com.demo.cyclone.interfaces.NavigatorInterface
import com.demo.cyclone.ui.composable.shared.LockScreenOrientation
import com.demo.cyclone.ui.composable.shared.SystemUi
import com.demo.cyclone.ui.models.AddressListModel
import com.demo.cyclone.ui.models.AddressModel
import com.demo.cyclone.ui.models.HeaderModel
import com.demo.cyclone.ui.navigation.Screen
import com.demo.cyclone.usecase.CreateTransactionUseCase
import com.demo.cyclone.viewmodel.TransactionSelectAddressViewModel
import kotlinx.coroutines.launch

@Composable
fun TransactionSelectAddress(
    window: Window,
    navigatorInterface: NavigatorInterface? = null
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val coroutine = rememberCoroutineScope()

    val viewModel = ViewModelProvider(
        LocalContext.current.activity() as ViewModelStoreOwner
    )[TransactionSelectAddressViewModel::class.java]

    SystemUi(
        window = window,
        statusBarColor = "#222222".toColorInt(),
        navigationBarColor = "#222222".toColorInt()
    )

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(topBar = { SelectAddressAppBar() }) {
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
                AddressInputField(viewModel.uiState.recipientAddress) { address ->
                    viewModel.updateAddress(address)
                }

                Spacer(modifier = Modifier.padding(bottom = 5.dp))

                val testClipboardList = AddressListModel(
                    addresses = listOf(
                        AddressModel(
                            address = "DjPi1LtwrXJMAh2AUvuUMajCpMJEKg8N1J8fU4L2Xr9D",
                            name = "Paste from clipboard",
                            type = AddressListModel.Type.CLIPBOARD
                        )
                    ),
                    listHeader = null,
                    listType = AddressListModel.Type.CLIPBOARD
                )

                val testAddressBookList = AddressListModel(
                    addresses = listOf(
                        AddressModel(
                            address = "DjPi1LtwrXJMAh2AUvuUMajCpMJEKg8N1J8fU4L2Xr9D",
                            name = "Wallet 1",
                            type = AddressListModel.Type.ADDRESS_BOOK
                        ),
                        AddressModel(
                            address = "DjPi1LtwrXJMAh2AUvuUMajCpMJEKg8N1J8fU4L2Xr9D",
                            name = "Wallet 2",
                            type = AddressListModel.Type.ADDRESS_BOOK
                        ),
                        AddressModel(
                            address = "DjPi1LtwrXJMAh2AUvuUMajCpMJEKg8N1J8fU4L2Xr9D",
                            name = "Wallet 3",
                            type = AddressListModel.Type.ADDRESS_BOOK
                        ),
                        AddressModel(
                            address = "DjPi1LtwrXJMAh2AUvuUMajCpMJEKg8N1J8fU4L2Xr9D",
                            name = "Wallet 4",
                            type = AddressListModel.Type.ADDRESS_BOOK
                        )
                    ),
                    listHeader = HeaderModel(headerTitle = "Address Book"),
                    listType = AddressListModel.Type.CLIPBOARD
                )

                AddressList(addressLists = listOf(
                    testClipboardList,
                    testAddressBookList
                )) { address ->
                    viewModel.updateAddress(address.address)
                }

                NextButton {
                    coroutine.launch {
                        val success = CreateTransactionUseCase.createTransaction(
                            context = context,
                            viewModel.uiState.recipientAddress
                        )

                        if (success) {
                            navigatorInterface?.navigate(Screen.TransactionSelectAmountScreen.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectAddressAppBar() {
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
            text = "Send SOL",
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
fun ColumnScope.AddressList(addressLists: List<AddressListModel>, onClick: (addressModel: AddressModel) -> Unit) {
    val configuration = LocalConfiguration.current

    val items = addressLists.flatMap {
        val list = mutableListOf<Any>()

        if (it.listHeader != null) {
            list.add(it.listHeader)
        }

        if (!it.addresses.isNullOrEmpty()) {
            list.addAll(it.addresses)
        }

        list
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            .padding(
                start = configuration.widthPercentageDP(5f),
                end = configuration.widthPercentageDP(5f),
                bottom = configuration.heightPercentageDP(2f)
            )
    ) {
        items(items = items) { model ->
            when (model) {
                is HeaderModel -> HeaderItem(model)
                is AddressModel -> {
                    when (model.type) {
                        AddressListModel.Type.CLIPBOARD -> ClipboardItem(model) {
                            onClick.invoke(it)
                        }
                        AddressListModel.Type.ADDRESS_BOOK -> AddressItem(model) {
                            onClick.invoke(it)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 5.dp))
        }
    }
}

@Composable
fun HeaderItem(header: HeaderModel) {
    Text(
        text = header.headerTitle,
        textAlign = TextAlign.Start,
        style = TextStyle(
            fontSize = 15.sp,
            color = Color("#A5A5A5".toColorInt()),
            fontWeight = FontWeight.Medium
        ),
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClipboardItem(address: AddressModel, onClick: (address: AddressModel) -> Unit) {
    Surface(
        elevation = 10.dp,
        onClick = { onClick.invoke(address) }
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color("#272727".toColorInt()),
                            Color("#272727".toColorInt())
                        )
                    )
                )
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp,
                    )
                )
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {

            Box(modifier = Modifier
                .size(50.dp)
                .background(
                    color = Color("#AC22E7".toColorInt()),
                    shape = RoundedCornerShape(25.dp)
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(Modifier.padding(start = 20.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = address.name,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color("#A5A5A5".toColorInt()),
                        fontWeight = FontWeight.Medium
                    ),
                )

                Text(
                    text = address.address,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddressItem(address: AddressModel, onClick: (addressModel: AddressModel) -> Unit) {
    Surface(
        elevation = 10.dp,
        onClick = { onClick.invoke(address) }
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color("#272727".toColorInt()),
                            Color("#272727".toColorInt())
                        )
                    )
                )
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp,
                    )
                )
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {


            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Color("#0AB9EE".toColorInt()),
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {
                Text(
                    text = "W1",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(Modifier.padding(start = 20.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = address.name,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                )

                val addressText = "${address.address.take(4)}...${address.address.takeLast(4)}"

                Text(
                    text = addressText,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color("#A5A5A5".toColorInt()),
                        fontWeight = FontWeight.Medium
                    ),
                )
            }
        }
    }
}

@Composable
fun AddressInputField(address: String, onAddressChanged: (String) -> Unit) {
    val configuration = LocalConfiguration.current

    var text by remember(address) {
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
                enabled = true,
                readOnly = false,
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