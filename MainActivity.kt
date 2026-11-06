package com.shopeedivulgaai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Pink = Color(0xFFE91E63)
private val DeepPink = Color(0xFFC2185B)
private val Ink = Color(0xFF191319)
private val Soft = Color(0xFFFFF2F6)
private val categories = listOf("Todas", "Beleza", "Casa e decoração", "Moda", "Eletrônicos", "Infantil", "Cozinha", "Pet", "Esporte e lazer")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { ShopeeApp() } }
}

@Composable fun ShopeeApp() {
    var page by remember { mutableStateOf("Início") }
    MaterialTheme(colorScheme = lightColorScheme(primary = Pink, secondary = DeepPink, background = Color.White)) {
        Scaffold(bottomBar = {
            NavigationBar(containerColor = Color.White) {
                val labels = listOf("Início", "Produtos", "Vídeos", "Configurações")
                val icons = listOf(Icons.Default.Home, Icons.Default.ShoppingBag, Icons.Default.VideoLibrary, Icons.Default.Settings)
                labels.forEachIndexed { i, label -> NavigationBarItem(selected = page == label, onClick = { page = label }, icon = { Icon(icons[i], null) }, label = { Text(label, fontSize = 10.sp) }) }
            }
        }) { padding ->
            when(page) {
                "Início" -> HomeScreen(Modifier.padding(padding)) { page = it }
                "Produtos" -> ProductsScreen(Modifier.padding(padding))
                "Vídeos" -> VideoScreen(Modifier.padding(padding))
                else -> SettingsScreen(Modifier.padding(padding))
            }
        }
    }
}

@Composable private fun HomeScreen(modifier: Modifier, navigate: (String)->Unit) {
    LazyColumn(modifier.fillMaxSize().background(Color(0xFFFFFAFC)).padding(20.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
        item { Text("Shopee Divulga AI", fontSize = 27.sp, fontWeight = FontWeight.ExtraBold, color = Ink); Text("Sua central de divulgação", color = Color.Gray) }
        item { Card(colors = CardDefaults.cardColors(containerColor = Ink), shape = RoundedCornerShape(22.dp)) { Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("PRONTA PARA DIVULGAR?", color = Color(0xFFFFB6CF), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text("Organize seus achadinhos e crie conteúdo", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Text("Cadastre um produto, gere um roteiro e prepare sua publicação.", color = Color(0xFFE9E1E5), fontSize = 13.sp)
            Button(onClick = { navigate("Produtos") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Pink)) { Icon(Icons.Default.AutoAwesome, null); Spacer(Modifier.width(8.dp)); Text("COMEÇAR") }
        } } }
        item { Text("Seu painel", fontSize = 19.sp, fontWeight = FontWeight.Bold, color = Ink) }
        item { Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) { Metric("Produtos", "0", Modifier.weight(1f)); Metric("Roteiros", "0", Modifier.weight(1f)); Metric("Publicações", "0", Modifier.weight(1f)) } }
        item { MenuCard("Encontrar produtos", "Cadastre itens e organize oportunidades", Icons.Default.Search) { navigate("Produtos") } }
        item { MenuCard("Estúdio de conteúdo", "Gere roteiro, legenda e hashtags", Icons.Default.MovieCreation) { navigate("Vídeos") } }
        item { MenuCard("Configurações", "Categorias e preferências de divulgação", Icons.Default.Settings) { navigate("Configurações") } }
        item { Text("Versão inicial: as funções de busca, geração de mídia e publicação automática ainda dependem de integrações externas autorizadas. Nunca informe sua senha da Shopee neste app.", fontSize = 11.sp, color = Color.Gray) }
    }
}

@Composable private fun Metric(title:String, value:String, modifier:Modifier) { Card(modifier, colors=CardDefaults.cardColors(containerColor=Soft), shape=RoundedCornerShape(15.dp)) { Column(Modifier.padding(12.dp)) { Text(value, fontSize=22.sp, color=DeepPink, fontWeight=FontWeight.Bold); Text(title, fontSize=11.sp, color=Ink) } } }
@Composable private fun MenuCard(title:String, subtitle:String, icon:androidx.compose.ui.graphics.vector.ImageVector, click:()->Unit) { Card(onClick=click, modifier=Modifier.fillMaxWidth(), colors=CardDefaults.cardColors(containerColor=Color.White), shape=RoundedCornerShape(16.dp)) { Row(Modifier.padding(15.dp), verticalAlignment=Alignment.CenterVertically) { Icon(icon,null,tint=Pink,modifier=Modifier.size(34.dp)); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text(title,fontWeight=FontWeight.Bold,color=Ink); Text(subtitle,fontSize=12.sp,color=Color.Gray) }; Icon(Icons.Default.ChevronRight,null,tint=Pink) } } }

