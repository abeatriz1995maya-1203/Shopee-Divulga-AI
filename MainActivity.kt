package com.shopeedivulgaai

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ShopeeDivulgaScreen()
            }
        }
    }
}

@Composable
fun ShopeeDivulgaScreen() {
    val context = LocalContext.current

    var produto by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var detalhe by remember { mutableStateOf("") }
    var texto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Shopee Divulga AI",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text("Prepare suas divulgações em poucos segundos!")

        OutlinedTextField(
            value = produto,
            onValueChange = { produto = it },
            label = { Text("Nome do produto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = preco,
            onValueChange = { preco = it },
            label = { Text("Preço (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Link de afiliada Shopee") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = detalhe,
            onValueChange = { detalhe = it },
            label = { Text("Benefício ou detalhe do produto") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                texto = if (produto.isNotBlank() && link.isNotBlank()) {
                    buildString {
                        append("🛍️ OLHA ESSE ACHADINHO!\n\n")
                        append("✨ ${produto.trim()}\n")
                        if (preco.isNotBlank()) {
                            append("💰 Por apenas ${preco.trim()}\n")
                        }
                        if (detalhe.isNotBlank()) {
                            append("💖 ${detalhe.trim()}\n")
                        }
                        append("\n🛒 Confira aqui:\n${link.trim()}")
                        append("\n\n#Shopee #Achadinhos #Ofertas")
                    }
                } else {
                    "Preencha o nome do produto e o link de afiliada."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("GERAR DIVULGAÇÃO")
        }

        if (texto.isNotBlank()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier
