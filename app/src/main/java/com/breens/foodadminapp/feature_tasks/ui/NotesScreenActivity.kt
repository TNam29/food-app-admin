package com.breens.foodadminapp.feature_tasks.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.breens.foodadminapp.common.SIDE_EFFECTS_KEY
import com.breens.foodadminapp.data.model.TabItems
import com.breens.foodadminapp.feature_tasks.events.BannerScreenUiEvent
import com.breens.foodadminapp.feature_tasks.events.CardsScreenUiEvent
import com.breens.foodadminapp.feature_tasks.events.CatesScreenUiEvent
import com.breens.foodadminapp.feature_tasks.events.ChatScreenUiEvent
import com.breens.foodadminapp.feature_tasks.events.OrderScreenUiEvent
import com.breens.foodadminapp.feature_tasks.events.SignInScreenUiEvent
import com.breens.foodadminapp.feature_tasks.events.TasksScreenUiEvent
import com.breens.foodadminapp.feature_tasks.side_effects.BannerScreenSideEffects
import com.breens.foodadminapp.feature_tasks.side_effects.CateScreenSideEffects
import com.breens.foodadminapp.feature_tasks.side_effects.ChatScreenSideEffects
import com.breens.foodadminapp.feature_tasks.side_effects.SignInScreenSideEffects
import com.breens.foodadminapp.feature_tasks.side_effects.TaskScreenSideEffects
import com.breens.foodadminapp.feature_tasks.ui.components.AddBannerDialogComponent
import com.breens.foodadminapp.feature_tasks.ui.components.AddCardDialogComponent
import com.breens.foodadminapp.feature_tasks.ui.components.AddCateDialogComponent
import com.breens.foodadminapp.feature_tasks.ui.components.AddTaskDialogComponent
import com.breens.foodadminapp.feature_tasks.ui.components.ChatComponent
import com.breens.foodadminapp.feature_tasks.ui.components.DetailCateComponent
import com.breens.foodadminapp.feature_tasks.ui.components.DetailTaskComponent
import com.breens.foodadminapp.feature_tasks.ui.components.EmptyComponent
import com.breens.foodadminapp.feature_tasks.ui.components.ListChatComponent
import com.breens.foodadminapp.feature_tasks.ui.components.LoadingComponent
import com.breens.foodadminapp.feature_tasks.ui.components.ManagerOrderComponent
import com.breens.foodadminapp.feature_tasks.ui.components.Profile
import com.breens.foodadminapp.feature_tasks.ui.components.UpdateCardDialogComponent
import com.breens.foodadminapp.feature_tasks.ui.components.UpdateCateDialogComponent
import com.breens.foodadminapp.feature_tasks.ui.components.UpdateTaskDialogComponent
import com.breens.foodadminapp.feature_tasks.viewmodel.BannerViewModel
import com.breens.foodadminapp.feature_tasks.viewmodel.CardsViewModel
import com.breens.foodadminapp.feature_tasks.viewmodel.CateViewModel
import com.breens.foodadminapp.feature_tasks.viewmodel.OrderViewModel
import com.breens.foodadminapp.feature_tasks.viewmodel.StoreUser
import com.breens.foodadminapp.feature_tasks.viewmodel.TasksViewModel
import com.breens.foodadminapp.theme.TodoChampTheme
import com.breens.orderfood.feature_tasks.ui.components.SignInComponent
import com.breens.orderfood.feature_tasks.ui.components.SignUpComponent
import com.breens.orderfood.feature_tasks.viewmodel.AccountViewModel
import com.breens.orderfood.feature_tasks.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint


import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NotesScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoChampTheme{
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
                    FoodScreenActivity()
                }



            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FoodScreenActivity() {
    val tasksViewModel: TasksViewModel = viewModel()
    val catesViewModel: CateViewModel = viewModel()
    val bannerViewModel: BannerViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()
    val cardsViewModel: CardsViewModel = viewModel()
    val chatViewModel: ChatViewModel = viewModel()
    val accountViewModel: AccountViewModel = viewModel()

    val effectFlow = tasksViewModel.effect
    val effectFlowCate = catesViewModel.effectCate
    val effectFlowBanner = bannerViewModel.effect1
    val effectFlowAccount = accountViewModel.effectAccount
    val effectMessage= chatViewModel.effectMessage
    val snackBarHostState = remember { SnackbarHostState() }

    val uiState = tasksViewModel.state.collectAsState().value
    val uiStateCate = catesViewModel.stateCate.collectAsState().value
    val uiStateBanner = bannerViewModel.state1.collectAsState().value
    val uiStateOrder = orderViewModel.stateOrder.collectAsState().value
    val uiStateCard = cardsViewModel.state.collectAsState().value
    val uiStateAccount = accountViewModel.stateAccount.collectAsState().value
    val uiStateMessage = chatViewModel.stateMessage.collectAsState().value

    val tabItems = listOf(
        TabItems(
            id = 1,
            title = "QL Danh mục",
        ),
        TabItems(
            id = 2,
            title = "QL Món ăn",
        ),
        TabItems(
            id = 3,
            title = "QL Đơn",
        ),
        TabItems(
            id = 4,
            title = "ChatBox",
        ),
        TabItems(
            id = 5,
            title = "Tôi",
        )
    )
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val pagerState = rememberPagerState{
        tabItems.size
    }
    val navController = rememberNavController()
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlowAccount.onEach { effectAccount ->
            when (effectAccount) {
                is SignInScreenSideEffects.NavigateToHome -> navController.navigate("TabComponent")
                is SignInScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effectAccount.messageAccount,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlow.onEach { effect ->
            when (effect) {
                is TaskScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlowBanner.onEach { effect1 ->
            when (effect1) {
                is BannerScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effect1.messageBanner,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlowCate.onEach { effectCate ->
            when (effectCate) {
                is CateScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effectCate.messageCate,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectMessage.onEach { effectMessage ->
            when (effectMessage) {
                is ChatScreenSideEffects.ShowSnackBarMessage -> {
                        snackBarHostState.showSnackbar(
                            message = effectMessage.messageChat,
                            duration = SnackbarDuration.Short,
                            actionLabel = "DISMISS"
                        )
                }

            }
        }.collect()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) { index ->
            TodoChampTheme {
                if (uiStateCard.isShowAddCardDialog) {
                    AddCardDialogComponent(
                        uiState = uiStateCard,
                        uiStateCate = uiStateCate,
                        setCardImage = { image ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardImage(image = image),
                            )
                        },
                        setCardTitle = { title ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardTitle(title = title),
                            )
                        },
                        setCardBody = { body ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardBody(body = body),
                            )
                        },
                        setCardPrice = { price ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardPrice(price = price),
                            )
                        },
                        setCardSales = { sales ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardSales(sale = sales),
                            )
                        },
                        setCategory = { cate ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCate(cate = cate),
                            )
                        },
                        saveCard = {


                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.AddCard(
                                    title = uiStateCard.currentTextFieldTitle,
                                    image = uiStateCard.imgUrl,
                                    price = uiStateCard.currentTextFieldPrice,
                                    body = uiStateCard.currentTextFieldBody,
                                    sale = uiStateCard.currentTextFieldSale,
                                    favorite = uiStateCard.currentTextFieldFavorite,
                                    views = uiStateCard.currentTextFieldViews,
                                    cate = uiStateCard.cate,
                                ),
                            )


                        },

                        closeDialog = {
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeAddCardDialogState(show = false),
                            )
                        },

                        )
                }
                if (uiState.isShowAddTaskDialog) {
                    AddTaskDialogComponent(
                        uiState = uiState,
                        setTaskImage = { image ->
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeTaskImage(image = image),
                            )
                        },
                        setTaskTitle = { title ->
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeTaskTitle(title = title),
                            )
                        },
                        setTaskBody = { body ->
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeTaskBody(body = body),
                            )
                        },
                        setTaskPrice = { price ->
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeTaskPrice(price = price),
                            )
                        },

                        saveTask = {


                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.AddTask(
                                    title = uiState.currentTextFieldTitle,
                                    image = uiState.imgUrl,
                                    price = uiState.currentTextFieldPrice,
                                    body = uiState.currentTextFieldBody,
                                ),
                            )


                        },

                        closeDialog = {
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeAddTaskDialogState(show = false),
                            )
                        },

                        )
                }
                if (uiStateBanner.isShowAddBannerDialog) {
                    AddBannerDialogComponent(
                        uiStateBanner = uiStateBanner,
                        setBannerImage = { image ->
                            bannerViewModel.sendEvent(
                                event = BannerScreenUiEvent.OnChangeBannerImage(imageBanner = image),
                            )
                        },
                        setBannerTitle = { title ->
                            bannerViewModel.sendEvent(
                                event = BannerScreenUiEvent.OnChangeBannerTitle(titleBanner = title),
                            )
                        },
                        saveBanner = {


                            bannerViewModel.sendEvent(
                                event = BannerScreenUiEvent.AddBanner(
                                    titleBanner = uiStateBanner.currentTextFieldTitleBanner,
                                    imageBanner = uiStateBanner.imgUrlBanner,
                                ),
                            )


                        },

                        closeDialog = {
                            bannerViewModel.sendEvent(
                                event = BannerScreenUiEvent.OnChangeAddBannerDialogState(show = false),
                            )
                        },

                        )
                }
                if (uiStateCate.isShowAddCateDialog) {
                    AddCateDialogComponent(
                        uiStateCate = uiStateCate,
                        setCateImage = { image->
                            catesViewModel.sendEvent(
                                event = CatesScreenUiEvent.OnChangeCateImage(imageCate = image),
                            )
                        },
                        setCateTitle = { title ->
                            catesViewModel.sendEvent(
                                event = CatesScreenUiEvent.OnChangeCateTitle(titleCate = title),
                            )
                        },
                        saveCate = {
                            catesViewModel.sendEvent(
                                event = CatesScreenUiEvent.AddCate(
                                    titleCate = uiStateCate.currentTextFieldTitleCate,
                                    imageCate = uiStateCate.imgUrlCate,
                                ),
                            )
                        },
                        closeDialog = {
                            catesViewModel.sendEvent(
                                event = CatesScreenUiEvent.OnChangeAddCateDialogState(show = false),
                            )
                        },
                    )
                }
                if (uiState.isShowUpdateTaskDialog) {
                    UpdateTaskDialogComponent(
                        uiState = uiState,
                        setTaskTitle = { title ->
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeTaskTitle(title = title),
                            )
                        },
                        setTaskBody = { body ->
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeTaskBody(body = body),
                            )
                        },
                        setTaskImage = { image ->
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeTaskImage(image = image),
                            )
                        },
                        setTaskPrice = { price ->
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeTaskPrice(price = price),
                            )
                        },
                        saveTask = {
                            tasksViewModel.sendEvent(event = TasksScreenUiEvent.UpdateNote)
                        },
                        closeDialog = {
                            tasksViewModel.sendEvent(
                                event = TasksScreenUiEvent.OnChangeUpdateTaskDialogState(show = false),
                            )
                        },
                        task = uiState.taskToBeUpdated,
                    )
                }
                if (uiStateCard.isShowUpdateCardDialog) {
                    UpdateCardDialogComponent(
                        uiState = uiStateCard,
                        setTitle = { title ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardTitle(title = title),
                            )
                        },
                        setBody = { body ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardBody(body = body),
                            )
                        },
                        setImage = { image ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardImage(image = image),
                            )
                        },
                        setPrice = { price ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardPrice(price = price),
                            )
                        },
                        setSale = { sale ->
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeCardSales(sale = sale),
                            )
                        },
                        saveCard = {
                            cardsViewModel.sendEvent(event = CardsScreenUiEvent.UpdateNote)
                        },
                        closeDialog = {
                            cardsViewModel.sendEvent(
                                event = CardsScreenUiEvent.OnChangeUpdateCardDialogState(show = false),
                            )
                        },
                        card = uiStateCard.cardToBeUpdated,
                    )
                }
                if (uiStateCate.isShowUpdateCateDialog) {
                    UpdateCateDialogComponent(
                        uiState = uiStateCate,
                        setTitle = { title ->
                            catesViewModel.sendEvent(
                                event = CatesScreenUiEvent.OnChangeCateTitle(titleCate = title),
                            )
                        },
                        setImage = { image ->
                            catesViewModel.sendEvent(
                                event = CatesScreenUiEvent.OnChangeCateImage(imageCate = image),
                            )
                        },

                        saveCate = {
                            catesViewModel.sendEvent(event = CatesScreenUiEvent.UpdateNote)
                        },
                        closeDialog = {
                            catesViewModel.sendEvent(
                                event = CatesScreenUiEvent.OnChangeUpdateCateDialogState(show = false),
                            )
                        },
                        cate = uiStateCate.cateToBeUpdated,
                    )
                }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackBarHostState)
                    },
                    containerColor = Color(0XFFFAFAFA),
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(
                            navController = navController,
                            startDestination = "SignInComponent",
                            ) {
                            composable("SignInComponent"){
                                var hasLoggedIn by remember { mutableStateOf(false) }
                                val context = LocalContext.current
                                val dataStore = StoreUser(context)
                                val savedEmail = dataStore.getEmail.collectAsState(initial = "")
                                val savedPassword = dataStore.getPassword.collectAsState(initial = "")
                                LaunchedEffect(savedEmail, savedPassword) {
                                    if (savedEmail.value!!.isNotEmpty() && savedPassword.value!!.isNotEmpty() && !hasLoggedIn) {
                                        hasLoggedIn = true // Đánh dấu đã thực hiện đăng nhập
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.OnChangeEmail(email = savedEmail.value!!)
                                        )
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.OnChangePassword(password = savedPassword.value!!)
                                        )
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.LoginUser
                                        )
                                    }
                                }
                                SignInComponent(
                                    uiStateAccount = uiStateAccount,
                                    navController = navController,
                                    setEmail = { email->
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.OnChangeEmail(email = email),
                                        )
                                    },
                                    setPassword = { password ->
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.OnChangePassword(password = password),
                                        )
                                    },
                                    saveAccount = {
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.LoginUser
                                        )
                                    },
                                )
                            }
                            composable("SignUpComponent"){
                                SignUpComponent(
                                    uiStateAccount = uiStateAccount,
                                    navController = navController,
                                    setFirstname = { firstname->
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.OnChangeFirstname(firstname = firstname),
                                        )
                                    },
                                    setLastname = { lastname->
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.OnChangeLastname(lastname = lastname),
                                        )
                                    },
                                    setEmail = { email->
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.OnChangeEmail(email = email),
                                        )
                                    },
                                    setPassword = { password ->
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.OnChangePassword(password = password),
                                        )
                                    },
                                    saveAccount = {
                                        accountViewModel.sendEvent(
                                            event = SignInScreenUiEvent.RegisterUser(
                                                firstname = uiStateAccount.currentFirstname,
                                                lastname = uiStateAccount.currentLastName,
                                                email = uiStateAccount.currentEmail,
                                                password = uiStateAccount.currentPassword,
                                            ),
                                        )
                                    },
                                )
                            }
                            composable("TabComponent") {
                                when (tabItems[index].id) {
                                    1 ->
                                        Scaffold(
                                            floatingActionButton = {
                                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                                    ExtendedFloatingActionButton(
                                                        icon = {
                                                            Icon(
                                                                Icons.Rounded.AddCircle,
                                                                contentDescription = "Thêm Banner",
                                                                tint = Color.White,
                                                            )
                                                        },
                                                        text = {
                                                            Text(
                                                                text = "Thêm Banner",
                                                                color = Color.White,
                                                            )
                                                        },
                                                        onClick = {
                                                            bannerViewModel.sendEvent(
                                                                event = BannerScreenUiEvent.OnChangeAddBannerDialogState(show = true),
                                                            )
                                                        },
                                                        modifier = Modifier.padding(horizontal = 12.dp),
                                                        containerColor = Color.Black,
                                                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                                                    )
                                                    ExtendedFloatingActionButton(
                                                        icon = {
                                                            Icon(
                                                                Icons.Rounded.AddCircle,
                                                                contentDescription = "Thêm danh mục",
                                                                tint = Color.White,
                                                            )
                                                        },
                                                        text = {
                                                            Text(
                                                                text = "Thêm danh mục",
                                                                color = Color.White,
                                                            )
                                                        },
                                                        onClick = {
                                                            catesViewModel.sendEvent(
                                                                event = CatesScreenUiEvent.OnChangeAddCateDialogState(show = true),
                                                            )
                                                        },
                                                        modifier = Modifier.padding(horizontal = 12.dp),
                                                        containerColor = Color.Black,
                                                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                                                    )
                                                }
                                            },
                                            containerColor = Color(0XFFFAFAFA),
                                        ) {
                                            Box(modifier = Modifier.padding(it)) {
                                                when {
                                                    uiStateCate.isLoadingCate -> {
                                                        LoadingComponent()
                                                    }
                                                    !uiStateCate.isLoadingCate && uiStateCate.cates.isNotEmpty() -> {

                                                                DetailCateComponent(
                                                                    uiStateBanner = uiStateBanner,
                                                                    uiStateCate = uiStateCate,
                                                                    updateCate = { cateToBeUpdated ->
                                                                        catesViewModel.sendEvent(
                                                                            CatesScreenUiEvent.OnChangeUpdateCateDialogState(
                                                                                show = true,
                                                                            ),
                                                                        )

                                                                        catesViewModel.sendEvent(
                                                                            event = CatesScreenUiEvent.SetCateToBeUpdated(
                                                                                cateToBeUpdated = cateToBeUpdated,
                                                                            ),
                                                                        )
                                                                    },
                                                                    deleteCate = { cateId ->
                                                                        Log.d("TASK_ID: ", cateId)
                                                                        catesViewModel.sendEvent(
                                                                            event = CatesScreenUiEvent.DeleteNote(
                                                                                cateId = cateId
                                                                            ),
                                                                        )
                                                                    },
                                                                )
                                                    }
                                                    !uiStateCate.isLoadingCate && uiStateCate.cates.isEmpty() -> {
                                                        EmptyComponent()
                                                    }
                                                }
                                            }
                                        }
                                    2 ->
                                        Scaffold(
                                            floatingActionButton = {
                                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                                    ExtendedFloatingActionButton(
                                                        icon = {
                                                            Icon(
                                                                Icons.Rounded.AddCircle,
                                                                contentDescription = "Thêm món 1",
                                                                tint = Color.White,
                                                            )
                                                        },
                                                        text = {
                                                            Text(
                                                                text = "Thêm món 1",
                                                                color = Color.White,
                                                            )
                                                        },
                                                        onClick = {
                                                            tasksViewModel.sendEvent(
                                                                event = TasksScreenUiEvent.OnChangeAddTaskDialogState(show = true),
                                                            )
                                                        },
                                                        modifier = Modifier.padding(horizontal = 12.dp),
                                                        containerColor = Color.Black,
                                                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                                                    )
                                                    ExtendedFloatingActionButton(
                                                        icon = {
                                                            Icon(
                                                                Icons.Rounded.AddCircle,
                                                                contentDescription = "Thêm món 2",
                                                                tint = Color.White,
                                                            )
                                                        },
                                                        text = {
                                                            Text(
                                                                text = "Thêm món 2",
                                                                color = Color.White,
                                                            )
                                                        },
                                                        onClick = {
                                                            cardsViewModel.sendEvent(
                                                                event = CardsScreenUiEvent.OnChangeAddCardDialogState(show = true),
                                                            )
                                                        },
                                                        modifier = Modifier.padding(horizontal = 12.dp),
                                                        containerColor = Color.Black,
                                                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                                                    )
                                                }
                                            },
                                            containerColor = Color(0XFFFAFAFA),
                                        ) {
                                            Box(modifier = Modifier.padding(it)) {
                                                when {
                                                    uiState.isLoading -> {
                                                        LoadingComponent()
                                                    }
                                                    !uiState.isLoading && uiState.tasks.isNotEmpty() -> {
                                                        DetailTaskComponent(
                                                            uiState = uiState,
                                                            uiStateCard = uiStateCard,
                                                            deleteTask = { taskId ->
                                                                Log.d("TASK_ID: ", taskId)
                                                                tasksViewModel.sendEvent(
                                                                    event = TasksScreenUiEvent.DeleteNote(
                                                                        taskId = taskId
                                                                    ),
                                                                )
                                                            },
                                                            updateTask = { taskToBeUpdated ->
                                                                tasksViewModel.sendEvent(
                                                                    TasksScreenUiEvent.OnChangeUpdateTaskDialogState(
                                                                        show = true,
                                                                    ),
                                                                )

                                                                tasksViewModel.sendEvent(
                                                                    event = TasksScreenUiEvent.SetTaskToBeUpdated(
                                                                        taskToBeUpdated = taskToBeUpdated,
                                                                    ),
                                                                )
                                                            },
                                                            deleteCard = { cardId ->
                                                                Log.d("CARD_ID: ", cardId)
                                                                cardsViewModel.sendEvent(
                                                                    event = CardsScreenUiEvent.DeleteNote(
                                                                        cardId = cardId
                                                                    ),
                                                                )
                                                            },
                                                            updateCard = { cardToBeUpdated ->
                                                                cardsViewModel.sendEvent(
                                                                    CardsScreenUiEvent.OnChangeUpdateCardDialogState(
                                                                        show = true,
                                                                    ),
                                                                )

                                                                cardsViewModel.sendEvent(
                                                                    event = CardsScreenUiEvent.SetCardToBeUpdated(
                                                                        cardToBeUpdated = cardToBeUpdated,
                                                                    ),
                                                                )
                                                            },


                                                        )
                                                    }
                                                    !uiState.isLoading && uiState.tasks.isEmpty() -> {
                                                        EmptyComponent()
                                                    }

                                                }
                                            }
                                        }
                                    3 ->
                                        ManagerOrderComponent(
                                            uiStateOrder = uiStateOrder,
                                            setOrderStatus = { status ->
                                                orderViewModel.sendEvent(
                                                    event = OrderScreenUiEvent.OnChangeOrderStatus(status = status),
                                                )
                                            },
                                            updateStatus = { statusToBeUpdated ->
                                                orderViewModel.sendEvent(
                                                    event = OrderScreenUiEvent.SetStatusToBeUpdated(
                                                        statusToBeUpdated = statusToBeUpdated,
                                                    ),
                                                )
                                            },

                                            saveStatus = {
                                                orderViewModel.sendEvent(event = OrderScreenUiEvent.UpdateNote)
                                            },
                                        )
                                    4 ->
                                        when {
                                            uiStateMessage.isLoading -> {
                                                LoadingComponent()
                                            }
                                            !uiStateMessage.isLoading -> {
                                                ListChatComponent(
                                                    navControllerNotes = navController,
                                                    uiStateAccount = uiStateAccount,
                                                    uiState = uiStateMessage
                                                )

                                            }


                                        }

                                    5 ->
                                        when {
                                            uiStateAccount.isLoading -> {
                                                LoadingComponent()
                                            }

                                            !uiStateAccount.isLoading  -> {

                                                Profile(
                                                    navController = navController,
                                                    uiStateAccount = uiStateAccount,
                                                    logoutUser = {
                                                        accountViewModel.sendEvent(
                                                            event = SignInScreenUiEvent.LogoutUser
                                                        )
                                                    }
                                                )

                                            }
                                        }
                                }
                            }
                            composable("ChatComponent/{Id}"){backStackEntry->
                                var check by remember {
                                    mutableStateOf(false)
                                }
                                val id = backStackEntry.arguments?.getString("Id")

                                if(id!=null){
                                    LaunchedEffect(id) {
                                        chatViewModel.sendEvent(ChatScreenUiEvent.GetMessage(id))
                                        check = true
                                    }

                                    if(check==true){
                                        ChatComponent(
                                            navController = navController,
                                            uiState = uiStateMessage,
                                            uiStateAccount = uiStateAccount,
                                            setMessage = {message ->
                                                chatViewModel.sendEvent(
                                                    event = ChatScreenUiEvent.OnChangeMessage(message = message),
                                                )
                                            },
                                            direction = {direction ->
                                                chatViewModel.sendEvent(
                                                    event = ChatScreenUiEvent.OnChangeDirection(direction = direction),
                                                )
                                            },
                                            senderID = {senderID ->
                                                chatViewModel.sendEvent(
                                                    event = ChatScreenUiEvent.OnChangeSenderID(senderID = senderID),
                                                )
                                            },
                                            senderMessage = {
                                                chatViewModel.sendEvent(
                                                    event = ChatScreenUiEvent.AddMessage(
                                                        message = uiStateMessage.currentMessage,
                                                        senderID = uiStateMessage.currentSenderID,
                                                        direction = uiStateMessage.direction

                                                    ),
                                                )
                                            }
                                        )
                                        
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, item ->
                androidx.compose.material3.Tab(
                    modifier = Modifier
                        .background(color = Color.White)
                        .height(63.dp),
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            color = if (index == selectedTabIndex) {
                                Color.Red
                            } else {
                                Color.Gray
                            }
                        )
                    },

                )
            }
        }
    }
}