@Composable private fun ProductsScreen(modifier:Modifier) {
    var name by remember { mutableStateOf("") }; var price by remember { mutableStateOf("") }; var category by remember { mutableStateOf("Todas") }; var expanded by remember { mutableStateOf(false) }; var saved by remember { mutableStateOf(false) }
    Column(modifier.fillMaxSize().background(Color(0xFFFFFAFC)).padding(20.dp), verticalArrangement=Arrangement.spacedBy(13.dp)) {
        Text("Meus produtos",fontSize=25.sp,fontWeight=FontWeight.Bold,color=Ink); Text("Cadastre um produto para preparar seu conteúdo.",color=Color.Gray)
        OutlinedTextField(name,{name=it},label={Text("Nome do produto")},modifier=Modifier.fillMaxWidth(),singleLine=true)
        OutlinedTextField(price,{price=it},label={Text("Preço (opcional)")},modifier=Modifier.fillMaxWidth(),singleLine=true)
        Box { OutlinedButton(onClick={expanded=true},modifier=Modifier.fillMaxWidth()) { Text("Categoria: $category"); Spacer(Modifier.weight(1f)); Icon(Icons.Default.ArrowDropDown,null) }; DropdownMenu(expanded,{expanded=false}) { categories.forEach { DropdownMenuItem(text={Text(it)},onClick={category=it;expanded=false}) } } }
        Button(onClick={saved=true},modifier=Modifier.fillMaxWidth(),colors=ButtonDefaults.buttonColors(containerColor=Pink),enabled=name.isNotBlank()) { Icon(Icons.Default.Save,null); Spacer(Modifier.width(8.dp)); Text("Salvar produto") }
        if(saved) Text("Produto preparado nesta sessão. A conexão com o catálogo Shopee ainda não está ativa.",color=DeepPink)
        Text("Dica: use fotos e vídeos próprios ou materiais cujo uso promocional você tenha autorização.",fontSize=12.sp,color=Color.Gray)
    }
}

@Composable private fun VideoScreen(modifier:Modifier) {
    var product by remember { mutableStateOf("") }; var style by remember { mutableStateOf("Oferta e achadinho") }; var duration by remember { mutableStateOf("IA escolhe") }; var script by remember { mutableStateOf("") }
    val context= LocalContext.current
    Column(modifier.fillMaxSize().background(Color(0xFFFFFAFC)).padding(20.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
        Text("Estúdio de conteúdo",fontSize=25.sp,fontWeight=FontWeight.Bold,color=Ink); Text("Monte um roteiro para gravar ou produzir seu vídeo.",color=Color.Gray)
        OutlinedTextField(product,{product=it},label={Text("Qual é o produto?")},modifier=Modifier.fillMaxWidth())
        Text("Estilo",fontWeight=FontWeight.Bold); Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) { listOf("Oferta e achadinho","Demonstração","Lista de benefícios").forEach { FilterChip(selected=style==it,onClick={style=it},label={Text(it,fontSize=11.sp)}) } }
        Text("Duração",fontWeight=FontWeight.Bold); Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) { listOf("IA escolhe","15 s","30 s","45 s").forEach { FilterChip(selected=duration==it,onClick={duration=it},label={Text(it)}) } }
        Button(onClick={ script="Olha esse achadinho: ${product.ifBlank { "[nome do produto]" }}! No estilo $style, destaque os benefícios reais, mostre detalhes e convide a pessoa a conferir o preço e as condições na Shopee. Duração sugerida: $duration. Confira as informações antes de publicar." },modifier=Modifier.fillMaxWidth(),colors=ButtonDefaults.buttonColors(containerColor=Pink)) { Icon(Icons.Default.AutoAwesome,null); Spacer(Modifier.width(8.dp)); Text("Gerar roteiro") }
        if(script.isNotBlank()) { Card(colors=CardDefaults.cardColors(containerColor=Soft)) { Text(script,Modifier.padding(14.dp),color=Ink) }; OutlinedButton(onClick={ val send=Intent(Intent.ACTION_SEND).apply { type="text/plain"; putExtra(Intent.EXTRA_TEXT,script) }; context.startActivity(Intent.createChooser(send,"Compartilhar roteiro")) },modifier=Modifier.fillMaxWidth()) { Icon(Icons.Default.Share,null); Spacer(Modifier.width(8.dp)); Text("Compartilhar roteiro") } }
        Text("Este estúdio gera texto de roteiro no aparelho; ainda não cria arquivo de vídeo, voz ou publicação automática.",fontSize=11.sp,color=Color.Gray)
    }
}

@Composable private fun SettingsScreen(modifier:Modifier) {
    var high by remember { mutableStateOf(true) }; var popular by remember { mutableStateOf(true) }; var all by remember { mutableStateOf(true) }; var female by remember { mutableStateOf(true) }
    LazyColumn(modifier.fillMaxSize().background(Color(0xFFFFFAFC)).padding(20.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
        item { Text("Configurações",fontSize=25.sp,fontWeight=FontWeight.Bold,color=Ink); Text("Preferências para organizar sua divulgação.",color=Color.Gray) }
        item { SettingSwitch("Todas as categorias", "Beleza, casa, moda, eletrônicos e mais",all){all=it} }
        item { SettingSwitch("Priorizar comissão alta", "Dar atenção a produtos com maior comissão informada",high){high=it} }
        item { SettingSwitch("Priorizar produtos populares", "Considerar avaliações e procura informadas",popular){popular=it} }
        item { SettingSwitch("Preferir narração feminina", "Preferência para roteiros futuros",female){female=it} }
        item { Card(colors=CardDefaults.cardColors(containerColor=Soft)) { Text("Segurança: não existe conexão de afiliada configurada nesta versão. Não coloque senha, cookie ou código de acesso da Shopee aqui. A publicação deve usar somente recursos oficiais autorizados.",Modifier.padding(15.dp),fontSize=12.sp,color=Ink) } }
    }
}
@Composable private fun SettingSwitch(title:String,subtitle:String,checked:Boolean,onChange:(Boolean)->Unit) { Card(colors=CardDefaults.cardColors(containerColor=Color.White),shape=RoundedCornerShape(15.dp)) { Row(Modifier.fillMaxWidth().padding(14.dp),verticalAlignment=Alignment.CenterVertically) { Column(Modifier.weight(1f)) { Text(title,fontWeight=FontWeight.Bold,color=Ink); Text(subtitle,fontSize=11.sp,color=Color.Gray) }; Switch(checked,onChange) } } }
