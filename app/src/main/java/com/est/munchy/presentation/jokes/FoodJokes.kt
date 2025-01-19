package com.est.munchy.presentation.jokes

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.utils.AppConstants
import com.est.munchy.utils.NetworkResponse
import com.est.munchy.viewModels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodJokeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    var foodJoke by remember { mutableStateOf("No Food Joke") }
//    val foodJokeState = viewModel.uiState.collectAsState().value
//
//    // Fetch food joke on first composition
//    LaunchedEffect(Unit) {
//        viewModel.getFoodJoke(AppConstants.API_KEY)
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Food Joke") },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            val shareIntent = Intent().apply {
//                                action = Intent.ACTION_SEND
//                                putExtra(Intent.EXTRA_TEXT, foodJoke)
//                                type = "text/plain"
//                            }
//                            context.startActivity(Intent.createChooser(shareIntent, "Share Joke"))
//                        }
//                    ) {
//                        Icon(Icons.Default.Share, "Share Joke")
//                    }
//                }
//            )
//        },
//        snackbarHost = { SnackbarHost(snackbarHostState) }
//    ) { paddingValues ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            when (foodJokeState) {
//                else -> {
//                    LoadingState()
//                }
//            }
//
//            // Pull to refresh
//            val refreshState = rememberPullToRefreshState(
//                refreshing = foodJokeState is NetworkResponse.Loading,
//                onRefresh = {
//                    scope.launch {
//                        viewModel.getFoodJoke(AppConstants.API_KEY)
//                    }
//                }
//            )
//
//            Box(
//                modifier = Modifier.pullToRefresh(refreshState)
//            ) {
//                PullRefreshIndicato(
//                    refreshing = foodJokeState is NetworkResponse.Loading,
//                    state = refreshState,
//                    modifier = Modifier.align(Alignment.TopCenter)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun LoadingState() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        CircularProgressIndicator()
//    }
//}
//
//@Composable
//private fun JokeContent(joke: String) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.MoreVert,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .size(48.dp)
//                        .padding(bottom = 16.dp),
//                    tint = MaterialTheme.colorScheme.primary
//                )
//
//                Text(
//                    text = joke,
//                    style = MaterialTheme.typography.bodyLarge,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//        }
//    }
}