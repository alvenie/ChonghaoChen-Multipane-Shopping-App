package com.example.chonghaochen_multipane_shopping_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingApp()
        }
    }
}

data class Product(val name: String, val price: String, val description: String)

@Composable
fun ShoppingApp() {
    val products = listOf(
        Product("Stanley Quencher Tumbler", "$45", "40 oz insulated tumbler that keeps drinks cold for hours."),
        Product("Pickleball Paddle Set", "$80", "Set includes 2 paddles, 4 balls, and a carrying case."),
        Product("Automatic Pet Feeder", "$120", "Programmable feeder for cats and dogs with portion control."),
        Product("Ergonomic Wireless Mouse", "$60", "Vertical mouse design for reduced wrist strain."),
        Product("Crochet Bucket Hat", "$25", "Trendy handmade hat in various colors."),
        Product("Niacinamide Body Lotion", "$35", "Moisturizing lotion with 5% niacinamide for skin brightening.")
    )

    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(modifier = Modifier.fillMaxSize()) {
                ProductList(
                    products = products,
                    onProductSelected = { selectedProduct = it },
                    modifier = Modifier.weight(1f)
                )
                ProductDetails(
                    product = selectedProduct,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        else -> {
            if (selectedProduct == null) {
                ProductList(
                    products = products,
                    onProductSelected = { selectedProduct = it },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                ProductDetails(
                    product = selectedProduct,
                    onBackPressed = { selectedProduct = null },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductSelected: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(products) { product ->
            Text(
                text = product.name,
                fontSize = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductSelected(product) }
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ProductDetails(
    product: Product?,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (product == null) {
            Text(text = "Select a product to view details.", fontSize = 36.sp)
        } else {
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Button(onClick = onBackPressed) {
                    Text("Back to List")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Text(text = "Name: ${product.name}", fontSize = 36.sp)
            Text(text = "Price: ${product.price}", fontSize = 36.sp)
            Text(text = "Description: ${product.description}", fontSize = 36.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingAppPreview() {
    ShoppingApp()
}