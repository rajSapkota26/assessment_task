package com.example.assessment.feature.dashboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.assessment.feature.dashboard.dto.IAndORecord
import com.example.assessment.feature.dashboard.event.DashEvent
import com.example.assessment.feature.dashboard.state.DashState
import com.example.assessment.ui.theme.white
import com.example.assessment.uiComponent.CommonCard
import com.example.assessment.uiComponent.DialogMessage
import com.example.assessment.uiComponent.LabelTextCompo
import com.example.assessment.utils.navigation.NavigationScreen
import com.example.samplesetting.component.ProgressScreen
import com.mahmoud.composecharts.linechart.LineChart
import com.mahmoud.composecharts.linechart.LineChartEntity
import timber.log.Timber

@Composable
fun DashBoardScreen(
    navController: NavHostController,
    state: DashState,
    onEvent: (DashEvent) -> Unit
) {
//    val context = LocalContext.current
    InitDependency(
    ) { onEvent(it) }
    ManageScreenState(
        uiState = state,
        navController = navController
    ) { onEvent(it) }
    MainContent(
        uiState = state,
    ) { onEvent(it) }
}

@Composable
private fun MainContent(
    uiState: DashState,
    onEvent: (DashEvent) -> Unit
) {
    Scaffold(
    ) { pad ->
        val a = pad
        CommonCard(
            color = MaterialTheme.colorScheme.surface,
            paddingTop = 0.dp,
            paddingBottom = 0.dp,
            paddingStart = 0.dp,
            paddingEnd = 0.dp,
        ) {
//            val lineChartData = listOf(
//                LineChartEntity(1500f, "A"),
//                LineChartEntity(2000f, "B"),
//                LineChartEntity(5000f, "C"),
//                LineChartEntity(3500f, "D"),
//                LineChartEntity(50000f, "E")
//            )
//            val verticalAxisValues =
//                listOf(500f, 1000f, 2000f, 5000f, 10000f, 25000f, 50000f, 100000f)

            Column(modifier = Modifier.fillMaxSize()) {
                DashTopBar(onAddClick = {
                    onEvent(DashEvent.OnTransactionAddDialogOpen(false))

                }, onSettingClick = {
                    onEvent(DashEvent.OnSettingDialogOpen)

                }, state = uiState)
//                LineChart(
//                    lineChartData = lineChartData,
//                    verticalAxisValues = verticalAxisValues
//                )
                Column(modifier = Modifier.padding(8.dp)) {
                    CategoryCompo(state = uiState)
                    TransactionList(onItemClick = { data ->
                        onEvent(DashEvent.OnTransactionDetailDialogOpen(data))
                    }, state = uiState)
                }
            }
        }
    }
}

@Composable
fun TransactionList(onItemClick: (IAndORecord) -> Unit, state: DashState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
    ) {
        LabelTextCompo(title = "Recent Transactions", fontSize = 18.sp)

    }
    if (state.allRecords.isNullOrEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(white)
        ) {
            Text(
                "There is no record \n please add new one",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    } else {
        AllRecords(state.allRecords) {
            onItemClick(it)
        }
    }

}

@Composable
fun AllRecords(allRecords: List<IAndORecord>, onClick: (IAndORecord) -> Unit) {
    LazyColumn {
        items(allRecords) {
            Row(
                modifier = Modifier
                    .clickable {
                        onClick(it)
                    }
                    .fillMaxWidth(1f)
                    .background(if (it.transactionType == "Expense") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondaryContainer)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (it.transactionType == "Expense") Icons.Filled.ArrowCircleDown else Icons.Filled.ArrowCircleUp,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp),
                    tint = if (it.transactionType == "Expense") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(end = 4.dp)
                ) {
                    Text(
                        it.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(0.6f),
                    )
                    Text(
                        it.tag,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(0.6f),
                    )
                }
                LabelTextCompo(title = "${it.amount}")

            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }

}


@Composable
fun CategoryCompo(state: DashState) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.secondaryContainer,
                    RoundedCornerShape(4.dp)
                )
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Income",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    textAlign = TextAlign.Center
                )
                Text(
                    "RS: ${state.totalIncome ?: 0}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Icon(
                imageVector = Icons.Filled.ArrowCircleUp,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 4.dp, top = 4.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(
            modifier = Modifier
                .height(4.dp)
                .width(4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .background(MaterialTheme.colorScheme.secondary)
                .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(4.dp))

        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Expenses",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    textAlign = TextAlign.Center
                )
                Text(
                    "RS: ${state.totalExpense ?: 0}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Icon(
                imageVector = Icons.Filled.ArrowCircleDown,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 4.dp, top = 4.dp),
                tint = MaterialTheme.colorScheme.error

            )
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashTopBar(onAddClick: () -> Unit, onSettingClick: () -> Unit, state: DashState) {
    TopAppBar(
        title = {
            Row {
                Text(
                    "Balance ",
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                )
                Text(
                    "Rs:${state.totalBalance ?: 0}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                )
            }


        },
        actions = {
            IconButton(onClick = { onAddClick() }) {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            IconButton(onClick = { onSettingClick() }) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "setting",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )


    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun ManageScreenState(
    navController: NavHostController,
    uiState: DashState,
    onEvent: (DashEvent) -> Unit
) {
    if (uiState.isTransactionAddAndEditDialogShow) {
        AddScreen(
            isDialogOpen = true,
            onDialogDismiss = {
                onEvent(DashEvent.OnTransactionAddDialogCLose)
            },
            isDataEdit = uiState.isTransactionEdit,
            onSubmit = { data ->
                onEvent(DashEvent.OnTransactionDetailAdd(data, uiState.isTransactionEdit))
            },
            data = uiState.editData,
        )
    }
    if (uiState.isTransactionDetailShown) {
        TransactionDetail(
            isDialogOpen = true,
            onDialogDismiss = {
                onEvent(DashEvent.OnTransactionDetailDialogClose)
            },
            data = uiState.selectedData!!,
            onDelete = { data ->
                onEvent(DashEvent.OnTransactionDetailDelete(data))
            },
            onEdit = { data ->

                onEvent(DashEvent.OnTransactionAddDialogOpen(true, data))
            },
        )
    }
    if (uiState.isSettingDialogShown) {
        SettingScreen(
            isDialogOpen = true,
            onDialogDismiss = {
                onEvent(DashEvent.OnSettingDialogClose)
            },
            onDelete = {
                onEvent(DashEvent.OnDeleteAccount)
            },
            onLogout = {
                onEvent(DashEvent.OnLogOut)
            },
            session = uiState.session
        )
    }


    if (uiState.isLoading) {
        ProgressScreen(
            navHostController = navController,
            message = "Sending Request"
        )
    }
    if (uiState.onLogoutSuccess) {
        navController.popBackStack()
        navController.navigate(NavigationScreen.loginScreen)
        navController.clearBackStack(NavigationScreen.homeScreen)
    }
    if (uiState.serverError != null) {
        val errorMessage = uiState.serverError
        Timber.v("show error message : $errorMessage")
        DialogMessage(
            isDialogMessageOpen = true,
            message = "$errorMessage",
            onClickOK = {
                onEvent(DashEvent.OnStopErrorDialogue)
            },
            onClickCancel = {
                onEvent(DashEvent.OnStopErrorDialogue)
            },
        )
    }

}

@Composable
private fun InitDependency(
    onEvent: (DashEvent) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onEvent(DashEvent.OnInit)
    }

}