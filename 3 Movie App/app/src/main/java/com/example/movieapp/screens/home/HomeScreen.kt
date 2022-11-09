package com.example.movieapp.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.model.getMovies
import com.example.movieapp.model.movie
import com.example.movieapp.navigations.MovieScreens
import com.example.movieapp.widgets.MovieRow

@Composable
fun HomeScreen(navController:  NavController){
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Magenta,
            elevation = 5.dp)
        { Text(text = "Movies") } }) {
        MainContent(navController = navController)
    }
}

@Composable
fun MainContent(navController: NavController,
                movieList:List<movie> = getMovies()) {
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn{
            items(items = movieList){
                MovieRow(it){
                    navController.navigate(MovieScreens.DetailsScreen.name+"/$it")
                }
            }
        }
    }
}